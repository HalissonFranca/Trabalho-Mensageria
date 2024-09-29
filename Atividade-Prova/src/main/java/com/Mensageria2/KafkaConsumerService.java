package com.Mensageria2;

import org.springframework.kafka.annotation.KafkaListener; // Importa a anotação para marcar métodos como consumidores de mensagens Kafka
import org.springframework.stereotype.Service; // Importa a anotação para definir a classe como um serviço gerenciado pelo Spring

import javax.management.Notification; // Importa a classe Notification. Pode ser necessário substituir por uma classe personalizada dependendo do uso.

@Service // Define esta classe como um serviço, permitindo que o Spring gerencie seu ciclo de vida e realize a injeção de dependências
public class KafkaConsumerService {

    // Anotação que define este método como um consumidor de mensagens Kafka.
    // `topics` especifica o tópico Kafka que será consumido e `groupId` define o grupo de consumidores
    @KafkaListener(topics = "notificacoes", groupId = "grupo-notificacoes")
    public void consume(Notification notification) {
        // Método chamado automaticamente quando uma nova mensagem é recebida no tópico "notificacoes".
        // O parâmetro `notification` contém a mensagem recebida (será necessário utilizar um deserializador apropriado).

        // Imprime a notificação recebida no console
        System.out.println("Notificação recebida: " + notification);

    }
}
