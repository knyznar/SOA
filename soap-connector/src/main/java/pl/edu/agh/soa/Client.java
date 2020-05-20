package pl.edu.agh.soa;


import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Client {
    public static void main(String[] args) throws IOException {
        StudentControllerService studentControllerService = new StudentControllerService();
        StudentController studentController = studentControllerService.getStudentControllerPort();
        authorize((BindingProvider) studentController, "user", "haslo");
        addStudent(studentController);
        System.out.println(getStudent(studentController, "1234").getFirstName());
    }

    private static void addStudent(StudentController studentController) throws IOException {
        String coded = convertToBase64("soap-connector/src/main/resources/no-profile-picture.jpg");
        studentController.addStudent("FirstName", "LastName","1234", coded);
    }

    private static Student getStudent(StudentController studentController, String album) throws IOException {
        Student response = studentController.getStudent(album);
        convertFromBase64(response.getAvatar());
        return response;
    }

    private static void authorize(BindingProvider bindingProvider, String user, String password) {
        bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        bindingProvider.getRequestContext().put(BindingProvider. PASSWORD_PROPERTY, password);
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

