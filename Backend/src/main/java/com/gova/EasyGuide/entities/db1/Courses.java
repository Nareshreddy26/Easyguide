package com.gova.EasyGuide.entities.db1;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "course_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Courses {

    @Id
    private String courseCode;

    private String courseName;

    // Mapping with Users
    @ManyToMany
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_code")
    )
    private Set<User> users = new HashSet<>();

    // Mapping with Mentors
    @ManyToMany
    @JoinTable(
            name = "mentor_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_code")
    )
    private Set<Mentors> mentors = new HashSet<>();


    public void  setUser(User user)
    {
        //courses.getusers.add(users)
        this.getUsers().add(user);
        //user.getpu....add(Courses)
        user.getPurchasedCourses().add(this);
    }

    public void removeUser(User user)
    {
        this.getUsers().remove(user);
        user.getPurchasedCourses().remove(this);
    }

    public void  setMentor(Mentors mentor)
    {

        this.getMentors().add(mentor);
        mentor.getPurchasedCourses().add(this);
    }

    public void removeMentor(Mentors mentor)
    {
        this.getMentors().remove(mentor);
        mentor.getPurchasedCourses().remove(this);
    }




}
