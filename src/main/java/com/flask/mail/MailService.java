package com.flask.mail;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/25
 */
public interface MailService {


    /**
     * Send a simple email
     *
     * @param to      recipient
     * @param subject subject
     * @param content content
     */
    void sendTextMail(String to, String subject, String content);
}
