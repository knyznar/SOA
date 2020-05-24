package pl.edu.agh.soa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StudentDao {
    @PersistenceContext(unitName = "Kanapka")
    EntityManager entityManager;
    public void addStudent(Student student){
        entityManager.persist(student);
    }

    public void addKanapka(){
        Kanapka kanapka = new Kanapka();
        kanapka.setName("nazwa");
        entityManager.persist(kanapka);
    }
}
