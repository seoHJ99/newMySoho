package com.study.springboot.admin.controller;




import com.study.springboot.admin.service.ListService;
import com.study.springboot.admin.dto.MemberResponseDTO;

import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.admin.dto.ReviewResponseDTO;
import com.study.springboot.admin.service.SearchService;
import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.study.springboot.admin.controller.SiteController.cateMap;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class ListController {
    private final ListService listService;
    private final SearchService searchService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/member")  // 맴버 리스트 찾기
    public String getMemberList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Member> paging = listService.findMemberList(page);
        model.addAttribute("paging",paging);
        List<MemberResponseDTO> list = new ArrayList<>();
        for(Member entity : paging){
            list.add(new MemberResponseDTO(entity));
        }
        searchService.categoryInsertAndFilter();
        model.addAttribute("memberCate", cateMap.get("memberCate"));
        model.addAttribute("type","member");
        model.addAttribute("list", list);
        return "listForm";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/product")  // 제품 리스트 찾기
    public String getProductList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Product> paging = listService.findProductList(page);
        model.addAttribute("paging",paging);
        List<ProductResponseDto> list = new ArrayList<>();
        for(Product entity : paging){
            list.add(new ProductResponseDto(entity));
        }
        searchService.categoryInsertAndFilter();
        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));
        model.addAttribute("type","product");
        model.addAttribute("list", list);
        return "listForm";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/review")  // 리뷰 리스트 찾기
    public String getReviewList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Review> paging = listService.findReviewList(page);
        model.addAttribute("paging",paging);
        List<ReviewResponseDTO> list = new ArrayList<>();
        for(Review entity : paging){
            list.add(new ReviewResponseDTO(entity));
        }
        searchService.reviewInsertAndFilter();
        model.addAttribute("review",cateMap.get("reviewCate"));
        model.addAttribute("type","review");
        model.addAttribute("list", list);
        return "listForm";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/QnA")  // 문의 리스트 찾기
    public String getQnAList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Qna> paging = listService.findQnAList(page);
        model.addAttribute("paging",paging);
        List<QnaResponseDto> list = new ArrayList<>();
        for(Qna entity : paging){
            list.add(new QnaResponseDto(entity));
        }
        searchService.qnaInsertAndFilter();
        model.addAttribute("qnaCate1",cateMap.get("qnaCate1"));
        model.addAttribute("qnaCate2",cateMap.get("qnaCate2"));
        model.addAttribute("type","qna");
        model.addAttribute("list", list);
        return "listForm";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/notice")  // 공지 리스트 찾기
    public String getNoticeList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Notice> paging = listService.findNoticeList(page);
        model.addAttribute("paging",paging);
        List<NoticeResponseDTO> list = new ArrayList<>();
        for(Notice entity : paging){
            list.add(new NoticeResponseDTO(entity));
        }
        searchService.noticeCteInsert();

        model.addAttribute("cate1",cateMap.get("noticeCate1"));
        model.addAttribute("cate2",cateMap.get("noticeCate2"));
        model.addAttribute("type","notice");
        model.addAttribute("list", list);
        return "listForm";
    }

    @Transactional(readOnly = true)
    @GetMapping("/list/order")  // 오더 리스트 찾기
    public String getOrderList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){ // 오더 리스트
        Page<Order> paging = listService.findOrderList(page);
        List<OrderResponseDto> list = new ArrayList<>();
        for(Order entity : paging){
            list.add(new OrderResponseDto(entity));
        }
        searchService.orderCateInsert();
        model.addAttribute("paging",paging);
        model.addAttribute("cate1", cateMap.get("orderCate1"));
        model.addAttribute("cate2", cateMap.get("orderCate2"));
        model.addAttribute("type","order");
        model.addAttribute("list", list);
        return "listForm";
    }



}
