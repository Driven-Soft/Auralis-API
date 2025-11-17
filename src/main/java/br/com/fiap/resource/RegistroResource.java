package br.com.fiap.resource;

import br.com.fiap.business.RegistroBusiness;
import br.com.fiap.model.Registro;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

import java.util.List;

@Path("/registros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistroResource {

    private RegistroBusiness business = new RegistroBusiness();

    // Criar novo registro
    @POST
    public Response criarRegistro(Registro registro) {
        try {
            business.salvarRegistro(registro);
            Long id = registro.getIdRegistro();
            URI location = URI.create("/registros/" + id);
            return Response.created(location).entity(Map.of("id", id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Listar todos os registros
    @GET
    public Response listarRegistros() {
        try {
            List<Registro> registros = business.listarTodos();
            return Response.ok(registros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // Buscar registro por ID
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Registro registro = business.buscarPorId(id);
            return Response.ok(registro).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // Listar últimos 7 registros de um usuário
    @GET
    @Path("/usuario/{id}/semana")
    public Response ultimos7PorUsuario(@PathParam("id") Long id) {
        try {
            List<Registro> registros = business.listarUltimosPorUsuario(id, 7);
            return Response.ok(registros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Atualizar registro
    @PUT
    @Path("/{id}")
    public Response atualizarRegistro(@PathParam("id") Long id, Registro registro) {
        try {
            registro.setIdRegistro(id);
            business.atualizarRegistro(registro);
            return Response.ok("Registro atualizado com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Deletar registro
    @DELETE
    @Path("/{id}")
    public Response deletarRegistro(@PathParam("id") Long id) {
        try {
            business.deletarRegistro(id);
            return Response.ok("Registro deletado com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}