package com.main.spring.board.controller;

import com.main.spring.board.dto.BoardDto;
import com.main.spring.board.dto.BoardUpdateDTO;
import com.main.spring.board.entity.BoardEntity;
import com.main.spring.board.entity.BoardRepository;
import com.main.spring.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping(value = "/api/ip")
    public ResponseEntity<String> ip (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        return ResponseEntity.ok(request.getRemoteAddr());
    }
    
    // 게시판 목록 조회하기
    @GetMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<BoardDto>> getboardList() {
        log.info("=========전체 목록 조회=========");
        List<BoardDto> boards = this.boardService.findAll();
        return new ResponseEntity<List<BoardDto>>(boards, HttpStatus.OK);
    }
    
    // 게시판 등록하기
    @PostMapping(value = "/write")
    public Object boardWrite(@RequestBody BoardDto boardto) {
        //GET 통신에서는 @RequestParam을 사용하지만, POST 통신에서는 @RequestBody를 사용한다.
        log.info("========== 등록하기 ========"+boardto);
        return this.boardService.write(boardto);
    }

    // 게시판 상세보기
    // @PathVariable 에선 무조건 String으로 인식됨, int 로 가져와도 자동으로 string으로 매핑됨
    // int로 idx를 가져왔는데, 레포지토리에 있는 Long 타입 때문인지 에러가 떨어짐
    @GetMapping("/{idx}")
    public Optional<BoardEntity> detail(@PathVariable Long idx) {
        log.info("========= 상세보기 조회 =========");
        // 조회수 증가
        boardService.updateViw(idx);
        return  boardService.findById(idx);
    }

    // 게시판 삭제하기
    @DeleteMapping("/{idx}")
    public void delete(@PathVariable Long idx){
        log.info("========= 삭제하기 =========");
       boardService.deleteById(idx);
    }
    
    // 게시판 수정하기
    @GetMapping("/update/{idx}")
    public Optional<BoardEntity> udpate(@PathVariable Long idx) {
        log.info("========= 수정하기 조회 =========");
        return boardService.updateFindId(idx);
    }

    // 게시판 수정처리
    @PutMapping(value = "/update/{idx}")
    public ResponseEntity<BoardEntity> boardUpdate(@RequestBody BoardUpdateDTO boardUpdateDTO, @PathVariable Long idx) {
        //GET 통신에서는 @RequestParam을 사용하지만, POST 통신에서는 @RequestBody를 사용한다.
        // boardto = boardService.findById(idx);
        log.info("========= 수정하기 ======="+boardUpdateDTO+"====idx==="+idx);
        Optional<BoardEntity> boardto = this.boardService.Update(idx, boardUpdateDTO);
        //return null;
        return new ResponseEntity(boardto, HttpStatus.OK);
        // return ResponseEntity.badRequest().build();
    }
}