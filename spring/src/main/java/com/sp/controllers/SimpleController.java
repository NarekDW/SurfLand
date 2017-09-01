package com.sp.controllers;

import com.sp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class SimpleController {

    // FOR @SessionAttributes("user")
    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    // Web-Services

    /**
     * produces - it's what format we return
     * consumes - it's what format we consume
     * RequestParam String name = 'http://localhost:8080/...?name=some_name'
     *
     * @return JSON format
     */
    @RequestMapping(value = "/json", produces = "application/json")
    @ResponseBody
    public User main(
            @RequestParam String name,
            ModelMap map // For accessible from @SessionAttributes("user")
    ) {
        User user = new User();
        user.setFirstName(name)
                .setLastName("Ivanov")
                .setAge(25);

        map.addAttribute("user", user);

        return user;
    }

    @RequestMapping(value = "/fromsession", produces = "application/json")
    @ResponseBody
//    HttpSession session, SessionStatus status
    public User sessionAttributesTest(@ModelAttribute("user") User user) {

        System.out.println(user);

        return user;
    }


    /**
     * @return XML format
     */
    @RequestMapping(value = "/xml", produces = "application/xml")
    @ResponseBody
    public User mainXML(@RequestParam String name) {
        User user = new User();
        user.setFirstName(name)
                .setLastName("Ivanov")
                .setAge(25);

        return user;
    }

    @RequestMapping("/")
    public ResponseEntity<String> main() {
        // HttpStatus.ACCEPTED = 202
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    // ERROR
//    java.lang.IllegalStateException: Ambiguous mapping found. Cannot map 'simpleController' bean method
//    @RequestMapping("/")
//    public ResponseEntity<String> main2() {
//        // HttpStatus.ACCEPTED = 202
//        System.out.println("\n\n\n2222222\n\n\n");
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }


    @RequestMapping(value = "/consume", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> consume(@RequestBody User user) {
        System.out.println(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
