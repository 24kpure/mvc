package com.lmj.controller;

import com.lmj.annotation.component.Controller;
import com.lmj.annotation.RequestMethod;
import com.lmj.annotation.scan.RequestMapping;
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

    @RequestMapping(value = "/say",method = {RequestMethod.POST, RequestMethod.GET})
    public void say(){
          log.info("first say ..");
    }

    @RequestMapping("/write")
    public void write(){
        log.info("first write ..");
    }

}

