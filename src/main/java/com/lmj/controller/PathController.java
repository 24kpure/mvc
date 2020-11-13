package com.lmj.controller;

import com.lmj.annotation.component.PathValue;
import com.lmj.annotation.component.RestController;
import com.lmj.annotation.scan.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:38 下午 2020/3/18
 **/
@Slf4j
@RestController
@RequestMapping("/path")
public class PathController {
    @RequestMapping("/{name}")
    public String getName(@PathValue("name") String name) {
        return "myName is :" + name;
    }

    @RequestMapping("/{name}/{age}")
    public String getNameAndAge(@PathValue("name") String name, @PathValue("age") Integer age) {
        return "myName is :" + name + " age:" + age;
    }

    @RequestMapping("/{name}/forSex")
    public String getNameAndSet(@PathValue("name") String name, String sex) {
        return "myName is :" + name + " sex " + sex;
    }

}