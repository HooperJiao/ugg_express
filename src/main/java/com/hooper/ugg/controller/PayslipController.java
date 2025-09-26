package com.hooper.ugg.controller;

import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Payslip;
import com.hooper.ugg.service.IPayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hooper
 * @since 2025-09-24
 */
@Controller
@RequestMapping("/ugg/payslip")
public class PayslipController {

    public PayslipController() {
        System.out.println(">>>> PayslipController 加载成功");
    }

    @Autowired
    private IPayslipService payslipService;

    @PostMapping("/upload")
    @ResponseBody
    public Result<?> uploadPayslip(@RequestParam("file") MultipartFile file) {
        System.out.println("成功访问uploadPayslip方法");
        // 步骤 1：保存到临时目录
        File tempFile = null;
        try {
            tempFile = File.createTempFile("Payslip_", ".pdf");
            file.transferTo(tempFile);  // 保存上传文件到临时路径

            // 步骤 2：Java 调用 Python OCR/PDF 解析脚本
            Payslip payslip = payslipService.parseAndSaveFromPDF(tempFile.getAbsolutePath());

            // 打印或处理返回结果
            System.out.println(payslip.toString());

            // 步骤 3：返回结果（后续可插入数据库等）
            return Result.success(payslip);
        } catch (Exception e) {
            throw new RuntimeException("解析工资单失败：" + e.getMessage(), e);
        } finally {
            // catch之后删掉暂存
            if (tempFile != null && tempFile.exists()) {
                boolean deleted = tempFile.delete();
                if (!deleted) {
                    System.err.println("⚠️ 警告：临时文件删除失败 -> " + tempFile.getAbsolutePath());
                }
            }
        }
    }
}
