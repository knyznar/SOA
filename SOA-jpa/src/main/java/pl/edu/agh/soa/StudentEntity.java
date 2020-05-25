package pl.edu.agh.soa;

import javax.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class StudentEntity {
    @Id
    private int album;
    private String firstName;
    private String lastName;
    private String avatarPath;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BooksEntity> booksEntities;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SandwichEntity> sandwichesEntity;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CourseEntity> coursesEntity;
}
