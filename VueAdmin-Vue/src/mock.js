// 引入mockjs
const Mock = require('mockjs')

// 获取 mock.Random 对象
const Random = Mock.Random

let Result = {
    code: 200,
    msg: '操作成功',
    data: null
}

/*Mock.mock( url, post/get , function(options))；
url 表示需要拦截的 URL，
post/get 需要拦截的 Ajax 请求类型
用于生成响应数据的函数*/

Mock.mock('/captcha', 'get', () => {

    Result.data = {
        token: Random.string(32),
        captchaImg: Random.dataImage('120x40', 'p7n5w')
    }
    return Result
})

Mock.mock('/login', 'post', () => {

    // 无法在header中传入数jwt

/*    Result.code = 400
    Result.msg = "验证码错误"*/

    return Result
})

Mock.mock(RegExp('/login/getrolelist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 3,
                "created": "2021-01-04T10:09:14",
                "updated": "2021-01-30T08:19:52",
                "statu": 1,
                "name": "普通用户",
                "code": "normal",
                "remark": "只有基本查看功能",
                "menuIds": []
            },
            {
                "id": 6,
                "created": "2021-01-16T13:29:03",
                "updated": "2021-01-17T15:50:45",
                "statu": 1,
                "name": "超级管理员",
                "code": "admin",
                "remark": "系统默认最高权限，不可以编辑和任意修改",
                "menuIds": []
            },
            {
                "id": 1,
                "created": "2021-01-16T13:29:03",
                "updated": "2021-01-17T15:50:45",
                "statu": 1,
                "name": "测试",
                "code": "test",
                "remark": "系统默认最高权限，不可以编辑和任意修改",
                "menuIds": []
            }
        ],
        "total": 3,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result

})

Mock.mock(RegExp('/login/logon'), 'post', () => {
    return Result
})











Mock.mock('/sys/userInfo', 'get', () => {
    Result.data = {
        id: "1",
        username: "test"
    }

    return Result
})

Mock.mock('/logout', 'post', () => {

    return Result
})

// 主页显示
Mock.mock(RegExp('/index/getvillagelist'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 106.53369,
                "lat": 29.612911,
                "remark": "渝北区红叶路锦上华庭",
            },
            {
                "id": 2,
                "villagename": "阳关金典",
                "keyword": "阳光金典",
                "lng": 106.531435,
                "lat": 29.607992,
                "remark": "阳光金典",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})




