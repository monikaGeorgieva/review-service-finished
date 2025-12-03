package com.example.reviewservice.web.controller;

import com.example.reviewservice.review.model.Review;
import com.example.reviewservice.review.service.ReviewService;
import com.example.reviewservice.web.dto.ReviewRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;


    @GetMapping
    public List<Review> getAll() {
        return reviewService.findAll();
    }


    @GetMapping("/{id}")
    public Review getById(@PathVariable UUID id) {
        return reviewService.findById(id);
    }


    @GetMapping("/book/{bookIsbn}")
    public List<Review> getByBook(@PathVariable UUID bookIsbn) {
        return reviewService.findAllByBookIsbn(bookIsbn);
    }


    @PostMapping
    public ResponseEntity<Review> create(@Valid @RequestBody ReviewRequest request) {
        Review created = reviewService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public Review update(@PathVariable UUID id,
                         @Valid @RequestBody ReviewRequest request) {
        return reviewService.update(id, request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
