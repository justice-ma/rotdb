package com.rotdb.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginResult(
        @NotBlank String token,
        @NotBlank Long userId,
        @NotBlank @Email String email,
        @NotBlank String username
) {
}
