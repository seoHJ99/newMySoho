package com.study.springboot.admin.controller;


import com.study.springboot.admin.dto.ReviewResponseDTO;
import com.study.springboot.admin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class ReviewController {
    private final ReviewService reviewService;

    @RequestMapping("/review")
    public String reviewView( int idx, Model model){
        ReviewResponseDTO dto = reviewService.findReview(idx);
        System.out.println(dto.getReview_TITLE());
        model.addAttribute("review", dto);
        return "reviewForm";
    }

    @RequestMapping("/review/reply")
    public String modifyReview(Model model, @RequestParam("idx") int idx,
                                 @RequestParam("reply")String reply,
                                 @RequestParam("status")String status){
        reviewService.replyModify(idx, reply, status);
        model.addAttribute("review", reviewService.findReview(idx));
        System.out.println(reviewService.findReview(idx).getReview_REPLY());
        return "reviewForm";
    }

    @RequestMapping("/review/delete")
    public String reviewDelete(int id){
        reviewService.reviewDelete(id);
        return "/admin/list/review";
    }

    @RequestMapping("/reviews/delete")
    @ResponseBody
    public String reviewListDelete(@RequestParam("reviewNo") String reviewNo){
        System.out.println(reviewNo);
        String[] arrIdx = reviewNo.split(",");
        for (int i=0; i<arrIdx.length; i++) {
            System.out.println(Integer.valueOf(arrIdx[i]));
            reviewService.reviewDelete(Integer.valueOf(arrIdx[i]));
        }
        return "1";
    }
}