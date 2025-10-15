/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 90400 (9.4.0)
 Source Host           : localhost:3306
 Source Schema         : ugg_express

 Target Server Type    : MySQL
 Target Server Version : 90400 (9.4.0)
 File Encoding         : 65001

 Date: 15/10/2025 22:43:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ugg_payslip
-- ----------------------------
DROP TABLE IF EXISTS `ugg_payslip`;
CREATE TABLE `ugg_payslip` (
  `payslip_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '薪资编号',
  `payslip_number` int(10) unsigned zerofill NOT NULL COMMENT '工资单序号',
  `staff_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号_外键',
  `file_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储路径_相对路径',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真名',
  `pay_period_start` date NOT NULL COMMENT '薪资起始日期\n',
  `pay_period_end` date NOT NULL COMMENT '薪资截止日期\n',
  `payment_date` date DEFAULT NULL COMMENT '实际发薪日期\n',
  `total_earnings` decimal(10,2) NOT NULL COMMENT '总计（本次）',
  `net_pay` decimal(10,2) NOT NULL COMMENT '实得收入（税后）\n',
  `ordinary_hours` decimal(6,2) DEFAULT NULL COMMENT '普通工时\n',
  `ordinary_hours_hourly_rate` decimal(10,2) DEFAULT NULL COMMENT '普通时薪',
  `ordinary_hours_this_pay` decimal(10,2) DEFAULT NULL COMMENT '普通工时薪资（本次）',
  `ordinary_hours_ytd` decimal(10,2) DEFAULT NULL COMMENT '普通工时薪资（年）',
  `after6pm_hours` decimal(10,2) DEFAULT NULL COMMENT '6点后工时',
  `after6pm_hours_hourly_rate` decimal(10,2) DEFAULT NULL COMMENT '6点后时薪',
  `after6pm_hours_this_pay` decimal(10,2) DEFAULT NULL COMMENT '6点后工时薪资（本次）',
  `after6pm_hours_ytd` decimal(10,2) DEFAULT NULL COMMENT '6点后工时薪资（年）',
  `sat_hours` decimal(10,2) DEFAULT NULL COMMENT '周六工时',
  `sat_hours_hourly_rate` decimal(10,2) DEFAULT NULL COMMENT '周六时薪',
  `sat_hours_this_pay` decimal(10,2) DEFAULT NULL COMMENT '周六工时薪资（本次）',
  `sat_hours_ytd` decimal(10,2) DEFAULT NULL COMMENT '周六工时薪资（年）',
  `gross_pay` decimal(10,2) DEFAULT NULL COMMENT '总收入（税前）\n',
  `gross_pay_ytd` decimal(10,2) DEFAULT NULL COMMENT '年总收入（税前）\n',
  `tax` decimal(10,2) DEFAULT NULL COMMENT '税额（PAYG）\n',
  `tax_ytd` decimal(10,2) DEFAULT NULL COMMENT '总税（年）',
  `superannuation` decimal(10,2) DEFAULT NULL COMMENT '养老金（SGC）\n',
  `super_ytd` decimal(10,2) DEFAULT NULL COMMENT '年养老金（SGC）',
  `bank_ref` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '银行转账参考信息\n',
  `reference_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '银行账单名称显示\n',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '银行支付金额',
  `if_check` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否确认',
  `notes` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`payslip_id`) USING BTREE,
  KEY `payslip_staff_fk` (`staff_id`),
  CONSTRAINT `payslip_staff_fk` FOREIGN KEY (`staff_id`) REFERENCES `ugg_staff` (`staff_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for ugg_sales_history
-- ----------------------------
DROP TABLE IF EXISTS `ugg_sales_history`;
CREATE TABLE `ugg_sales_history` (
  `sales_history_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `staff_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`sales_history_id`),
  KEY `staff_sales_history_fk` (`staff_id`),
  CONSTRAINT `staff_sales_history_fk` FOREIGN KEY (`staff_id`) REFERENCES `ugg_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for ugg_staff
-- ----------------------------
DROP TABLE IF EXISTS `ugg_staff`;
CREATE TABLE `ugg_staff` (
  `staff_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `real_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `prefer_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `reference_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `TFN_tax` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `light_speed_account` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `light_speed_password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `warranty_account` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `warranty_pass` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_number_1` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_name_1` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_number_2` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_name_2` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_number_3` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `store_name_3` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`staff_id`),
  KEY `staff_user_fk` (`user_id`),
  CONSTRAINT `staff_user_fk` FOREIGN KEY (`user_id`) REFERENCES `ugg_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for ugg_user
-- ----------------------------
DROP TABLE IF EXISTS `ugg_user`;
CREATE TABLE `ugg_user` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_no` int DEFAULT '2' COMMENT '1. 员工，2.普通用户',
  `user_code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for ugg_work_schedule
-- ----------------------------
DROP TABLE IF EXISTS `ugg_work_schedule`;
CREATE TABLE `ugg_work_schedule` (
  `work_schedule_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `staff_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hour` decimal(8,2) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `weekday` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`work_schedule_id`),
  KEY `work_schedule_staff_fk` (`staff_id`),
  CONSTRAINT `work_schedule_staff_fk` FOREIGN KEY (`staff_id`) REFERENCES `ugg_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
