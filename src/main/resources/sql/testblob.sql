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

 Date: 12/10/2020 10:32:08
*/

-- ----------------------------
-- Table structure for testblob
-- ----------------------------
DROP TABLE IF EXISTS `testblob`;
CREATE TABLE `testblob`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `bytes` blob NULL,
  PRIMARY KEY (`id`) USING BTREE
);

