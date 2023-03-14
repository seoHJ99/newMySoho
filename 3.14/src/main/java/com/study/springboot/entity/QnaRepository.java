package com.study.springboot.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository <Qna,Integer> {
    @Query(value = "SELECT * FROM qna WHERE qna_TITLE LIKE CONCAT('%',:keyword,'%') OR qna_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Qna> searchAll(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM qna WHERE qna_CONTENT AND qna_SECRET =:1 LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    Page<Qna> searchQnaContent(@Param(value = "keyword") String keyword, Pageable pageable);


    // 옵션 둘 다 선택했을때
    @Query(value = "SELECT * FROM qna WHERE qna_CATE =:category1 AND qna_SORT =:category2 AND qna_IDX LIKE CONCAT('%',:keyword,'%') OR qna_CATE =:category1 AND qna_SORT =:category2 AND qna_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Qna> searchWithCate1AndCate2 (@Param(value = "category1") String category1,
                                       @Param(value = "category2") String category2,
                                       @Param(value = "keyword") String keyword,
                                       Pageable pageable);

    //옵션 1번만 선택했을때
    @Query(value = "SELECT * FROM item WHERE qna_CATE =:category1 AND qna_IDX LIKE CONCAT('%',:keyword,'%') OR qna_CATE =:category1 AND qna_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Qna> searchWithCate1 (@Param(value = "category1") String category1,
                               @Param(value = "keyword") String keyword,
                               Pageable pageable);

    //옵션 2번만 선택했을때
    @Query(value = "SELECT * FROM item WHERE qna_SORT =:category2 AND qna_IDX LIKE CONCAT('%',:keyword,'%') OR qna_SORT =:category2 AND qna_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Qna> searchWithCate2 (@Param(value = "category2") String category2,
                               @Param(value = "keyword") String keyword,
                               Pageable pageable);

    @Query(value = "select * from qna where qna_IDX = :qna_IDX_param", nativeQuery = true)
    List<Qna> findByQnaIdx(@Param("qna_IDX_param") int qna_IDX);

}
