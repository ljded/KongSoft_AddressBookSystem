/*
 Navicat Premium Dump SQL

 Source Server         : 本机MySQL91
 Source Server Type    : MySQL
 Source Server Version : 90100 (9.1.0)
 Source Host           : localhost:3306
 Source Schema         : contacts_book_text

 Target Server Type    : MySQL
 Target Server Version : 90100 (9.1.0)
 File Encoding         : 65001

 Date: 15/06/2025 14:04:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NOT NULL,
  `group_id` int NULL DEFAULT NULL,
  `Created_at` datetime NOT NULL,
  `Updated_at` datetime NOT NULL,
  `Deleted_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `gid_gid`(`group_id` ASC) USING BTREE,
  INDEX `uid_cid`(`user_id` ASC) USING BTREE,
  CONSTRAINT `gid_gid` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uid_cid` FOREIGN KEY (`user_id`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `Created_at` datetime NOT NULL,
  `Updated_at` datetime NOT NULL,
  `Deleted_at` datetime NULL DEFAULT NULL,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `User_id` int NOT NULL,
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `uid_gid`(`User_id` ASC) USING BTREE,
  INDEX `name`(`Name` ASC) USING BTREE,
  CONSTRAINT `uid_gid` FOREIGN KEY (`User_id`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE,
  UNIQUE INDEX `user_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
