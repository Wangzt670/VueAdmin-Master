# VueAdmin后端管理系统

## 一、技术选型

### 1.前端

#### （1）环境

##### node.js

v 16.18.1

##### npm

v 8.19.2

#### （2）框架

##### Vue

v 2.6.14

##### vue/cli

v5.0.8

#### （3）插件

##### Router

WebApp的链接路径管理系统，建立起url和页面之间的映射关系

##### Vuex

一个专为 Vue.js 应用程序开发的状态管理模式，为了方便数据的操作而建立的一个临时” 前端数据库“，用于各个组件间共享和检测数据变化。

##### element-ui

v 2.15.0

##### axios

v 1.3.3

一个基于 promise 的 HTTP 库，ajax的封装异步框架

##### qs

v 6.11.0

查询参数序列化和解析库

##### mockjs

v 1.1.0

提供api返回数据，前端测试

##### Baidumap

v 0.21.22

#### （4）组件

##### Echart

v 5.4.1



### 2.后端

#### （1）开发框架

SpringBoot + MyBatis Plus

##### SpringBoot

v 2.4.0

##### MyBatis Plus

v 3.4.1

##### Maven

v 3.5.3

#### （2）权限框架

SpringSecurity + JWT

##### SpringSecurity

##### Jwt

v 0.9.1

#### （3）其它插件与依赖

##### lombok

简化代码工具

##### kaptcha

v 0.0.9

验证码生成工具



## 二、系统功能

### 1.基础模块

#### （1）认证

| 前端      |                                                              | 后端                                               |
| --------- | ------------------------------------------------------------ | -------------------------------------------------- |
| Login.vue | 异步获取验证码请求                                           |                                                    |
|           | 生成返回验证码图片、随机验证码                               | AuthController                                     |
|           | 验证码vlaue、随机验证码key存入redis                          | AuthController                                     |
|           |                                                              |                                                    |
| Login.vue | 提交登录表单                                                 |                                                    |
|           | 校验验证码                                                   | CaptchaFilter                                      |
|           | 失败，跳转到认证失败处理器                                   | loginFailureHandler                                |
|           | 成功，清除redis中验证码vlaue、随机验证码key，放行到下一个Security过滤器 | CaptchaFilter                                      |
|           | 校验用户名、用户密码                                         | Security自带的UsernamePasswordAuthenticationFilter |
|           | 跳转到ProviderManger                                         | Security自带的ProviderManger                       |
|           | 跳转到DaoAuthenticationProvider                              | Security自带的DaoAuthenticationProvider            |
|           | 跳转到UserDetailServiceImpl                                  | UserDetailServiceImpl                              |
|           | 访问数据库查找用户                                           | UserDetailServiceImpl                              |
|           | 用户名不存在，抛出异常                                       | Security自带的UsernameNotFoundException            |
|           | 返回accountUser实例对象，返回DaoAuthenticationProvider       | UserDetailServiceImpl                              |
|           | 解密密码，密码校验                                           | DaoAuthenticationProvider                          |
|           | 密码错误，抛出异常                                           | DaoAuthenticationProvider                          |
|           | 角色禁用、用户禁用，抛出异常                                 | DaoAuthenticationProvider                          |
|           | 密码校验成功，跳转到LoginSuccessHandler                      | LoginSuccessHandler                                |
|           | 生成、返回Jwt                                                | LoginSuccessHandler                                |
| Login.vue | 保存Jwt到store以及localStorage中                             |                                                    |
|           |                                                              |                                                    |
| 二次      |                                                              |                                                    |
| 发送请求  |                                                              |                                                    |
| axios.js  | 请求头中携带token                                            |                                                    |
|           | 没有jwt跳过，交给Security拦截                                | JWTAuthenticationFilter                            |
|           | 有Jwt解析token获取user id                                    | JWTAuthenticationFilter                            |
|           | token过期、异常，抛出异常                                    | JWTAuthenticationFilter                            |
|           | 存入Security ContextHolder                                   | JWTAuthenticationFilter                            |
|           | 失败，跳转到AuthenticationEntryPoint                         | AuthenticationEntryPoint                           |
|           |                                                              |                                                    |

#### （2）授权

