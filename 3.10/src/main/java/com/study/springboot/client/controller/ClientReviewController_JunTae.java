package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.client.dto.ReviewResponseDto;
import com.study.springboot.client.dto.ReviewSaveResponsedto;
import com.study.springboot.client.service.ClientReviewService_JunTae;
import com.study.springboot.other.ckEditor.AwsS3Service;
import com.study.springboot.other.ckEditor.FileResponse;
import com.study.springboot.other.ckEditor.MainService;
import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientReviewController_JunTae {
    private final ClientReviewService_JunTae clientReviewService;
    private final AwsS3Service awsS3Service;
    private final MainService mainService;

    @RequestMapping("/review")
    public String test(Model model, int item_idx) {
        ProductResponseDto dto = clientReviewService.findById(item_idx);
        model.addAttribute("dto", dto);
        return "client/theOthers/reviewpage";
    }

    @RequestMapping("/review/save")
    public String saveReviewAction(ReviewSaveResponsedto reviewSaveResponsedto, // 이미지 등록 수정 완료
                                 @RequestParam(value = "image", required = false) MultipartFile review_IMAGE ,
                                 HttpSession session) throws Exception {
        if(!review_IMAGE.isEmpty()) {
            String url = awsS3Service.upload(review_IMAGE);
            new ResponseEntity<>(FileResponse.builder().uploaded(true).url(url).build(), HttpStatus.OK);
            reviewSaveResponsedto.setReview_IMAGE(url);
        }
        reviewSaveResponsedto.setReview_WRITER((String) session.getAttribute("memberID"));
        clientReviewService.SaveReview(reviewSaveResponsedto);
        return "redirect:/myorder/list";
    }
}
