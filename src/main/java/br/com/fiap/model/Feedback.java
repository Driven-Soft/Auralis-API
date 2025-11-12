package br.com.fiap.model;

import java.time.LocalDateTime;

public class Feedback {
    private long idFeedback;
    private long idUsuario;
    private String mensagem;
    private int nota;
    private LocalDateTime dataFeedback;

    public Feedback() {
    }

    public Feedback(long idFeedback, long idUsuario, String mensagem, int nota, LocalDateTime dataFeedback) {
        this.idFeedback = idFeedback;
        this.idUsuario = idUsuario;
        this.mensagem = mensagem;
        this.nota = nota;
        this.dataFeedback = dataFeedback;
    }

    public long getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(long idFeedback) {
        this.idFeedback = idFeedback;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }

    public void setDataFeedback(LocalDateTime dataFeedback) {
        this.dataFeedback = dataFeedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", idUsuario=" + idUsuario +
                ", mensagem='" + mensagem + '\'' +
                ", nota=" + nota +
                ", dataFeedback=" + dataFeedback +
                '}';
    }
}