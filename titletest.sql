/*
Navicat MySQL Data Transfer

Source Server         : 跳蚤
Source Server Version : 50723
Source Host           : localhost:3307
Source Database       : titletest

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-08-01 17:44:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `question_item_id` int(10) NOT NULL,
  `question_bank_id` int(10) DEFAULT NULL COMMENT '答案对应的问题id',
  `name` varchar(255) DEFAULT NULL COMMENT '答案内容',
  `result` int(1) DEFAULT NULL COMMENT '正确性（1：正确，2：错误）',
  `index` int(1) DEFAULT NULL COMMENT '选项（1,2，3,4）',
  `index_letter` varchar(10) DEFAULT NULL COMMENT 'ABCD四个选项',
  PRIMARY KEY (`question_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_bank_id` int(10) NOT NULL AUTO_INCREMENT,
  `question_type_id` int(10) DEFAULT NULL COMMENT '问题类型id  对应问题类型表',
  `question_title` varchar(255) DEFAULT NULL COMMENT '问题内容',
  `explanation` varchar(255) DEFAULT '' COMMENT '解析内容',
  `parent_id` int(10) DEFAULT NULL COMMENT '问题的父类id',
  `question_bank_category_id` int(10) DEFAULT NULL COMMENT '问题标签id',
  `name` varchar(255) DEFAULT NULL COMMENT '问题属于那一类型的题目（如A类）',
  `difficulty_degree` int(1) DEFAULT '1' COMMENT '难度等级（默认为1级）',
  `isclose` int(1) DEFAULT '0' COMMENT '是否关闭（0：未关闭，1：关闭）',
  `iddelete` int(1) DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
  PRIMARY KEY (`question_bank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for question_type
-- ----------------------------
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE `question_type` (
  `question_type_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '问题类型名称（如A类）',
  `statu` int(1) DEFAULT '1' COMMENT '状态（1：可用，0：不可用）',
  PRIMARY KEY (`question_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
