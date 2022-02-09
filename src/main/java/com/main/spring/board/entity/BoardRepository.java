package com.main.spring.board.entity;

import com.main.spring.board.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findAll();

    public String save(BoardDto entity);

   Optional<BoardEntity> findById(Long idx);

    @Transactional
    @Modifying
    @Query("UPDATE BoardEntity b SET b.hit_cnt = hit_cnt+1 WHERE b.idx=:idx")
    int updateView(@Param("idx") Long idx);
}