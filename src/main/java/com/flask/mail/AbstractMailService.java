package com.flask.mail;

import com.flask.framework.annotation.Autowired;
import com.flask.framework.annotation.NonNull;
import com.flask.framework.javamail.JavaMailSender;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/25
 */
public abstract class AbstractMailService implements MailService{

    private JavaMailSender cachedMailSender;

    //private JavaMailSender cachedMailSender;

    ///**
    // * Get java mail sender.
    // *
    // * @return java mail sender
    // */
    @NonNull
    private synchronized JavaMailSender getMailSender() {
        if (this.cachedMailSender == null) {
            // create mail sender factory
            MailSenderFactory mailSenderFactory = new MailSenderFactory();
            // get mail sender
            this.cachedMailSender = mailSenderFactory.getMailSender();
        }

        return this.cachedMailSender;
    }
}
