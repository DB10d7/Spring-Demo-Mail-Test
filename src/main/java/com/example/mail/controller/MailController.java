package com.example.mail.controller;

import com.example.mail.dto.MailRequest;
import com.example.mail.dto.MailResponse;
import com.example.mail.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MailController {

     private final EmailService emailService;

     @PostMapping("/sendingEmail")
     public MailResponse sendMail(@RequestBody MailRequest request){
         Map<String, Object> map = new HashMap<>();
         map.put("Name", request.getName());
         map.put("location", "Hyderabad, India");

         return emailService.sendEmail(request, map);
     }

}
