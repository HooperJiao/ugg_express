package com.hooper.ugg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooper.ugg.entity.Payslip;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.mapper.PayslipMapper;
import com.hooper.ugg.mapper.UserMapper;
import com.hooper.ugg.service.IPayslipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  服务实现类
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

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PayslipMapper payslipMapper;

    @Override
    public Payslip parseAndSaveFromPDF(String pdfPath) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(pythonCommand, pythonScriptPath, pdfPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        String line;
        String jsonLine = null;
        while ((line = reader.readLine()) != null) {
            System.out.println("line =====>" + line);
            if (line.startsWith("{") && line.endsWith("}")) {
                jsonLine = line;
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("未从 Python 输出中找到 JSON 数据！");
        }

        Payslip Payslip = objectMapper.readValue(jsonLine, Payslip.class);
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getRealName, Payslip.getRealName())
        );
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "请先注册用户信息");
        }
        Payslip.setUggUserId(user.getUserId());
        Payslip.setFileName(Payslip.getPayPeriodStart() + " - " + Payslip.getPayPeriodEnd());
        Payslip.setFilePath("");

        // 插入数据库
        //PayslipMapper.insert(Payslip);

        return Payslip;
    }
}
