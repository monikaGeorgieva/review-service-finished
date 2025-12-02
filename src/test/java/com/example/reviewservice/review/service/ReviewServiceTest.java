package com.example.reviewservice.review.service;



import com.example.reviewservice.exeption.ReviewNotFoundException;
import com.example.reviewservice.review.model.Review;
import com.example.reviewservice.review.repository.ReviewRepository;
import com.example.reviewservice.web.dto.ReviewRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void findAll_returnsAllReviews() {
        Review r1 = new Review();
        Review r2 = new Review();
        when(reviewRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Review> result = reviewService.findAll();

        assertThat(result).hasSize(2);
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void findById_returnsReview_whenExists() {
        UUID id = UUID.randomUUID();
        Review review = new Review();
        review.setId(id);

        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        Review result = reviewService.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(reviewRepository).findById(id);
    }

    @Test
    void findById_throws_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.findById(id))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    @Test
    void create_savesNewReview() {
        UUID bookId = UUID.randomUUID();
        ReviewRequest request = new ReviewRequest(bookId, "user1", 5, "Nice!");

        when(reviewRepository.save(any(Review.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Review created = reviewService.create(request);

        assertThat(created.getBookIsbn()).isEqualTo(bookId);
        assertThat(created.getUsername()).isEqualTo("user1");
        assertThat(created.getRating()).isEqualTo(5);
        assertThat(created.getComment()).isEqualTo("Nice!");
        assertThat(created.getCreatedOn()).isNotNull();
        assertThat(created.getUpdatedOn()).isNotNull();
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void update_updatesExistingReview() {
        UUID id = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        Review existing = Review.builder()
                .id(id)
                .bookIsbn(bookId)
                .username("oldUser")
                .rating(2)
                .comment("old")
                .createdOn(LocalDateTime.now().minusDays(1))
                .updatedOn(LocalDateTime.now().minusDays(1))
                .build();

        ReviewRequest request = new ReviewRequest(bookId, "newUser", 4, "new comment");

        when(reviewRepository.findById(id)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));

        Review updated = reviewService.update(id, request);

        assertThat(updated.getUsername()).isEqualTo("newUser");
        assertThat(updated.getRating()).isEqualTo(4);
        assertThat(updated.getComment()).isEqualTo("new comment");
        assertThat(updated.getUpdatedOn()).isAfter(existing.getCreatedOn());
        verify(reviewRepository).save(existing);
    }

    @Test
    void delete_deletes_whenExists() {
        UUID id = UUID.randomUUID();
        when(reviewRepository.existsById(id)).thenReturn(true);

        reviewService.delete(id);

        verify(reviewRepository).deleteById(id);
    }

    @Test
    void delete_throws_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(reviewRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> reviewService.delete(id))
                .isInstanceOf(ReviewNotFoundException.class);
    }
}

