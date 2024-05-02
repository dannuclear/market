package ru.bisoft.market.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClientForwardController {

    //@RequestMapping(value = {"/{path:^(?!api|public)[^\\.]*}", "/**/{path:^(?!api|public).*}/{path:[^\\.]*}"})
    @GetMapping(value = {"/{path:^(?!api).*}/**/{path:[^\\.]*}", "/{path:^(?!api)[^\\.]*}"})
    public String forward(@PathVariable String path) {
        return "forward:/";
    }
}
