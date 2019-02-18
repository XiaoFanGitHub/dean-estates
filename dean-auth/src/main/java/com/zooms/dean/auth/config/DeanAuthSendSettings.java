package com.zooms.dean.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author slacrey
 * @since 2018/1/17
 */
@Configuration
@ConfigurationProperties(prefix = "dean.auth.send")
public class DeanAuthSendSettings {

    private int codeExpTime;
    private SendMessageLimitSettings sendLimit;
    private Mail mail;
    private Sms sms;

    public int getCodeExpTime() {
        return codeExpTime;
    }

    public void setCodeExpTime(int codeExpTime) {
        this.codeExpTime = codeExpTime;
    }

    public SendMessageLimitSettings getSendLimit() {
        return sendLimit;
    }

    public void setSendLimit(SendMessageLimitSettings sendLimit) {
        this.sendLimit = sendLimit;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public static class Mail {

        private SendTemplate template;

        public SendTemplate getTemplate() {
            return template;
        }

        public void setTemplate(SendTemplate template) {
            this.template = template;
        }
    }

    public static class Sms {

        private SendTemplate template;

        public SendTemplate getTemplate() {
            return template;
        }

        public void setTemplate(SendTemplate template) {
            this.template = template;
        }
    }

    public static class SendTemplate {

        private String templateRegister;
        private String templateLogin;
        private String templateAuthSure;
        private String templateChangePassword;
        private String templateInfoModify;
        private String templateCommon;

        public String getTemplateRegister() {
            return templateRegister;
        }

        public void setTemplateRegister(String templateRegister) {
            this.templateRegister = templateRegister;
        }

        public String getTemplateLogin() {
            return templateLogin;
        }

        public void setTemplateLogin(String templateLogin) {
            this.templateLogin = templateLogin;
        }

        public String getTemplateAuthSure() {
            return templateAuthSure;
        }

        public void setTemplateAuthSure(String templateAuthSure) {
            this.templateAuthSure = templateAuthSure;
        }

        public String getTemplateChangePassword() {
            return templateChangePassword;
        }

        public void setTemplateChangePassword(String templateChangePassword) {
            this.templateChangePassword = templateChangePassword;
        }

        public String getTemplateInfoModify() {
            return templateInfoModify;
        }

        public void setTemplateInfoModify(String templateInfoModify) {
            this.templateInfoModify = templateInfoModify;
        }

        public String getTemplateCommon() {
            return templateCommon;
        }

        public void setTemplateCommon(String templateCommon) {
            this.templateCommon = templateCommon;
        }
    }

    public static class SendMessageLimitSettings {

        private int interval;
        private int limitTime;
        private int limitCount;

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getLimitTime() {
            return limitTime;
        }

        public void setLimitTime(int limitTime) {
            this.limitTime = limitTime;
        }

        public int getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(int limitCount) {
            this.limitCount = limitCount;
        }
    }

}
