package br.com.fiap.model;

import java.time.LocalDateTime;

public class Registro {
    private long idRegistro;
    private long idUsuario;
    private int hidratacao;
    private int tempo_sol;
    private int nivel_estresse;
    private float sono;
    private float tempo_tela;
    private float trabalho_horas;
    private int atividade_fisica;
    private int score;
    private LocalDateTime dataRegistro;

    public Registro() {
    }

    public Registro(long idRegistro, long idUsuario, int hidratacao, int tempo_sol, int nivel_estresse, float sono, float tempo_tela, float trabalho_horas, int atividade_fisica, int score, LocalDateTime dataRegistro) {
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.hidratacao = hidratacao;
        this.tempo_sol = tempo_sol;
        this.nivel_estresse = nivel_estresse;
        this.sono = sono;
        this.tempo_tela = tempo_tela;
        this.trabalho_horas = trabalho_horas;
        this.atividade_fisica = atividade_fisica;
        this.score = score;
        this.dataRegistro = dataRegistro;
    }

    public long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(long idRegistro) {
        this.idRegistro = idRegistro;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getHidratacao() {
        return hidratacao;
    }

    public void setHidratacao(int hidratacao) {
        this.hidratacao = hidratacao;
    }

    public int getTempo_sol() {
        return tempo_sol;
    }

    public void setTempo_sol(int tempo_sol) {
        this.tempo_sol = tempo_sol;
    }

    public int getNivel_estresse() {
        return nivel_estresse;
    }

    public void setNivel_estresse(int nivel_estresse) {
        this.nivel_estresse = nivel_estresse;
    }

    public float getSono() {
        return sono;
    }

    public void setSono(float sono) {
        this.sono = sono;
    }

    public float getTempo_tela() {
        return tempo_tela;
    }

    public void setTempo_tela(float tempo_tela) {
        this.tempo_tela = tempo_tela;
    }

    public float getTrabalho_horas() {
        return trabalho_horas;
    }

    public void setTrabalho_horas(float trabalho_horas) {
        this.trabalho_horas = trabalho_horas;
    }

    public int getAtividade_fisica() {
        return atividade_fisica;
    }

    public void setAtividade_fisica(int atividade_fisica) {
        this.atividade_fisica = atividade_fisica;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "idRegistro=" + idRegistro +
                ", idUsuario=" + idUsuario +
                ", hidratacao=" + hidratacao +
                ", tempo_sol=" + tempo_sol +
                ", nivel_estresse=" + nivel_estresse +
                ", sono=" + sono +
                ", tempo_tela=" + tempo_tela +
                ", trabalho_horas=" + trabalho_horas +
                ", atividade_fisica=" + atividade_fisica +
                ", score=" + score +
                ", dataRegistro=" + dataRegistro +
                '}';
    }
}
