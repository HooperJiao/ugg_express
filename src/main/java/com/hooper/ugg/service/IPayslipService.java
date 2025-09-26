package com.hooper.ugg.service;

import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Payslip;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Hooper
 * @since 2025-09-24
 */
public interface IPayslipService extends IService<Payslip> {
    /**
     *
     * @param pdfPath pdf文件路径
     */
    public Result<?> parseAndSaveFromPDF(String pdfPath) throws Exception;
}
