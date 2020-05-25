package pl.edu.agh.soa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