// 获取用户菜单以及权限接口
Mock.mock('/sys/menu/nav', 'get', () => {

    //菜单栏信息
    let nav = [
        {
            name: 'SysManga',
            title: '系统管理',
            icon: 'el-icon-s-tools',
            component: '',
            path: '',
            children: [
                {
                    name: 'SysUser',
                    title: '用户管理',
                    icon: 'el-icon-s-custom',
                    path: '/sys/user',
                    component: 'sys_management/User',
                    children: []
                },
                {
                    name: 'SysRole',
                    title: '角色管理',
                    icon: 'el-icon-rank',
                    path: '/sys/role',
                    component: 'sys_management/Role',
                    children: []
                },
                {
                    name: 'SysMenu',
                    title: '菜单管理',
                    icon: 'el-icon-menu',
                    path: '/sys/menu',
                      component: 'sys_management/Menu',
                    children: []
                }
            ]
        },
        {
            name: 'VilManga',
            title: '小区管理',
            icon: 'el-icon-s-platform',
            component: '',
            path: '',
            children: [
                {
                    name: 'VilVillage',
                    title: '小区总览',
                    icon: 'el-icon-house',
                    path: '/vilman/village',
                    component: 'village_management/Village',
                    children: []
                },
                {
                    name: 'VilMyVillage',
                    title: '我的小区',
                    icon: 'el-icon-house',
                    path: '/vilman/myvillage',
                    component: 'village_management/MyVillage',
                    children: []
                }
            ]
        },
        {
            name: 'ParManga',
            title: '车位管理',
            icon: 'el-icon-s-flag',
            component: '',
            path: '',
            children: [
                {
                    name: 'ParPark',
                    title: '车位总览',
                    icon: 'el-icon-full-screen',
                    path: '/parkman/park',
                    component: 'park_management/Park',
                    children: []
                },
                {
                    name: 'ParMyPark',
                    title: '我的车位',
                    icon: 'el-icon-full-screen',
                    path: '/parkman/mypark',
                    component: 'park_management/MyPark',
                    children: []
                }
            ]
        },
        {
            name: 'CarManga',
            title: '车辆管理',
            icon: 'el-icon-s-promotion',
            component: '',
            path: '',
            children: [
                {
                    name: 'CarCar',
                    title: '车辆总览',
                    icon: 'el-icon-truck',
                    path: '/carman/car',
                    component: 'car_management/Car',
                    children: []
                },
                {
                    name: 'CarMyCar',
                    title: '我的车辆',
                    icon: 'el-icon-truck',
                    path: '/carman/mycar',
                    component: 'car_management/MyCar',
                    children: []
                }
            ]
        },
        {
            name: 'OrdManga',
            title: '订单管理',
            icon: 'el-icon-s-order',
            component: '',
            path: '',
            children: [
                {
                    name: 'OrdOrder',
                    title: '订单总览',
                    icon: 'el-icon-tickets',
                    path: '/ordman/order',
                    component: 'order_management/Order',
                    children: []
                },
                {
                    name: 'OrdMyOrder',
                    title: '我的订单',
                    icon: 'el-icon-tickets',
                    path: '/ordman/myorder',
                    component: 'order_management/MyOrder',
                    children: []
                }
            ]
        },
        {
            name: 'LocationView',
            title: '定位显示',
            icon: 'el-icon-location',
            path: '',
            component: '',
            children: [
                {
                    name: 'LocView',
                    title: '定位显示',
                    icon: 'el-icon-location-outline',
                    path: '/locview/locview',
                    component: 'location_view/LocationView',
                    children: []
                }
            ]
        },
        {
            name: 'StatisticsView',
            title: '数据显示',
            icon: 'el-icon-s-data',
            path: '',
            component: '',
            children: [
                {
                    name: 'StaView',
                    title: '数据显示',
                    icon: 'el-icon-pie-chart',
                    path: '/sta/staview',
                    component: 'statistics_view/StatisticsView',
                    children: []
                }
            ]
        }

    ]

    //权限信息
/*    let authoritys = ['sys:user:list', "sys:user:save", "sys:user:delete"]*/
    let authoritys = ['sys:menu:list','sys:menu:save','sys:menu:updata',
        'sys:role:list','sys:role:save','sys:role:updata',
        'sys:user:list','sys:user:save','sys:user:updata',

        'village:village:list','village:village:save','village:village:updata',
        'village:myvillage:list',

        'carman:car:list','carman:car:save','carman:car:updata',
        'carman:mycar:list','carman:mycar:save','carman:mycar:updata',

        'parkman:park:list','parkman:park:save','parkman:park:updata',
        'parkman:mypark:list','parkman:mypark:save','parkman:mypark:updata',

        'ordman:order:list','ordman:order:updata',
        'ordman:myorder:list','ordman:myorder:updata',


        'sta:staview:list',

        'locview:locview:list','locview:locview:save',

    ]

    Result.data = {
        nav: nav,
        authoritys: authoritys
    }

    return Result
})

//////////////// 菜单管理 ////////////////

