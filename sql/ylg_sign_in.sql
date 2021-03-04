/*
 Navicat MySQL Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.175.133:3306
 Source Schema         : ylg_sign_in

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 04/03/2020 19:59:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bs_user
-- ----------------------------
DROP TABLE IF EXISTS `bs_user`;
CREATE TABLE `bs_user`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `mobile` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'openid',
  `enable_state` int(2) NULL DEFAULT 0 COMMENT '启用状态 0是待审核，1是审核通过，2是不通过',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `department_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `work_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `in_service_status` int(1) NULL DEFAULT NULL COMMENT '在职状态 1.在职 2.离职',
  `organization_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织ID',
  `organization_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_phone`(`mobile`) USING BTREE,
  UNIQUE INDEX `uk_openid`(`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bs_user
-- ----------------------------
INSERT INTO `bs_user` VALUES ('1230502343926222848', '13700000000', 'winfrid', '12345678', 1, '2020-02-20 22:39:55', '1234511002557616128', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'coAdmin');
INSERT INTO `bs_user` VALUES ('1234385384511770625', '13500000000', '张三', '12345678q', 1, '2020-03-02 15:49:44', '1234511268652650496', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', '网络部', 'user');
INSERT INTO `bs_user` VALUES ('1234419981589024768', '13800000000', '李四', '1234567890', 2, '2020-03-02 18:07:13', '1234511002557616128', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'user');
INSERT INTO `bs_user` VALUES ('1234421587764187136', '12300000005', '张三', '12345678901', 1, '2020-03-02 18:13:36', '1234511002557616128', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', NULL, 'user');
INSERT INTO `bs_user` VALUES ('1234421789787033600', '13800000020', '李四1', '123456789012', 0, '2020-03-02 18:14:24', '1234511002557616128', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'user');
INSERT INTO `bs_user` VALUES ('1234421836402528256', '13800000030', '李四1', '123456789013', 0, '2020-03-02 18:14:35', '1234511002557616128', NULL, 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'user');
INSERT INTO `bs_user` VALUES ('1234437900809146369', '13800010030', '王五', '123456789', 1, '2020-03-02 19:18:25', NULL, NULL, 1, '1234490431593648128', '云麓谷易班', NULL, 'coAdmin');
INSERT INTO `bs_user` VALUES ('1234438052949135361', '13802010030', '赵六', '123456789d', 1, '2020-03-02 19:19:01', NULL, NULL, 1, NULL, NULL, NULL, 'coAdmin');
INSERT INTO `bs_user` VALUES ('1234438118342529025', '13802210030', '田七', '123456789s', 0, '2020-03-02 19:19:17', '', NULL, 1, NULL, NULL, NULL, 'coAdmin');
INSERT INTO `bs_user` VALUES ('1234438391232335873', '13802211030', 'aaa', '123456789a', 0, '2020-03-02 19:20:22', '1234511268652650496', '', 1, '1234741533559885824', '云麓谷信息技术中心', '网络部', 'user');
INSERT INTO `bs_user` VALUES ('1234441529817436161', '13802211230', 'aaa', '123456789c', 0, '2020-03-02 19:32:50', '1234511268652650496', '', 1, '1234741533559885824', '云麓谷信息技术中心', '网络部', 'user');
INSERT INTO `bs_user` VALUES ('1234746189723865088', '12340000000', '杜源丰', '1234567', 1, '2020-02-19 14:43:44', '1234511002557616128', 'XXB-036', 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'user');
INSERT INTO `bs_user` VALUES ('1234746803572838400', '12300000000', '魏林智', '123456', 1, '2020-02-19 14:39:37', '1234511002557616128', 'XXB-032', 1, '1234741533559885824', '云麓谷信息技术中心', '研发部', 'user');

-- ----------------------------
-- Table structure for co_department
-- ----------------------------
DROP TABLE IF EXISTS `co_department`;
CREATE TABLE `co_department`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `organization_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织ID',
  `pid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级部门ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `manager_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人ID',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '介绍',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `manager` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门负责人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_organizationid_name`(`organization_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of co_department
-- ----------------------------
INSERT INTO `co_department` VALUES ('1234511002557616128', '1234741533559885824', '0', '研发部', 'DEPT-TECHNOLOG', NULL, NULL, '2020-02-19 11:04:51', NULL);
INSERT INTO `co_department` VALUES ('1234511268652650496', '1234741533559885824', '0', '网络部', 'abc', NULL, NULL, '2020-03-02 19:54:21', NULL);
INSERT INTO `co_department` VALUES ('1234741770760359936', '1234490431593648128', NULL, '行政部', NULL, NULL, NULL, '2020-03-03 00:08:54', NULL);

-- ----------------------------
-- Table structure for co_organization
-- ----------------------------
DROP TABLE IF EXISTS `co_organization`;
CREATE TABLE `co_organization`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `manager_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织管理员ID',
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `audit_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核状态(0为待审核,1为审核中,2为已审核)',
  `state` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态(1为通过,0为未通过)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `mobile` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员手机号码',
  `mailbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `manager` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织管理员'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of co_organization
-- ----------------------------
INSERT INTO `co_organization` VALUES ('1234741533559885824', '云麓谷信息技术中心', NULL, NULL, NULL, 1, '2020-02-19 00:20:48', '', NULL, NULL);
INSERT INTO `co_organization` VALUES ('1234490431593648128', '云麓谷易班', '1234437900809146369', NULL, NULL, 1, '2020-03-02 22:55:22', '13800010030', NULL, NULL);

-- ----------------------------
-- Table structure for duty_time
-- ----------------------------
DROP TABLE IF EXISTS `duty_time`;
CREATE TABLE `duty_time`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `start_time` time(0) NOT NULL COMMENT '开始的时间',
  `end_time` time(0) NOT NULL,
  `organization_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `organization_id_start_time`(`organization_id`, `start_time`) USING BTREE,
  UNIQUE INDEX `organization_id_end_time`(`organization_id`, `end_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of duty_time
-- ----------------------------
INSERT INTO `duty_time` VALUES ('1234753274578604035', '08:00:00', '09:40:00', '1234741533559885824');
INSERT INTO `duty_time` VALUES ('1234753274578604036', '10:00:00', '11:40:00', '1234741533559885824');
INSERT INTO `duty_time` VALUES ('1234753274578604037', '14:00:00', '15:40:00', '1234741533559885824');
INSERT INTO `duty_time` VALUES ('1234753274578604038', '16:00:00', '17:40:00', '1234741533559885824');
INSERT INTO `duty_time` VALUES ('1234753274578604039', '19:00:00', '20:40:00', '1234741533559885824');
INSERT INTO `duty_time` VALUES ('1234753274578604040', '01:31:23', '01:40:49', '1234490431593648128');

-- ----------------------------
-- Table structure for pe_permission
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission`;
CREATE TABLE `pe_permission`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '权限描述',
  `type` int(1) NULL DEFAULT NULL COMMENT '权限类型 1为菜单 2为功能 3为API',
  `pid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级ID',
  `en_visible` int(1) NULL DEFAULT NULL COMMENT '可见性 1为可见 0为不可见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pe_permission
-- ----------------------------
INSERT INTO `pe_permission` VALUES ('1', '签到系统管理', 'sign-in-clients', NULL, 1, '0', 0);
INSERT INTO `pe_permission` VALUES ('1226806383995817984', '查看部门', 'point-dept', '查看部门的按钮', 2, '1', 0);
INSERT INTO `pe_permission` VALUES ('1226806428983922688', '员工管理', 'employees', '用户管理菜单', 1, '0', 1);
INSERT INTO `pe_permission` VALUES ('1226806708987269120', '用户删除按钮', 'point-user-delete', '用户删除按钮', 2, '1226806428983922688', 1);
INSERT INTO `pe_permission` VALUES ('1226806743036628992', '删除用户api', 'API-USER-DELETE', '删除api', 3, '1226806708987269120', 1);
INSERT INTO `pe_permission` VALUES ('1226806822195728384', '删除用户角色', 'API-USER-DELETE-ROLE', '删除用户角色', 3, '1226806708987269120', 1);
INSERT INTO `pe_permission` VALUES ('1226806848301076480', '组织架构', 'departments', '组织架构菜单', 1, '0', 1);
INSERT INTO `pe_permission` VALUES ('1226806881415106560', '组织设置', 'settings', '组织设置菜单', 1, '0', 1);
INSERT INTO `pe_permission` VALUES ('1226807015750275072', '用户添加按钮', 'POINT-USER-ADD', '用户添加按钮', 2, '1226806428983922688', 1);
INSERT INTO `pe_permission` VALUES ('1226807162240536576', '用户修改按钮', 'POINT-USER-UPDATE', '用户修改按钮', 2, '1226806428983922688', 1);
INSERT INTO `pe_permission` VALUES ('1226807213058723840', '权限管理', 'permissions', NULL, 1, '0', 1);

-- ----------------------------
-- Table structure for pe_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_api`;
CREATE TABLE `pe_permission_api`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `api_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `api_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型',
  `api_level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限等级，1为通用接口权限，2为需校验接口权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pe_permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_menu`;
CREATE TABLE `pe_permission_menu`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `menu_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '展示图标',
  `menu_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pe_permission_point
-- ----------------------------
DROP TABLE IF EXISTS `pe_permission_point`;
CREATE TABLE `pe_permission_point`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `point_class` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限代码',
  `point_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `point_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pe_role
-- ----------------------------
DROP TABLE IF EXISTS `pe_role`;
CREATE TABLE `pe_role`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `organization_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_k3beff7qglfn58qsf2yvbg41i`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pe_role
-- ----------------------------
INSERT INTO `pe_role` VALUES ('1', '系统管理员', '', '');
INSERT INTO `pe_role` VALUES ('2', '组织管理员', '', '1');
INSERT INTO `pe_role` VALUES ('3', '普通员工', NULL, '1');

-- ----------------------------
-- Table structure for pe_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `pe_role_permission`;
CREATE TABLE `pe_role_permission`  (
  `role_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `permission_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FK74qx7rkbtq2wqms78gljv87a0`(`permission_id`) USING BTREE,
  INDEX `FKee9dk0vg99shvsytflym6egxd`(`role_id`) USING BTREE,
  CONSTRAINT `fk-p-rid` FOREIGN KEY (`role_id`) REFERENCES `pe_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-pid` FOREIGN KEY (`permission_id`) REFERENCES `pe_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pe_role_permission
-- ----------------------------
INSERT INTO `pe_role_permission` VALUES ('1', '1');

-- ----------------------------
-- Table structure for pe_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pe_user_role`;
CREATE TABLE `pe_user_role`  (
  `role_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `user_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  INDEX `FK74qx7rkbtq2wqms78gljv87a1`(`role_id`) USING BTREE,
  INDEX `FKee9dk0vg99shvsytflym6egx1`(`user_id`) USING BTREE,
  CONSTRAINT `fk-rid` FOREIGN KEY (`role_id`) REFERENCES `pe_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-uid` FOREIGN KEY (`user_id`) REFERENCES `bs_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sign_limit
-- ----------------------------
DROP TABLE IF EXISTS `sign_limit`;
CREATE TABLE `sign_limit`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `start_time` time(0) NOT NULL COMMENT '开始的时间',
  `start_day` tinyint(2) NOT NULL COMMENT '开始的星期几',
  `end_time` time(0) NOT NULL,
  `end_day` tinyint(1) NOT NULL,
  `organization_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `max_people` tinyint(2) NOT NULL COMMENT '最大值班人数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_organization_id`(`organization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sign_limit
-- ----------------------------
INSERT INTO `sign_limit` VALUES ('1234496552467304448', '16:30:00', 7, '13:40:00', 1, '1234490431593648128', 3);
INSERT INTO `sign_limit` VALUES ('1234753274578604033', '17:00:00', 6, '16:00:00', 7, '1234741533559885824', 2);

-- ----------------------------
-- Table structure for sign_rules
-- ----------------------------
DROP TABLE IF EXISTS `sign_rules`;
CREATE TABLE `sign_rules`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `before_in` int(4) NOT NULL COMMENT '该班开始前几分钟可签到',
  `late_after_in` int(4) NOT NULL COMMENT '该班开始几分钟后算迟到',
  `absent_after_in` int(4) NOT NULL COMMENT '开始几分钟后算旷班，不可签到',
  `before_out` int(4) NOT NULL COMMENT '结束前几分钟可签退',
  `after_out` int(4) NOT NULL COMMENT '结束后几分钟可签退',
  `organization_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sign_in_once` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否允许连续签到，1允许，0不允许',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_organization_id`(`organization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sign_rules
-- ----------------------------
INSERT INTO `sign_rules` VALUES ('1234501543953305600', 2, 1, 4, 10, 20, '1234490431593648128', 1);
INSERT INTO `sign_rules` VALUES ('1234753274578604034', 20, 10, 90, 10, 20, '1234741533559885824', 1);

-- ----------------------------
-- Table structure for tb_duty_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_duty_info`;
CREATE TABLE `tb_duty_info`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NOT NULL COMMENT '开始的时间',
  `end_time` datetime(0) NOT NULL,
  `organization_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `sign_in_time` datetime(0) NULL DEFAULT NULL COMMENT '签到时间',
  `sign_out_time` datetime(0) NULL DEFAULT NULL COMMENT '签退时间',
  `state` tinyint(2) NULL DEFAULT 0 COMMENT '0未签到,1请假,2正常签到未签退,3迟到未签退,4正常签到正常签退,5迟到正常签退',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `week_day` tinyint(2) NOT NULL COMMENT '1~7 星期日~星期六',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_start_time_user_id`(`user_id`, `start_time`) USING BTREE,
  INDEX `key_start_time`(`start_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_duty_info
-- ----------------------------
INSERT INTO `tb_duty_info` VALUES ('1233550533722247168', '1234746803572838400', '魏林智', '2020-03-05 08:00:00', '2020-03-05 09:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-02-29 08:32:20', '2020-03-05 08:25:57', '2020-03-05 09:39:56', 5, '12300000000', 5);
INSERT INTO `tb_duty_info` VALUES ('1233550549861928960', '1234746803572838400', '魏林智', '2020-03-05 10:00:00', '2020-03-05 11:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-02-29 08:32:24', NULL, NULL, 1, '12300000000', 5);
INSERT INTO `tb_duty_info` VALUES ('1233620503411953664', '1234746803572838400', '魏林智', '2020-03-05 14:00:00', '2020-03-05 15:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-02-29 13:10:22', '2020-03-05 14:01:25', '2020-03-05 17:56:00', 4, '12300000000', 5);
INSERT INTO `tb_duty_info` VALUES ('1233620520260472832', '1234746803572838400', '魏林智', '2020-03-05 16:00:00', '2020-03-05 17:40:00', '1234741533559885824', '1234511002557616129', '研发部', '2020-02-29 13:10:26', '2020-03-05 14:01:25', '2020-03-05 17:56:00', 4, '12300000000', 5);
INSERT INTO `tb_duty_info` VALUES ('1234024844841062400', '1234746803572838400', '魏林智', '2020-02-27 19:00:00', '2020-02-27 20:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-03-01 15:57:05', NULL, NULL, 0, '12340000000', 6);
INSERT INTO `tb_duty_info` VALUES ('1234026540275208192', '1234746803572838400', '魏林智', '2020-03-03 19:00:00', '2020-03-05 20:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-03-01 16:03:49', NULL, NULL, 4, '12340000000', 1);
INSERT INTO `tb_duty_info` VALUES ('1234753274578604032', '1234746803572838400', '魏林智', '2020-02-25 08:00:00', '2020-02-25 09:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-02-24 22:44:17', '2020-02-25 08:01:51', NULL, 2, '', 4);
INSERT INTO `tb_duty_info` VALUES ('1234753274578604033', '1234419981589024768', '李四', '2020-02-25 10:00:00', '2020-02-25 11:40:00', '1234741533559885824', '1234511002557616128', '研发部', '2020-02-22 16:43:39', NULL, NULL, 0, '12345648515', 4);

-- ----------------------------
-- Table structure for wifi_info
-- ----------------------------
DROP TABLE IF EXISTS `wifi_info`;
CREATE TABLE `wifi_info`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `wifi_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wifi_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wifi_ssid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'wifi标识',
  `organization_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_organization_id`(`organization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wifi_info
-- ----------------------------
INSERT INTO `wifi_info` VALUES ('1234459122729095168', 'abc', '16153498', 'xxxxxxxx', '1234490431593648128');
INSERT INTO `wifi_info` VALUES ('1234753274574409728', 'xgb404', '88830180', 'wbevhasduvowe', '1234741533559885824');

SET FOREIGN_KEY_CHECKS = 1;
