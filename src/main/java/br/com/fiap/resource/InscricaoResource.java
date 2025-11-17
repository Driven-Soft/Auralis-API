package br.com.fiap.resource;

import br.com.fiap.business.InscricaoBusiness;
import br.com.fiap.model.Inscricao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/inscricoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InscricaoResource {

    private InscricaoBusiness business = new InscricaoBusiness();

    @GET
    public Response listarTodos() {
        List<Inscricao> inscricoes = business.listarTodos();
        return Response.ok(inscricoes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Inscricao inscricao = business.buscarPorId(id);
            return Response.ok(inscricao).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    public Response criarInscricao(Inscricao inscricao) {
        try {
            business.criarInscricao(inscricao);
            return Response.status(Response.Status.CREATED)
                    .entity("Inscrição criada com sucesso!")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalStateException e) {
            // usado para indicar conflito/duplicata (inscrição ativa já existe)
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar inscrição: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarInscricao(@PathParam("id") Long id, Inscricao inscricao) {
        try {
            business.atualizarInscricao(id, inscricao);
            return Response.ok("Inscrição atualizada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar inscrição: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarInscricao(@PathParam("id") Long id) {
        try {
            business.deletarInscricao(id);
            return Response.ok("Inscrição deletada com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar inscrição: " + e.getMessage())
                    .build();
        }
    }
}
