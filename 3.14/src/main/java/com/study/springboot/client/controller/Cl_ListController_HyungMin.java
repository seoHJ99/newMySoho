package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.ProductResponseDto;
import com.study.springboot.client.service.Cl_ListService_HyungMin;
import com.study.springboot.client.service.Cl_SearchService_HyungMin;
import com.study.springboot.client.service.ClientNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class Cl_ListController_HyungMin {

    private final ClientNoticeService noticeService;
    private final Cl_ListService_HyungMin listService;
    private final Cl_SearchService_HyungMin searchService;

    // home.html
    @GetMapping("/main")
    public String home(Model model) {
        List<ProductResponseDto> list = listService.findProductList();
        List<ProductResponseDto> list1 = new ArrayList<>();
        List<ProductResponseDto> list2 = new ArrayList<>();
        int cnt = 0;
        for (ProductResponseDto temp : list) {
            if (cnt < 6) {
                list1.add(temp);
                cnt++;
            }
        }
        for (ProductResponseDto temp : list) {
            list2.add(temp);
        }
        model.addAttribute("list1", list1);
        model.addAttribute("list2", list2);
        
        // 정렬 기준
        model.addAttribute("date", listService.pdOrderByDate());
        model.addAttribute("selling", listService.pdOrderBySelling());
        model.addAttribute("price", listService.pdOrderByPrice());
        model.addAttribute("review", listService.pdOrderByReview());
        model.addAttribute("score", listService.pdOrderByScore());
        NoticeResponseDTO dto = noticeService.findRecentNotice();
        model.addAttribute("dto",dto);
        return "client/theOthers/home";
    }


    @GetMapping("/list/category")
    public String findProduct(@RequestParam("category") String item_CATEGORY, Model model) {

        List<ProductResponseDto> list = listService.findProductListByCate(item_CATEGORY);

        model.addAttribute("type",item_CATEGORY);
        model.addAttribute("item",list);

        return "client/categoryPage/ACC";
    }

//    작성자 서호준
//    헤더 검색 기능 구현.
    @GetMapping("/search/product")
    public String search(@RequestParam("search") String keyword, Model model) {
        List<ProductResponseDto> list = searchService.findProductList(keyword); // 검색기능만 찾아옴
        model.addAttribute("list", list);
        model.addAttribute("date", listService.pdOrderByDate(keyword));
        model.addAttribute("selling", listService.pdOrderBySelling(keyword));
        model.addAttribute("price", listService.pdOrderByPrice(keyword));
        model.addAttribute("review", listService.pdOrderByReview(keyword));
        model.addAttribute("score", listService.pdOrderByScore(keyword));
        model.addAttribute("keyword",keyword);
        model.addAttribute("count", list.size());
        return "/client/theOthers/searchpage";
    }
}
