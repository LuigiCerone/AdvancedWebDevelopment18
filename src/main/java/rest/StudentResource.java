package rest;

import controller.CredentialController;
import controller.StudentController;
import model.Student;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;


@Path("studenti")
public class StudentResource {
    private Cookie authcookie;

    final static Logger logger = Logger.getLogger(StudentResource.class);

    public StudentResource(Cookie authcookie) {
        this.authcookie = authcookie;
    }

    public StudentResource() {
    }

    //POST /rest/studenti
    //Content-Type: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertStudent(@Context UriInfo c, Student studentToInsert) {
        logger.debug(studentToInsert.toString());
        int id = new StudentController().insertStudent(studentToInsert);

        if (id != -1) {
            URI u = c.getBaseUriBuilder()
                    .path(StudentResource.class)
                    .path(StudentResource.class, "getStudentById")
                    .build(id);
            return Response.created(u).build();
        } else {
            return Response.serverError().build();
        }
    }


    //GET /rest/auth/studenti/<numero>
    //Accept: application/json
    @GET
    @Path("{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentInfo(@PathParam("id") int idStudent) {

        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                if (idLoggedUser != -1) {
                    Student student = new StudentController().selectiveSelect(userType, idStudent, idLoggedUser);
                    if (student != null)
                        return Response.ok(student).build();
                    else
                        return Response.status(403).build();
                } else {
                    logger.error("idLoggedUser error.");
                    return Response.serverError().build();
                }
            } else {
                logger.error("userType value is not correct.");
                return Response.serverError().build();
            }
        } else {
            return Response.ok("No active session").build();
        }
    }
}
