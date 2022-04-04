package vn.com.multiplechoice.business.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import vn.com.multiplechoice.dao.model.User;

public interface MailService {
    public void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

}
