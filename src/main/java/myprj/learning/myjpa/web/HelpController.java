package myprj.learning.myjpa.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/help")
public class HelpController {


    @RequestMapping(method = RequestMethod.GET)
    public String helpController(Model model) {
        User user1 = new User("myName", 10);
        User user2 = new User("hello", 20);
        User user3 = new User("내이름은", 30);


        List<User> memberList = Arrays.asList(user1, user2, user3);
        Map<String, User> memberMap = new HashMap<>();
        memberMap.put("userA", user3);

        model.addAttribute("user", user1);
        model.addAttribute("users", memberList);
        model.addAttribute("userMap", memberMap);
        model.addAttribute("data", "가나다라마바사");

        return "help";
    }


    public class User {
        private String username;
        private int userage;

        public User(String username, int age) {
            this.username = username;
            this.userage = age;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUserage() {
            return userage;
        }

        public void setUserage(int userage) {
            this.userage = userage;
        }
    }
}
