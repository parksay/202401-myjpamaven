package myprj.learning.myjpa.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import myprj.learning.myjpa.service.OrdersService;
import myprj.learning.myjpa.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrdersService orderService;
    @Autowired
    ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String registOrderGet(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login";
        }
        String userid = (String) session.getAttribute("userid");
        if( userid == null || "".equals(userid) ) {
            return "login";
        }

        model.addAttribute("productList", productService.findAll());
        logger.info("login passed =====================");
        return "regist-order";
    }


    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String registOrderPost() {
        return "regist-complete";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listOrders(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login";
        }
        String userid = (String) session.getAttribute("userid");
        if( userid != null && !"".equals(userid) ) {
            model.addAttribute("orderList", this.orderService.findByUserid(userid));
            return "list-order";
        }
        return "login";
    }


//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    public String listOrders(Model model, String userid) {
//        model.addAttribute("orderList", ordersService.findByUserid(userid));
//        return "list-order";
//    }



}
