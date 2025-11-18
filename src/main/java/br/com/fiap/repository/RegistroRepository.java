package br.com.fiap.repository;

import br.com.fiap.model.Registro;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroRepository {

    private ConnectionFactory factory = new ConnectionFactory();

    // INSERIR REGISTRO
    public void salvarRegistro(Registro registro) {
        String sql = """
            INSERT INTO AURALIS_REGISTROS 
            (ID_USUARIO, HIDRATACAO_ML, TEMPO_SOL_MIN, NIVEL_ESTRESSE, SONO_HORAS, TEMPO_TELA_HORAS, TRABALHO_HORAS, ATIVIDADE_FISICA_MIN, SCORE, DATA_REGISTRO) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // verificar se já existe registro do usuário na mesma data (um por dia)
        java.time.LocalDateTime dataParaChecar = registro.getDataRegistro() != null ? registro.getDataRegistro() : java.time.LocalDateTime.now();
        String existsSql = "SELECT 1 FROM AURALIS_REGISTROS WHERE ID_USUARIO = ? AND TRUNC(DATA_REGISTRO) = TRUNC(?) AND ROWNUM = 1";
        try (Connection conn = factory.getConnection();
             PreparedStatement existsPs = conn.prepareStatement(existsSql)) {

            existsPs.setLong(1, registro.getIdUsuario());
            existsPs.setTimestamp(2, Timestamp.valueOf(dataParaChecar));
            try (ResultSet rs = existsPs.executeQuery()) {
                if (rs.next()) {
                    throw new RuntimeException("REGISTRO_JA_REALIZADO_HOJE");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de registro", e);
        }

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_REGISTRO"})) {

            ps.setLong(1, registro.getIdUsuario());
            ps.setInt(2, registro.getHidratacao());
            ps.setInt(3, registro.getTempo_sol());
            ps.setInt(4, registro.getNivel_estresse());
            ps.setFloat(5, registro.getSono());
            ps.setFloat(6, registro.getTempo_tela());
            ps.setFloat(7, registro.getTrabalho_horas());
            ps.setInt(8, registro.getAtividade_fisica());
            ps.setInt(9, registro.getScore());

            Timestamp timestampToUse;
            if (registro.getDataRegistro() != null) {
                timestampToUse = Timestamp.valueOf(registro.getDataRegistro());
                ps.setTimestamp(10, timestampToUse);
            } else {
                timestampToUse = Timestamp.valueOf(LocalDateTime.now());
                ps.setTimestamp(10, timestampToUse);
                registro.setDataRegistro(timestampToUse.toLocalDateTime());
            }

            int rows = ps.executeUpdate();

            boolean idSet = false;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys != null && generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);
                    registro.setIdRegistro(generatedId);
                    idSet = true;
                }
            } catch (SQLException e) {
            }

            if (!idSet) {
                String findSql = "SELECT ID_REGISTRO FROM (SELECT ID_REGISTRO FROM AURALIS_REGISTROS WHERE ID_USUARIO = ? AND DATA_REGISTRO = ? ORDER BY ID_REGISTRO DESC) WHERE ROWNUM = 1";
                try (PreparedStatement findPs = conn.prepareStatement(findSql)) {
                    findPs.setLong(1, registro.getIdUsuario());
                    findPs.setTimestamp(2, timestampToUse);
                    try (ResultSet rs = findPs.executeQuery()) {
                        if (rs.next()) {
                            registro.setIdRegistro(rs.getLong("id_registro"));
                            idSet = true;
                        }
                    }
                }
            }

            if (rows == 0 || !idSet) {
                throw new RuntimeException("Falha ao inserir registro, nenhuma linha afetada ou id não recuperado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar registro: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS OS REGISTROS
    public List<Registro> listarTodos() {
        List<Registro> registros = new ArrayList<>();
        String sql = """
            SELECT ID_REGISTRO, ID_USUARIO, HIDRATACAO_ML, TEMPO_SOL_MIN, NIVEL_ESTRESSE, SONO_HORAS, 
                   TEMPO_TELA_HORAS, TRABALHO_HORAS, ATIVIDADE_FISICA_MIN, SCORE, DATA_REGISTRO 
            FROM AURALIS_REGISTROS
        """;

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Registro r = new Registro();
                r.setIdRegistro(rs.getLong("id_registro"));
                r.setIdUsuario(rs.getLong("id_usuario"));
                r.setHidratacao(rs.getInt("hidratacao_ml"));
                r.setTempo_sol(rs.getInt("tempo_sol_min"));
                r.setNivel_estresse(rs.getInt("nivel_estresse"));
                r.setSono(rs.getFloat("sono_horas"));
                r.setTempo_tela(rs.getFloat("tempo_tela_horas"));
                r.setTrabalho_horas(rs.getFloat("trabalho_horas"));
                r.setAtividade_fisica(rs.getInt("atividade_fisica_min"));
                r.setScore(rs.getInt("score"));

                Timestamp data = rs.getTimestamp("data_registro");
                if (data != null) {
                    r.setDataRegistro(data.toLocalDateTime());
                }

                registros.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar registros", e);
        }

        return registros;
    }

    // BUSCAR POR ID
    public Registro buscarPorId(Long id) {
        String sql = """
            SELECT ID_REGISTRO, ID_USUARIO, HIDRATACAO_ML, TEMPO_SOL_MIN, NIVEL_ESTRESSE, SONO_HORAS, 
                   TEMPO_TELA_HORAS, TRABALHO_HORAS, ATIVIDADE_FISICA_MIN, SCORE, DATA_REGISTRO  
            FROM AURALIS_REGISTROS 
            WHERE ID_REGISTRO = ?
        """;

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Registro r = new Registro();
                    r.setIdRegistro(rs.getLong("id_registro"));
                    r.setIdUsuario(rs.getLong("id_usuario"));
                    r.setHidratacao(rs.getInt("hidratacao_ml"));
                    r.setTempo_sol(rs.getInt("tempo_sol_min"));
                    r.setNivel_estresse(rs.getInt("nivel_estresse"));
                    r.setSono(rs.getFloat("sono_horas"));
                    r.setTempo_tela(rs.getFloat("tempo_tela_horas"));
                    r.setTrabalho_horas(rs.getFloat("trabalho_horas"));
                    r.setAtividade_fisica(rs.getInt("atividade_fisica_min"));
                    r.setScore(rs.getInt("score"));

                    Timestamp data = rs.getTimestamp("data_registro");
                    if (data != null) {
                        r.setDataRegistro(data.toLocalDateTime());
                    }

                    return r;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registro por ID", e);
        }

        return null;
    }

    // ATUALIZAR REGISTRO
    public void atualizarRegistro(Registro registro) {
        String sql = """
            UPDATE AURALIS_REGISTROS 
            SET ID_USUARIO = ?, HIDRATACAO_ML = ?, TEMPO_SOL_MIN = ?, NIVEL_ESTRESSE = ?, SONO_HORAS = ?, 
                TEMPO_TELA_HORAS = ?, TRABALHO_HORAS = ?, ATIVIDADE_FISICA_MIN = ?, SCORE = ?, DATA_REGISTRO = ?
            WHERE ID_REGISTRO = ?
        """;

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, registro.getIdUsuario());
            ps.setInt(2, registro.getHidratacao());
            ps.setInt(3, registro.getTempo_sol());
            ps.setInt(4, registro.getNivel_estresse());
            ps.setFloat(5, registro.getSono());
            ps.setFloat(6, registro.getTempo_tela());
            ps.setFloat(7, registro.getTrabalho_horas());
            ps.setInt(8, registro.getAtividade_fisica());
            ps.setInt(9, registro.getScore());

            if (registro.getDataRegistro() != null) {
                ps.setTimestamp(10, Timestamp.valueOf(registro.getDataRegistro()));
            } else {
                ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.setLong(11, registro.getIdRegistro());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro", e);
        }
    }

    // DELETAR REGISTRO
    public void deletarRegistro(Long id) {
        String sql = "DELETE FROM AURALIS_REGISTROS WHERE ID_REGISTRO = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar registro", e);
        }
    }

    // LISTAR ÚLTIMOS REGISTROS DE UM USUÁRIO (7)
    public List<Registro> listarUltimosPorUsuario(Long idUsuario, int limit) {
        List<Registro> registros = new ArrayList<>();
        String sql = """
            SELECT ID_REGISTRO, ID_USUARIO, HIDRATACAO_ML, TEMPO_SOL_MIN, NIVEL_ESTRESSE, SONO_HORAS, 
                   TEMPO_TELA_HORAS, TRABALHO_HORAS, ATIVIDADE_FISICA_MIN, SCORE, DATA_REGISTRO 
            FROM (
                SELECT ID_REGISTRO, ID_USUARIO, HIDRATACAO_ML, TEMPO_SOL_MIN, NIVEL_ESTRESSE, SONO_HORAS, 
                       TEMPO_TELA_HORAS, TRABALHO_HORAS, ATIVIDADE_FISICA_MIN, SCORE, DATA_REGISTRO 
                FROM AURALIS_REGISTROS
                WHERE ID_USUARIO = ?
                ORDER BY DATA_REGISTRO DESC
            )
            WHERE ROWNUM <= ?
        """;

        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUsuario);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registro r = new Registro();
                    r.setIdRegistro(rs.getLong("id_registro"));
                    r.setIdUsuario(rs.getLong("id_usuario"));
                    r.setHidratacao(rs.getInt("hidratacao_ml"));
                    r.setTempo_sol(rs.getInt("tempo_sol_min"));
                    r.setNivel_estresse(rs.getInt("nivel_estresse"));
                    r.setSono(rs.getFloat("sono_horas"));
                    r.setTempo_tela(rs.getFloat("tempo_tela_horas"));
                    r.setTrabalho_horas(rs.getFloat("trabalho_horas"));
                    r.setAtividade_fisica(rs.getInt("atividade_fisica_min"));
                    r.setScore(rs.getInt("score"));

                    Timestamp data = rs.getTimestamp("data_registro");
                    if (data != null) {
                        r.setDataRegistro(data.toLocalDateTime());
                    }

                    registros.add(r);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar últimos registros do usuário", e);
        }

        return registros;
    }
}
