package com.study.springboot.admin.controller;

import com.study.springboot.admin.dto.*;
import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.entity.ProductRepository;
import com.study.springboot.admin.service.SearchService;
import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.study.springboot.admin.controller.SiteController.cateMap;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final ProductRepository productRepository;

    // 상품 검색
    @RequestMapping("/list/search/product")
    public String search(@RequestParam("option1") String category1, @RequestParam("option2") String category2, @RequestParam("search") String keyWord, Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page){
        List<ProductResponseDto> list = new ArrayList<>();
        Page<Product> paging = searchService.findSearchProduct(category1, category2, keyWord, page);
        model.addAttribute("paging",paging);
        for(Product entity : paging){
            list.add(new ProductResponseDto(entity));
        }
        model.addAttribute("type","product");
        model.addAttribute("list",list);
        searchService.categoryInsertAndFilter();
        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));

        return "listForm";
    }

// 맴버 검색
    @RequestMapping("/list/search/member")
    public String memberSearch(@RequestParam("option") String category, @RequestParam("search") String keyWord, Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page){

        List<MemberResponseDTO> list = new ArrayList<>();

        if( !category.equals("전체")){
            Page<Member> paging = searchService.findSearchMember(category, keyWord, page);
            model.addAttribute("paging",paging);
            for(Member entity : paging){
                list.add(new MemberResponseDTO(entity));
            }
        }else{
            Page<Member> paging = searchService.findSearchMember(keyWord, page);
            model.addAttribute("paging",paging);
            for(Member entity : paging){
                list.add(new MemberResponseDTO(entity));
            }
        }
        model.addAttribute("type","member");
        model.addAttribute("list",list);

        searchService.memberInsertAndFilter();

        model.addAttribute("memberCate",cateMap.get("memberCate"));

        return "listForm";
    }

    // Q&A 검색
    @RequestMapping("/list/search/qna")
    public String qnaSearch(@RequestParam("option1") String category1, @RequestParam("option2") String category2, @RequestParam("search") String keyWord, Model model,
                            @RequestParam(value = "page", defaultValue = "0") int page){

        List<QnaResponseDto> list = new ArrayList<>();
        Page<Qna> paging = searchService.findSearchQna(category1, category2, keyWord, page);
        model.addAttribute("paging",paging);
        for(Qna entity : paging){
            list.add(new QnaResponseDto(entity));
        }
        model.addAttribute("type","qna");
        model.addAttribute("list",list);
        searchService.qnaInsertAndFilter();
        model.addAttribute("qnaCate1",cateMap.get("qnaCate1"));
        model.addAttribute("qnaCate2",cateMap.get("qnaCate2"));

        return "listForm";
    }

    // 리뷰 검색
    @RequestMapping("/list/search/review")
    public String reviewSearch(@RequestParam("option") String category, @RequestParam("search") String keyWord, Model model,
                            @RequestParam(value = "page", defaultValue = "0") int page){
        List<ReviewResponseDTO> list = new ArrayList<>();
            Page<Review> paging = searchService.findSearchReviewWriter(category, keyWord, page);
            for(Review entity : paging){
                list.add(new ReviewResponseDTO(entity));
        }

        model.addAttribute("paging", paging);
        model.addAttribute("type","review");
        model.addAttribute("list",list);
        searchService.reviewInsertAndFilter();
        model.addAttribute("review",cateMap.get("reviewCate"));

        return "listForm";
    }

    @RequestMapping("/list/search/order")
    public String orderSearch(@RequestParam("option1") String category1,
                              @RequestParam("option2") String category2,
                              @RequestParam("search") String keyWord, Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page){
        List<OrderResponseDto> list = new ArrayList<>();
        Page<Order> paging = searchService.OrderSearcher(category1, category2, keyWord, page);
        for(Order entity : paging){
            list.add(new OrderResponseDto(entity));
        }
        searchService.orderCateInsert();
        model.addAttribute("cate1", cateMap.get("orderCate1"));
        model.addAttribute("cate2", cateMap.get("orderCate2"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","order");
        model.addAttribute("list",list);
        return "listForm";
    }


    @RequestMapping("/list/search/notice")
    public String noticeSearch(@RequestParam("option1") String category1,
                              @RequestParam("option2") String category2,
                              @RequestParam("search") String keyWord, Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page){
        List<NoticeResponseDTO> list = new ArrayList<>();
        Page<Notice> paging = searchService.noticeSearcher(category1, category2, keyWord, page);
        for(Notice entity : paging){
            list.add(new NoticeResponseDTO(entity));
        }
        searchService.orderCateInsert();
        model.addAttribute("cate1", cateMap.get("noticeCate1"));
        model.addAttribute("cate2", cateMap.get("noticeCate2"));
        model.addAttribute("paging",paging);
        model.addAttribute("type","notice");
        model.addAttribute("list",list);
        return "listForm";
    }
}
