package com.exemplo.consumer;

import com.exemplo.model.Notification;
import com.exemplo.producer.AlunoProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoNotificationConsumer {
    @KafkaListener(topics = "batch-aluno", groupId = "batch-grupo-aluno",containerFactory = "kafkaListenerContainerFactory")
    public void consumeBatch(List<String> notifications) {

        processBatch(notifications);
    }

    private void processBatch(List<String> notifications) {
        notifications.forEach(
                notification -> {
                    System.err.println("Batches para aluno: " + notification);
                }
        );
    }
    /*@Autowired
    private AlunoProducer alunoProducer; // Injetando o AlunoProducer

    @KafkaListener(topics = "aluno", groupId = "notif-aluno")
    public void consume(Notification notification) {
        alunoProducer.enviarMensagem(notification);
    }*/
}
