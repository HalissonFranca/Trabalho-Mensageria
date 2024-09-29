package com.Mensageria2; // Define o pacote da classe

import java.io.Serializable; // Importa a interface Serializable, que permite que a classe seja convertida em uma sequência de bytes

// Classe Notification implementa a interface Serializable para permitir a serialização
public class Notification implements Serializable {
    // Atributos da classe Notification
    private String tipo; // Tipo da notificação (por exemplo, aviso, alerta, etc.)
    private String mensagem; // Mensagem da notificação
    private String prioridade; // Prioridade da notificação (por exemplo, alta, média, baixa)

    // Construtor da classe Notification
    public Notification(String tipo, String mensagem, String prioridade) {
        this.tipo = tipo; // Inicializa o atributo tipo
        this.mensagem = mensagem; // Inicializa o atributo mensagem
        this.prioridade = prioridade; // Inicializa o atributo prioridade
    }

    // Getters e setters podem ser adicionados se necessário para acessar e modificar os atributos

    public String getTipo() {
        return tipo; // Retorna o tipo da notificação
    }

    public void setTipo(String tipo) {
        this.tipo = tipo; // Define um novo tipo para a notificação
    }

    public String getMensagem() {
        return mensagem; // Retorna a mensagem da notificação
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem; // Define uma nova mensagem para a notificação
    }

    public String getPrioridade() {
        return prioridade; // Retorna a prioridade da notificação
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade; // Define uma nova prioridade para a notificação
    }
}
