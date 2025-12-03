package com.example.reviewservice.review.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {

    @Test
    void builder_shouldCreateReviewWithAllFields() {
        UUID id = UUID.randomUUID();
        UUID bookIsbn = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Review review = Review.builder()
                .id(id)
                .bookIsbn(bookIsbn)
                .username("testUser")
                .rating(5)
                .comment("Great book")
                .createdOn(now)
                .updatedOn(now)
                .build();

        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getBookIsbn()).isEqualTo(bookIsbn);
        assertThat(review.getUsername()).isEqualTo("testUser");
        assertThat(review.getRating()).isEqualTo(5);
        assertThat(review.getComment()).isEqualTo("Great book");
        assertThat(review.getCreatedOn()).isEqualTo(now);
        assertThat(review.getUpdatedOn()).isEqualTo(now);
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        UUID id = UUID.randomUUID();
        UUID bookIsbn = UUID.randomUUID();
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime updated = LocalDateTime.now();

        Review review = new Review();
        review.setId(id);
        review.setBookIsbn(bookIsbn);
        review.setUsername("anotherUser");
        review.setRating(3);
        review.setComment("Not bad");
        review.setCreatedOn(created);
        review.setUpdatedOn(updated);

        assertThat(review.getId()).isEqualTo(id);
        assertThat(review.getBookIsbn()).isEqualTo(bookIsbn);
        assertThat(review.getUsername()).isEqualTo("anotherUser");
        assertThat(review.getRating()).isEqualTo(3);
        assertThat(review.getComment()).isEqualTo("Not bad");
        assertThat(review.getCreatedOn()).isEqualTo(created);
        assertThat(review.getUpdatedOn()).isEqualTo(updated);
    }
}

