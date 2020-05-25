package pl.edu.agh.soa;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
@Data
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer ects;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "coursesEntity")
    private List<StudentEntity> studentEntities;
}
