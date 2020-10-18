package ru.tabakov.termoadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class InboundHandler implements MessageHandler {

    @Autowired
    MessageChannel mqttOutboundChannel;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
//        mqttOutboundChannel.send(message);
    }
}
