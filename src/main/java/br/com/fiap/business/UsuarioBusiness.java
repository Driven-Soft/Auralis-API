package br.com.fiap.business;

import br.com.fiap.model.Usuario;
import br.com.fiap.repository.UsuarioRepository;

import java.util.List;

public class UsuarioBusiness {

    private UsuarioRepository repository = new UsuarioRepository();

    // Criar novo usuário com validação
    public void criarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail do usuário é obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        repository.salvarUsuario(usuario);
    }

    // Listar todos
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    // Buscar por ID
    public Usuario buscarPorId(Long id) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        return usuario;
    }

    // Atualizar
    public void atualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id); // valida se existe
        usuario.setIdUsuario(existente.getIdUsuario());
        repository.atualizarUsuario(usuario);
    }

    // Deletar
    public void deletarUsuario(Long id) {
        buscarPorId(id); // valida se existe
        repository.deletarUsuario(id);
    }
}
