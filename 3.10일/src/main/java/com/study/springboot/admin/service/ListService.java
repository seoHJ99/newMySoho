package com.study.springboot.admin.service;



import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ListService {
    private final MemberListRepository memberRepository;
    private final ProductRepository productRepository;
    private final NoticeListRepository noticeRepository;
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;
    private final QnaRepository qnaRepository;


    @Transactional(readOnly = true)
    public Page<Member> findMemberList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("joinDate"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return memberRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Product> findProductList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("registeredDate"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Notice> findNoticeList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("noticeREGDATE"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return noticeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Qna> findQnAList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("registeredDate"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return qnaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Review> findReviewList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("reviewREGDATE"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return reviewRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Order> findOrderList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("ordersDATE"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return ordersRepository.findAll(pageable);
    }





}
