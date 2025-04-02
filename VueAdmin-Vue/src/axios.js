//自定义Axios实例

import axios from "axios";
import router from "./router";
import Element from "element-ui"

//开发环境
axios.defaults.baseURL = "http://localhost:8081"

//部署环境
//axios.defaults.baseURL = "http://8.130.82.204:8081"


const request = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': "application/json; charset=utf-8"
    }
})

//前置拦截
//统一为所有需要权限的请求装配上header的token信息
request.interceptors.request.use(config => {
    //覆盖token,请求头带上token
    config.headers['Authorization'] = localStorage.getItem("token")
    return config
})

//后置拦截
//判断status.code和error.response.status
//如果是401未登录没权限的就调到登录页面，其他的就直接弹窗显示错误
request.interceptors.response.use(
    response => {

/*        console.log("response ->" + response)*/
        //拦截结果
        let res = response.data
        //判断状态码是否为200
        if (res.code === 200) {
            //正常，继续向下运行
            return response
        } else {
            //判断系统异常还是验证码错误
            Element.Message.error(!res.msg ? '系统异常' : res.msg)
            return Promise.reject(response.data.msg)
        }
    },
    error => {

/*        console.log(error)*/

        if (error.response.data) {
            error.massage = error.response.data.msg
        }

        if (error.response.status === 401) {
            router.push("/login")
        }

        Element.Message.error(error.massage, {duration: 3000})
        return Promise.reject(error)
    }
)

export default request