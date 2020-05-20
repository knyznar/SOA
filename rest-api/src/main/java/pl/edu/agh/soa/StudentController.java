package pl.edu.agh.soa;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.edu.agh.soa.jwt.JWTTokenNeeded;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@Path("/Student")
@Api(value = "/Student")
public class StudentController {
    List<Student> students = new ArrayList<>();
    public StudentController() {
        Course SOAcourse = new Course("SOA", 5);
        Course Anothercourse = new Course("Another", 1);
        Student student = new Student
                .StudentBuilder()
                .firstName("firstName")
                .lastName("lastName")
                .album("123")
                .avatar("avatar")
                .courses(Arrays.asList(SOAcourse, Anothercourse))
                .build();

        students.add(student);
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent() {
        return Response
                .ok()
                .entity(students)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @GET
    @Path("/{album}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code=404, message="Not Found")})
    public Response getStudent(@PathParam("album") int album) {
        Student student;
        try {
            student = students
                    .stream()
                    .filter(s -> s.getAlbum().equals(Integer.toString(album)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Student with album number " + album + " does not exist"));
        } catch (IllegalArgumentException e) {
            return Response.
                    status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        return Response
                .ok()
                .entity(student)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @POST
    @Path("")
//    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("album") String album, @QueryParam("avatar") String avatar) {
        Course SOAcourse = new Course("SOA", 5);
        Course Anothercourse = new Course("Another", 1);
        Student student = new Student
                .StudentBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .album(album)
                .avatar(avatar)
                .courses(Arrays.asList(SOAcourse, Anothercourse))
                .build();

        students.add(student);
        return Response
                .ok()
                .entity(student)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "POST")
                .build();
    }

    @PUT
    @Path("/{album}")
//    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("album") Integer album, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("avatar") String avatar){
        Student student;
        try {
            student = students
                    .stream()
                    .filter(s -> s.getAlbum().equals(Integer.toString(album)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Student with album number " + album + " does not exist"));
        } catch (IllegalArgumentException e) {
            return Response.
                    status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Methods", "PUT")
                    .build();
        }
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAvatar(avatar);

        return Response
                .ok()
                .entity(student)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "PUT")
                .build();
    }
}
