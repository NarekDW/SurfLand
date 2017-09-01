package com.sp.controllers;

import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@RestController
@SessionAttributes("myRequestObject")
public class ModelSessionController {

    @ModelAttribute("myRequestObject") // Called before any controller in this class
    public MyCommandBean addStuffToRequestScope() {
        System.out.println("\n\nInside of addStuffToRequestScope\n");
        MyCommandBean bean = new MyCommandBean("Hello World", 42);
        return bean;
    }

    @RequestMapping("/dosomething")
    public String requestHandlingMethod(Model model, HttpServletRequest request, HttpSession session) {
        System.out.println("\n\n");

        System.out.println("Inside of dosomething handler method");

        System.out.println("\n");
        System.out.println("--- Model data ---");
        Map modelMap = model.asMap();
        for (Object modelKey : modelMap.keySet()) {
            Object modelValue = modelMap.get(modelKey);
            System.out.println(modelKey + " -- " + modelValue);
        }

        System.out.println("\n\n");
        System.out.println("\n\n");
        System.out.println("\n\n");


        /*
         Во время роботы контроллера MyCommandBean не будет еще в HttpServletRequest
         Он появится там ПОСЛЕ того как отработает контроллер!!!
         */
        System.out.println("=== Request data ===");
        Enumeration<String> reqEnum = request.getAttributeNames();
        while (reqEnum.hasMoreElements()) {
            String s = reqEnum.nextElement();
            System.out.println(s + " == " + request.getAttribute(s));
        }

        System.out.println("\n\n");
        System.out.println("\n\n");


        /*
         Во время роботы контроллера MyCommandBean не будет еще в HttpSession
         Он появится там ПОСЛЕ того как отработает контроллер!!!
         */
        System.out.println("*** Session data ***");
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            System.out.println(s);
            System.out.println("**" + session.getAttribute(s));
        }


        System.out.println("\n\n");
        System.out.println("\n\n");

        return "nextpage";
    }


    @RequestMapping("/endsession")
    public String nextHandlingMethod2(SessionStatus status) {
        status.setComplete();
        return "lastpage";
    }
}


@ToString
class MyCommandBean {

    private String message;
    private int count;

    public MyCommandBean(String message, int count) {
        this.message = message;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }
}