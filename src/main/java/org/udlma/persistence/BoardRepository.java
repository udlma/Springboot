package org.udlma.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.udlma.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
	
	//Named Query
	public List<Board> findBoardByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable page);
	
	public Page<Board> findByTitleContains(String title, Pageable page);
	
	public Page<Board> findBoardByBnoGreaterThan(Long bno, Pageable page);
	
	//JPQL Query
	@Query("select b from Board b where b.bno > 0 order by b.bno desc")
	public Page<Board> getList(Pageable page);
	
	//Native Query
	@Query(value="select bno, title, content, writer from tbl_board where bno>0 limit 0,10", nativeQuery=true)
	public List<Object[]> getListNative();
}
