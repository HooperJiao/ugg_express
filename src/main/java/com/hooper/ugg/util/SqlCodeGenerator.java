package com.hooper.ugg.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlCodeGenerator {

    public static void main(String[] args) {
        // 动态获取所有以 "ugg_" 开头的表名
        List<String> tables = getAllTableNamesWithPrefix("ugg_");

        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/ugg_express?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "root")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("Hooper")
                            .enableSwagger()
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd");
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.hooper")
                            .moduleName("ugg")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder
                            //.addInclude(tables)               // 所有ugg_开头的表
                            .addInclude("ugg_payslip")               // 指定表
                            //.addInclude("ugg_user")
                            .addTablePrefix("ugg_")           // 去掉表名前缀
                            .entityBuilder()
                            .enableLombok()
                            .superClass("com.hooper.ugg.entity.UggBase")  // 父类
                            .enableFileOverride()
                            // 自动添加填充字段
                            .addTableFills(getCommonFills())
                            .build();

                    builder.controllerBuilder().enableFileOverride();
                    builder.serviceBuilder().enableFileOverride();
                    builder.mapperBuilder().enableFileOverride();
                })
                .templateEngine(new VelocityTemplateEngine())
                .templateConfig(config -> {
                    config.entity("templates/entity.java.vm"); // 使用本地模板
                })
                .execute();
    }

    // 动态获取所有以指定前缀开头的表名
    public static List<String> getAllTableNamesWithPrefix(String prefix) {
        List<String> tableNames = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ugg_express?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true",
                "root", "root")) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, prefix + "%", new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    // 封装公共字段填充策略
    public static List<IFill> getCommonFills() {
        List<IFill> fills = new ArrayList<>();
        fills.add(new Column("created_time", FieldFill.INSERT));
        fills.add(new Column("updated_time", FieldFill.INSERT_UPDATE));
        return fills;
    }
}