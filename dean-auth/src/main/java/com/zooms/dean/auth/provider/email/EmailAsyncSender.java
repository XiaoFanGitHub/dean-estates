package com.zooms.dean.auth.provider.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;

@Service
@EnableAsync
public class EmailAsyncSender {

    private static final boolean ISHTML = true;
    private static final boolean ISMULTIPART = true;
    private static final String encoding = "UTF-8";

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MessageContentBuilder messageContentBuilder;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendMail(String[] recipients, String subject, String templateName, Map<String, Object> datas, String[] attachments) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, ISMULTIPART, encoding);
            composeMessageHeader(recipients, subject, attachments, messageHelper);
            messageHelper.setText(messageContentBuilder.buildMessage(templateName, datas), ISHTML);
        };

        mailSender.send(mimeMessagePreparator);
    }

    private void composeMessageHeader(String[] recipients, String subject, String[] attachments,
                                      MimeMessageHelper messageHelper) throws MessagingException {

        messageHelper.setFrom(from);
        messageHelper.setTo(recipients);
        messageHelper.setSubject(subject);
        if (attachments != null) {
            for (String filename : attachments) {
                FileSystemResource file = new FileSystemResource(filename);
                messageHelper.addAttachment(file.getFilename(), file);
            }
        }
    }

}
