package com.example.reviewservice.web.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;


import java.util.UUID;

@Builder
public record ReviewRequest(
        @NotNull
        UUID bookIsbn,

        @NotBlank
        String username,

        @Min(1)
        @Max(5)
        int rating,

        @NotBlank
        @Size(max = 1000)
        String comment
) {}
