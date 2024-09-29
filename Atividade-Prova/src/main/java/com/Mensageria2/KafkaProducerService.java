package com.Mensageria2;

import org.springframework.beans.factory.annotation.Autowired; // Permite a injeção automática de dependências do Spring
import org.springframework.kafka.core.KafkaTemplate; // Fornece uma interface para enviar mensagens para o Kafka
import org.springframework.stereotype.Service; // Define a classe como um serviço gerenciado pelo Spring

@Service // Marca esta classe como um serviço, tornando-a um bean gerenciado pelo Spring
public class KafkaProducerService {

    // Declara um campo privado para o KafkaTemplate, que será usado para enviar mensagens para o Kafka
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    // Construtor com a anotação @Autowired para que o Spring injete automaticamente o KafkaTemplate necessário
    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate; // Inicializa o campo kafkaTemplate com o valor fornecido pelo Spring
    }

    // Método público para enviar mensagens. Recebe uma instância de `Notification` e envia para o Kafka
    public void sendMessage(Notification notification) {
        // Envia a notificação para o tópico Kafka chamado "notificacoes"
        kafkaTemplate.send("notificacoes", notification);
    }
}
