package school.dao;

import java.util.List;

import school.model.Course;

public interface CourseDao extends BaseDao<Course> {

	List<Course> findAllAddition();

	List<Course> findByGroupNumber(int groupNumber);

	List<Course> findByCoefficient(int coefficient);

	List<Course> findByCourseName(String courseName);

	List<Course> findByCourseNameAndGroupNumber(String courseName,
			int groupNumber);

	List<Course> findByPrice(int price);

	List<Course> findAllMandatoryCourses();

	List<Course> findByPriceRange(int from, int to);

}