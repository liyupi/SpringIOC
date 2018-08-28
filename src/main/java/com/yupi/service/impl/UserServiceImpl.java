package com.yupi.service.impl;

import com.yupi.service.OrderService;
import com.yupi.service.UserService;
import com.yupi.spring.annotation.SpringBean;
import com.yupi.spring.annotation.SpringResource;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/8/28 22:25
 */
@SpringBean
public class UserServiceImpl implements UserService {

    @SpringResource
    private OrderService orderServiceImpl;

    public void init() {
        System.out.println("bean init success");
    }

    public void di(){
        init();
        orderServiceImpl.add();
    }
}
