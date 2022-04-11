package com.board.web.dto;


// 모든 응답은 Dto 은 해당 패키지에

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
