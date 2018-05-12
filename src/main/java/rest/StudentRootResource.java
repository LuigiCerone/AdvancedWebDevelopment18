package rest;

import controller.StudentController;
import model.Student;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


@Path("studenti")
public class StudentRootResource {
    final static Logger logger = Logger.getLogger(StudentRootResource.class);

    //POST /rest/student
    //Content-Type: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertStudent(@Context UriInfo c, Student studentToInsert) {
        logger.debug(studentToInsert.toString());
        int id = new StudentController().insertStudent(studentToInsert);

        if (id != -1) {
            URI u = c.getBaseUriBuilder()
                    .path(StudentRootResource.class)
                    .path(StudentRootResource.class, "getStudentById")
                    .path("" + id)
                    .build();
            return Response.created(u).build();
        } else {
            return Response.serverError().build();
        }
    }

    //GET /rest/res1/pippo/<numero>
    //Accept: application/json
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") int n) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        // TODO return student info except sensitive information.
        return Response.ok(n).build();
    }
}
