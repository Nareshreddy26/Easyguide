package com.gova.EasyGuide.entities.db1;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gova.EasyGuide.Enums.Weekday;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "mentor_availability")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@IdClass(MentorAvalibilityId.class)
public class MentorAvalibility {

    @JsonBackReference
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Reuses mentor_id as FK
    private Mentors mentor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Id
    private Weekday weekday;

    @Id
    private LocalTime startTime;

    private  LocalTime endTime;

    private Boolean bookingStatus=false;

    public void setMentor(Mentors mentors)
    {
        this.mentor=mentors;
    }




}
