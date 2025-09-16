package com.mentora.backend.agent;

import jakarta.validation.constraints.NotNull;

public record ChatRequest(@NotNull String prompt) {}