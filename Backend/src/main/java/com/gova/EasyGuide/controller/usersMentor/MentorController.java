package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.DTOS.CourseDto;
import com.gova.EasyGuide.DTOS.MentorAvailabilityResponseDTO;
import com.gova.EasyGuide.entities.db1.*;
import com.gova.EasyGuide.service.db1.Users.CourseServiceImpl;
import com.gova.EasyGuide.service.db1.Users.MentorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class MentorController {


    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private MentorServiceImpl mentorService;

    @PostMapping("/add-course")
    public ResponseEntity<Courses> addnewCourse(@RequestBody Courses course)
    {
        courseService.addNewCourse(course);
        return  new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-course")
    public ResponseEntity<Courses> removeCourse(@RequestBody Courses course)
    {
        courseService.deleteCourse(course.getCourseCode());
        return  new ResponseEntity<>(course,HttpStatus.OK);
    }

    @GetMapping("/list-courses")
    public ResponseEntity<List<CourseDto>> getallCourses()
    {
         courseService.listofCourses();
        return new ResponseEntity<>(courseService.listofCourses(),HttpStatus.OK);
    }

    @PostMapping("/addSlot/{id}")
    public ResponseEntity<String>  addMentorSlots(@RequestBody List<MentorAvalibility> mentorAvalibilityList ,@PathVariable Long id)
    {
        mentorService.addMentorSlots(mentorAvalibilityList,id);
        return  new ResponseEntity<>("New Slots are updated",HttpStatus.OK);
    }



    @GetMapping("/getpreviousSlots/{id}")
    public ResponseEntity<MentorAvailabilityResponseDTO> getPreviousMentorSlots(@PathVariable Long id)
    {
        return new ResponseEntity<>(mentorService.getPreviousMentorSlots(id),HttpStatus.OK);
    }

//    @GetMapping("/mentorlogin")
//    public ResponseEntity<Boolean> validateMentor(@RequestBody UserLogin user)
//    {
//        return new ResponseEntity<>(
//                mentorService.validateMentor(user.getUserName(), user.getUserPassword())
//                ,HttpStatus.OK);
//    }




}
