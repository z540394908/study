package com.zzy.aop.easyscheduler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/app")
public class sendController {

    @GetMapping("/send")
    public String send(@RequestParam("index") Integer index) {
        System.out.println("-----------------------------send方法正在执行---------------------------------");
        List<Object> list = new ArrayList<>();
        if (index != null) {
            list.get(index);
        }
        return "我是返回值";
    }
}
