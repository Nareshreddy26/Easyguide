package com.gova.EasyGuide.entities.db1;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gova.EasyGuide.Enums.MentorServices;
import com.gova.EasyGuide.entities.db2.MentorReview;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "mentors_table")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mentors extends BaseUser{

    private String workingCompany;

    private String workingRole;


    @Column(name = "user_rating", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer ratting=0;

    private Integer domainExperience;


    @ElementCollection
    @CollectionTable(name = "mentoring_table",joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "service_type")
    @Column(name = "avalibility")
    private Map<MentorServices,Boolean> mentoringService;

    @ManyToMany(mappedBy = "mentors", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Courses> purchasedCourses = new HashSet<>();


    public void addCourse(Courses course)
    {
        this.getPurchasedCourses().add(course);
        course.getMentors().add(this);
    }

    public void removeCourse(Courses course)
    {
        this.purchasedCourses.remove(course);
        course.getMentors().remove(this);
    }

    public Mentors(String userName, String userEmail, String userPassword)

    {
        super(userName, userEmail, userPassword);
    }

//    @ElementCollection
//    @CollectionTable(name="slots_timing",joinColumns=@JoinColumn (name="user_id"))
//    @MapKeyColumn(name = "day")
//    @Column(name = "available_time")
//    private Set<DayOfWeek,String> st = new Set<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<MentorAvalibility> availabilitySlots = new ArrayList<>() {
    };

    @Transient
    private List<MentorReview> reviewList = new ArrayList<>();





}
