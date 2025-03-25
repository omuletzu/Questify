package com.example.auth.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.account.token}")
    private String authToken;

    @Value("${twilio.phone}")
    private String phone;

    public PhoneService() {

    }

    public void sensSms(String phoneToSend, String messageBody) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                new PhoneNumber(phoneToSend),
                new PhoneNumber(phone),
                messageBody
        ).create();
    }
}
