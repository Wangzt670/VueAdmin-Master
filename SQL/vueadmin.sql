/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : vueadmin

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 01/03/2023 00:15:01
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
  `statu` int(5) NOT NULL COMMENT '状态(0：禁用，1：正常)',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `carnum`(`carnum`) USING BTREE,
  INDEX `car_username`(`username`) USING BTREE,
  CONSTRAINT `car_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1, '渝AHY584', '王正霆', 1, '红色奔驰');
INSERT INTO `car` VALUES (2, '渝AHY583', '王正霆', 1, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `menu` VALUES (22, 4, '编辑菜单', 'sys:menu:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (23, 4, '删除菜单', 'sys:menu:dalete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (24, 3, '新建角色', 'sys:role:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (25, 3, '编辑角色', 'sys:role:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (26, 3, '删除角色', 'sys:role:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (27, 2, '新建用户', 'sys:user:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (28, 2, '编辑用户', 'sys:user:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (29, 2, '删除用户', 'sys:user:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (30, 6, '总览-新建小区', 'vilman:village:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (31, 6, '总览-编辑小区', 'vilman:village:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (32, 6, '总览-删除小区', 'vilman:village:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (33, 7, '我的-新建小区', 'vilman:myvillage:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (34, 7, '我的-编辑小区', 'vilman:myvillage:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (35, 7, '我的-删除小区', 'vilman:myvillage:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (36, 12, '总览-新建车辆', 'carman:car:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (37, 12, '总览-编辑车辆', 'carman:car:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (38, 12, '总览-删除车辆', 'carman:car:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (39, 13, '我的-新建车辆', 'carman:mycar:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (40, 13, '我的-编辑车辆', 'carman:mycar:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (41, 13, '我的-删除车辆', 'carman:mycar:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (42, 9, '总览-新建车位', 'parkman:park:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (43, 9, '总览-编辑车位', 'parkman:park:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (44, 9, '总览-删除车位', 'parkman:park:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (45, 10, '我的-新建车位', 'parkman:mypark:save', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (46, 10, '我的-编辑车位', 'parkman:mypark:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (47, 10, '我的-删除车位', 'parkman:mypark:delete', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (48, 15, '总览-编辑订单', 'ordman:order:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (49, 16, '我的-编辑订单', 'ordman:myorder:updata', NULL, 2, NULL, NULL);
INSERT INTO `menu` VALUES (50, 18, '创建订单', 'locview:locview:save', NULL, 2, NULL, NULL);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ordernum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `carnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键车牌号',
  `villagename` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键小区名称',
  `parknum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键车位编号',
  `lease` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键出租人',
  `rent` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键租借人',
  `statu` int(5) NOT NULL COMMENT '状态(0：已结束；1：进行中)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ordernum`(`ordernum`) USING BTREE,
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
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, '2023/2/27 18:39:22', '渝AHY584', '锦上华庭', 'A区1号', '王正霆', '杨帆', 0);
INSERT INTO `order` VALUES (2, '2023/2/27 18:41:31', '渝AHY583', '锦上华庭', 'A区2号', '王正霆', '王开发', 1);

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
  `price` int(5) NOT NULL COMMENT '价格',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `parknum`(`parknum`) USING BTREE,
  INDEX `park_villagename`(`villagename`) USING BTREE,
  INDEX `park_username`(`username`) USING BTREE,
  CONSTRAINT `park_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `park_villagename` FOREIGN KEY (`villagename`) REFERENCES `village` (`villagename`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of park
-- ----------------------------
INSERT INTO `park` VALUES (1, 'A区1号', '锦上华庭', '王正霆', 1, 5, '地下车位');
INSERT INTO `park` VALUES (2, 'A区2号', '锦上华庭', '王正霆', 0, 5, '地下车位');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'admin', '系统管理员，默认最高权限，不可以编辑和任意修改');
INSERT INTO `role` VALUES (2, '用户', 'normal', '系统用户，具有基础功能');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT 'role表单主键',
  `menu_id` bigint(20) NOT NULL COMMENT 'menu表单主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 1, 3);
INSERT INTO `role_menu` VALUES (4, 1, 4);
INSERT INTO `role_menu` VALUES (5, 1, 5);
INSERT INTO `role_menu` VALUES (6, 1, 6);
INSERT INTO `role_menu` VALUES (7, 1, 7);
INSERT INTO `role_menu` VALUES (8, 1, 8);
INSERT INTO `role_menu` VALUES (9, 1, 9);
INSERT INTO `role_menu` VALUES (10, 1, 10);
INSERT INTO `role_menu` VALUES (11, 1, 11);
INSERT INTO `role_menu` VALUES (12, 1, 12);
INSERT INTO `role_menu` VALUES (13, 1, 13);
INSERT INTO `role_menu` VALUES (14, 1, 14);
INSERT INTO `role_menu` VALUES (15, 1, 15);
INSERT INTO `role_menu` VALUES (16, 1, 16);
INSERT INTO `role_menu` VALUES (17, 1, 17);
INSERT INTO `role_menu` VALUES (18, 1, 18);
INSERT INTO `role_menu` VALUES (19, 1, 19);
INSERT INTO `role_menu` VALUES (20, 1, 20);
INSERT INTO `role_menu` VALUES (21, 2, 1);
INSERT INTO `role_menu` VALUES (22, 2, 2);
INSERT INTO `role_menu` VALUES (23, 2, 3);
INSERT INTO `role_menu` VALUES (24, 2, 4);
INSERT INTO `role_menu` VALUES (25, 2, 5);
INSERT INTO `role_menu` VALUES (26, 2, 6);
INSERT INTO `role_menu` VALUES (27, 2, 7);
INSERT INTO `role_menu` VALUES (28, 2, 8);
INSERT INTO `role_menu` VALUES (29, 2, 9);
INSERT INTO `role_menu` VALUES (30, 2, 10);
INSERT INTO `role_menu` VALUES (31, 2, 11);
INSERT INTO `role_menu` VALUES (32, 2, 12);
INSERT INTO `role_menu` VALUES (33, 2, 13);
INSERT INTO `role_menu` VALUES (34, 2, 14);
INSERT INTO `role_menu` VALUES (35, 2, 15);
INSERT INTO `role_menu` VALUES (36, 2, 16);
INSERT INTO `role_menu` VALUES (37, 2, 17);
INSERT INTO `role_menu` VALUES (38, 2, 18);
INSERT INTO `role_menu` VALUES (39, 2, 19);
INSERT INTO `role_menu` VALUES (40, 2, 20);

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT 'role表单主键',
  `user_id` bigint(20) NOT NULL COMMENT 'user表单主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (1, 1, 1);
INSERT INTO `role_user` VALUES (2, 2, 2);
INSERT INTO `role_user` VALUES (3, 2, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `statu` int(5) NOT NULL COMMENT '用户状态(1正常；0禁用)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '王正霆', '123456', '670170128@qq.com', '18423963820', 1);
INSERT INTO `user` VALUES (2, '王开发', '123456', NULL, NULL, 1);
INSERT INTO `user` VALUES (3, '杨帆', '123456', NULL, NULL, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of village
-- ----------------------------
INSERT INTO `village` VALUES (1, '锦上华庭', '重庆市渝北区红叶路锦上华庭', 106.534, 29.6129, 1, '新世纪超市对面');

SET FOREIGN_KEY_CHECKS = 1;
