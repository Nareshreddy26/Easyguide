package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db1.*;
import com.gova.EasyGuide.entities.db2.MentorReview;
import com.gova.EasyGuide.service.db1.Users.CustomerCareImpl;
import com.gova.EasyGuide.service.db1.Users.MentorService;
import com.gova.EasyGuide.service.db1.Users.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private CustomerCareImpl customerCareService;


    @PostMapping("/user-signup")
    public ResponseEntity<String> userRegistartion(@RequestBody UserRegistartionDto dto)
    {
        User response = userService.regiseterUser(dto);
        System.out.println(response);
        System.out.println(dto);
        return  new ResponseEntity<>("user registered sucesfully",HttpStatus.CREATED);
    }


    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable  Long id )
    {
        user.setUserId(id);
         userService.updatingUsersDeatails(user);
         return  new ResponseEntity<>("user data updated sucesfully", HttpStatus.OK);
    }

    @PostMapping("/mentor-signup")
    public ResponseEntity<String> mentorRegistration(@RequestBody UserRegistartionDto dto)
    {
        mentorService.registerMentor(dto);
        return new ResponseEntity<>("Mentor is registered is sucesfully",HttpStatus.CREATED);
    }

    @PutMapping("/update-mentor/{id}")
    public ResponseEntity<String> updateUser(@RequestBody Mentors mentor,@PathVariable Long id)
    {
        mentor.setUserId(id);
        mentorService.updateMentor(mentor);
        return  new ResponseEntity<>("Mentor details updated sucesfully",HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getMentorList(
            @RequestParam(required = false) String profession,
            @RequestParam(required = false ,defaultValue = "0") Integer rating,
            @RequestParam(required = false ,defaultValue = "0") Integer experience,
            @RequestParam(required = false) String company,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "10")Integer pageSize,
            @RequestParam(required = false,defaultValue ="userId") String sortBy,
            @RequestParam(required = false,defaultValue = "true") Boolean ascending
            ) {
//        Sort sort=ascending?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        System.out.println("Received parameters:");
        System.out.println("profession: " + profession);
        System.out.println("rating: " + rating);
        System.out.println("experience: " + experience);
        System.out.println("company: " + company);
        System.out.println("pagenumber: " + pageNumber);
        System.out.println("ascending"+ascending);
        Pageable pagable= PageRequest.of(pageNumber,pageSize);
        Page<Mentors> data = mentorService.getMentorListForUsers(profession, rating, experience, company, pagable);

        if(data.hasContent())
        {
            List<Mentors> sortedMentors = data.getContent().stream().sorted(
                ascending? Comparator.comparing(Mentors::getUserName):Comparator.comparing(Mentors::getUserName).reversed()
            ).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("content", data.getContent());
            response.put("totalPages", data.getTotalPages());
            response.put("totalElements", data.getTotalElements());
            response.put("currentPage", data.getNumber());
            response.put("size", data.getSize());
            response.put("length",data.getNumberOfElements());
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            return  new ResponseEntity<>("No content is matched with the follwing requirments",HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> validateUser(@RequestBody UserLogin userDetails,
                                          HttpServletResponse response) {
        try {
            HashMap<String, String> tokens = userService.validateUser(userDetails);

            if (!tokens.isEmpty()) {
                // Set secure HTTP-only cookies
                ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", tokens.get("JWT_TOKEN"))
                        .httpOnly(true)
                        .secure(false) // Set to true in production with HTTPS
                        .path("/")
                        .maxAge(24 * 60 * 60) // 1 day
                        .sameSite("Lax") // or "None" if cross-site needed
                        .build();

                ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", tokens.get("REFRESH_TOKEN"))
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(7 * 24 * 60 * 60) // 7 days
                        .sameSite("Lax")
                        .build();

                // Add cookies to response
                response.addHeader("Set-Cookie", jwtCookie.toString());
                response.addHeader("Set-Cookie", refreshCookie.toString());

                // Also return tokens in response body if needed
                return ResponseEntity.ok()
                        .header("Set-Cookie", jwtCookie.toString())
                        .header("Set-Cookie", refreshCookie.toString())
                        .body(tokens);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid credentials"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Login failed: " + e.getMessage()));
        }
    }

    @GetMapping("/getmentor/{userId}")
    public ResponseEntity<Mentors> getMentorById(@PathVariable Long userId)
    {

        return  new ResponseEntity<>(mentorService.getMentorWithId(userId),HttpStatus.OK);
    }


    @PostMapping("/review/{mentorId}/{userId}")
    public ResponseEntity<?> addMentorReview(@RequestBody MentorReview review,
                                             @PathVariable Long mentorId,@PathVariable Long userId)
    {
        return  new ResponseEntity<>(mentorService.addMentorReview(review,mentorId,userId),HttpStatus.CREATED);
    }

    @PostMapping("/saveCutomerCare")
    public  ResponseEntity<?> saveCustomerCare(@RequestBody CustomerCare customerCare)
    {
        customerCareService.saveCustomerCare(customerCare);
        return new ResponseEntity<>("Data Saved sucessfully",HttpStatus.OK);
    }

    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity<String> frogotPassword(@PathVariable String email)
    {
        return  new ResponseEntity<>(userService.forgotPasswordLink(email),HttpStatus.OK);

    }
    @PostMapping("/verify-forgot-password")

    public ResponseEntity<String> verifyForgotPassword(
                                                       @RequestBody ForgotPassword credentials) {
        String response = userService.verifyForgotPasswordLink(credentials, credentials.token);

        if (response.equals("expired")) {
            return new ResponseEntity<>("Token expired or invalid", HttpStatus.GONE);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }






}