Mock.mock('/sys/menu/list', 'get', () => {
    let menus = [
        {
            "id": 1,
            "created": "2021-01-15T18:58:18",
            "updated": "2021-01-15T18:58:20",
            "statu": 1,
            "parentId": 0,
            "name": "系统管理",
            "path": "",
            "perms": "sys:manage",
            "component": "",
            "type": 0,
            "icon": "el-icon-s-operation",
            "ordernum": 1,
            "children": [
                {
                    "id": 2,
                    "created": "2021-01-15T19:03:45",
                    "updated": "2021-01-15T19:03:48",
                    "statu": 1,
                    "parentId": 1,
                    "name": "用户管理",
                    "path": "/sys/users",
                    "perms": "sys:user:list",
                    "component": "sys/User",
                    "type": 1,
                    "icon": "el-icon-s-custom",
                    "ordernum": 1,
                    "children": [
                        {
                            "id": 9,
                            "created": "2021-01-17T21:48:32",
                            "updated": null,
                            "statu": 1,
                            "parentId": 2,
                            "name": "添加用户",
                            "path": null,
                            "perms": "sys:user:save",
                            "component": null,
                            "type": 2,
                            "icon": null,
                            "ordernum": 1,
                            "children": []
                        },
                        {
                            "id": 10,
                            "created": "2021-01-17T21:49:03",
                            "updated": "2021-01-17T21:53:04",
                            "statu": 1,
                            "parentId": 2,
                            "name": "修改用户",
                            "path": null,
                            "perms": "sys:user:update",
                            "component": null,
                            "type": 2,
                            "icon": null,
                            "ordernum": 2,
                            "children": []
                        },
                        {
                            "id": 11,
                            "created": "2021-01-17T21:49:21",
                            "updated": null,
                            "statu": 1,
                            "parentId": 2,
                            "name": "删除用户",
                            "path": null,
                            "perms": "sys:user:delete",
                            "component": null,
                            "type": 2,
                            "icon": null,
                            "ordernum": 3,
                            "children": []
                        },
                        {
                            "id": 12,
                            "created": "2021-01-17T21:49:58",
                            "updated": null,
                            "statu": 1,
                            "parentId": 2,
                            "name": "分配角色",
                            "path": null,
                            "perms": "sys:user:role",
                            "component": null,
                            "type": 2,
                            "icon": null,
                            "ordernum": 4,
                            "children": []
                        },
                        {
                            "id": 13,
                            "created": "2021-01-17T21:50:36",
                            "updated": null,
                            "statu": 1,
                            "parentId": 2,
                            "name": "重置密码",
                            "path": null,
                            "perms": "sys:user:repass",
                            "component": null,
                            "type": 2,
                            "icon": null,
                            "ordernum": 5,
                            "children": []
                        }
                    ]
                },
                {
                    "id": 3,
                    "created": "2021-01-15T19:03:45",
                    "updated": "2021-01-15T19:03:48",
                    "statu": 1,
                    "parentId": 1,
                    "name": "角色管理",
                    "path": "/sys/roles",
                    "perms": "sys:role:list",
                    "component": "sys/Role",
                    "type": 1,
                    "icon": "el-icon-rank",
                    "ordernum": 2,
                    "children": []
                },

            ]
        },
        {
            "id": 5,
            "created": "2021-01-15T19:06:11",
            "updated": null,
            "statu": 1,
            "parentId": 0,
            "name": "系统工具",
            "path": "",
            "perms": "sys:tools",
            "component": null,
            "type": 0,
            "icon": "el-icon-s-tools",
            "ordernum": 2,
            "children": [
                {
                    "id": 6,
                    "created": "2021-01-15T19:07:18",
                    "updated": "2021-01-18T16:32:13",
                    "statu": 1,
                    "parentId": 5,
                    "name": "数字字典",
                    "path": "/sys/dicts",
                    "perms": "sys:dict:list",
                    "component": "sys/Dict",
                    "type": 1,
                    "icon": "el-icon-s-order",
                    "ordernum": 1,
                    "children": []
                }
            ]
        }
    ]

    Result.data = menus

    return Result
})

Mock.mock(RegExp('/sys/menu/info/*'), 'get', () => {

    Result.data = {
        "id": 3,
        "statu": 1,
        "parentId": 1,
        "name": "角色管理",
        "path": "/sys/roles",
        "perms": "sys:role:list",
        "component": "sys/Role",
        "type": 1,
        "icon": "el-icon-rank",
        "orderNum": 2,
        "children": []
    }

    return Result
})

Mock.mock(RegExp('/sys/menu/*'), 'post', () => {

    return Result
})


//////////////// 角色管理 ////////////////

Mock.mock(RegExp('/sys/role/list*'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 3,
                "created": "2021-01-04T10:09:14",
                "updated": "2021-01-30T08:19:52",
                "statu": 1,
                "name": "普通用户",
                "code": "normal",
                "remark": "只有基本查看功能",
                "menuIds": []
            },
            {
                "id": 6,
                "created": "2021-01-16T13:29:03",
                "updated": "2021-01-17T15:50:45",
                "statu": 1,
                "name": "超级管理员",
                "code": "admin",
                "remark": "系统默认最高权限，不可以编辑和任意修改",
                "menuIds": []
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result

})

