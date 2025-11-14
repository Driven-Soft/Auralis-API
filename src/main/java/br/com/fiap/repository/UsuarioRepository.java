package br.com.fiap.repository;

import br.com.fiap.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private ConnectionFactory factory = new ConnectionFactory();

    // INSERIR USUÁRIO
    public void salvarUsuario(Usuario usuario) {
        String sql = "INSERT INTO AURALIS_USUARIOS (NOME_USUARIO, EMAIL, SENHA, GENERO, DATA_NASCIMENTO, DATA_CADASTRO) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getGenero());

            if (usuario.getNascimento() != null) {
                ps.setDate(5, Date.valueOf(usuario.getNascimento()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            if (usuario.getDataCadastro() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(usuario.getDataCadastro()));
            } else {
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // <-- imprime a causa real no console
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT ID_USUARIO, NOME_USUARIO, EMAIL, SENHA, GENERO, DATA_NASCIMENTO, DATA_CADASTRO FROM AURALIS_USUARIOS";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getLong("id_usuario"));
                u.setNome(rs.getString("nome_usuario"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setGenero(rs.getString("genero"));

                Date nascimento = rs.getDate("data_nascimento");
                if (nascimento != null) {
                    u.setNascimento(nascimento.toLocalDate());
                }

                Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                if (dataCadastro != null) {
                    u.setDataCadastro(dataCadastro.toLocalDateTime());
                }

                usuarios.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }

        return usuarios;
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT ID_USUARIO, NOME_USUARIO, EMAIL, SENHA, GENERO, DATA_NASCIMENTO, DATA_CADASTRO FROM AURALIS_USUARIOS WHERE ID_USUARIO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getLong("id_usuario"));
                    u.setNome(rs.getString("nome_usuario"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setGenero(rs.getString("genero"));

                    Date nascimento = rs.getDate("data_nascimento");
                    if (nascimento != null) {
                        u.setNascimento(nascimento.toLocalDate());
                    }

                    Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                    if (dataCadastro != null) {
                        u.setDataCadastro(dataCadastro.toLocalDateTime());
                    }

                    return u;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }

        return null;
    }

    // ATUALIZAR USUÁRIO
    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE AURALIS_USUARIOS SET NOME_USUARIO = ?, EMAIL = ?, SENHA = ?, GENERO = ?, DATA_NASCIMENTO = ? WHERE ID_USUARIO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getGenero());

            if (usuario.getNascimento() != null) {
                ps.setDate(5, Date.valueOf(usuario.getNascimento()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setLong(6, usuario.getIdUsuario());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum usuário encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    // DELETAR USUÁRIO
    public void deletarUsuario(Long id) {
        String sql = "DELETE FROM AURALIS_USUARIOS WHERE ID_USUARIO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum usuário encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    // LOGIN
    public Usuario buscarPorEmailESenha(String email, String senha) {
        String sql = "SELECT * FROM auralis_usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getLong("id_usuario"));
                u.setNome(rs.getString("nome_usuario"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setGenero(rs.getString("genero"));

                Date nascimento = rs.getDate("data_nascimento");
                if (nascimento != null) {
                    u.setNascimento(nascimento.toLocalDate());
                }

                Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
                if (dataCadastro != null) {
                    u.setDataCadastro(dataCadastro.toLocalDateTime());
                }
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
