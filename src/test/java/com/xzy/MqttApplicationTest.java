package com.xzy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 说明：
 *
 * @author xzy
 * @date 2023/6/3  23:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttApplicationTest {

    @Autowired
    private MqttClient mqttClient;

    @Test
    public void run() throws MqttException {
        String topic = "health";
        String content = "hello world!";
        int qos = 0;
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);

        mqttClient.publish(topic, message);
    }
}