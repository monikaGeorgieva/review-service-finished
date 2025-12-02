package com.example.reviewservice.web.dto;



import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_hasNoViolations() {
        ReviewRequest req = new ReviewRequest(
                UUID.randomUUID(),
                "user",
                5,
                "Nice book"
        );

        Set violations = validator.validate(req);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalidRating_returnsError() {
        ReviewRequest req = new ReviewRequest(
                UUID.randomUUID(),
                "user",
                10, // invalid
                "Nice book"
        );

        Set violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void emptyUsername_returnsViolation() {
        ReviewRequest req = new ReviewRequest(
                UUID.randomUUID(),
                "",
                3,
                "ok"
        );

        Set violations = validator.validate(req);

        assertThat(violations).isNotEmpty();
    }
}

