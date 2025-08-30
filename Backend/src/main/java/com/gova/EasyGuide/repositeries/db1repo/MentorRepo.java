package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.entities.db1.Mentors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MentorRepo extends JpaRepository<Mentors,Long> {


    Optional<Mentors> findByUserEmail(String email);

    Optional<Mentors> findByUserId(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM mentors_table WHERE (:profession IS NULL OR LOWER(profession) = LOWER(:profession)) " +
                    "AND (:rating IS NULL OR user_rating >= :rating) " +
                    "AND (:experience IS NULL OR domain_experience >= :experience) " +
                    "AND (:company IS NULL OR LOWER(working_company) = LOWER(:company)) " +
                    "ORDER BY user_id ASC",
            countQuery = "SELECT COUNT(*) FROM mentors_table WHERE (:profession IS NULL OR LOWER(profession) = LOWER(:profession)) " +
                    "AND (:rating IS NULL OR user_rating >= :rating) " +
                    "AND (:experience IS NULL OR domain_experience >= :experience) " +
                    "AND (:company IS NULL OR LOWER(working_company) = LOWER(:company))"
    )
    Page<Mentors> getmentorlistforUsers(@Param("profession") String profession, @Param("rating") Integer rating,
                                        @Param("experience") Integer experience, @Param("company") String company, Pageable paga);

    Mentors getMentorsByUserId(Long id);
}
