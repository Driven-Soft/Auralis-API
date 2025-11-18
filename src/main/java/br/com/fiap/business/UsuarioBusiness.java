package br.com.fiap.business;

import br.com.fiap.DTO.LoginDTO;
import br.com.fiap.model.Usuario;
import br.com.fiap.repository.UsuarioRepository;

import java.util.List;

public class UsuarioBusiness {

    private UsuarioRepository repository = new UsuarioRepository();

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

    public Usuario login(LoginDTO dto) {
        Usuario usuario = repository.buscarPorEmailESenha(dto.email, dto.senha);

        if (usuario == null) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        return usuario;
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
        Usuario existente = buscarPorId(id);
        usuario.setIdUsuario(existente.getIdUsuario());
        repository.atualizarUsuario(usuario);
    }

    // Deletar
    public void deletarUsuario(Long id) {
        buscarPorId(id);
        repository.deletarUsuario(id);
    }
}
