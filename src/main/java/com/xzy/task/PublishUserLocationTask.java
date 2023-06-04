package com.xzy.task;

import com.alibaba.fastjson2.JSONObject;
import com.xzy.model.UserLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 说明：发布用户坐标
 *
 * @author xzy
 * @date 2023/6/4  16:40
 */
@ConditionalOnProperty(name = "task.PublishUserLocationTask.enable", havingValue = "true")
@Slf4j
@Component
public class PublishUserLocationTask {

    private final MqttClient mqttClient;

    @Autowired
    public PublishUserLocationTask(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Scheduled(cron = "${task.PublishUserLocationTask.cron:0 */1 * ? * *}")
    public void doPublishUserLocationTask() throws InterruptedException, MqttException {
        String sn = UUID.randomUUID().toString().substring(10);
        for (int floor = 1; floor <= 3; floor++) {
            List<UserLocationDTO> userLocationList = generateUserLocationList(10, 360, floor, 0.0, 0.0, sn);
            for (UserLocationDTO userLocation : userLocationList) {
                publishUserLocation("userLocation", JSONObject.toJSONString(userLocation), 1);
                Thread.sleep(1000);
            }
        }
    }

    public List<UserLocationDTO> generateUserLocationList(int radius, int totalPoints, int floor, double centerX, double centerZ, String sn) {
        List<UserLocationDTO> userLocationList = new ArrayList<>(totalPoints);
        double angleIncrement = 360.0 / totalPoints;
        for (int i = 0; i < totalPoints; i++) {
            double angle = i * angleIncrement;
            double x = centerX + radius * Math.cos(Math.toRadians(angle));
            double z = centerZ + radius * Math.sin(Math.toRadians(angle));
            userLocationList.add(new UserLocationDTO(x, 0.0, z, floor, LocalDateTime.now(), sn));
        }
        return userLocationList;
    }

    public void publishUserLocation(String topic, String content, int qos) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(qos);
        mqttClient.publish(topic, mqttMessage);

        log.info("publish user location：{}", content);
    }

}
