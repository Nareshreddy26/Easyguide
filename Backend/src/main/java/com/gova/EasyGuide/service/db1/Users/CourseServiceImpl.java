package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.DTOS.CourseDto;
import com.gova.EasyGuide.entities.db1.Courses;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.CoursesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CoursesRepo coursesRepo;


    @Override
    public void addNewCourse(Courses course) {

        Optional<Courses> dbCourse = coursesRepo.findByCourseCode(course.getCourseCode());
        if (!dbCourse.isEmpty()) {
            throw new AllExceptions
                    .courseAllReadyExist("course with this code is already exist try with new course code");
        }
        coursesRepo.save(course);

    }

    @Override
    @Transactional
    public void deleteCourse(String courseCode) {
        Optional<Courses> existingCourse = coursesRepo.findByCourseCode(courseCode);
        if (!existingCourse.isPresent()) {
            throw new AllExceptions.courseAllReadyExist("course with this id is not present you can add");
        } else {
            coursesRepo.deleteByCourseCode(courseCode);
        }
    }

    @Override
    public List<CourseDto> listofCourses() {
        List<CourseDto> courses = coursesRepo.findallListedCourse();
        return courses;

    }
}

