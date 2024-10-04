package com.exemplo.producer;

import com.exemplo.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class ProfProducer {

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;
    private List<Notification> batchNotifications = new ArrayList<>();
    private Notification notification;

    private final String TOPIC = "batch-prof";
    public void enviarMensagem(Notification notification) {
        if (notification.getPriority().equalsIgnoreCase("baixa")) {
            batchNotifications.add(notification);  // Adiciona a notificação ao lote
            System.out.println("Nova notificação adicionada ao lote: " + notification);

            if (batchNotifications.size() >= 3) {
                enviarBatch();  // Envia as notificações em lote
            }
        } else {
            kafkaTemplate.send(TOPIC, notification);
            System.out.println("Mensagem de alta prioridade enviada: " + notification);
        }
    }

    // Método para enviar todas as notificações do lote
    private void enviarBatch() {
        StringBuilder mensagemLote = new StringBuilder("Lote de Notificações: ");
        for (Notification n : batchNotifications) {
            mensagemLote.append(n.getMessage()).append(", ");
        }
        mensagemLote.setLength(mensagemLote.length() - 2);  // Remove a última vírgula

        Notification loteNotification = new Notification(mensagemLote.toString(), "baixa");
        kafkaTemplate.send(TOPIC, loteNotification);

        batchNotifications.clear();
    }

    // Método para enviar qualquer lote restante ao sair
    public void enviarLoteFinal() {
        if (!batchNotifications.isEmpty()) {
            enviarBatch();
        }
    }

    // Classe main para o consumidor Kafka - mensagens de alta prioridade
    public static void main(String[] args) {
        Properties consumidorProps = new Properties();
        consumidorProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumidorProps.put(ConsumerConfig.GROUP_ID_CONFIG, "cliente-group");
        consumidorProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumidorProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        consumidorProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        consumidorProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.exemplo.model.Notification");
        consumidorProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumidorProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        KafkaConsumer<String, Notification> consumidor = new KafkaConsumer<>(consumidorProps);
        consumidor.subscribe(Collections.singleton("batch-prof"));

        try {
            while (true) {
                ConsumerRecords<String, Notification> records = consumidor.poll(100);
                for (ConsumerRecord<String, Notification> record : records) {
                    System.out.println("Nova notificação recebida no sistema: " + record.value());
                }
            }
        } finally {
            consumidor.close();
        }
    }
}
