package com.exemplo.producer.teste;

import com.exemplo.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerTeste {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Permite todas as classes, ajuste conforme necessário
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Notification.class.getName()); // Especifica a classe que será deserializada

        KafkaConsumer<String, Notification> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton("aluno")); // ou "professor"

        try {
            while (true) {
                ConsumerRecords<String, Notification> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Notification> record : records) {
                    System.out.println("Mensagem recebida: " + record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
