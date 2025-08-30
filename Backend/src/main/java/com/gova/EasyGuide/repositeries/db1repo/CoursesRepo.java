package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.DTOS.CourseDto;
import com.gova.EasyGuide.entities.db1.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepo extends JpaRepository<Courses,String> {

    Optional<Courses> findByCourseCode(String code);

    void deleteByCourseCode(String str);

    @Query(nativeQuery = true,value = "select course_code,course_name from course_table")
    List<CourseDto> findallListedCourse();




}
