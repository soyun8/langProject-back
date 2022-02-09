package com.main.spring.board.entity;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@DynamicUpdate
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private int hit_cnt;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @CreationTimestamp
    @NotNull
    private LocalDateTime create_date;

    @Column
    @UpdateTimestamp
    private LocalDateTime update_date;

}
