package com.exemplo;

import com.exemplo.model.Notification;
import com.exemplo.producer.AlunoProducer;
import com.exemplo.producer.ProfProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TRABALHO REALIZADO POR:

// BRUNO TELES
// &
// HALISSON FRANÇA

@SpringBootApplication
public class NotificationApplication {

    @Autowired
    private AlunoProducer alunoProducer; // Injeção do AlunoProducer

    @Autowired
    private ProfProducer profProducer; // Injeção do ProfProducer

    // Lista para armazenar mensagens enviadas
    private final List<Notification> messageLog = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Escolha uma opção: ");
                System.out.println("1 - Enviar mensagem");
                System.out.println("2 - Ver log de mensagens");
                System.out.println("0 - Sair");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                if (choice == 0) {
                    System.out.println("Saindo do sistema...");
                    break; // Sai do loop
                }

                if (choice == 1) {
                    // Pergunta sobre o tópico
                    System.out.println("Escolha um tópico (1 para Aluno, 2 para Professor, 3 para Todos): ");
                    int topico = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    // Pergunta sobre a prioridade
                    System.out.print("Escolha a prioridade (baixa ou alta): ");
                    String prioridade = scanner.nextLine();

                    // Valida a prioridade
                    if (!prioridade.equalsIgnoreCase("baixa") && !prioridade.equalsIgnoreCase("alta")) {
                        System.out.println("Prioridade inválida. Tente novamente.");
                        continue; // Volta para o início do loop
                    }

                    // Pergunta a mensagem a ser enviada
                    System.out.print("Digite a mensagem: ");
                    String mensagem = scanner.nextLine();
                    System.out.println("Enviando mensagem: " + mensagem);

                    // Criação do objeto Notification
                    Notification notification = new Notification(mensagem, prioridade);
                    messageLog.add(notification); // Adiciona a mensagem ao log

                    // Envio de mensagens baseado na escolha do usuário
                    if (topico == 1) {
                        alunoProducer.enviarMensagem(notification); // Envia para o produtor de Aluno
                    } else if (topico == 2) {
                        profProducer.enviarMensagem(notification); // Envia para o produtor de Professor
                    } else if (topico == 3) {
                        alunoProducer.enviarMensagem(notification); // Envia para o produtor de Aluno
                        profProducer.enviarMensagem(notification); // Envia para o produtor de Professor
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                } else if (choice == 2) {
                    // Mostra o log de mensagens
                    System.out.println("Log de mensagens enviadas:");
                    for (Notification loggedMessage : messageLog) {
                        System.out.println("Mensagem: " + loggedMessage.getMessage() + ", Prioridade: " + loggedMessage.getPriority());
                    }
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            scanner.close();
        };
    }
}
