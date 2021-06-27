package vn.com.multiplechoice.business.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface MailService {
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}