package br.com.fiap.business;

import br.com.fiap.model.Inscricao;
import br.com.fiap.repository.InscricaoRepository;

import java.util.List;

public class InscricaoBusiness {

    private InscricaoRepository repository = new InscricaoRepository();

    // Criar nova inscrição com validação
    public void criarInscricao(Inscricao inscricao) {
        if (inscricao.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório.");
        }

        if (inscricao.getWhatsapp() == null || inscricao.getWhatsapp().isBlank()) {
            throw new IllegalArgumentException("O número de WhatsApp é obrigatório.");
        }

        if (inscricao.getEmail() == null || inscricao.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }

        if (inscricao.getStatus() == null || inscricao.getStatus().isBlank()) {
            inscricao.setStatus("A"); // default (A = Ativa)
        }

        // checar se já existe inscrição para este usuário
        br.com.fiap.model.Inscricao existente = repository.buscarPorUsuario(inscricao.getIdUsuario());
        if (existente != null) {
            String status = existente.getStatus();
            if ("A".equalsIgnoreCase(status)) {
                throw new IllegalStateException("Usuário já possui uma inscrição ativa.");
            } else if ("I".equalsIgnoreCase(status)) {
                throw new IllegalArgumentException("Usuário possui uma inscrição inativa.");
            } else {
                throw new IllegalStateException("Usuário já possui uma inscrição com status: " + status);
            }
        }

        repository.salvarInscricao(inscricao);
    }

    // Listar todas
    public List<Inscricao> listarTodos() {
        return repository.listarTodos();
    }

    // Buscar por ID
    public Inscricao buscarPorId(Long id) {
        Inscricao inscricao = repository.buscarPorId(id);
        if (inscricao == null) {
            throw new RuntimeException("Inscrição não encontrada com ID: " + id);
        }
        return inscricao;
    }

    // Atualizar
    public void atualizarInscricao(Long id, Inscricao inscricao) {
        Inscricao existente = buscarPorId(id); // valida se existe

        // garantir que o id da inscrição esteja correto
        inscricao.setIdInscricao(existente.getIdInscricao());

        // preservar campos existentes caso não tenham sido fornecidos
        if (inscricao.getIdUsuario() <= 0) {
            inscricao.setIdUsuario(existente.getIdUsuario());
        }

        if (inscricao.getWhatsapp() == null || inscricao.getWhatsapp().isBlank()) {
            inscricao.setWhatsapp(existente.getWhatsapp());
        }

        if (inscricao.getEmail() == null || inscricao.getEmail().isBlank()) {
            inscricao.setEmail(existente.getEmail());
        }

        if (inscricao.getDataInscricao() == null) {
            inscricao.setDataInscricao(existente.getDataInscricao());
        }

        // alternar o status: se estava 'A' -> vira 'I', se estava 'I' -> vira 'A'
        String statusExistente = existente.getStatus();
        if ("A".equalsIgnoreCase(statusExistente)) {
            inscricao.setStatus("I");
        } else {
            inscricao.setStatus("A");
        }

        repository.atualizarInscricao(inscricao);
    }

    // Deletar
    public void deletarInscricao(Long id) {
        buscarPorId(id); // valida se existe
        repository.deletarInscricao(id);
    }
}
