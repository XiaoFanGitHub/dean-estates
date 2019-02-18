package com.zooms.dean.auth.provider.email;

import com.zooms.dean.auth.exceptions.SendLimitException;
import com.zooms.dean.auth.provider.MessageSender;
import com.zooms.dean.common.web.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.zooms.dean.auth.provider.MessageConstant.PARAMS_SUBJECT;

@Component
public class EmailMessageSender implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(EmailMessageSender.class);

    @Autowired
    private EmailAsyncSender emailAsyncSender;

    @Override
    public View<String> sendMessage(String address, Map<String, Object> params, String templateCode) throws SendLimitException {

        try {
            String subject = (String) params.get(PARAMS_SUBJECT);
            emailAsyncSender.sendMail(new String[]{address}, subject, templateCode, params, null);
            return View.ofOk("verification code send is success");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return View.ofError("verification code send is failed");
        }
    }

}
