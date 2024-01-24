package myprj.learning.myjpa.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import myprj.learning.myjpa.domain.Address;
import myprj.learning.myjpa.domain.Album;
import myprj.learning.myjpa.domain.Book;
import myprj.learning.myjpa.domain.Member;
import myprj.learning.myjpa.service.MemberService;
import myprj.learning.myjpa.service.OrdersService;
import myprj.learning.myjpa.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @RequestMapping(method = RequestMethod.GET, value = "/regist")
    public String registMember(Model model) {
        return "regist-member";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/regist")
    public String registMember(Model model,
                               String userid,
                               String name,
                               String psw,
                               String city,
                               String street,
                               String detail ) {
        Address address = new Address(city, street, detail);
        Member member = new Member(name, userid, psw, address);
        logger.info(this.getClass().getSimpleName());
        logger.info(member.toString());
        this.memberService.joinMember(member);
        model.addAttribute("member", member);

        return "regist-complete";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String listMember(Model model) {
        model.addAttribute("memberList", this.memberService.findMembers());
        return "list-member";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Model model) {
        return "login";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String loginMember(HttpServletRequest request, Model model, String userid, String psw) {
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);  // Session이 없으면 생성
        if(this.memberService.login(userid, psw)) {
            session.setAttribute("userid", userid);
            session.setMaxInactiveInterval(60 * 5);
        } else {
            model.addAttribute("msg", "회원 정보를 확인해 주세요");
            return "login";
        }
        return "index";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting() {
        Address address = new Address("seoul", "jongro", "building");
        Member member1 = new Member("123", "123", "123", address);
        Album product2 = new Album();
        product2.setTitle("album1");
        product2.setPrice(23456);
        product2.setStock(8);
        product2.setArtist("artist1");

        Book product3 = new Book();
        product3.setTitle("book1");
        product3.setPrice(34567);
        product3.setStock(7);
        product3.setAuthor("author1");
        memberService.joinMember(member1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);

        return "index";
    }
}
