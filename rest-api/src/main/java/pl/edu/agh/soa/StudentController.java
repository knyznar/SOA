package pl.edu.agh.soa;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.edu.agh.soa.jwt.JWTTokenNeeded;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
@Path("/Student")
@Api(value = "/Student")
public class StudentController {
    @EJB
    StudentDao studentDao = new StudentDao();
    List<Student> students = new ArrayList<>();
    public StudentController() {
        Course SOAcourse = new Course("SOA", 5);
        Course Anothercourse = new Course("Another", 1);
        Student student = new Student
                .StudentBuilder()
                .firstName("firstName")
                .lastName("lastName")
                .album(123)
                .avatar("avatar")
                .courses(Arrays.asList(SOAcourse, Anothercourse))
                .build();

        students.add(student);
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent() {
        List<Student> result = studentDao.findAll();
        return Response
                .ok()
                .entity(result)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @GET
    @Path("/{album}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code=404, message="Not Found")})
    public Response getStudent(@PathParam("album") int album) throws Exception {
        Student student;
        try {
            student = studentDao.findStudent(album);
        } catch (Exception e) {
            return Response.
                    status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        try {
            student.setAvatar(fileToBase64(student.getAvatarPath()));
        } catch (IOException e) {
            e.printStackTrace();
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
    @ApiResponses({@ApiResponse(code=201, message="Created")})
    public Response addStudent(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("album") int album, @QueryParam("avatar") String avatar) {
        try {
            base64ToFile(avatar, Integer.toString(album));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Course SOAcourse = new Course("SOA", 5);
        Course Anothercourse = new Course("Another", 1);
        Sandwich hamSandwich = new Sandwich("hamSandwich");
        Sandwich cheeseSandwich = new Sandwich("cheeseSandwich");
        Book someBook = new Book("someBook");

        Student student = new Student
                .StudentBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .album(album)
                .avatar(avatar)
                .avatarPath(Integer.toString(album))
                .books(Arrays.asList(someBook))
                .sandwiches(Arrays.asList(hamSandwich, cheeseSandwich))
                .courses(Arrays.asList(SOAcourse, Anothercourse))
                .build();

        students.add(student);
        studentDao.save(student);
        return Response
                .ok()
                .entity(student)
                .status(Response.Status.CREATED)
                .header("Access-Control-Allow-Methods", "POST")
                .build();
    }

    @PUT
    @Path("/{album}")
//    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("album") int album, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("avatar") String avatar){
        Student student;
        try {
            student = students
                    .stream()
                    .filter(s -> s.getAlbum() == album)
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
        studentDao.update(student);

        return Response
                .ok()
                .entity(student)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Methods", "PUT")
                .build();
    }

    public void base64ToFile(String imgBase64, String imgPath) throws IOException {
        byte[] decodedImg = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        java.nio.file.Path destinationFile = Paths.get(imgPath);
        Files.write(destinationFile, decodedImg);
    }

    public String fileToBase64(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
