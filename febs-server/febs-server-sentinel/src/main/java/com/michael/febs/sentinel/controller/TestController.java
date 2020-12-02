package com.michael.febs.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: michael
 * @create: 2020/11/30 10:35
 */
@RestController
public class TestController {
   @GetMapping(value = "/hello")public String hello() {
      return "Hello Sentinel";}
}
