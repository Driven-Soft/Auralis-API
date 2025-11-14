package br.com.fiap.business;

import br.com.fiap.model.Registro;
import br.com.fiap.repository.RegistroRepository;

import java.util.List;

public class RegistroBusiness {

    private RegistroRepository repository = new RegistroRepository();

    // Salvar novo registro
    public void salvarRegistro(Registro registro) {
        if (registro.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }

        repository.salvarRegistro(registro);
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