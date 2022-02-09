package com.main.spring.board.service;

import com.main.spring.board.dto.BoardDto;
import com.main.spring.board.dto.BoardUpdateDTO;
import com.main.spring.board.entity.BoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BoardService {
    public List<BoardDto> findAll();

    BoardEntity write(BoardDto boardto);

    Optional<BoardEntity> findById(Long idx);

    void deleteById(Long idx);


    int updateViw(Long idx);

    Optional<BoardEntity> updateFindId(Long idx);

    Optional<BoardEntity> Update(Long idx, BoardUpdateDTO boardUpdateDTO);
}
