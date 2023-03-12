package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Car;
import com.vueadmin.entity.Order;
import com.vueadmin.entity.Park;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/ordman")
public class OrderController extends BaseController {


//    @GetMapping("/order/info/{id}")
//    @PreAuthorize("hasAuthority('ordman:order:list')")
//    public Result info(@PathVariable("id") Long id) {
//
//        Order order = orderService.getById(id);
//
//        return Result.succ(order);
//    }

//    @GetMapping("/order/list")
//    @PreAuthorize("hasAuthority('ordman:order:list')")
//    public Result list(String ordernum) {
//
//        Page<Order> pageData = orderService.page(getPage(),
//                new QueryWrapper<Order>()
//                        .like(StrUtil.isNotBlank(ordernum),"ordernum",ordernum)
//        );
//
//        return Result.succ(pageData);
////        return Result.succ("");
//    }

//    @PostMapping("/order/update")
//    @PreAuthorize("hasAuthority('ordman:order:update')")
//    public Result updata(@Validated @RequestBody Order order) {
//
//        orderService.updateById(order);
//
//        return Result.succ(order);
//    }


}
