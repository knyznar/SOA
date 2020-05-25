package pl.edu.agh.soa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sandwich")
@Data
public class SandwichEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sandwichId;
    private String name;

}
