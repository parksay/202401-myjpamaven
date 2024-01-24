package myprj.learning.myjpa.web;

import myprj.learning.myjpa.domain.Address;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, value = "index")
    public String indexController() {
        return "index";
    }

}
