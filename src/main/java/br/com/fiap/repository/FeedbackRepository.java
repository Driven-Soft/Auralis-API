package br.com.fiap.repository;

import br.com.fiap.model.Feedback;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {

    private ConnectionFactory factory = new ConnectionFactory();

    // INSERIR FEEDBACK
    public void salvarFeedback(Feedback feedback) {
        String sql = "INSERT INTO AURALIS_FEEDBACKS (ID_USUARIO, MENSAGEM, NOTA_FEEDBACK, DATA_FEEDBACK) VALUES (?, ?, ?, ?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedback.getIdUsuario());
            ps.setString(2, feedback.getMensagem());
            ps.setInt(3, feedback.getNota());

            if (feedback.getDataFeedback() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(feedback.getDataFeedback()));
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar feedback: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS
    public List<Feedback> listarTodos() {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT ID_FEEDBACK, ID_USUARIO, MENSAGEM, NOTA_FEEDBACK, DATA_FEEDBACK FROM AURALIS_FEEDBACKS";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Feedback f = new Feedback();
                f.setIdFeedback(rs.getLong("id_feedback"));
                f.setIdUsuario(rs.getLong("id_usuario"));
                f.setMensagem(rs.getString("mensagem"));
                f.setNota(rs.getInt("nota_feedback"));

                Timestamp data = rs.getTimestamp("data_feedback");
                if (data != null) {
                    f.setDataFeedback(data.toLocalDateTime());
                }

                feedbacks.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar feedbacks", e);
        }

        return feedbacks;
    }

    // BUSCAR POR ID
    public Feedback buscarPorId(Long id) {
        String sql = "SELECT ID_FEEDBACK, ID_USUARIO, MENSAGEM, NOTA_FEEDBACK, DATA_FEEDBACK FROM AURALIS_FEEDBACKS WHERE ID_FEEDBACK = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Feedback f = new Feedback();
                    f.setIdFeedback(rs.getLong("id_feedback"));
                    f.setIdUsuario(rs.getLong("id_usuario"));
                    f.setMensagem(rs.getString("mensagem"));
                    f.setNota(rs.getInt("nota_feedback"));

                    Timestamp data = rs.getTimestamp("data_feedback");
                    if (data != null) {
                        f.setDataFeedback(data.toLocalDateTime());
                    }

                    return f;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar feedback por ID", e);
        }

        return null;
    }

    // ATUALIZAR FEEDBACK
    public void atualizarFeedback(Feedback feedback) {
        String sql = "UPDATE AURALIS_FEEDBACKS SET ID_USUARIO = ?, MENSAGEM = ?, NOTA_FEEDBACK = ?, DATA_FEEDBACK = ? WHERE ID_FEEDBACK = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedback.getIdUsuario());
            ps.setString(2, feedback.getMensagem());
            ps.setInt(3, feedback.getNota());

            if (feedback.getDataFeedback() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(feedback.getDataFeedback()));
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.setLong(5, feedback.getIdFeedback());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum feedback encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar feedback", e);
        }
    }

    // DELETAR FEEDBACK
    public void deletarFeedback(Long id) {
        String sql = "DELETE FROM AURALIS_FEEDBACKS WHERE ID_FEEDBACK = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum feedback encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar feedback", e);
        }
    }
}
