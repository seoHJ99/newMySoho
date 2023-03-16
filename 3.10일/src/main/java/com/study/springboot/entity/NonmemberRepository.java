package com.study.springboot.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NonmemberRepository extends JpaRepository<Nonmember, Integer> {
    @Query(value = "SELECT * FROM nonmember WHERE nonmember_NAME = :name AND nonmember_PHONE = :phone" , nativeQuery = true)
    Nonmember findNonmemberBynameAndPhone(@Param("name") String name, @Param("phone") String phone);
}
