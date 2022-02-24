package com.example.viewResolverIssue.controller;

import com.example.viewResolverIssue.Consumer.MyTopicConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    private KafkaTemplate<String, String> template;
    private MyTopicConsumer myTopicConsumer;

    public MainController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer){
        this.template = template;
        this.myTopicConsumer = myTopicConsumer;
    }

    @PostMapping("/kafka/producer")
    public String produce(@RequestParam String message) {
        System.out.println(message);
        template.send("myTopic" , message);
//        template.send("myTopic", message);
        return "index";

    }

    @GetMapping("/show")
    public String show(Model model){
        ArrayList<String> list = (ArrayList<String>) myTopicConsumer.getMessages();
        model.addAttribute("list",list);
        System.out.println(list);
        return "showMessages";
    }

    @GetMapping("kafka/messages")
    public List<String> getMessages(){
        System.out.println("hello");

        return myTopicConsumer.getMessages();
    }


}
