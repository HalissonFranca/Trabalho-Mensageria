package com.exemplo.consumer;

import com.exemplo.model.Notification;
import com.exemplo.producer.ProfProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Component
public class ProfessorNotificationConsumer {
    @KafkaListener(topics = "batch-prof", groupId = "batch-grupo-prof",containerFactory = "kafkaListenerContainerFactory")
    public void consumeBatch(List<String> notifications) {

        processBatch(notifications);
    }

    private void processBatch(List<String> notifications) {
        notifications.forEach(
                notification -> {
                    System.err.println("Batches para professor: " + notification);
                }
        );
    }

    /*@Autowired
    private ProfProducer profProducer; // Injetando o ProfProducer

    @KafkaListener(topics = "professor", groupId = "notif-prof")
    public void consume(Notification notification) {
        profProducer.enviarMensagem(notification);
    }*/
}
