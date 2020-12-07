package com.flask.mail;


import com.flask.framework.annotation.NonNull;
import com.flask.framework.javamail.JavaMailSender;
import com.flask.framework.javamail.JavaMailSenderImpl;

/**
 * Description: Java mail sender factory.
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/25
 */
public class MailSenderFactory {


    /**
     * Get java mail sender.
     *
     * @return java mail sender
     */
    @NonNull
    public  JavaMailSender getMailSender()  {
        // create mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        return mailSender;
    }
}
