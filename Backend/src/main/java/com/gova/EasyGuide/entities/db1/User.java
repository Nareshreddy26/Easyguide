package com.gova.EasyGuide.entities.db1;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_table")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User extends BaseUser {

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Courses> purchasedCourses = new HashSet<>();


    public User(String userName, String userEmail, String userPassword) {
        super(userName, userEmail, userPassword);

    }

    public User(String userEmail,String userPassword)
    {
        super(userEmail,userPassword);
    }

    public void addCourse(Courses course)
    {
        this.purchasedCourses.add(course);
        course.getUsers().add(this);
    }

    public void removeCourse(Courses course)
    {
        this.purchasedCourses.remove(course);
        course.getUsers().remove(this);
    }

}
