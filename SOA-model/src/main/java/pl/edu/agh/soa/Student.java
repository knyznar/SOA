package pl.edu.agh.soa;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class Student {
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private int album;
    @JsonProperty
    private String avatar;
    @JsonProperty
    private String avatarPath;
    @JsonProperty
    private List<Book> books;
    @JsonProperty
    private  List<Sandwich> sandwiches;
    @JsonProperty
    private List<Course> courses;


    public List<Course> getCourses() {
        return courses;
    }

    @JsonCreator
    public Student(@JsonProperty(value = "firstName") String firstName,
                   @JsonProperty(value = "lastName") String lastName,
                   @JsonProperty(value = "album") int album,
                   @JsonProperty(value = "avatar") String avatar,
                   @JsonProperty(value = "avatarPath") String avatarPath,
                   @JsonProperty(value = "book") List<Book> books,
                   @JsonProperty(value = "sandwiches") List<Sandwich> sandwiches,
                   @JsonProperty(value = "courses") List<Course> courses){
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.avatar = avatar;
        this.avatarPath = avatarPath;
        this.books = books;
        this.sandwiches = sandwiches;
        this.courses = courses;
    }
}
