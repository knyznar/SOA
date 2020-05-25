package pl.edu.agh.soa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StudentDao {
    @PersistenceContext(unitName = "pUnit")
    EntityManager entityManager;

    public static StudentEntity studentToEntity(Student student) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setAlbum(student.getAlbum());
        studentEntity.setFirstName(student.getFirstName());
        studentEntity.setLastName(student.getLastName());
        studentEntity.setAvatarPath(student.getAvatarPath());
        List<CourseEntity> coursesEntity = student.getCourses()
                .stream()
                .map(CourseDao::courseToEntity)
                .collect(Collectors.toList());
        studentEntity.setCoursesEntity(coursesEntity);

        List<SandwichEntity> sandwichEntities = student.getSandwiches()
                .stream()
                .map(SandwichDao::sandwichToEntity)
                .collect(Collectors.toList());
        studentEntity.setSandwichesEntity(sandwichEntities);

        List<BooksEntity> bookEntities = student.getBooks()
                .stream()
                .map(BooksDao::bookToEntity)
                .collect(Collectors.toList());
        studentEntity.setBooksEntities(bookEntities);

        return studentEntity;
    }

    public static Student entityToStudent(StudentEntity studentEntity) {
        Student student = new Student();
        student.setAlbum(studentEntity.getAlbum());
        student.setAvatarPath(studentEntity.getAvatarPath());
        student.setFirstName(studentEntity.getFirstName());
        student.setLastName(studentEntity.getLastName());
        List<Course> courses = studentEntity.getCoursesEntity()
                .stream()
                .map(CourseDao::entityToCourse)
                .collect(Collectors.toList());
        student.setCourses(courses);

        List<Sandwich> sandwiches = studentEntity.getSandwichesEntity()
                .stream()
                .map(SandwichDao::entityToSandwich)
                .collect(Collectors.toList());
        student.setSandwiches(sandwiches);

        List<Book> books = studentEntity.getBooksEntities()
                .stream()
                .map(BooksDao::entityToBook)
                .collect(Collectors.toList());
        student.setBooks(books);

        return student;
    }

    public Student findStudent(int album) throws Exception {
        return Optional.ofNullable(entityManager.find(StudentEntity.class, album))
                .map(StudentDao::entityToStudent)
                .orElseThrow(Exception::new);
    }

    public List<Student> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot);

        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(StudentDao::entityToStudent)
                .collect(Collectors.toList());
    }

    public void save(Student student) {
        entityManager.persist(StudentDao.studentToEntity(student));
    }

    public void update(Student student) {
        StudentEntity studentEntity = StudentDao.studentToEntity(student);
        studentEntity.setFirstName(student.getFirstName());
        studentEntity.setLastName(student.getLastName());
        studentEntity.setAvatarPath(student.getAvatarPath());

    }

}
