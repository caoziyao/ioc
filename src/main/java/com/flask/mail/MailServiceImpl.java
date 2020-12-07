package com.flask.mail;

import com.flask.framework.annotation.Autowired;
import com.flask.framework.javamail.JavaMailSender;
import com.flask.framework.javamail.SimpleMailMessage;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/25
 */
public class MailServiceImpl extends AbstractMailService {


    @Override
    public void sendTextMail(String to, String subject, String content) {
        //SimpleMailMessage message = new SimpleMailMessage();
        //message.setFrom(from);
        //message.setTo(to);
        //message.setSubject(subject);
        //message.setText(content);
        //
        //mailSender.send(message);
    }
}