Mock.mock(RegExp('/sys/role/info/*'), 'get', () => {

    Result.data = {
        "id": 6,
        "created": "2021-01-16T13:29:03",
        "updated": "2021-01-17T15:50:45",
        "statu": 1,
        "name": "超级管理员",
        "code": "admin",
        "remark": "系统默认最高权限，不可以编辑和任意修改",
        "menuIds": [3]
    }

    return Result
})

Mock.mock(RegExp('/sys/role/*'), 'post', () => {

    return Result
})


//////////////// 用户管理 ////////////////

Mock.mock(RegExp('/sys/user/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "created": "2021-01-12T22:13:53",
                "updated": "2021-01-16T16:57:32",
                "statu": 1,
                "username": "admin",
                "password": "$2a$10$R7zegeWzOXPw871CmNuJ6upC0v8D373GuLuTw8jn6NET4BkPRZfgK",
                "avatar": "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg",
                "email": "123@qq.com",
                "city": "广州",
                "lastLogin": "2020-12-30T08:38:37",
                "role": "超级管理员",
            },
            {
                "id": 2,
                "created": "2021-01-30T08:20:22",
                "updated": "2021-01-30T08:55:57",
                "statu": 1,
                "username": "test",
                "password": "$2a$10$0ilP4ZD1kLugYwLCs4pmb.ZT9cFqzOZTNaMiHxrBnVIQUGUwEvBIO",
                "avatar": "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg",
                "email": "test@qq.com",
                "city": null,
                "lastLogin": null,
                "role": "普通用户",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/sys/user/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/sys/user/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "created": "2021-01-30T08:20:22",
        "updated": "2021-01-30T08:55:57",
        "statu": 1,
        "username": "test",
        "password": "$2a$10$0ilP4ZD1kLugYwLCs4pmb.ZT9cFqzOZTNaMiHxrBnVIQUGUwEvBIO",
        "avatar": "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg",
        "email": "test@qq.com",
        "city": null,
        "lastLogin": null,
        "role": "普通用户"
    }
    return Result
})

Mock.mock(RegExp('/sys/user/getrolelist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 3,
                "created": "2021-01-04T10:09:14",
                "updated": "2021-01-30T08:19:52",
                "statu": 1,
                "name": "普通用户1",
                "code": "normal",
                "remark": "只有基本查看功能",
                "menuIds": []
            },
            {
                "id": 6,
                "created": "2021-01-16T13:29:03",
                "updated": "2021-01-17T15:50:45",
                "statu": 1,
                "name": "超级管理员",
                "code": "admin",
                "remark": "系统默认最高权限，不可以编辑和任意修改",
                "menuIds": []
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result

})


//////////////// 车辆总览 ////////////////

Mock.mock(RegExp('/carman/car/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "username": "test1",
                "carnum": "渝AHY584",
                "statu": 1,
                "remark": "红色奔驰",
            },
            {
                "id": 2,
                "username": "test2",
                "carnum": "渝AHY584",
                "statu": 1,
                "remark": "红色奔驰",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/carman/car/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/carman/car/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "username": "test2",
        "carnum": "渝AHY584",
        "statu": 0,
        "remark": "红色奔驰",
    }
    return Result
})

Mock.mock(RegExp('/carman/car/getuserlist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 1,
                "username": "test1",

            },
            {
                "id": 2,
                "username": "test2",
            }
        ],
    }
    return Result
})



//////////////// 我的车辆 ////////////////

Mock.mock(RegExp('/carman/mycar/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "username": "test1",
                "carnum": "渝AHY584",
                "statu": 1,
                "remark": "红色奔驰",
            },
            {
                "id": 2,
                "username": "test2",
                "carnum": "渝AHY584",
                "statu": 1,
                "remark": "红色奔驰",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/carman/mycar/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/carman/mycar/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "username": "test2",
        "carnum": "渝AHY584",
        "statu": 1,
        "remark": "红色奔驰",
    }
    return Result
})

Mock.mock(RegExp('/carman/mycar/getuserinfo'), 'get', () => {

    Result.data = {
        id: "1",
        username: "test1"
    }

    return Result
})



