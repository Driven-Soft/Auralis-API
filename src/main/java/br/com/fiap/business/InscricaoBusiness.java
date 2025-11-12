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
        inscricao.setIdInscricao(existente.getIdInscricao());
        repository.atualizarInscricao(inscricao);
    }

    // Deletar
    public void deletarInscricao(Long id) {
        buscarPorId(id); // valida se existe
        repository.deletarInscricao(id);
    }
}
