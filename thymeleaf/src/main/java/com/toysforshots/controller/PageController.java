package com.toysforshots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 0007195 on 17/05/2017.
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }


}
