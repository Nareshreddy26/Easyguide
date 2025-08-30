package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.entities.db1.Mentors;
import com.gova.EasyGuide.entities.db1.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserId(Long id);



    Optional<User>findByUserName(String name);

    @Query(nativeQuery = true,value = "Select * from users_table where (:profession is null or profession = :profession) AND (:ratting is null or ratting = :ratting)")
    List<Mentors> findmyoptional(@Param("profession") String profession, @Param("ratting") Double ratting);

}
