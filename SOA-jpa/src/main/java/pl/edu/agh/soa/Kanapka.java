package pl.edu.agh.soa;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "kanapka", schema = "public")
@Setter
@Getter
public class Kanapka {

    @Id
    @GeneratedValue
    public Long kanapkaId;
    public String name;

}