/*
 Navicat Premium Data Transfer

 Source Server         : bill
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : bill

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 12/10/2020 11:06:16
*/

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `NO` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TX_TYP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '1：支出    2：收入',
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `USR_NO` int(10) NOT NULL COMMENT '用户号',
  `CNL_NO` int(10) NOT NULL COMMENT '交易渠道编号',
  `TX_AMT` double(10, 2) NULL DEFAULT NULL,
  `TX_DT` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`NO`) USING BTREE,
  INDEX `BILL_IND1`(`USR_NO`) USING BTREE COMMENT '用户号索引'
) ;

