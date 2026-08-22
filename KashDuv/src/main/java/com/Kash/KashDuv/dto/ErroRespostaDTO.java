package com.Kash.KashDuv.dto;

import java.time.OffsetDateTime;

public record ErroRespostaDTO(String mensagem, String campo, OffsetDateTime timestamp) {
}
