package com.study.springboot.admin.service;


import com.study.springboot.entity.ProductRepository;
import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.study.springboot.admin.controller.SiteController.cateMap;


@RequiredArgsConstructor
@Service
public class SearchService {
    private final ProductRepository productRepository;
    private final MemberListRepository memberRepository;
    private final QnaRepository qnaRepository;
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;
    private final NoticeListRepository noticeRepository;


    // 제품 검색
    @Transactional(readOnly = true)
    public Page<Product> findSearchProduct(String category1, String category2, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("item_NAME"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));

        Page<Product> a = null;
        if(category1.equals("전체") && category2.equals("전체")) {
            a = productRepository.searchAll(keyword, pageable);
            return a;// 둘다 전체일때
        } else if (!category1.equals("전체") && category2.equals("전체")) {
            a = productRepository.searchWithCate1( category1, keyword ,pageable);
            return a;// 1만 선택되었을때
        } else if (!category2.equals("전체") && category1.equals("전체")) {
            a = productRepository.searchWithCate2(category2, keyword, pageable);
            return a;// 2만 선택되었을때
        } else if (!category1.equals("전체") && !category2.equals("전체")) {
            a = productRepository.searchWithCate1AndCate2(category1, category2, keyword, pageable);
            return a;// 둘다 옵션 선택했을때
        }

