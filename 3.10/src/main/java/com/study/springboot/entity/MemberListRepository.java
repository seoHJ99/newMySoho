package com.study.springboot.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberListRepository extends JpaRepository<Member, Integer> {
    @Query(value = "SELECT * FROM member WHERE member_DROP=:category AND member_NAME LIKE CONCAT('%',:keyword,'%') OR member_DROP=:category AND member_ID LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Member> findByCategory(@Param(value = "category") String category, @Param(value = "keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM member WHERE member_NAME LIKE CONCAT('%',:keyword,'%') or member_ID LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Member> findByCategory(@Param(value = "keyword") String keyword, Pageable pageable);

    Optional<Member> findByMemberID(String id);

    @Query(value = "select * from member where member_PW = :member_PW_param", nativeQuery = true)
    List<Member> findByUserPw(@Param("member_PW_param") String member_PW);

    @Query(value = "select * from member where member_ID = :member_ID_param", nativeQuery = true)
    Optional<Member> findByUserId(@Param("member_ID_param") String memberId);

//    @Query(value = "select * from member where member_ID = :member_ID_param and member_PW = :member_PW_param", nativeQuery = true)
//    List<Member> findByUserIdAndUserPw(@Param("member_ID_param") String member_ID, @Param("member_PW_param") String member_PW);

    @Query(value = "select * from member where member_ID = :member_ID_param", nativeQuery = true)
    List<Member> findByUserLoginId(@Param("member_ID_param") String memberId);

    @Query(value = "select * from member where member_NAME =:name AND member_PHONE = :phone", nativeQuery = true)
    Member findByNameAndPhone(@Param("name") String name, @Param("phone")String phone);

    @Query(value = "select * from member where member_MAIL = :MAIL AND member_ID = :ID", nativeQuery = true)
    Member findByMailAndID(@Param("MAIL") String MAIL, @Param("ID")String ID);
}
