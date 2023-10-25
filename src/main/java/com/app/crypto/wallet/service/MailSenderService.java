package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Mail;
import com.app.crypto.wallet.domain.VerificationKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void sendMailToActiveAccount(final Mail mail, VerificationKey verificationKey) {
        log.info("starting email preparation...");
        try {
            javaMailSender.send(createMessage(mail, verificationKey));
            log.info("email has been send");
        } catch (MailException e) {
            log.error("filed to process email sending: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMessage(Mail mail, VerificationKey verificationKey) {
       return mimeMessage -> {
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
           mimeMessageHelper.setTo(mail.getMailTo());
           mimeMessageHelper.setSubject(mail.getSubject());
           mimeMessageHelper.setText(mailCreatorService.BuildVerifyEmail(mail.getMessage(), verificationKey), true);
       };
    }
 }
