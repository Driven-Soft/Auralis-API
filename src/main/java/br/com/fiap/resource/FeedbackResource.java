package br.com.fiap.resource;

import br.com.fiap.business.FeedbackBusiness;
import br.com.fiap.model.Feedback;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackResource {

    private FeedbackBusiness business = new FeedbackBusiness();

    @GET
    public Response listarTodos() {
        List<Feedback> feedbacks = business.listarTodos();
        return Response.ok(feedbacks).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Feedback feedback = business.buscarPorId(id);
            return Response.ok(feedback).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    public Response criarFeedback(Feedback feedback) {
        try {
            business.criarFeedback(feedback);
            return Response.status(Response.Status.CREATED)
                    .entity("Feedback criado com sucesso!")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar feedback: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarFeedback(@PathParam("id") Long id, Feedback feedback) {
        try {
            business.atualizarFeedback(id, feedback);
            return Response.ok("Feedback atualizado com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar feedback: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarFeedback(@PathParam("id") Long id) {
        try {
            business.deletarFeedback(id);
            return Response.ok("Feedback deletado com sucesso!").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar feedback: " + e.getMessage())
                    .build();
        }
    }
}
