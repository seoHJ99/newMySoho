package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.client.dto.ReviewResponseDto;
import com.study.springboot.client.service.Cl_ProductService_HyungMin;
import com.study.springboot.client.service.ClientReviewService_JunTae;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class Cl_ProductController_HyungMin {
    private final Cl_ProductService_HyungMin clProductService;
    private final ClientReviewService_JunTae clientReviewService;

    @RequestMapping("/product")
    public String product(@RequestParam("idx") int item_IDX, Model model) {
        ProductResponseDto dto = clProductService.findById(item_IDX);
        List<ProductResponseDto> all = clProductService.findByAllConnection(item_IDX);
        List<ReviewResponseDto> dto1 = clientReviewService.findByItemId(item_IDX);
        if(!all.isEmpty()){
            model.addAttribute("options", all);
        }
        float avg=0;
        if(dto1.size()>0) {
            for (ReviewResponseDto reviewResponseDto : dto1) {
                avg += reviewResponseDto.getReview_SCORE();
            }
            avg = Math.round ((((float) avg *10 / (dto1.size())) / 10) *10);
        }
        avg = avg/10;
        System.out.println(avg);

        model.addAttribute("product", dto);
        model.addAttribute("review",dto1);
        model.addAttribute("reviewAVG",avg);
        model.addAttribute("reviewCount", dto1.size());
        return "/client/product/productDetailPage";
    }

    @RequestMapping("/product/detail")
    public String productDetail(int item_IDX, Model model){
        ProductResponseDto dto = clProductService.findById(item_IDX);
        model.addAttribute("item", dto.getItem_idx());
        model.addAttribute("item_DETAIL", dto.getItem_DETAIL());
        return "client/product/productDetail-NewPage";
    }
}
