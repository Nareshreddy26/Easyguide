package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.Enums.Weekday;
import com.gova.EasyGuide.entities.db1.MentorAvalibility;
import com.gova.EasyGuide.entities.db1.MentorAvalibilityId;
import com.gova.EasyGuide.entities.db1.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface MentorAvalibilityRepo extends JpaRepository<MentorAvalibility, MentorAvalibilityId> {

    List<MentorAvalibility> findByMentor_UserId(Long userId);


    boolean existsByMentorAndWeekdayAndStartTimeAndEndTime(Mentors mentor, Weekday weekday, LocalTime startTime, LocalTime endTime);

}
