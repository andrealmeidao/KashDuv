package com.Kash.KashDuv.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioDTO(@NotBlank String username, @NotBlank String password) { }