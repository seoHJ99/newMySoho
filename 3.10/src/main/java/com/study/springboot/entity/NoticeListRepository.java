package com.study.springboot.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeListRepository extends JpaRepository<Notice, Integer> {

    @Query(value = "SELECT * FROM notice WHERE notice_IDX LIKE CONCAT('%',:keyword,'%') OR " +    // 전체 전체
            "notice_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Notice> searchAll(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM notice WHERE notice_CATE = :cate AND notice_IDX LIKE CONCAT('%',:keyword,'%') OR " +    // 선택 전체
            "notice_CATE = :cate AND notice_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Notice> searchWithCate(@Param(value = "cate") String cate, @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM notice WHERE notice_REGTYPE = :cate AND notice_IDX LIKE CONCAT('%',:keyword,'%') OR " +   // 전체 선택
            "notice_REGTYPE = :cate AND notice_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Notice> searchWithReserve(@Param(value = "cate") String cate, @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM notice WHERE notice_CATE = :cate1 AND notice_REGTYPE = :cate2 AND notice_IDX LIKE CONCAT('%',:keyword,'%') OR " +   // 전체 선택
            "notice_CATE = :cate1 AND notice_REGTYPE = :cate2 AND notice_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Notice> searchWithReserveAndCate(@Param(value = "cate1") String cate1, @Param(value = "cate2") String cate2, @Param(value = "keyword") String keyword, Pageable pageable);
}
