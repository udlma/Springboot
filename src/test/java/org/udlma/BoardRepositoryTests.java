package org.udlma;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;
import org.udlma.domain.Board;
import org.udlma.domain.QBoard;
import org.udlma.persistence.BoardRepository;

import com.querydsl.core.BooleanBuilder;

import lombok.extern.java.Log;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {
	
	@Autowired
	BoardRepository repo;
	
	@Test
	public void insert() {
		
		for(int i=0; i<100; i++) {
			Board board = new Board();
			board.setTitle("title"+i);
			board.setContent("content"+i);
			board.setWriter("user"+i);
			repo.save(board);
		}
	}
	
	@Test
	public void read() {
		
		repo.findById(1L).ifPresent(board -> log.info(""+board));
		
	}
	
	@Test
	public void delete() {
		
		repo.deleteById(1L);
	}
	
	@Test
	public void update() {
		
		Board board = new Board();
		board.setBno(2L);
		board.setTitle("update");
		board.setContent("update");
		board.setWriter("updateUSER");
		
		repo.save(board);
		
	}
	
	@Test
	public void list() {
		
		
		Pageable paging = PageRequest.of(0, 10);
		
		repo.findBoardByBnoGreaterThanOrderByBnoDesc(0L, paging).forEach(vo -> log.info(""+vo));
		
	}
	
	@Test
	public void list2() {
		
		Page<Board> result = repo.findByTitleContains("2", PageRequest.of(0, 10));
		
		log.info(""+result);
		log.info(""+result.getTotalPages());
	}
	
	@Test
	public void list3() {
		
		Page<Board> result = repo.findBoardByBnoGreaterThan(0L, PageRequest.of(0, 10, Sort.Direction.DESC));
		
		log.info(""+result);
		log.info(""+result.getTotalPages());
	}
	
	@Test
	public void list4() {
		
		Page<Board> result = repo.getList(PageRequest.of(0, 10));
		
		log.info(""+result);
		
	}
	
	@Test
	public void testQuerydsl() {
		
		String type="t";
		String keyword="title";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard board = QBoard.board;
		
		if(type.equals("t")) {
			
			builder.and(board.title.like("%"+keyword+"%"));
		}
		
		builder.and(board.bno.gt(0L));
		
		Pageable page = PageRequest.of(0, 10, Direction.DESC, "bno");
		
		Page<Board> list = repo.findAll(builder, page);
		
		list.iterator().forEachRemaining(vo -> log.info(""+vo));
		
		log.info(""+list.getTotalPages());
	}

}
