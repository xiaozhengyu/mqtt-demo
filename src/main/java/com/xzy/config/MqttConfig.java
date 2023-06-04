package com.xzy.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明：MQTT配置类
 *
 * @author xzy
 * @date 2023/6/3  23:40
 */
@Configuration
public class MqttConfig {
    @Value("${mqtt.broker.url}")
    private String brokerUrl;
    @Value("${mqtt.broker.username}")
    private String brokerUsername;
    @Value("${mqtt.broker.password}")
    private String brokerPassword;
    @Value("${mqtt.client.clientId}")
    private String clientId;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(this.brokerUrl, this.clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(this.brokerUsername);
        options.setPassword(this.brokerPassword.toCharArray());
        options.setConnectionTimeout(60);
        mqttClient.connect();
        return mqttClient;
    }
}
