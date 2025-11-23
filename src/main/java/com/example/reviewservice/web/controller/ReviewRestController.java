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
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    /**
     * Връща всички ревюта.
     */
    @GetMapping
    public List<Review> getAll() {
        return reviewService.findAll();
    }

    /**
     * Връща ревю по неговото id.
     */
    @GetMapping("/{id}")
    public Review getById(@PathVariable UUID id) {
        return reviewService.findById(id);
    }

    /**
     * Връща всички ревюта за дадена книга.
     */
    @GetMapping("/book/{bookIsbn}")
    public List<Review> getByBook(@PathVariable UUID bookIsbn) {
        return reviewService.findAllByBookIsbn(bookIsbn);
    }

    /**
     * Създава ново ревю.
     */
    @PostMapping
    public ResponseEntity<Review> create(@Valid @RequestBody ReviewRequest request) {
        Review created = reviewService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновява съществуващо ревю.
     */
    @PutMapping("/{id}")
    public Review update(@PathVariable UUID id,
                         @Valid @RequestBody ReviewRequest request) {
        return reviewService.update(id, request);
    }

    /**
     * Изтрива ревю.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
