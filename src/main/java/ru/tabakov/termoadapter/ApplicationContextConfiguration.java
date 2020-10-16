package ru.tabakov.termoadapter;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ComponentScan
public class ApplicationContextConfiguration {

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageProducer mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(MqttAsyncClient.generateClientId(), mqttClientFactory(),
//                        "/lytko/0/thermostat/2693064/data");
                        "topic1");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    @Bean
    @Transformer(inputChannel = "mqttInboundChannel", outputChannel = "mqttOutboundChannel")
    public InboundTransformer transformer() {
        return new InboundTransformer();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttClientFactory());
        messageHandler.setAsync(false);
        messageHandler.setDefaultTopic("test");
        return messageHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler() {
        return new InboundHandler();
    }

    @Bean
    @Scope("singleton")
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
//		options.setUserName("username");
//		options.setPassword("password".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

}
