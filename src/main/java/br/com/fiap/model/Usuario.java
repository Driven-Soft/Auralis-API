package br.com.fiap.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {
    private long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String genero;
    private LocalDate nascimento;
    private LocalDateTime dataCadastro;

    public Usuario() {
    }

    public Usuario(long idUsuario, String nome, String email, String senha, String genero, LocalDate nascimento, LocalDateTime dataCadastro) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.genero = genero;
        this.nascimento = nascimento;
        this.dataCadastro = dataCadastro;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", genero='" + genero + '\'' +
                ", nascimento=" + nascimento +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
