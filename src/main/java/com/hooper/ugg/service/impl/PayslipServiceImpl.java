package com.hooper.ugg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooper.ugg.common.ResponseCode;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Payslip;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.mapper.PayslipMapper;
import com.hooper.ugg.mapper.UserMapper;
import com.hooper.ugg.service.IPayslipService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Hooper
 * @since 2025-09-24
 */
@Service
public class PayslipServiceImpl extends ServiceImpl<PayslipMapper, Payslip> implements IPayslipService {
    @Value("${python.command}")
    private String pythonCommand;

    @Value("${python.script-path}")
    private String pythonScriptPath;

    @Value("${payslip.save-dir}")
    private String payslipSaveDir;

    @Value("${payslip.file-extension}")
    private String fileExtension;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PayslipMapper payslipMapper;

    /**
     *
     * ① 查询工资单归属用户
     * ② 存储工资单信息
     * ③ 存储工资单文件
     * ④ 填充文件路径、文件序号
     *
     */
    @Override
    public Result<?> parseAndSaveFromPDF(String pdfPath) throws Exception {
        String jsonLine = getJsonLine(pdfPath);

        Payslip payslip = objectMapper.readValue(jsonLine, Payslip.class);
        // 判断文件是否已上传存储
        LambdaQueryWrapper<Payslip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payslip::getRealName, payslip.getRealName())
                .eq(Payslip::getPayPeriodStart, payslip.getPayPeriodStart())
                .eq(Payslip::getPayPeriodEnd, payslip.getPayPeriodEnd());

        Payslip existing = payslipMapper.selectOne(wrapper);
        if (existing != null) {
            return Result.fail(ResponseCode.ALREADY_UPLOADED);
        }

        //查询工资单对应用户信息
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getRealName, payslip.getRealName())
        );
        if (user == null) {
            return Result.fail(ResponseCode.USER_NOT_REGISTERED);
        }

        // 填充payslip_number
        assignPayslipNumberWithReordering(payslip);

        // 填充工资表属性
        payslip.setUggUserId(user.getUserId());

        // 文件名
        String fileName = buildPayslipFileName(user.getRealName(), payslip.getPayPeriodStart(), payslip.getPayPeriodEnd());
        payslip.setFileName(fileName);

        //保存文件
        File targetDir = new File(payslipSaveDir);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }


        // 构造目标路径：加后缀
        File finalFile = new File(payslipSaveDir, fileName + fileExtension);
        FileUtils.copyFile(new File(pdfPath), finalFile);

        // 存储相对路径
        payslip.setFilePath(fileName + fileExtension);

        // 插入数据库
        int insert = payslipMapper.insert(payslip);

        if (insert != 1) {
            return Result.fail(ResponseCode.INSERT_FAILED);
        }
        return Result.success(payslip);
    }

    private void assignPayslipNumberWithReordering(Payslip payslip) {
        // 1. 先查最后一条
        Payslip last = payslipMapper.selectOne(
                Wrappers.<Payslip>lambdaQuery()
                        .orderByDesc(Payslip::getPayPeriodStart)
                        .last("LIMIT 1")
        );

        // 2. 如果是追加
        if (last == null || payslip.getPayPeriodStart().isAfter(last.getPayPeriodStart())) {
            payslip.setPayslipNumber(last == null ? 1 : last.getPayslipNumber() + 1);
            return;
        }

        // 3. 否则查全表 + 重排
        List<Payslip> all = payslipMapper.selectList(
                Wrappers.<Payslip>lambdaQuery().orderByAsc(Payslip::getPayPeriodStart)
        );

        int insertIndex = 0;
        for (int i = 0; i < all.size(); i++) {
            if (!payslip.getPayPeriodStart().isAfter(all.get(i).getPayPeriodStart())) {
                break;
            }
            insertIndex++;
        }

        for (int i = all.size() - 1; i >= insertIndex; i--) {
            Payslip p = all.get(i);
            p.setPayslipNumber(p.getPayslipNumber() + 1);
            payslipMapper.updateById(p);
        }

        payslip.setPayslipNumber(insertIndex + 1);
    }

    public static String buildPayslipFileName(String name, LocalDate start, LocalDate end) {
        String year = String.valueOf(start.getYear());
        String startStr = start.format(DateTimeFormatter.ofPattern("MM-dd"));
        String endStr = end.format(DateTimeFormatter.ofPattern("MM-dd"));
        return String.format("[%s] [%s] %s ~ %s", name, year, startStr, endStr);
    }

    private String getJsonLine(String pdfPath) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(pythonCommand, pythonScriptPath, pdfPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        String line;
        String jsonLine = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("{") && line.endsWith("}")) {
                jsonLine = line;
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("未从 Python 输出中找到 JSON 数据！");
        }
        return jsonLine;
    }
}
