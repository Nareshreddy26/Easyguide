package com.gova.EasyGuide.service.db1.Users;

import com.gova.EasyGuide.DTOS.CourseDto;
import com.gova.EasyGuide.entities.db1.Courses;

import java.util.List;

public interface CourseService {

    public void addNewCourse(Courses course);

    public void deleteCourse(String courseCode);

    public List<CourseDto> listofCourses();
}
