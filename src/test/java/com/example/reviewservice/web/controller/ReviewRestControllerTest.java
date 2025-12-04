package com.example.reviewservice.web.controller;

import com.example.reviewservice.review.model.Review;
import com.example.reviewservice.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReviewRestControllerTest {

    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Създаваме контролера ръчно и строим MockMvc около него
        ReviewRestController controller = new ReviewRestController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAll_returnsListOfReviews() throws Exception {
        Review r = Review.builder()
                .id(UUID.randomUUID())
                .bookIsbn(UUID.randomUUID())
                .username("testuser")
                .rating(5)
                .comment("Super book!")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        when(reviewService.findAll()).thenReturn(List.of(r));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].rating").value(5));

        verify(reviewService, times(1)).findAll();
    }

    @Test
    void create_persistsReview_andReturns201() throws Exception {
        UUID bookId = UUID.randomUUID();

        String body = """
                {
                  "bookIsbn": "%s",
                  "username": "apiUser",
                  "rating": 4,
                  "comment": "Nice book"
                }
                """.formatted(bookId);

        // stub-ваме service.create(...) да върне Review
        when(reviewService.create(any()))
                .thenAnswer(invocation -> {
                    // можеш да капчърнеш request-а, ако искаш да го провериш
                    return Review.builder()
                            .id(UUID.randomUUID())
                            .bookIsbn(bookId)
                            .username("apiUser")
                            .rating(4)
                            .comment("Nice book")
                            .createdOn(LocalDateTime.now())
                            .updatedOn(LocalDateTime.now())
                            .build();
                });

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("apiUser"))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Nice book"));

        verify(reviewService, times(1)).create(any());
    }
}

