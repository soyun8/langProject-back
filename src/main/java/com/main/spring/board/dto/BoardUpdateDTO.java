package com.main.spring.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardUpdateDTO {

    private Long idx;
    private int hit_cnt;
    private String title;
    private String content;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
}
