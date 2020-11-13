package com.lmj.controller;

import com.lmj.annotation.component.Controller;
import com.lmj.annotation.RequestMethod;
import com.lmj.annotation.component.RequestParam;
import com.lmj.annotation.scan.RequestBody;
import com.lmj.annotation.scan.RequestMapping;
import com.lmj.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lmj
 * @date 2019/6/22 21:55
 */
@Slf4j
@Controller
@RequestMapping("/first")
public class FirstController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstController.class);

    @RequestMapping(value = "/say", method = {RequestMethod.POST, RequestMethod.GET})
    public void say() {
        log.info("first say ..");
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public Student get(@RequestParam("name") String nameParam) {
        Student student = new Student();
        student.setAge(10);
        student.setName(nameParam);
        student.setScore(97);

        return student;
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public Student write(@RequestBody Student student) {
        return student;
    }

}

