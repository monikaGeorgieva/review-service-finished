package com.example.reviewservice.review.repository;


import com.example.reviewservice.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findAllByBookIsbn(UUID bookIsbn);
}
