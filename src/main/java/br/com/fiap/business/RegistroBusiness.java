package br.com.fiap.business;

import br.com.fiap.model.Registro;
import br.com.fiap.repository.RegistroRepository;

import java.time.LocalDateTime;
import java.util.List;

public class RegistroBusiness {

    private RegistroRepository repository = new RegistroRepository();

    // Salvar novo registro
    public void salvarRegistro(Registro registro) {
        if (registro.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        // calcular e setar score se não informado
        int scoreCalculado = calcularScore(
                registro.getHidratacao(),
                registro.getTempo_sol(),
                registro.getNivel_estresse(),
                registro.getSono(),
                registro.getTempo_tela(),
                registro.getTrabalho_horas(),
                registro.getAtividade_fisica()
        );
        registro.setScore(scoreCalculado);

        // setar data se não fornecida
        if (registro.getDataRegistro() == null) {
            registro.setDataRegistro(LocalDateTime.now());
        }

        repository.salvarRegistro(registro);
    }

    public int calcularScore(
            int hidratacao_ml,
            int tempo_sol_min,
            int nivel_estresse,
            float sono_horas,
            float tempo_tela_horas,
            float trabalho_horas,
            int atividade_fisica_min
    ) {

        // 1. Hidratação (meta: 2000 ml)
        double hidratacaoScore = Math.min((hidratacao_ml / 2000.0) * 100.0, 100.0);

        // 2. Tempo de sol (meta: 30 min)
        double solScore = Math.min((tempo_sol_min / 30.0) * 100.0, 100.0);

        // 3. Estresse (1 a 10) – quanto menor, melhor
        double estresseScore = (10 - nivel_estresse) * 10.0;
        if (estresseScore < 0) estresseScore = 0;

        // 4. Sono (meta: 8 horas)
        double sonoScore = Math.min((sono_horas / 8.0) * 100.0, 100.0);

        // 5. Tempo de tela (meta: <=2h ideal, penalidade após isso)
        double telaScore = 100 - ((tempo_tela_horas - 2) * 20.0);
        if (telaScore > 100) telaScore = 100;
        if (telaScore < 0) telaScore = 0;

        // 6. Trabalho (ideal entre 6 e 8 horas)
        double trabalhoScore;
        if (trabalho_horas >= 6 && trabalho_horas <= 8) {
            trabalhoScore = 100;
        } else {
            trabalhoScore = 100 - Math.abs(trabalho_horas - 7) * 20;
            if (trabalhoScore < 0) trabalhoScore = 0;
        }

        // 7. Atividade física (meta: 30 min)
        double atividadeScore = Math.min((atividade_fisica_min / 30.0) * 100.0, 100.0);

        // --- PESOS ---
        double scoreFinal =
                (hidratacaoScore * 0.15) +
                (solScore * 0.10) +
                (estresseScore * 0.20) +
                (sonoScore * 0.20) +
                (telaScore * 0.15) +
                (trabalhoScore * 0.10) +
                (atividadeScore * 0.10);

        return (int) Math.round(scoreFinal);
    }

    // Listar todos os registros
    public List<Registro> listarTodos() {
        return repository.listarTodos();
    }

    // Buscar por ID
    public Registro buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }

        Registro registro = repository.buscarPorId(id);
        if (registro == null) {
            throw new RuntimeException("Registro não encontrado.");
        }

        return registro;
    }

    // Atualizar registro
    public void atualizarRegistro(Registro registro) {
        if (registro.getIdRegistro() <= 0) {
            throw new IllegalArgumentException("ID do registro inválido para atualização.");
        }

        repository.atualizarRegistro(registro);
    }

    // Deletar registro
    public void deletarRegistro(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }

        repository.deletarRegistro(id);
    }

    // Listar últimos registros de um usuário com limite
    public List<Registro> listarUltimosPorUsuario(Long idUsuario, int limit) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        if (limit <= 0) {
            limit = 7;
        }

        return repository.listarUltimosPorUsuario(idUsuario, limit);
    }
}