package com.travelbnb.service;

import com.travelbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SmsService {
    private TwilioConfig twilioConfig;

    @Value("${twilio.sms.phone.number}")
    private String fromPhoneNumber;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();

        System.out.println("SMS sent: " + message.getSid());
        return toPhoneNumber;
    }
    // WhatsApp  message sending throw twilio

    public String sendWhatsAppMessage(String to, String messageBody) {
        try {
            PhoneNumber toPhoneNumber = new PhoneNumber("whatsapp:" + to);
            PhoneNumber fromPhoneNumber = new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappPhoneNumber());
            Message message = Message.creator(toPhoneNumber, fromPhoneNumber, messageBody).create();
            return message.getSid();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}

