package com.lmj.controller;

import com.lmj.annotation.component.RestController;
import com.lmj.annotation.scan.RequestMapping;
import com.lmj.pojo.Student;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: lmj
 * @Description: test restController function
 * @Date: Create in 22:10 2019-12-29
 **/
@Slf4j
@RequestMapping("/third")
@RestController
public class ThirdController {

    @RequestMapping("/say")
    public Student say() {
        Student student = new Student();
        student.setAge(10);
        student.setName("name");
        student.setScore(97);
        log.info("first write ..");
        return student;
    }
}