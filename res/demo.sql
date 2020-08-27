/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 47.106.103.121:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 27/08/2020 16:13:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for plat_account
-- ----------------------------
DROP TABLE IF EXISTS `plat_account`;
CREATE TABLE `plat_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `account_id` bigint(20) NOT NULL COMMENT '业务主键',
  `account_type` tinyint(4) DEFAULT NULL COMMENT '账号类型（0.超级管理员，1.普通管理员，2.业务管理员，3.业务人员）',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `login_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户姓名',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `language` tinyint(4) DEFAULT 1 COMMENT '语言',
  `length_unit` tinyint(4) DEFAULT NULL COMMENT '长度单位',
  `volume_unit` tinyint(4) DEFAULT NULL COMMENT '重量单位',
  `status` tinyint(4) DEFAULT 0 COMMENT '状态',
  `distribution` tinyint(4) DEFAULT 0 COMMENT '是否已分配公司（0.未分配 1.已分配）',
  `landed` tinyint(4) DEFAULT 0 COMMENT '是否首次登录',
  `login_status` tinyint(4) DEFAULT 0 COMMENT '登录状态',
  `last_login_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_account_id`(`account_id`) USING BTREE,
  UNIQUE INDEX `uni_login_name`(`login_name`) USING BTREE,
  UNIQUE INDEX `uni_email`(`email`) USING BTREE,
  INDEX `idx_user_status`(`status`) USING BTREE,
  INDEX `idx_account_type`(`account_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 409 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台账号表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of plat_account
-- ----------------------------
INSERT INTO `plat_account` VALUES (382, 0, 0, '', '', '', '', '', '', '', 0, 0, 0, 1, 0, 0, 1, '2019-12-16 14:13:24', 1, '2019-12-16 14:13:24', NULL, '2019-12-24 12:13:19');
INSERT INTO `plat_account` VALUES (387, 1206460621825310721, 0, '', 'cccc', '擦擦擦', '', '', '', '', 0, 0, 0, 1, 0, 0, 1, '2019-12-16 14:26:41', 1, '2019-12-16 14:26:41', NULL, '2019-12-24 12:13:19');
INSERT INTO `plat_account` VALUES (388, 1206460712631992322, 0, '', 'cccccc', '擦c擦擦', '', '', '', '', 0, 0, 0, 1, 0, 0, 1, '2019-12-16 14:27:03', 1, '2019-12-16 14:27:03', NULL, '2019-12-24 12:13:19');
INSERT INTO `plat_account` VALUES (393, 333, 0, '', 'ccc', 'cccc', '', '', '', '', 0, 0, 0, 1, 0, 0, 2, '2019-12-16 15:34:10', 1, '2019-12-16 15:34:10', NULL, '2020-06-11 14:55:23');
INSERT INTO `plat_account` VALUES (396, 11, NULL, NULL, '22', '22', NULL, NULL, NULL, NULL, 1, NULL, NULL, 1, 0, 0, 1, '2019-12-20 15:30:02', 1, '2019-12-20 15:30:02', NULL, '2019-12-24 12:13:20');
INSERT INTO `plat_account` VALUES (400, 1208937406022303745, 0, '', 'ccchh', 'c6ccc', '', '', '', '', 0, 0, 0, 1, 0, 0, 1, '2019-12-23 10:28:33', 1, '2019-12-23 10:28:33', NULL, '2019-12-24 12:13:20');
INSERT INTO `plat_account` VALUES (408, 1272380130762969090, 0, '', '123433403@qq.com', 'ccccccc', '', '', '', '', 0, 0, 0, 0, 0, 0, 0, '2020-06-15 12:07:18', NULL, '2020-06-15 12:07:18', NULL, '2020-06-15 12:07:18');

SET FOREIGN_KEY_CHECKS = 1;