| 前端            |                                                              | 后端                                       |
| --------------- | ------------------------------------------------------------ | ------------------------------------------ |
| 用户登录        |                                                              |                                            |
|                 | 通过userService获取权限信息                                  | UserDetailServiceImpl                      |
|                 | 缓存过则直接取出，否则获取权限并存入redis                    | userService                                |
|                 | 权限并存入redis                                              | redis                                      |
|                 | 权限返回UserDetailServiceImpl，存入accountUser实例对象       | UserDetailServiceImpl                      |
|                 |                                                              |                                            |
|                 |                                                              |                                            |
| 调用接口        |                                                              |                                            |
| router/index.js | 判断是否获取过路由（权限），token为空跳转到Login.vue，已获取路由则放行 |                                            |
| router/index.js | 未获取路由（权限）且有token则发送获取导航栏请求，请求头中携带token |                                            |
|                 | 解析token获取user id                                         | JWTAuthenticationFilter                    |
|                 | 通过userService获取权限信息                                  | userService                                |
|                 | 权限并存入redis                                              | redis                                      |
|                 | 返回用户权限信息、菜单信息                                   | JWTAuthenticationFilter                    |
|                 |                                                              |                                            |
|                 |                                                              |                                            |
|                 |                                                              | 注解标识Controller中的方法需要的权限或角色 |
|                 | Security通过FilterSecurityInterceptor匹配URI和权限是否匹配   | FilterSecurityInterceptor                  |
|                 | 有权限则可以访问接口                                         | FilterSecurityInterceptor                  |
|                 | 当无权限的时候返回异常交给JwtAccessDeniedHandler操作类处理   | JwtAccessDeniedHandler                     |

#### （3）动态导航

| 前端            |                                                              | 后端                    |
| --------------- | ------------------------------------------------------------ | ----------------------- |
| router/index.js | 判断是否获取过路由，token为空跳转到Login.vue，已获取路由则放行 |                         |
| router/index.js | 未获取路由且有token则发送获取导航栏请求，请求头中携带token   |                         |
|                 | 解析token获取user id                                         | JWTAuthenticationFilter |
|                 | 获取、返回用户权限信息、菜单信息                             | JWTAuthenticationFilter |
| router/index.js | 将权限信息、菜单信息存入store，将菜单路由加入已有路由，放行到响应请求 |                         |

#### （4）注册

| 前端      |                                   | 后端           |
| --------- | --------------------------------- | -------------- |
| Login.vue | 发送注册请求                      |                |
|           | 调用roleService                   | AuthController |
|           | 获取role数据返回Login.vue         | roleService    |
| Login.vue | 自身先进行表格校验                |                |
| Login.vue | 发送提交注册表单请求              |                |
|           | 设置默认密码加密，调用userService | AuthController |
|           | 进行注册，返回成功信息            | userService    |

#### （5）退出

| 前端     |                                           | 后端                        |
| -------- | ----------------------------------------- | --------------------------- |
| Home.vue | 发送登出请求                              |                             |
|          | 登出                                      | Security自带的LogoutHandler |
|          | 登出成功，跳转到JwtLogoutSuccessHandler   | JwtLogoutSuccessHandler     |
|          | 清除请求头中Jwt，store、localStorage中Jwt | JwtLogoutSuccessHandler     |

#### （6）修改密码

| 前端           |                          | 后端           |
| -------------- | ------------------------ | -------------- |
| UserCenter.vue | 填写重置密码表单         |                |
|                | 新密码与确认密码是否一致 |                |
|                | 新密码长度是否规范       |                |
|                | 提交重置密码表单         |                |
|                | 校验旧密码是否正确       | UserController |
|                | 正确则更新密码           | UserController |
| UserCenter.vue | 错误返回错误提示         | UserController |

### 2.核心功能模块

其中，系统管理中的角色管理、用户管理、菜单管理，以及小区管理、车位管理、车辆管理中的表单与查询、新增与编辑两大功能的设计逻辑大体一致，首先进行统一设计。再对核心功能模块下各模块中的特别功能进行设计阐述。

#### （1）表单与查询

| 前端     |                        | 后端           |
| -------- | ---------------------- | -------------- |
| 主体.Vue | 发送表单更新或查询请求 |                |
|          | 调用主体Service        | 主体Controller |
|          | 查询数据库，返回数据   | 主体Service    |
| 主体.Vue | 展示相应表单           |                |

#### （2）新增与编辑

