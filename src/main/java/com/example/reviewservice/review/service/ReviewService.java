package com.example.reviewservice.review.service;

import com.example.reviewservice.exeption.ReviewNotFoundException;
import com.example.reviewservice.review.model.Review;
import com.example.reviewservice.review.repository.ReviewRepository;
import com.example.reviewservice.web.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<Review> findAllByBookIsbn(UUID bookIsbn) {
        return reviewRepository.findAllByBookIsbn(bookIsbn);
    }


    public Review findById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }


    public Review create(ReviewRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Review review = Review.builder()
                .bookIsbn(request.bookIsbn())
                .username(request.username())
                .rating(request.rating())
                .comment(request.comment())
                .createdOn(now)
                .updatedOn(now)
                .build();

        return reviewRepository.save(review);
    }


    public Review update(UUID id, ReviewRequest request) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        existing.setBookIsbn(request.bookIsbn());
        existing.setUsername(request.username());
        existing.setRating(request.rating());
        existing.setComment(request.comment());
        existing.setUpdatedOn(LocalDateTime.now());

        return reviewRepository.save(existing);
    }


    public void delete(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }
        reviewRepository.deleteById(id);
    }
}
