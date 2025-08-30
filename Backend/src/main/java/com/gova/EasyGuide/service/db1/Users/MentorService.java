package com.gova.EasyGuide.service.db1.Users;



import com.gova.EasyGuide.DTOS.MentorAvailabilityResponseDTO;
import com.gova.EasyGuide.entities.db1.MentorAvalibility;
import com.gova.EasyGuide.entities.db1.Mentors;
import com.gova.EasyGuide.entities.db1.UserLogin;
import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db2.MentorReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
public interface MentorService {
    public Mentors registerMentor(UserRegistartionDto dto);

    public Mentors updateMentor(Mentors mentor);

    Page<Mentors> getMentorListForUsers(String profession, Integer rating, Integer experience, String company, Pageable pageable);

    public void addMentorSlots(List<MentorAvalibility> mentorSlots,Long id);

    public Mentors getMentorWithId(Long id);

    public MentorAvailabilityResponseDTO getPreviousMentorSlots(Long id);

    public HashMap<String,String> validateMentor(UserLogin userLogin);

    public String addMentorReview(MentorReview review,Long mentorId,Long userId);

    public List<MentorReview> getReviwsForMentor(Long id);

}



