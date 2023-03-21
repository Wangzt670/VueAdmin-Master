/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : vueadmin

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 21/03/2023 23:55:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `carnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键用户',
  `statu` int(5) NOT NULL COMMENT '状态(0：禁用；1：空闲；2：占用)',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `carnum`(`carnum`) USING BTREE,
  INDEX `car_username`(`username`) USING BTREE,
  CONSTRAINT `car_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1, '渝AHY584', '王正霆', 1, '红色奔驰');
INSERT INTO `car` VALUES (2, '渝A8YE39', '王正霆', 1, '白色奔驰');
INSERT INTO `car` VALUES (10, '用户1车牌1', '用户1', 1, '用户1车牌1描述1');
INSERT INTO `car` VALUES (11, '用户1车牌2', '用户1', 1, '用户1车牌2描述2');
INSERT INTO `car` VALUES (12, '用户1车牌3', '用户1', 1, '用户1车牌3描述3');
INSERT INTO `car` VALUES (13, '用户3车牌1', '用户3', 1, '用户3车牌1描述1');
INSERT INTO `car` VALUES (14, '用户3车牌2', '用户3', 1, '用户3车牌2描述2');
INSERT INTO `car` VALUES (15, '用户3车牌3', '用户3', 0, '用户3车牌3描述3');

