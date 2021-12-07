package com.example.mail.service;

import com.example.mail.dto.MailRequest;
import com.example.mail.dto.MailResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public MailResponse sendEmail(MailRequest request, Map<String, Object> model){
        MailResponse response= new MailResponse();
        MimeMessage message= sender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template template = config.getTemplate("email-template.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            sender.send(message);

            response.setMessage("Mail Send To : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        }catch(MessagingException | IOException | TemplateException e){
            response.setMessage("Mail Sending Failure : " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }
}
