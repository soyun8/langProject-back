package com.main.spring.board.dto;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class BoardDto {

    private Long idx;
    private int hit_cnt;
    private String title;
    private String content;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    @Builder
    public BoardDto(Long idx, String title, String content, LocalDateTime update_date) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.update_date = update_date;
    }

    public BoardDto toEntity(){
        return BoardDto.builder()
                .idx(idx)
                .title(title)
                .content(content)
                .update_date(update_date)
                .build();
    }
}
