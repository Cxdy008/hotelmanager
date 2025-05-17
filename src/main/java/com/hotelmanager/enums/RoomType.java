package com.hotelmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    STANDARD("Quarto Standard"),
    LUXO("Quarto de Luxo"),
    SUITE("Suíte Master");

    private final String descricao;
}
