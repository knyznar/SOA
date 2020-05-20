package pl.edu.agh.soa;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Stateless
@WebService
@SecurityDomain("test-security-domain")
@RolesAllowed({"TestRole"})
@WebContext(contextRoot="/SOA-1", urlPattern="/Hello", authMethod="BASIC",transportGuarantee="NONE", secureWSDLAccess = false)
public class StudentController {
    @RolesAllowed("TestRole")
    @WebMethod(action = "AddStudent")
    @WebResult(name = "album")
    public void addStudent(@WebParam(name="firstName") String firstName, @WebParam(name="lastName") String lastName, @WebParam(name="album") String album, @WebParam(name = "avatar") String avatar){
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

        Gson gson = new Gson();
        String json = gson.toJson(student);
        System.out.println(json);

        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(student.getAlbum()));
            byte[] strToBytes = json.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RolesAllowed("TestRole")
    @WebMethod(action = "GetStudent")
    @WebResult(name = "Student")
    public Student getStudent (@WebParam(name = "album") String album) {
        ObjectMapper mapper = new ObjectMapper();
        Student student = new Student();
        try {
            student = mapper.readValue(new File(String.valueOf(album)), Student.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return student;
    }

}
