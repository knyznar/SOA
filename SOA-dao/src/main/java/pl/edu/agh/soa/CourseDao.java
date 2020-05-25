package pl.edu.agh.soa;

import javax.ejb.Stateless;

@Stateless
public class CourseDao {
    public static CourseEntity courseToEntity(Course course) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setEcts(course.getEcts());
        courseEntity.setName(course.getName());
        return courseEntity;
    }

    public static Course entityToCourse(CourseEntity courseEntity) {
        Course course = new Course();
        course.setEcts(courseEntity.getEcts());
        course.setName(courseEntity.getName());
        return course;
    }
}
