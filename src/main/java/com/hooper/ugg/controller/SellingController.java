package com.hooper.ugg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ugg/sell")
public class SellingController {

    /**
     * [销售表]
     * 销售id (UUID)
     * 销售员真名（外键：user.real_name）
     * 销售SKU
     * 颜色
     * 尺码
     * 价格
     * 售出日期
     * 是否完成补货操作
     * create_date
     * update_date
     *
     */

    /**
     * 1.根据当前用户信息，刷新销售记录，将数据库内没有的销售记录存入数据库中
     */

    /**
     * 一键补货
     * 根据销售记录 & 是否完成补货操作(sell表属性)，进行补货
     * 添加补货记录（补货记录表）
     * [补货记录表]
     * 补货ID (UUID)
     * 补货员 （外键关联：user.real_name）
     * 补货SKU
     * 颜色
     * 尺码
     * 补货日期
     * create_date
     * update_date
     */

    /**
     * 一键下单
     * 输入指定订单号/根据输入|默认店铺+输入|默认日期，查询最近的create订单记录
     * 两个order连续请求（若有separate需处理）
     */



}
