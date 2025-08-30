package com.gova.EasyGuide.repositeries.db2repo;

import com.gova.EasyGuide.entities.db2.MentorReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MentorReviewRepo extends MongoRepository<MentorReview,Long> {

    List<MentorReview> findByMentorUserId(Long id);
}
