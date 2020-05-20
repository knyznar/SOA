package pl.edu.agh.soa;


import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api")
public class StudentApp extends Application {
    public StudentApp(@Context ServletConfig servletConfig) {
        super();

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Student Rest Api");
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/rest-api/api");
        beanConfig.setResourcePackage(StudentController.class.getPackage().getName());
        beanConfig.setScan(true);
    }

}
