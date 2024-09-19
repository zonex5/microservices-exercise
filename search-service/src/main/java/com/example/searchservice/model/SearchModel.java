package com.example.searchservice.model;

import jakarta.validation.constraints.NotNull;

public record SearchModel(@NotNull String q, @NotNull String place) { // ?????????
}
