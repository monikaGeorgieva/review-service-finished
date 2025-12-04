package com.example.reviewservice.review.repository;

import com.example.reviewservice.review.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void save_persistsReview_andGeneratesId() {
        Review review = Review.builder()
                .bookIsbn(UUID.randomUUID())
                .username("repoUser")
                .rating(4)
                .comment("Nice from repo test")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("repoUser");
    }

    @Test
    void findById_returnsSavedReview() {
        Review review = Review.builder()
                .bookIsbn(UUID.randomUUID())
                .username("findUser")
                .rating(3)
                .comment("to be found")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        Optional<Review> found = reviewRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getComment()).isEqualTo("to be found");
    }

    @Test
    void findAll_returnsAllReviews() {
        reviewRepository.deleteAll();

        Review r1 = Review.builder()
                .bookIsbn(UUID.randomUUID())
                .username("u1")
                .rating(5)
                .comment("r1")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Review r2 = Review.builder()
                .bookIsbn(UUID.randomUUID())
                .username("u2")
                .rating(2)
                .comment("r2")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        reviewRepository.saveAll(List.of(r1, r2));

        List<Review> all = reviewRepository.findAll();

        assertThat(all).hasSize(2);
    }

    @Test
    void deleteById_removesReview() {
        Review review = Review.builder()
                .bookIsbn(UUID.randomUUID())
                .username("deleteUser")
                .rating(1)
                .comment("to delete")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        reviewRepository.deleteById(saved.getId());

        Optional<Review> found = reviewRepository.findById(saved.getId());

        assertThat(found).isEmpty();
    }
}

