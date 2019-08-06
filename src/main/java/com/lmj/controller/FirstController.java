package com.lmj.controller;

import com.lmj.annotion.component.Controller;
import com.lmj.annotion.RequestMethod;
import com.lmj.annotion.scan.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lmj
 * @date 2019/6/22 21:55
 */
@Slf4j
@Controller
@RequestMapping("/first")
public class FirstController {

    @RequestMapping(value = "/say",method = {RequestMethod.POST, RequestMethod.GET})
    public void say(){
          log.info("first say ..");
    }

    @RequestMapping("/write")
    public void write(){
        log.info("first write ..");
    }

}

