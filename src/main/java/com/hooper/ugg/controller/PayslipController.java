package com.hooper.ugg.controller;

import com.hooper.ugg.ugg_enum.ResponseCode;
import com.hooper.ugg.common.Result;
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
 * @author Hooper
 * @since 2025-09-24
 */
@Controller
@RequestMapping("/ugg/payslip")
public class PayslipController {

    @Autowired
    private IPayslipService payslipService;

    @PostMapping("/upload")
    @ResponseBody
    public Result<?> uploadPayslip(@RequestParam("file") MultipartFile file) {
        // 保存到临时目录
        File tempFile = null;
        try {
            tempFile = File.createTempFile("Payslip_", ".pdf");
            file.transferTo(tempFile);  // 保存上传文件到临时路径

            // Java 调用 Python OCR/PDF 解析脚本
            return payslipService.parseAndSaveFromPDF(tempFile.getAbsolutePath());
        } catch (Exception e) {
            return Result.fail(ResponseCode.INVALID_PAYSLIP);
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
