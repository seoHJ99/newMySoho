package com.study.springboot.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository <Review, Integer> {

    @Query(value = "SELECT COUNT(*) FROM review WHERE item_IDX = :item_IDX", nativeQuery = true)
    int itemReviewCount(@Param(value = "item_IDX") int item_IDX);

    List<Review> findByItemIDX ( int item_idx);

    @Query(value = "SELECT * FROM review WHERE review_TITLE LIKE CONCAT('%',:keyword,'%') OR " +
            "review_WRITER LIKE CONCAT('%',:keyword,'%') OR " +
            "item_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Review> findByAll( @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE review_TITLE LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Review> findByTitle( @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE review_WRITER LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Review> findByWriter( @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE item_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Review> findByIdx( @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE review_WRITER = :memberID", nativeQuery = true)
    List<Review> findOnlyMembersReview( @Param(value = "memberID") String memberId);
}
