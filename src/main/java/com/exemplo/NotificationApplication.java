package com.exemplo;

import com.exemplo.model.Notification;
import com.exemplo.producer.AlunoProducer;
import com.exemplo.producer.ProfProducer;
import com.exemplo.teste.Prova1MensageriaApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

@SpringBootApplication
public class NotificationApplication {

    @Autowired
    private AlunoProducer alunoProducer; // Injeção do AlunoProducer

    @Autowired
    private ProfProducer profProducer; // Injeção do ProfProducer
    /**
     * Metodo principal que inicia a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Escolha um tópico (1 para Aluno, 2 para Professor, 0 para sair): ");
                int topico = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                if (topico == 0) {
                    System.out.println("Saindo do sistema...");
                    break; // Sai do loop
                }

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

                // Envio de mensagens baseado na escolha do usuário
                if (topico == 1) {
                    alunoProducer.enviarMensagem(notification); // Envia para o produtor de Aluno
                } else if (topico == 2) {
                    profProducer.enviarMensagem(notification); // Envia para o produtor de Professor
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            scanner.close();
        };
    }

    /*public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext ctx) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Escolha um tópico (1 para Aluno, 2 para Professor, 0 para sair): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                if (choice == 0) {
                    System.out.println("Saindo do sistema...");
                    break; // Sai do loop
                }

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

                // Envio de mensagens baseado na escolha do usuário
                Notification notification = new Notification(mensagem, prioridade);
                if (choice == 1) {
                    alunoProducer.enviarMensagem(notification); // Envia para o produtor de Aluno
                } else if (choice == 2) {
                    profProducer.enviarMensagem(notification); // Envia para o produtor de Professor
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            scanner.close();
        };
    }*/
}
