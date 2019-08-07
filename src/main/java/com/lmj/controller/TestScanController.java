package com.lmj.controller;

import com.lmj.annotion.RequestMethod;
import com.lmj.annotion.scan.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 11:56 2019-08-07
 **/
@Slf4j
@RequestMapping(value = "/second",method = RequestMethod.POST)
public class TestScanController {
}