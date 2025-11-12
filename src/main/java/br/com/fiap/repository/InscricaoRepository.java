package br.com.fiap.repository;

import br.com.fiap.model.Inscricao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InscricaoRepository {

    private ConnectionFactory factory = new ConnectionFactory();

    // INSERIR INSCRIÇÃO
    public void salvarInscricao(Inscricao inscricao) {
        String sql = "INSERT INTO AURALIS_INSCRICOES (ID_USUARIO, RECEBE_WHATSAPP, RECEBE_EMAIL, DATA_INSCRICAO, STATUS) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, inscricao.getIdUsuario());
            ps.setString(2, inscricao.getWhatsapp());
            ps.setString(3, inscricao.getEmail());

            if (inscricao.getDataInscricao() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(inscricao.getDataInscricao()));
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.setString(5, inscricao.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar inscrição: " + e.getMessage(), e);
        }
    }

    // LISTAR TODAS
    public List<Inscricao> listarTodos() {
        List<Inscricao> inscricoes = new ArrayList<>();
        String sql = "SELECT ID_INSCRICAO, ID_USUARIO, RECEBE_WHATSAPP, RECEBE_EMAIL, DATA_INSCRICAO, STATUS FROM AURALIS_INSCRICOES";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setIdInscricao(rs.getLong("ID_INSCRICAO"));
                i.setIdUsuario(rs.getLong("ID_USUARIO"));
                i.setWhatsapp(rs.getString("RECEBE_WHATSAPP"));
                i.setEmail(rs.getString("RECEBE_EMAIL"));

                Timestamp ts = rs.getTimestamp("DATA_INSCRICAO");
                if (ts != null) {
                    i.setDataInscricao(ts.toLocalDateTime());
                }

                i.setStatus(rs.getString("STATUS"));

                inscricoes.add(i);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar inscrições", e);
        }

        return inscricoes;
    }

    // BUSCAR POR ID
    public Inscricao buscarPorId(Long id) {
        String sql = "SELECT ID_INSCRICAO, ID_USUARIO, RECEBE_WHATSAPP, RECEBE_EMAIL, DATA_INSCRICAO, STATUS FROM AURALIS_INSCRICOES WHERE ID_INSCRICAO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Inscricao i = new Inscricao();
                    i.setIdInscricao(rs.getLong("ID_INSCRICAO"));
                    i.setIdUsuario(rs.getLong("ID_USUARIO"));
                    i.setWhatsapp(rs.getString("RECEBE_WHATSAPP"));
                    i.setEmail(rs.getString("RECEBE_EMAIL"));

                    Timestamp ts = rs.getTimestamp("DATA_INSCRICAO");
                    if (ts != null) {
                        i.setDataInscricao(ts.toLocalDateTime());
                    }

                    i.setStatus(rs.getString("STATUS"));

                    return i;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar inscrição por ID", e);
        }

        return null;
    }

    // ATUALIZAR INSCRIÇÃO
    public void atualizarInscricao(Inscricao inscricao) {
        String sql = "UPDATE AURALIS_INSCRICOES SET ID_USUARIO = ?, RECEBE_WHATSAPP = ?, RECEBE_EMAIL = ?, DATA_INSCRICAO = ?, STATUS = ? WHERE ID_INSCRICAO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, inscricao.getIdUsuario());
            ps.setString(2, inscricao.getWhatsapp());
            ps.setString(3, inscricao.getEmail());

            if (inscricao.getDataInscricao() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(inscricao.getDataInscricao()));
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.setString(5, inscricao.getStatus());
            ps.setLong(6, inscricao.getIdInscricao());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nenhuma inscrição encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar inscrição", e);
        }
    }

    // DELETAR INSCRIÇÃO
    public void deletarInscricao(Long id) {
        String sql = "DELETE FROM AURALIS_INSCRICOES WHERE ID_INSCRICAO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhuma inscrição encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar inscrição", e);
        }
    }
}
