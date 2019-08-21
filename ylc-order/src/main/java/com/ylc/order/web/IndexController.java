package com.ylc.order.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Date: 2019/8/11
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index() throws Exception {
        return "index";
    }
}
