package com.gova.EasyGuide.entities.db1;

import com.gova.EasyGuide.Enums.Weekday;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MentorAvalibilityId implements Serializable {


    private Long mentor;

    private Weekday weekday;

    private LocalTime startTime;

    private LocalTime endTime;



}