        return a;
    }


    public void categoryInsertAndFilter(){

            List<Product> productList = productRepository.findAll();
            Set<String> set = new HashSet<>();

            for (Product products : productList) {
                set.add(products.getItem_CATEGORY1());
            }
            for (String cate1 : set) {
                if(cateMap.containsKey("cate1") && !cateMap.get("cate1").contains(cate1)) {
                    cateMap.add("cate1", cate1);
                }else if(!cateMap.containsKey("cate1")){
                    cateMap.add("cate1", cate1);
                }
            }
            set.clear();

            for (Product products : productList) {
                set.add(products.getItem_CATEGORY2());
            }

        for (String cate2 : set) {
            if(cateMap.containsKey("cate2") && !cateMap.get("cate2").contains(cate2)) {
                cateMap.add("cate2", cate2);
            }else if(!cateMap.containsKey("cate2")){
                cateMap.add("cate2", cate2);
            }
        }
            set.clear();
        }





    // 멤버 검색
    @Transactional(readOnly = true)
    public Page<Member> findSearchMember(String category, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("member_NAME"));
        sorts.add(Sort.Order.desc("member_ID"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Member> a = memberRepository.findByCategory(category,keyword,pageable);
        return a;
    }

    @Transactional(readOnly = true)
    public Page<Member> findSearchMember(String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("member_NAME"));
        sorts.add(Sort.Order.desc("member_ID"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Member> a = memberRepository.findByCategory(keyword,pageable);
        return a;
    }
    public void memberInsertAndFilter(){
        if(!cateMap.containsKey("memberCate")) {
            cateMap.add("memberCate", "활동");
            cateMap.add("memberCate", "정지");
            cateMap.add("memberCate", "탈퇴");
        }
    }

    // Q&A
    @Transactional(readOnly = true)
    public Page<Qna> findSearchQna(String category1, String category2, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("qna_CONTENT"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));

        Page<Qna> a = null;
        if(category1.equals("전체") && category2.equals("전체")) {
            a = qnaRepository.searchAll(keyword, pageable);
            return a;// 둘다 전체일때
        } else if (!category1.equals("전체") && category2.equals("전체")) {
            if(category1.equals("답변")){
               a = qnaRepository.searchWithCate1(keyword,pageable);
               return a;
            } else if (category1.equals("미답변")) {
                a = qnaRepository.searchWithCate(keyword, pageable);
                return a;
            }
            a = qnaRepository.searchWithCate1(  keyword ,pageable);
            return a;// 1만 선택되었을때
        } else if (!category2.equals("전체") && category1.equals("전체")) {
            System.out.println(category2);
            a = qnaRepository.searchWithCate2(category2,  pageable);
            if(category2.equals("배송문의")){
                a = qnaRepository.searchWithCate2(keyword,pageable);
            } else if (category2.equals("교환문의")) {
                a = qnaRepository.searchWithCate3(keyword,pageable);
            }
            else if (category2.equals("환불문의")) {
                a = qnaRepository.searchWithCate4(keyword,pageable);
            }
            else if (category2.equals("상품문의")) {
                a = qnaRepository.searchWithCate5(keyword,pageable);
            }
            else if (category2.equals("기타문의")) {
                a = qnaRepository.searchWithCate6(keyword,pageable);
            }

            return a;// 2만 선택되었을때
        } else if (!category1.equals("전체") && !category2.equals("전체")) {
            a = qnaRepository.searchWithCate1AndCate2(category2, keyword, pageable);
            return a;// 둘다 옵션 선택했을때
        }
        return a;
    }

    public void qnaInsertAndFilter(){
        if(!cateMap.containsKey("qnaCate1") && !cateMap.containsKey("qnaCate2")) {
            cateMap.add("qnaCate1", "답변");
            cateMap.add("qnaCate1", "미답변");
            cateMap.add("qnaCate2", "배송문의");
            cateMap.add("qnaCate2", "교환문의");
            cateMap.add("qnaCate2", "환불문의");
            cateMap.add("qnaCate2", "상품문의");
            cateMap.add("qnaCate2", "기타문의");
        }
    }

    //review 검색
    @Transactional(readOnly = true)
    public Page<Review> findSearchReviewWriter(String cate1, String keyword, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("review_WRITER"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Review> a = null;
        if(cate1.equals("전체")){
            a = reviewRepository.findByAll(keyword,pageable);
        }
        else if(cate1.equals("작성자")) {
            a = reviewRepository.findByWriter(keyword, pageable);
        }else if(cate1.equals("제목")) {
            a = reviewRepository.findByTitle(keyword, pageable);
        }else if(cate1.equals("번호")) {
            a = reviewRepository.findByIdx(keyword, pageable);
        }
        return a;
    }



    public void reviewInsertAndFilter() {
        if (!cateMap.containsKey("reviewCate")) {
            cateMap.add("reviewCate", "작성자");
            cateMap.add("reviewCate", "제목");
            cateMap.add("reviewCate", "번호");
        }
    }

    @Transactional(readOnly = true)
    public Page<Order> OrderSearcher(String cate1, String cate2, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("orders_IDX"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Order> a = null;
        if(cate1.equals("전체") && cate2.equals("전체")){
            a = ordersRepository.searchByKeyword(keyword,pageable);
        }else if (cate1.equals("회원") && cate2.equals("전체")){
            a = ordersRepository.searchOnlyMember(keyword,pageable);
        }else if (cate1.equals("비회원") && cate2.equals("전체")){
            a = ordersRepository.searchOnlyNonMember(keyword,pageable);
        }else if (cate1.equals("전체") && !cate2.equals("전체")){
            a = ordersRepository.searchOnlyStatus(cate2, keyword,pageable);
        }else if (cate1.equals("회원") && !cate2.equals("전체")){
            a = ordersRepository.searchMemberAndStatus(cate2, keyword,pageable);
        }else if (cate1.equals("비회원") && !cate2.equals("전체")){
            a = ordersRepository.searchNonMemberAndStatus(cate2, keyword,pageable);
        }
        return a;
    }

    public void orderCateInsert(){
        if(!cateMap.containsKey("orderCate1") && !cateMap.containsKey("orderCate2")) {
            cateMap.add("orderCate1", "회원");
            cateMap.add("orderCate1", "비회원");
            cateMap.add("orderCate2", "입금대기");
            cateMap.add("orderCate2", "배송준비");
            cateMap.add("orderCate2", "배송중");
            cateMap.add("orderCate2", "배송완료");
        }
    }

    @Transactional(readOnly = true)
    public Page<Notice> noticeSearcher(String cate1, String cate2, String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("notice_IDX"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        Page<Notice> a = null;
        if(cate1.equals("전체") && cate2.equals("전체")){
            a = noticeRepository.searchAll(keyword,pageable);
        }else if (!cate1.equals("전체") && cate2.equals("전체")){
            a = noticeRepository.searchWithCate(cate1,keyword, pageable);
        }else if (cate1.equals("전체") && !cate2.equals("전체")){
            a = noticeRepository.searchWithReserve(cate2, keyword, pageable);
        }else if(!cate1.equals("전체") && !cate2.equals("전체")){
            a = noticeRepository.searchWithReserveAndCate(cate1, cate2, keyword, pageable);
        }
        return a;
    }


    public void noticeCteInsert(){
        if(!cateMap.containsKey("noticeCate1") && !cateMap.containsKey("noticeCate2")){
            cateMap.add("noticeCate1", "일반");
            cateMap.add("noticeCate1", "이벤트");
            cateMap.add("noticeCate2", "일반");
            cateMap.add("noticeCate2", "예약");
        }
    }
}
