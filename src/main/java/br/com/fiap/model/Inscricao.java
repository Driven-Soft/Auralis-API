package br.com.fiap.model;

import org.jboss.resteasy.reactive.server.handlers.PerRequestInstanceHandler;

import java.time.LocalDateTime;

public class Inscricao {
    private long IdInscricao;
    private long IdUsuario;
    private String whatsapp;
    private String email;
    private LocalDateTime dataInscricao;
    private String status;

    public Inscricao() {
    }

    public Inscricao(long idInscricao, long idUsuario, String whatsapp, String email, LocalDateTime dataInscricao, String status) {
        IdInscricao = idInscricao;
        IdUsuario = idUsuario;
        this.whatsapp = whatsapp;
        this.email = email;
        this.dataInscricao = dataInscricao;
        this.status = status;
    }

    public long getIdInscricao() {
        return IdInscricao;
    }

    public void setIdInscricao(long idInscricao) {
        IdInscricao = idInscricao;
    }

    public long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inscricao{" +
                "IdInscricao=" + IdInscricao +
                ", IdUsuario=" + IdUsuario +
                ", whatsapp='" + whatsapp + '\'' +
                ", email='" + email + '\'' +
                ", dataInscricao=" + dataInscricao +
                ", status='" + status + '\'' +
                '}';
    }
}
