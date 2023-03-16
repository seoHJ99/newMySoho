package com.study.springboot.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<OrderDetail> findByOrdersIDX(int orders_idx);

    @Query(value = "SELECT * FROM orders WHERE member_IDX = :idx ORDER BY orders_DATE DESC, orders_IDX DESC", nativeQuery = true)
    List<Order> findOrdersByMemberIDX( @Param("idx") Integer member_IDX);

    @Query(value = "SELECT * FROM orders WHERE orders_NAME LIKE CONCAT('%',:keyword,'%') OR" +
            " orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 1. 전체 전체. 키워드만 받을때
    Page<Order> searchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE  orders_NAME LIKE CONCAT('%',:keyword,'%') AND member_IDX IS NOT NULL OR" +
            " member_IDX IS NOT NULL AND orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " member_IDX IS NOT NULL AND member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 2. 선택 전체. 카테1(회원) + 키워드만 받을때
    Page<Order> searchOnlyMember(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE nonmember_IDX IS NOT NULL AND orders_NAME LIKE CONCAT('%',:keyword,'%') OR" +
            " nonmember_IDX IS NOT NULL AND orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " nonmember_IDX IS NOT NULL AND member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 2. 선택 전체. 카테1(비회원) + 키워드만 받을때
    Page<Order> searchOnlyNonMember(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE orders_STATUS = :cate2 AND orders_NAME LIKE CONCAT('%',:keyword,'%') OR" +
            " orders_STATUS = :cate2 AND member_IDX IS NOT NULL AND orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " orders_STATUS = :cate2 AND member_IDX IS NOT NULL AND member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 3. 전체 선택. 카테2(배송 상태) + 키워드만 받을때
    Page<Order> searchOnlyStatus(@Param("cate2") String cate2, @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE member_IDX IS NOT NULL AND orders_STATUS = :cate2 AND orders_NAME LIKE CONCAT('%',:keyword,'%') OR" +
            " member_IDX IS NOT NULL AND orders_STATUS = :cate2 AND orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " member_IDX IS NOT NULL AND orders_STATUS = :cate2 AND member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 4. 선택 선택. 카테1(회원) + 카테2 + 키워드만 받을때
    Page<Order> searchMemberAndStatus(@Param("cate2") String cate2, @Param(value = "keyword") String keyword, Pageable pageable);


    @Query(value = "SELECT * FROM orders WHERE nonmember_IDX IS NOT NULL AND orders_STATUS = :cate2 AND orders_NAME LIKE CONCAT('%',:keyword,'%') OR" +
            " nonmember_IDX IS NOT NULL AND orders_STATUS = :cate2 AND orders_IDX LIKE CONCAT('%',:keyword,'%') OR" +
            " nonmember_IDX IS NOT NULL AND orders_STATUS = :cate2 AND member_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)   // 4. 선택 선택. 카테1(회원) + 카테2 + 키워드만 받을때
    Page<Order> searchNonMemberAndStatus(@Param("cate2") String cate2, @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE nonmember_IDX = :nonMemIDX ORDER BY orders_DATE DESC, orders_IDX DESC", nativeQuery = true)
    List<Order> findOrdersByNonMemberIDX(@Param("nonMemIDX") int nonMemIDX);
}