//////////////// 小区总览 ////////////////

Mock.mock(RegExp('/vilman/village/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 116.404,
                "lat": 39.915,
                "remark": "渝北区红叶路锦上华庭",
                "statu": 1,

            },
            {
                "id": 2,
                "villagename": "阳关金典",
                "keyword": "阳光金典",
                "lng": 116.404,
                "lat": 39.915,
                "remark": "阳光金典",
                "statu": 1,
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/vilman/village/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/vilman/village/info/*'), 'get', () => {

    Result.data = {
        "id": 1,
        "villagename": "锦上华庭",
        "keyword": "渝北区红叶路锦上华庭",
        "lng": 111,
        "lat": 111,
        "statu": 1,
        "remark": "渝北区红叶路锦上华庭",
    }
    return Result
})



//////////////// 我的小区 ////////////////

Mock.mock(RegExp('/vilman/myvillage/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 116.404,
                "lat": 39.915,
                "statu": 1,
                "remark": "渝北区红叶路锦上华庭",
            },
            {
                "id": 2,
                "villagename": "阳关金典",
                "keyword": "阳光金典",
                "lng": 116.404,
                "lat": 39.915,
                "statu": 1,
                "remark": "阳光金典",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/vilman/myvillage/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/vilman/myvillage/info/*'), 'get', () => {

    Result.data = {
        "id": 1,
        "villagename": "锦上华庭",
        "keyword": "渝北区红叶路锦上华庭",
        "lng": 111,
        "lat": 111,
        "statu": 1,
        "remark": "渝北区红叶路锦上华庭",
    }
    return Result
})


//////////////// 定位显示 ////////////////
Mock.mock(RegExp('/locview/locview/getvillagelist'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 106.53369,
                "lat": 29.612911,
                "remark": "渝北区红叶路锦上华庭",
            },
            {
                "id": 2,
                "villagename": "阳关金典",
                "keyword": "阳光金典",
                "lng": 106.531435,
                "lat": 29.607992,
                "remark": "阳光金典",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/locview/locview/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/locview/locview/getparklist/*'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test1",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 2,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})

Mock.mock(RegExp('/locview/locview/parkinfo/*'), 'get', () => {
    Result.data =
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            }
    return Result
})

Mock.mock(RegExp('/locview/locview/getuserinfo'), 'get', () => {

    Result.data = {
        "id": 1,
        "username": "wzt",
    }
    return Result
})

Mock.mock(RegExp('/locview/locview/getcarlist'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "username": "test1",
                "carnum": "渝AHY584",
                "remark": "红色奔驰",
            },
            {
                "id": 2,
                "username": "test1",
                "carnum": "渝AHY584",
                "remark": "红色奔驰",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})


//////////////// 车位总览 ////////////////

Mock.mock(RegExp('/parkman/park/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 0,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 2,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 2,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            }
        ],
        "total": 3,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/parkman/park/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/parkman/park/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "parknum": "A区2号",
        "villagename": "锦上华庭",
        "username": "test2",
        "statu": 1,
        "price": 5,
        "remark": "地上",
    }
    return Result
})

Mock.mock(RegExp('/parkman/park/getuserlist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 1,
                "username": "test1",

            },
            {
                "id": 2,
                "username": "test2",
            }
        ],
    }
    return Result
})

Mock.mock(RegExp('/parkman/park/getvillagelist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 106.53369,
                "lat": 29.612911,
                "remark": "渝北区红叶路锦上华庭",
            },
            {
                "id": 2,
                "villagename": "阳光今典",
                "keyword": "阳光今典",
                "lng": 106.531435,
                "lat": 29.607992,
                "remark": "阳光金典",
            }
        ],
    }
    return Result
})


//////////////// 我的车位 ////////////////

Mock.mock(RegExp('/parkman/mypark/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 0,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 2,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 2,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            }
        ],
        "total": 3,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})

Mock.mock(RegExp('/parkman/mypark/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/parkman/mypark/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "parknum": "A区2号",
        "villagename": "锦上华庭",
        "username": "test2",
        "statu": 1,
        "price": 5,
        "remark": "地上",
    }
    return Result
})

Mock.mock(RegExp('/parkman/mypark/getuserinfo'), 'get', () => {

    Result.data = {
            "id": 1,
            "username": "test2",
        }
    return Result
})

Mock.mock(RegExp('/parkman/mypark/getvillagelist'), 'get', () => {

    Result.data = {
        "records": [
            {
                "id": 1,
                "villagename": "锦上华庭",
                "keyword": "渝北区红叶路锦上华庭",
                "lng": 106.53369,
                "lat": 29.612911,
                "remark": "渝北区红叶路锦上华庭",
            },
            {
                "id": 2,
                "villagename": "阳光今典",
                "keyword": "阳光今典",
                "lng": 106.531435,
                "lat": 29.607992,
                "remark": "阳光金典",
            }
        ],
    }
    return Result
})





//////////////// 订单总览 ////////////////

Mock.mock(RegExp('/ordman/order/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区1号",
                "statu": 0,
                "lease":"wzt",
                "rent":"wsq",
            },
            {
                "id": 2,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区2号",
                "statu": 1,
                "lease":"wzt",
                "rent":"wsq",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})


Mock.mock(RegExp('/ordman/order/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/ordman/order/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "ordernum":"20230223",
        "carnum":"渝AHY584",
        "villagename": "锦上华庭",
        "parknum": "A区2号",
        "statu": 1,
        "lease":"wzt",
        "rent":"wsq",
    }
    return Result
})


//////////////// 我的订单 ////////////////

Mock.mock(RegExp('/ordman/myorder/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区1号",
                "statu": 0,
                "lease":"wzt",
                "rent":"wsq",
            },
            {
                "id": 2,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区2号",
                "statu": 1,
                "lease":"wzt",
                "rent":"wsq",
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})


Mock.mock(RegExp('/ordman/myorder/*'), 'post', () => {
    return Result
})

Mock.mock(RegExp('/ordman/myorder/info/*'), 'get', () => {

    Result.data = {
        "id": 2,
        "ordernum":"20230223",
        "carnum":"渝AHY584",
        "villagename": "锦上华庭",
        "parknum": "A区2号",
        "statu": 1,
        "lease":"wzt",
        "rent":"wsq",
    }
    return Result
})

Mock.mock(RegExp('/ordman/myorder/getuserinfo'), 'get', () => {
    Result.data = {
        "id": 1,
        "username": "test2",
    }
    return Result
})



//////////////// 数据统计 ////////////////


Mock.mock(RegExp('/sta/staview/ordertotal'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区1号",
                "statu": 0,
                "lease":"wzt",
                "rent":"wsq",
            },
            {
                "id": 2,
                "ordernum":"20230223",
                "carnum":"渝AHY584",
                "villagename": "锦上华庭",
                "parknum": "A区2号",
                "statu": 1,
                "lease":"wzt",
                "rent":"wsq",
            }
        ],
        "total": 10,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})
Mock.mock(RegExp('/sta/staview/usertotal'), 'get', () => {
    Result.data = {
        "records": [
        ],
        "total": 15,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})
Mock.mock(RegExp('/sta/staview/villagetotal'), 'get', () => {
    Result.data = {
        "records": [
        ],
        "total": 20,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})
Mock.mock(RegExp('/sta/staview/parktotal'), 'get', () => {
    Result.data = {
        "records": [
        ],
        "total": 30,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})
Mock.mock(RegExp('/sta/staview/cartotal'), 'get', () => {
    Result.data = {
        "records": [
        ],
        "total": 23,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }
    return Result
})


Mock.mock(RegExp('/sta/staview/parklist'), 'get', () => {
    Result.data = {
        "records": [
            {
                "id": 1,
                "parknum": "A区1号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 2,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 2,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 3,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 4,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 2,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 5,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 6,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
            {
                "id": 7,
                "parknum": "A区2号",
                "villagename": "锦上华庭",
                "username": "test2",
                "statu": 1,
                "price": 5,
                "remark": "地上",
            },
        ],
        "total": 7,
        "size": 10,
        "current": 1,
        "orders": [],
        "optimizeCountSql": true,
        "hitCount": false,
        "countId": null,
        "maxLimit": null,
        "searchCount": true,
        "pages": 1
    }

    return Result
})