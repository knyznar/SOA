package pl.edu.agh.soa;


import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class RestClient {
    public static void main(String[] args) throws URISyntaxException {
        addStudent("Example", "Student", 789, "/no-profile-picture.jpg");
        addStudent("Test1", "Nazwisko", 345, "/no-profile-picture.jpg");
        addStudent("Test2", "Nazwisko", 567, "/no-profile-picture.jpg");
        getStudent(789);
        getStudents();
        getStudentByLastname("Nazwisko");
    }

    public static void getStudents(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/rest-api/api/Student");
        List<Student> students = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Student>>() {});
        client.close();
        System.out.println(students.toString());
    }

    public static void getStudent(int studentAlbum){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/rest-api/api/Student/");
        Student student = target.path(Integer.toString(studentAlbum)).request(MediaType.APPLICATION_JSON).get(Student.class);
        client.close();
        try {
            convertFromBase64(student.getAvatar());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(student.toString());
    }

    public static void getStudentByLastname(String lastname) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/rest-api/api/Student/byLastname/")
                .path(String.valueOf(lastname))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            List<Student> students = response.readEntity(new GenericType<List<Student>>() {});
            client.close();
            System.out.println(students.toString());
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public static void addStudent(String firstName, String lastName, int album, String avatarPath) throws URISyntaxException {
        String pathToImg = Paths.get(RestClient.class.getResource(avatarPath).toURI()).toString();
        String codedImg = "";
        try {
            codedImg = convertToBase64(pathToImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/rest-api/api/Student")
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName)
                .queryParam("album", album)
                .queryParam("avatar", codedImg);

        Response response = target.request().post(null);
        String value = response.readEntity(String.class);
        System.out.println(value);
        response.close();
    }

    private static String convertToBase64(String path) throws IOException {
        byte[] content = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(content);
    }
    private static void convertFromBase64(String imgBase64) throws IOException {
        byte[] toDecode = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("imgResponse.jpeg");
        Files.write(destinationFile, toDecode);
    }
}