-- ----------------------------
-- Table structure for indent
-- ----------------------------
DROP TABLE IF EXISTS `indent`;
CREATE TABLE `indent`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orderstart` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单开始时间',
  `orderend` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单结束时间',
  `carnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键车牌号',
  `villagename` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键小区名称',
  `parknum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键车位编号',
  `lease` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键出租人',
  `rent` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键租借人',
  `statu` int(5) NOT NULL COMMENT '状态(0：已结束；1：进行中)',
  `cost` int(5) NULL DEFAULT NULL COMMENT '订单价格',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ordernum`(`orderstart`) USING BTREE,
  INDEX `order_carnum`(`carnum`) USING BTREE,
  INDEX `order_villagename`(`villagename`) USING BTREE,
  INDEX `order_parknum`(`parknum`) USING BTREE,
  INDEX `order_lease`(`lease`) USING BTREE,
  INDEX `order_rent`(`rent`) USING BTREE,
  CONSTRAINT `order_carnum` FOREIGN KEY (`carnum`) REFERENCES `car` (`carnum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_lease` FOREIGN KEY (`lease`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_parknum` FOREIGN KEY (`parknum`) REFERENCES `park` (`parknum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_rent` FOREIGN KEY (`rent`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_villagename` FOREIGN KEY (`villagename`) REFERENCES `village` (`villagename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of indent
-- ----------------------------
INSERT INTO `indent` VALUES (53, '2023-3-21 9:44:57', '2023-3-21 23:09:51', '用户3车牌1', '锦上华庭', '用户1车位1', '用户1', '用户3', 0, 70);
INSERT INTO `indent` VALUES (54, '2023-3-21 10:46:31', '2023-3-21 23:09:53', '用户3车牌2', '阳光今典', '用户2车位3', '用户2', '用户3', 0, 65);
INSERT INTO `indent` VALUES (55, '2023-3-21 11:48:15', '2023-3-21 23:09:57', '用户1车牌1', '锦上华庭', '用户2车位1', '用户2', '用户1', 0, 60);
INSERT INTO `indent` VALUES (56, '2023-3-21 15:29:08', '2023-3-21 23:10:01', '用户3车牌1', '锦上华庭', '用户1车位1', '用户1', '用户3', 0, 40);
INSERT INTO `indent` VALUES (57, '2023-3-21 18:30:18', '2023-3-21 23:10:08', '用户3车牌2', '阳光今典', '用户2车位3', '用户2', '用户3', 0, 25);
INSERT INTO `indent` VALUES (60, '2023-3-21 23:40:49', '2023-3-21 23:41:07', '用户1车牌1', '锦上华庭', '用户2车位1', '用户2', '用户1', 0, 5);
INSERT INTO `indent` VALUES (61, '2023-3-21 23:41:21', '2023-3-21 23:42:06', '用户1车牌2', '锦上华庭', '用户2车位1', '用户2', '用户1', 0, 5);
INSERT INTO `indent` VALUES (62, '2023-3-21 23:42:59', '2023-3-21 23:44:07', '用户1车牌1', '锦上华庭', '用户2车位1', '用户2', '用户1', 0, 5);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID(一级菜单为0)',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限编码(多个用逗号分隔，如：user:list,user:create)',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `type` int(5) NOT NULL COMMENT '菜单类型(0：目录   1：菜单   2：按钮)',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单组件',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '系统管理', 'sys:manage', 'el-icon-s-tools', 0, NULL, NULL);
INSERT INTO `menu` VALUES (2, 1, '用户管理', 'sys:user:list', 'el-icon-s-custom', 1, '/sys/user', 'sys_management/User');
INSERT INTO `menu` VALUES (3, 1, '角色管理', 'sys:role:list', 'el-icon-rank', 1, '/sys/role', 'sys_management/Role');
INSERT INTO `menu` VALUES (4, 1, '菜单管理', 'sys:menu:list', 'el-icon-menu', 1, '/sys/menu', 'sys_management/Menu');
INSERT INTO `menu` VALUES (5, 0, '小区管理', 'vilman:manage', 'el-icon-s-platform', 0, NULL, NULL);
INSERT INTO `menu` VALUES (6, 5, '小区总览', 'vilman:village:list', 'el-icon-house', 1, '/vilman/village', 'village_management/Village');
INSERT INTO `menu` VALUES (7, 5, '我的小区', 'vilman:myvillage:list', 'el-icon-house', 1, '/vilman/myvillage', 'village_management/MyVillage');
INSERT INTO `menu` VALUES (8, 0, '车位管理', 'parkman:manage', 'el-icon-s-flag', 0, NULL, NULL);
INSERT INTO `menu` VALUES (9, 8, '车位总览', 'parkman:park:list', 'el-icon-full-screen', 1, '/parkman/park', 'park_management/Park');
INSERT INTO `menu` VALUES (10, 8, '我的车位', 'parkman:mypark:list', 'el-icon-full-screen', 1, '/parkman/mypark', 'park_management/MyPark');
INSERT INTO `menu` VALUES (11, 0, '车辆管理', 'carman:manage', 'el-icon-s-promotion', 0, NULL, NULL);
INSERT INTO `menu` VALUES (12, 11, '车辆总览', 'carman:car:list', 'el-icon-truck', 1, '/carman/car', 'car_management/Car');
INSERT INTO `menu` VALUES (13, 11, '我的车辆', 'carman:mycar:list', 'el-icon-truck', 1, '/carman/mycar', 'car_management/MyCar');
INSERT INTO `menu` VALUES (14, 0, '订单管理', 'ordman:manage', 'el-icon-s-order', 0, NULL, NULL);
INSERT INTO `menu` VALUES (15, 14, '订单总览', 'ordman:order:list', 'el-icon-tickets', 1, '/ordman/order', 'order_management/Order');
INSERT INTO `menu` VALUES (16, 14, '我的订单', 'ordman:myorder:list', 'el-icon-tickets', 1, '/ordman/myorder', 'order_management/MyOrder');
INSERT INTO `menu` VALUES (17, 0, '定位显示', 'locview:manage', 'el-icon-location', 0, NULL, NULL);
INSERT INTO `menu` VALUES (18, 17, '定位查找', 'locview:locview:list', 'el-icon-location-outline', 1, '/locview/locview', 'location_view/LocationView');
INSERT INTO `menu` VALUES (19, 0, '数据显示', 'sta:manage', 'el-icon-s-data', 0, NULL, NULL);
INSERT INTO `menu` VALUES (20, 19, '数据图表', 'sta:staview:list', 'el-icon-pie-chart', 1, '/sta/staview', 'statistics_view/StatisticsView');
INSERT INTO `menu` VALUES (21, 4, '新建菜单', 'sys:menu:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (22, 4, '编辑菜单', 'sys:menu:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (23, 4, '删除菜单', 'sys:menu:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (24, 3, '新建角色', 'sys:role:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (25, 3, '编辑角色', 'sys:role:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (26, 3, '删除角色', 'sys:role:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (27, 2, '新建用户', 'sys:user:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (28, 2, '编辑用户', 'sys:user:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (29, 2, '删除用户', 'sys:user:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (30, 6, '总览-新建小区', 'vilman:village:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (31, 6, '总览-编辑小区', 'vilman:village:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (32, 6, '总览-删除小区', 'vilman:village:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (33, 7, '我的-新建小区', 'vilman:myvillage:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (34, 7, '我的-编辑小区', 'vilman:myvillage:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (35, 7, '我的-删除小区', 'vilman:myvillage:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (36, 12, '总览-新建车辆', 'carman:car:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (37, 12, '总览-编辑车辆', 'carman:car:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (38, 12, '总览-删除车辆', 'carman:car:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (39, 13, '我的-新建车辆', 'carman:mycar:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (40, 13, '我的-编辑车辆', 'carman:mycar:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (41, 13, '我的-删除车辆', 'carman:mycar:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (42, 9, '总览-新建车位', 'parkman:park:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (43, 9, '总览-编辑车位', 'parkman:park:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (44, 9, '总览-删除车位', 'parkman:park:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (45, 10, '我的-新建车位', 'parkman:mypark:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (46, 10, '我的-编辑车位', 'parkman:mypark:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (47, 10, '我的-删除车位', 'parkman:mypark:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (48, 15, '总览-编辑订单', 'ordman:order:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (49, 16, '我的-编辑订单', 'ordman:myorder:update', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (50, 18, '创建订单', 'locview:locview:save', NULL, 2, NULL, NULL);

-- ----------------------------
-- Table structure for park
-- ----------------------------
DROP TABLE IF EXISTS `park`;
CREATE TABLE `park`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parknum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车位编号',
  `villagename` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键小区名称',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键用户名称',
  `statu` int(5) NOT NULL COMMENT '状态(0：禁用；1：空闲；2：占用)',
  `avastart` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可用时段起始时间',
  `avaend` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可用时段结束时间',
  `price` int(5) NOT NULL COMMENT '价格',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `parknum`(`parknum`) USING BTREE,
  INDEX `park_villagename`(`villagename`) USING BTREE,
  INDEX `park_username`(`username`) USING BTREE,
  CONSTRAINT `park_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `park_villagename` FOREIGN KEY (`villagename`) REFERENCES `village` (`villagename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of park
-- ----------------------------
INSERT INTO `park` VALUES (1, 'A区1号', '锦上华庭', '王正霆', 1, '9:30:00', '17:30:00', 5, '地下车位');
INSERT INTO `park` VALUES (2, 'A区2号', '锦上华庭', '王正霆', 1, '8:30:00', '17:30:00', 5, NULL);
INSERT INTO `park` VALUES (7, '用户1车位1', '锦上华庭', '用户1', 1, '8:30:00', '17:30:00', 5, '用户1车位1描述1');
INSERT INTO `park` VALUES (8, '用户1车位2', '锦上华庭', '用户1', 1, '8:30:00', '17:30:00', 5, '用户1车位2描述2');
INSERT INTO `park` VALUES (9, '用户1车位3', '阳光今典', '用户1', 1, '8:30:00', '17:30:00', 5, '用户1车位3描述3');
INSERT INTO `park` VALUES (10, '用户2车位1', '锦上华庭', '用户2', 1, '8:30:00', '17:30:00', 5, '用户2车位1描述1');
INSERT INTO `park` VALUES (11, '用户2车位2', '锦上华庭', '用户2', 0, '8:30:00', '17:30:00', 5, '用户2车位2描述2');
INSERT INTO `park` VALUES (12, '用户2车位3', '阳光今典', '用户2', 1, '8:30:00', '17:30:00', 5, '用户2车位3描述3');
INSERT INTO `park` VALUES (15, 'A区3号', '锦上华庭', '王正霆', 1, '9:30:00', '17:30:00', 5, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `statu` int(5) NOT NULL COMMENT '状态(0：禁用，1：正常)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '开发人员', 'developer', '系统开发人员，具有一切功能', 1);
INSERT INTO `role` VALUES (2, '管理员', 'admin', '系统管理员，默认最高权限，不可以编辑和任意修改', 1);
INSERT INTO `role` VALUES (3, '用户', 'normal', '系统用户，具有基础功能', 1);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT 'role表单主键',
  `menu_id` bigint(20) NOT NULL COMMENT 'menu表单主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 262 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (149, 1, 1);
INSERT INTO `role_menu` VALUES (150, 1, 2);
INSERT INTO `role_menu` VALUES (151, 1, 27);
INSERT INTO `role_menu` VALUES (152, 1, 28);
INSERT INTO `role_menu` VALUES (153, 1, 29);
INSERT INTO `role_menu` VALUES (154, 1, 3);
INSERT INTO `role_menu` VALUES (155, 1, 24);
INSERT INTO `role_menu` VALUES (156, 1, 25);
INSERT INTO `role_menu` VALUES (157, 1, 26);
INSERT INTO `role_menu` VALUES (158, 1, 4);
INSERT INTO `role_menu` VALUES (159, 1, 21);
INSERT INTO `role_menu` VALUES (160, 1, 22);
INSERT INTO `role_menu` VALUES (161, 1, 23);
INSERT INTO `role_menu` VALUES (162, 1, 5);
INSERT INTO `role_menu` VALUES (163, 1, 6);
INSERT INTO `role_menu` VALUES (164, 1, 30);
INSERT INTO `role_menu` VALUES (165, 1, 31);
INSERT INTO `role_menu` VALUES (166, 1, 32);
INSERT INTO `role_menu` VALUES (167, 1, 7);
INSERT INTO `role_menu` VALUES (168, 1, 33);
INSERT INTO `role_menu` VALUES (169, 1, 34);
INSERT INTO `role_menu` VALUES (170, 1, 35);
INSERT INTO `role_menu` VALUES (171, 1, 8);
INSERT INTO `role_menu` VALUES (172, 1, 9);
INSERT INTO `role_menu` VALUES (173, 1, 42);
INSERT INTO `role_menu` VALUES (174, 1, 43);
INSERT INTO `role_menu` VALUES (175, 1, 44);
INSERT INTO `role_menu` VALUES (176, 1, 10);
INSERT INTO `role_menu` VALUES (177, 1, 45);
INSERT INTO `role_menu` VALUES (178, 1, 46);
INSERT INTO `role_menu` VALUES (179, 1, 47);
INSERT INTO `role_menu` VALUES (180, 1, 11);
INSERT INTO `role_menu` VALUES (181, 1, 12);
INSERT INTO `role_menu` VALUES (182, 1, 36);
INSERT INTO `role_menu` VALUES (183, 1, 37);
INSERT INTO `role_menu` VALUES (184, 1, 38);
INSERT INTO `role_menu` VALUES (185, 1, 13);
INSERT INTO `role_menu` VALUES (186, 1, 39);
INSERT INTO `role_menu` VALUES (187, 1, 40);
INSERT INTO `role_menu` VALUES (188, 1, 41);
INSERT INTO `role_menu` VALUES (189, 1, 14);
INSERT INTO `role_menu` VALUES (190, 1, 15);
INSERT INTO `role_menu` VALUES (191, 1, 48);
INSERT INTO `role_menu` VALUES (192, 1, 16);
INSERT INTO `role_menu` VALUES (193, 1, 49);
INSERT INTO `role_menu` VALUES (194, 1, 17);
INSERT INTO `role_menu` VALUES (195, 1, 18);
INSERT INTO `role_menu` VALUES (196, 1, 50);
INSERT INTO `role_menu` VALUES (197, 1, 19);
INSERT INTO `role_menu` VALUES (198, 1, 20);
INSERT INTO `role_menu` VALUES (199, 2, 1);
INSERT INTO `role_menu` VALUES (200, 2, 2);
INSERT INTO `role_menu` VALUES (201, 2, 27);
INSERT INTO `role_menu` VALUES (202, 2, 28);
INSERT INTO `role_menu` VALUES (203, 2, 3);
INSERT INTO `role_menu` VALUES (204, 2, 24);
INSERT INTO `role_menu` VALUES (205, 2, 25);
INSERT INTO `role_menu` VALUES (206, 2, 4);
INSERT INTO `role_menu` VALUES (207, 2, 21);
INSERT INTO `role_menu` VALUES (208, 2, 22);
INSERT INTO `role_menu` VALUES (209, 2, 5);
INSERT INTO `role_menu` VALUES (210, 2, 6);
INSERT INTO `role_menu` VALUES (211, 2, 30);
INSERT INTO `role_menu` VALUES (212, 2, 31);
INSERT INTO `role_menu` VALUES (213, 2, 8);
INSERT INTO `role_menu` VALUES (214, 2, 9);
INSERT INTO `role_menu` VALUES (215, 2, 42);
INSERT INTO `role_menu` VALUES (216, 2, 43);
INSERT INTO `role_menu` VALUES (217, 2, 11);
INSERT INTO `role_menu` VALUES (218, 2, 12);
INSERT INTO `role_menu` VALUES (219, 2, 36);
INSERT INTO `role_menu` VALUES (220, 2, 37);
INSERT INTO `role_menu` VALUES (221, 2, 14);
INSERT INTO `role_menu` VALUES (222, 2, 15);
INSERT INTO `role_menu` VALUES (223, 2, 48);
INSERT INTO `role_menu` VALUES (224, 2, 17);
INSERT INTO `role_menu` VALUES (225, 2, 18);
INSERT INTO `role_menu` VALUES (226, 2, 50);
INSERT INTO `role_menu` VALUES (227, 2, 19);
INSERT INTO `role_menu` VALUES (228, 2, 20);
INSERT INTO `role_menu` VALUES (246, 3, 5);
INSERT INTO `role_menu` VALUES (247, 3, 7);
INSERT INTO `role_menu` VALUES (248, 3, 8);
INSERT INTO `role_menu` VALUES (249, 3, 10);
INSERT INTO `role_menu` VALUES (250, 3, 45);
INSERT INTO `role_menu` VALUES (251, 3, 46);
INSERT INTO `role_menu` VALUES (252, 3, 11);
INSERT INTO `role_menu` VALUES (253, 3, 13);
INSERT INTO `role_menu` VALUES (254, 3, 39);
INSERT INTO `role_menu` VALUES (255, 3, 40);
INSERT INTO `role_menu` VALUES (256, 3, 14);
INSERT INTO `role_menu` VALUES (257, 3, 16);
INSERT INTO `role_menu` VALUES (258, 3, 49);
INSERT INTO `role_menu` VALUES (259, 3, 17);
INSERT INTO `role_menu` VALUES (260, 3, 18);
INSERT INTO `role_menu` VALUES (261, 3, 50);

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT 'role表单主键',
  `user_id` bigint(20) NOT NULL COMMENT 'user表单主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (1, 1, 1);
INSERT INTO `role_user` VALUES (52, 2, 52);
INSERT INTO `role_user` VALUES (53, 2, 53);
INSERT INTO `role_user` VALUES (54, 2, 54);
INSERT INTO `role_user` VALUES (55, 3, 55);
INSERT INTO `role_user` VALUES (56, 3, 56);
INSERT INTO `role_user` VALUES (57, 3, 57);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `role` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键角色',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `statu` int(5) NOT NULL COMMENT '用户状态(1正常；0禁用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username`) USING BTREE,
  INDEX `user_role`(`role`) USING BTREE,
  CONSTRAINT `user_role` FOREIGN KEY (`role`) REFERENCES `role` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '王正霆', '$2a$10$m.q9gGV5MEvMC6YiVxzh9eoq9EGq8VBWK7XJ/OUY1baSqmPcGpWme', '开发人员', '670170128@qq.com', '18423963820', 1);
INSERT INTO `user` VALUES (52, '管理员1', '$2a$10$h9Qvd7QFqmfzEy2lE/tQhuLwb58/VvqLuZ7qMggzlcM0l3NqrHtKu', '管理员', '管理员1邮箱', '管理员1手机号', 1);
INSERT INTO `user` VALUES (53, '管理员2', '$2a$10$0eqypN/YnLprneHQIeOhzewwpsT9dlJ8LlHBzcne1A2qLGsmo4m92', '管理员', '管理员2邮箱', '管理员2手机号', 1);
INSERT INTO `user` VALUES (54, '管理员3', '$2a$10$FP63W.jZm6LVME7N1aK5D.D/xVodutIarCAXarrOs1Pt32xs7usoC', '管理员', '管理员3邮箱', '管理员3手机号', 1);
INSERT INTO `user` VALUES (55, '用户1', '$2a$10$yjG.x4gWNmEdE69r8QW2w.KDGP6a0/NDf3HL9CKP5uf.HXYlT5tnm', '用户', '用户1邮箱', '用户1手机号', 1);
INSERT INTO `user` VALUES (56, '用户2', '$2a$10$GPgwvOQpErJnxnabNHlBxO8HOpu8jGcbGhS/m7UefRg35ME1zjKXG', '用户', '用户2邮箱', '用户2手机号', 1);
INSERT INTO `user` VALUES (57, '用户3', '$2a$10$VW4LAa2X/7nwlAr7yi/h4ufLyqLfuvrWYxcuUcLjkgTsbLWPw7KGy', '用户', '用户3邮箱', '用户3手机号', 1);

-- ----------------------------
-- Table structure for village
-- ----------------------------
DROP TABLE IF EXISTS `village`;
CREATE TABLE `village`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `villagename` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小区名称',
  `keyword` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小区地址',
  `lng` float NOT NULL COMMENT '坐标纬度',
  `lat` float NOT NULL COMMENT '坐标经度',
  `statu` int(5) NOT NULL COMMENT '状态(0：禁用，1：正常)',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `villagename`(`villagename`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of village
-- ----------------------------
INSERT INTO `village` VALUES (1, '锦上华庭', '重庆市渝北区红叶路锦上华庭', 106.534, 29.6129, 1, '新世纪超市对面');
INSERT INTO `village` VALUES (2, '阳光今典', '重庆市渝北区龙溪街道佳园路53号', 106.531, 29.608, 1, NULL);
INSERT INTO `village` VALUES (5, '东和春天', '重庆市渝北区松石北路232号 ', 106.411, 29.9583, 1, NULL);
INSERT INTO `village` VALUES (6, '协信天骄城', '重庆市九龙坡区高新区创新大道11号(科技新城彩云湖畔创新大道) ', 106.482, 29.5171, 1, NULL);
INSERT INTO `village` VALUES (7, '明珠上海城', '重庆市梁平区丹桂大道与红桂路交叉口 ', 107.775, 30.6551, 1, NULL);
INSERT INTO `village` VALUES (8, '重庆华南城', '重庆市巴南区东城大道8号', 106.622, 29.3428, 1, NULL);
INSERT INTO `village` VALUES (9, '重庆法苑小区', '重庆市渝北区龙塔街道紫园路69号', 106.542, 29.602, 1, NULL);
INSERT INTO `village` VALUES (10, '重庆长城小区', '重庆市渝中区大坪正街14号', 106.52, 29.5496, 1, NULL);
INSERT INTO `village` VALUES (11, '俊峰山万里', '重庆石井坡217号 ', 106.446, 29.592, 1, NULL);
INSERT INTO `village` VALUES (12, '龙湖紫云赋', '重庆市北碚区润龙路与逊敏路交叉口东120米', 106.553, 29.8369, 1, NULL);
INSERT INTO `village` VALUES (13, '金地自在城', '重庆市大渡口区华福大道与景雅路交叉口 ', 106.448, 29.4257, 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
