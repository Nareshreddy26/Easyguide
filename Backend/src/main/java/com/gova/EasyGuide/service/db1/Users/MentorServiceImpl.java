package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.DTOS.MentorAvailabilityResponseDTO;
import com.gova.EasyGuide.DTOS.MentorAvailabilitySlotDTO;
import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.entities.db1.MentorAvalibility;
import com.gova.EasyGuide.entities.db1.Mentors;
import com.gova.EasyGuide.entities.db1.UserLogin;
import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db2.MentorReview;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorAvalibilityRepo;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import com.gova.EasyGuide.repositeries.db1repo.UserRepo;
import com.gova.EasyGuide.repositeries.db2repo.MentorReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private MentorAvalibilityRepo mentorAvalibilityRepo;


    @Autowired
    private MentorReviewRepo mentorReviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;



    @Override
    public Mentors registerMentor(UserRegistartionDto dto) throws AllExceptions.userAllReadyExist{
        Optional<Mentors> userfind = mentorRepo.findByUserEmail(dto.getDtoUseremail());

        if(userfind.isPresent())
        {
            throw  new AllExceptions.userAllReadyExist("User allready exist with user mail");

        }
        Mentors mentor = new Mentors(dto.getDtoUsername(), dto.getDtoUseremail(), dto.getDtoUserPassword());
        mentor.setRatting(3);
        mentor.setRoles(Roles.MENTOR);
        return mentorRepo.save(mentor);
    }

    @Override
    public Mentors updateMentor(Mentors mentor) {
       Optional<Mentors> mentorfind=mentorRepo.findByUserId(mentor.getUserId());
       if(mentorfind.isPresent())
       {
          Mentors current= mentorfind.get();

          if(mentor.getWorkingCompany() !=null)
          {
              current.setWorkingCompany(mentor.getWorkingCompany());
          }
          if(mentor.getWorkingCompany() !=null)
          {
              current.setWorkingRole(mentor.getWorkingRole());
          }
          if(mentor.getAbout() !=null)
          {
              current.setAbout(mentor.getAbout());
          }
          if(mentor.getMentoringService() !=null)
          {
              if(current.getMentoringService()==null)
              {
                  current.setMentoringService(new HashMap<>());
              }
              current.getMentoringService().putAll(mentor.getMentoringService());
          }

          return mentorRepo.save(current);
       }
       else {
           throw new AllExceptions.userNotFound("User with the give id doesn't exist");
       }
    }


    //verification is pending
    @Override
    public Page<Mentors> getMentorListForUsers(String profession, Integer rating, Integer experience, String company, Pageable pageable) {
        return mentorRepo.getmentorlistforUsers(profession, rating, experience, company, pageable);
    }

    @Override
    public void addMentorSlots(List<MentorAvalibility> mentorSlots, Long id) {
        Optional<Mentors> mentorsOptional = mentorRepo.findByUserId(id);
        DateTimeFormatter formate = DateTimeFormatter.ofPattern("HH:mm");

        if (mentorsOptional.isPresent()) {
            Mentors mentor = mentorsOptional.get();

            for (MentorAvalibility slot : mentorSlots) {
                LocalTime startTime = LocalTime.parse(slot.getStartTime().toString(), formate);
                LocalTime endTime = LocalTime.parse(slot.getEndTime().toString(),formate);

                slot.setStartTime(startTime);
                slot.setEndTime(endTime);
                slot.setMentor(mentor);

                // Check if the slot already exists before inserting
                boolean exists = mentorAvalibilityRepo.existsByMentorAndWeekdayAndStartTimeAndEndTime(
                        mentor, slot.getWeekday(), slot.getStartTime(), slot.getEndTime()
                );

                if (!exists) {
                    mentorAvalibilityRepo.save(slot);
                }
                else {
                    throw new AllExceptions.resourceAllreadyExist("some new slots are previously exist");
                }
            }
        } else {
            throw new AllExceptions.userNotFound("User with this ID not found");
        }
    }

    @Override
    public Mentors getMentorWithId(Long id) {

        Optional<Mentors> optionalMentors =mentorRepo.findByUserId(id);


        if(optionalMentors.isPresent())
        {
            Mentors mentors = optionalMentors.get();
            mentors.setReviewList(mentorReviewRepo.findByMentorUserId(id));
            return mentors;
        }else {
            throw new AllExceptions.userNotFound("user not found with the given id");
        }
    }

    @Override
    public MentorAvailabilityResponseDTO getPreviousMentorSlots(Long id) {

        Optional<Mentors> optionalMentors = mentorRepo.findByUserId(id);
        List<MentorAvailabilitySlotDTO> slots = null;
        if (optionalMentors.isPresent()) {
            List<MentorAvalibility> mentorAvalibilityList = mentorAvalibilityRepo.findByMentor_UserId(id);
            slots = mentorAvalibilityList.stream().map(slot -> new MentorAvailabilitySlotDTO(
                            slot.getWeekday().toString(),
                            slot.getStartTime().toString(),
                            slot.getEndTime().toString(),
                            slot.getBookingStatus()
                    )
            ).collect(Collectors.toList());
            return new MentorAvailabilityResponseDTO(id, slots);
        }else {
            throw new AllExceptions.userNotFound("user not found with the given id");
        }


    }

    @Override
    public HashMap<String, String> validateMentor(UserLogin userLogin) {
        Optional<Mentors> optionalMentors = mentorRepo.findByUserEmail(userLogin.getUserName());
        if (optionalMentors.isPresent()) {
            try {
                // Authenticate credentials
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLogin.getUserName(),
                                userLogin.getUserPassword()
                        )
                );

                // Load UserDetails with proper authorities
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(userLogin.getUserName());

                // Generate tokens using UserDetails (recommended approach)
                HashMap<String, String> keys = new HashMap<>();
                keys.put("JWT_TOKEN", jwtService.generateToken(userDetails));
                keys.put("REFRESH_TOKEN", jwtService.generateRefreshToken(userDetails));

                return keys;

            } catch (AuthenticationException e) {
                throw new AllExceptions.invalidCredentails("Credentials are not matched");
            }
        } else {
            throw new AllExceptions.userNotFound("User with this username is not registered, please register");
        }
    }

    @Override
    public String addMentorReview(MentorReview review, Long mentorId, Long userId) {

        Mentors mentor = mentorRepo.findByUserId(mentorId).orElse(null);

        if(mentor==null)
        {
            throw new AllExceptions.userNotFound("Mentor is not found");
        }
        if(userRepo.findByUserId(userId).isEmpty())
        {
            throw new AllExceptions.userNotFound("Mentor is not found");
        }
        MentorReview review1 = new MentorReview();
        review1.setMentorReview(mentorId, mentor.getUserName(), review.getHeading(),review.getBody());
        mentorReviewRepo.save(review1);

        return "Reviewed sucesfully";
    }

    @Override
    public List<MentorReview> getReviwsForMentor(Long id) {
        return mentorReviewRepo.findByMentorUserId(id);
    }


}


