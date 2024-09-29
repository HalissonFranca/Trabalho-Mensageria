package com.Mensageria2; // Define o pacote da classe

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção de dependência
import org.springframework.boot.SpringApplication; // Importa a classe principal do Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importa a anotação para habilitar o Spring Boot
import org.springframework.context.ApplicationContext; // Importa o contexto da aplicação Spring
import org.springframework.kafka.core.KafkaTemplate; // Importa a classe para interagir com o Kafka

import javax.swing.*; // Importa componentes da interface gráfica Swing
import java.awt.*; // Importa classes de layout e componentes gráficos
import java.awt.event.ActionEvent; // Importa a classe para eventos de ação
import java.awt.event.ActionListener; // Importa a interface para ouvir eventos de ação

@SpringBootApplication // Adiciona esta anotação para ativar o Spring Boot
public class SalveAdminisstrador extends JFrame { // Extende JFrame para criar uma interface gráfica
    private JComboBox<String> comboBoxOpcoes; // ComboBox para opções de notificação
    private JTextArea campoTexto; // Área de texto para inserir a mensagem
    private JRadioButton radioAlta; // Botão de rádio para prioridade alta
    private JRadioButton radioMedia; // Botão de rádio para prioridade média
    private JRadioButton radioBaixa; // Botão de rádio para prioridade baixa
    private ButtonGroup grupoPrioridade; // Grupo de botões para gerenciar as prioridades

    @Autowired // Permite que o Spring injetar a dependência
    private KafkaProducerService kafkaProducerService; // Serviço para enviar mensagens ao Kafka

    // Construtor da classe, onde a interface é configurada
    public SalveAdminisstrador(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService; // Inicializa o KafkaProducerService
        setTitle("Escolha uma Opção"); // Define o título da janela
        setSize(300, 200); // Define o tamanho da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Define o comportamento ao fechar a janela
        setLayout(new FlowLayout()); // Define o layout da janela

        // Opções disponíveis para o usuário escolher
        String[] opcoes = {"Lembrete Tarefa", "Alteração no Cronograma", "Eventos"};
        comboBoxOpcoes = new JComboBox<>(opcoes); // Inicializa a ComboBox com as opções
        JButton botaoProximo = new JButton("Próximo"); // Botão para avançar

        // Adiciona componentes à janela
        add(new JLabel("Escolha uma opção:")); // Adiciona um rótulo
        add(comboBoxOpcoes); // Adiciona a ComboBox
        add(botaoProximo); // Adiciona o botão "Próximo"

        // Adiciona um ouvinte de ação para o botão "Próximo"
        botaoProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTelaPrioridade(); // Chama o método para mostrar a tela de prioridade
            }
        });
    }

    // Método para mostrar a tela de definição de prioridade
    private void mostrarTelaPrioridade() {
        JFrame telaPrioridade = new JFrame("Definir Prioridade"); // Cria uma nova janela
        telaPrioridade.setSize(400, 400); // Define o tamanho da nova janela
        telaPrioridade.setLayout(new FlowLayout()); // Define o layout da nova janela

        campoTexto = new JTextArea(5, 20); // Cria uma área de texto com tamanho definido
        campoTexto.setLineWrap(true); // Habilita a quebra de linha
        campoTexto.setWrapStyleWord(true); // Faz a quebra de linha em palavras
        telaPrioridade.add(new JLabel("Digite sua mensagem:")); // Adiciona um rótulo

        // Define o texto inicial na área de texto com a opção escolhida
        String opcaoEscolhida = (String) comboBoxOpcoes.getSelectedItem();
        campoTexto.setText("Opção escolhida: " + opcaoEscolhida + "\n");
        telaPrioridade.add(new JScrollPane(campoTexto)); // Adiciona a área de texto em um painel de rolagem

        // Cria os botões de rádio para prioridade
        radioAlta = new JRadioButton("Alta");
        radioMedia = new JRadioButton("Média");
        radioBaixa = new JRadioButton("Baixa");

        grupoPrioridade = new ButtonGroup(); // Agrupa os botões de rádio
        grupoPrioridade.add(radioAlta); // Adiciona o botão de prioridade alta ao grupo
        grupoPrioridade.add(radioMedia); // Adiciona o botão de prioridade média ao grupo
        grupoPrioridade.add(radioBaixa); // Adiciona o botão de prioridade baixa ao grupo

        // Adiciona componentes à janela de prioridade
        telaPrioridade.add(new JLabel("Escolha a prioridade:")); // Rótulo para prioridade
        telaPrioridade.add(radioAlta); // Adiciona botão de prioridade alta
        telaPrioridade.add(radioMedia); // Adiciona botão de prioridade média
        telaPrioridade.add(radioBaixa); // Adiciona botão de prioridade baixa

        JButton botaoSalvar = new JButton("Salvar"); // Botão para salvar a notificação
        telaPrioridade.add(botaoSalvar); // Adiciona o botão "Salvar"

        // Adiciona um ouvinte de ação para o botão "Salvar"
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prioridade = "Prioridade: "; // Inicializa a string de prioridade
                // Determina qual prioridade foi selecionada
                if (radioAlta.isSelected()) {
                    prioridade += "Alta";
                } else if (radioMedia.isSelected()) {
                    prioridade += "Média";
                } else if (radioBaixa.isSelected()) {
                    prioridade += "Baixa";
                } else {
                    prioridade += "Nenhuma selecionada"; // Se nenhuma prioridade for selecionada
                }

                // Obtém a mensagem da área de texto e envia via Kafka
                String message = campoTexto.getText();
                kafkaProducerService.sendMessage(new Notification(opcaoEscolhida, message, prioridade)); // Envia a notificação

                // Mostra uma caixa de diálogo com a mensagem e a prioridade
                JOptionPane.showMessageDialog(telaPrioridade,
                        "Mensagem: " + message + "\n" + prioridade);
            }
        });

        telaPrioridade.setVisible(true); // Torna a janela visível
    }

    // Método principal que inicializa a aplicação
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SalveAdminisstrador.class, args); // Inicializa o contexto do Spring
        SwingUtilities.invokeLater(() -> {
            // Obtém a instância de SalveAdminisstrador do contexto do Spring
            SalveAdminisstrador tela = context.getBean(SalveAdminisstrador.class); // Obtém o bean gerenciado pelo Spring
            tela.setVisible(true); // Torna a tela visível
        });
    }
}
