package br.com.fiap.business;

import br.com.fiap.model.Feedback;
import br.com.fiap.repository.FeedbackRepository;

import java.util.List;

public class FeedbackBusiness {

    private FeedbackRepository repository = new FeedbackRepository();

    // Criar novo feedback com validação
    public void criarFeedback(Feedback feedback) {
        if (feedback.getMensagem() == null || feedback.getMensagem().isBlank()) {
            throw new IllegalArgumentException("A mensagem do feedback é obrigatória.");
        }

        if (feedback.getNota() < 1 || feedback.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }

        repository.salvarFeedback(feedback);
    }

    // Listar todos
    public List<Feedback> listarTodos() {
        return repository.listarTodos();
    }

    // Buscar por ID
    public Feedback buscarPorId(Long id) {
        Feedback feedback = repository.buscarPorId(id);
        if (feedback == null) {
            throw new RuntimeException("Feedback não encontrado com ID: " + id);
        }
        return feedback;
    }

    // Atualizar
    public void atualizarFeedback(Long id, Feedback feedback) {
        Feedback existente = buscarPorId(id); // valida se existe
        feedback.setIdFeedback(existente.getIdFeedback());
        repository.atualizarFeedback(feedback);
    }

    // Deletar
    public void deletarFeedback(Long id) {
        buscarPorId(id); // valida se existe
        repository.deletarFeedback(id);
    }
}
