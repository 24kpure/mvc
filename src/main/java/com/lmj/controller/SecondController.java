package com.lmj.controller;

import com.lmj.annotation.component.Controller;
import com.lmj.annotation.RequestMethod;
import com.lmj.annotation.scan.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lmj
 * @date 2019/6/22 21:55
 */
@Slf4j
@Controller
@RequestMapping(value = "/second",method = RequestMethod.POST)
public class SecondController {

    @RequestMapping("/say")
    public void say(){
          log.info("second say ..");
    }

    @RequestMapping("/write")
    public void write(){
        log.info("second write ..");
    }

}

