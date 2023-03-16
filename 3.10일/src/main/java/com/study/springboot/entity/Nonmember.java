package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "nonmember")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nonmember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nonmember_IDX;
    private String nonmember_NAME;
    private String nonmember_PHONE;
}
