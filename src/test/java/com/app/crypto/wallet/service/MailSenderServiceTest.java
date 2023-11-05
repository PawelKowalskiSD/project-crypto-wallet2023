package com.app.crypto.wallet.service;

import com.app.crypto.wallet.domain.Mail;
import com.app.crypto.wallet.domain.VerificationKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private SimpleMailMessage simpleMailMessage;

    @InjectMocks
    private MailSenderService mailSenderService;

    @Test
    void ShouldSendMailToActiveAccount() {
        //Given
        String verifyToken = UUID.randomUUID().toString();
        VerificationKey verificationKey = new VerificationKey();
        verificationKey.setValue(verifyToken);
        Mail mail = Mail.builder()
                .mailTo("jan@wp.pl")
                .subject("first test mail")
                .message("this is test mail")
                .toCc("")
                .build();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setCc(mail.getToCc());
        //When
        mailSenderService.sendMailToActiveAccount(mail, verificationKey);
        //Then
        verify(javaMailSender, times(1)).send((MimeMessagePreparator) any());
        assertDoesNotThrow(() -> mailSenderService.sendMailToActiveAccount(mail, verificationKey));
    }
}