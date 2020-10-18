package ru.tabakov.termoadapter;

import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

public class InboundTransformer extends AbstractTransformer {

    @Override
    protected Object doTransform(Message<?> message) {
        return message;
    }
}
