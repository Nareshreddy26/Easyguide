package com.gova.EasyGuide.entities.db2;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gova.EasyGuide.entities.db1.Mentors;
import groovy.lang.GString;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mentor_reviews")
public class MentorReview {

    @Id
    private String id;

    private Long mentorUserId;

    private String userName;
    
    private String heading;
    
    private String body;
    
    public void setMentorReview(Long mentorUserId,String userName, String heading, String body)
    {

        this.body=body;
        this.heading=heading;
        this.userName=userName;
        this.mentorUserId=mentorUserId;
    }


    
    
    
}
