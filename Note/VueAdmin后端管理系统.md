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

### 1.登录模块

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

### 2.系统管理

#### （1）用户管理

#### （2）角色管理

#### （3）菜单管理

### 3.小区管理

#### （1）更新或查询

#### （2）新增或编辑

### 4.车位管理

### 5.车辆管理

### 6.订单管理

### 7.定位显示

### 8.数据显示

## 三、目录
