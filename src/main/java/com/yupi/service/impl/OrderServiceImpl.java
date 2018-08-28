package com.yupi.service.impl;

import com.yupi.service.OrderService;
import com.yupi.spring.annotation.SpringBean;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/8/28 22:24
 */
@SpringBean
public class OrderServiceImpl implements OrderService {
    public void add() {
        System.out.println("dependency injection success");
    }
}