| 前端     |                                                  | 后端           |
| -------- | ------------------------------------------------ | -------------- |
| 主体.Vue | 点击编辑，发送单个主体查询信息请求               |                |
|          | 调用主体Service                                  | 主体Controller |
|          | 查询数据库，返回数据                             | 主体Service    |
|          | （表单中存在其它主体对象则发送相应查询信息请求） |                |
| 主体.Vue | 回显新增/编辑表单                                |                |
| 主体.Vue | 填写新增/编辑表单                                |                |
| 主体.Vue | 校验表单                                         |                |
| 主体.Vue | 提交表单                                         |                |
|          | 调用主体Service                                  | 主体Controller |
|          | 存入数据到数据库                                 | 主体Service    |
| 主体.Vue | 成功，发送更新表单请求                           |                |

#### （3）角色管理-分配权限

| 前端     |                                              | 后端            |
| -------- | -------------------------------------------- | --------------- |
| Role.vue | 点击编辑，发送对应角色的查询信息请求         |                 |
|          | 通过roleService获取role后调用roleMenuService | RoleController  |
|          | 获取对应角色拥有的权限与菜单，返回数据       | roleMenuService |
| Role.vue | 回显角色拥有的权限与菜单，标记节点           |                 |
| Role.vue | 提交权限表，调用roleMenuService、userService | RoleController  |
|          | 存入新的权限表                               | roleMenuService |
|          | 删除缓存，redis中对应角色的权限信息          | userService     |
|          | 返回成功信息                                 | RoleController  |

#### （4）订单管理-编辑订单

| 前端      |                                                          | 后端             |
| --------- | -------------------------------------------------------- | ---------------- |
| Order.vue | 点击编辑，发送对应订单的查询信息请求                     |                  |
|           | 调用IndentService                                        | IndentController |
|           | 查询数据库                                               | IndentService    |
| Order.vue | 若订单状态为已结束则返回提示信息不可编辑                 | IndentService    |
| Order.vue | 进行中则返回数据                                         | IndentService    |
| Order.vue | 回显状态                                                 |                  |
| Order.vue | 将订单置为已结束状态，得到结束时间，得到订单总时长       |                  |
|           | 调用parkService、carService                              | IndentController |
|           | 对应车位状态置为空闲，得到车位价格信息到IndentController | parkService      |
|           | 对应车辆状态置为空闲                                     | carService       |
|           | 计算得到订单总价，调用IndentService                      | IndentController |
|           | 订单状态、结束时间、总价更新，存入数据库                 | IndentService    |
|           | 返回成功信息                                             | IndentController |
| Order.vue | 成功，发送更新表单请求                                   |                  |

#### （5）定位查找-创建订单

| 前端             |                                                    | 后端                  |
| ---------------- | -------------------------------------------------- | --------------------- |
| LocationView.vue | 获取小区信息                                       |                       |
|                  | 调用villageService                                 | LoctionViewController |
|                  | 返回状态正常的小区信息                             | villageService        |
| LocationView.vue | 根据小区经纬度渲染地图标点                         |                       |
| LocationView.vue | 点击小区标点，发送查询对应小区车位详情请求         |                       |
|                  | 调用parkService                                    | LoctionViewController |
|                  | 返回状态空闲的车位信息                             | parkService           |
| LocationView.vue | 显示表单                                           |                       |
| LocationView.vue | 点击创建订单，获取订单开始时间、获取车辆、用户信息 |                       |
|                  | carService、userService                            | LoctionViewController |
|                  | 返回出租人信息                                     | userService           |
|                  | 返回空闲车辆                                       | carService            |
| LocationView.vue | 显示新建订单对话框，填充信息                       |                       |
| LocationView.vue | 提交表单                                           |                       |
|                  | 订单状态置为进行中                                 | LoctionViewController |
|                  | 对应车位状态置占用                                 | parkService           |
|                  | 对应车辆状态置占用                                 | carService            |
|                  | 存入新订单                                         | indentService         |
|                  | 返回成功信息                                       | LoctionViewController |
| LocationView.vue | 成功，发送更新表单请求                             |                       |

### 3.辅助模块-数据显示

| 前端               |                          | 后端                 |
| ------------------ | ------------------------ | -------------------- |
| StatisticsView.vue | 发送获取数据请求         |                      |
|                    | 调用所有主体Service      | StatisticsController |
|                    | 返回数据                 | 所有主体Service      |
| StatisticsView.vue | 展示数据统计表、统计饼图 |                      |
