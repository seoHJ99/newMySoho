package com.study.springboot.admin.controller;


import com.study.springboot.other.ckEditor.AwsS3Service;
import com.study.springboot.other.ckEditor.FileResponse;
import com.study.springboot.other.ckEditor.MainService;
import com.study.springboot.entity.Product;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.entity.ProductRepository;
import com.study.springboot.admin.dto.ReviewResponseDTO;
import com.study.springboot.admin.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.study.springboot.admin.controller.SiteController.cateMap;


@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class ProductController {

    private final ProductService productService;
    private final ProductService service;
    private final AwsS3Service awsS3Service;
    private final MainService mainService;
    private final SearchService searchService;


    @RequestMapping("/product")
    public String product(int item_idx, Model model) {
        ProductResponseDto dto = productService.findById(item_idx);

        searchService.categoryInsertAndFilter();

        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));

        int scoreSum = 0;
        List<ReviewResponseDTO> reviewList = productService.findReviewScore(item_idx);
        for(int i=0; reviewList.size()>i; i++){
            int score = reviewList.get(i).getReview_SCORE();
            scoreSum = scoreSum + score;
        }

        if(reviewList.size()==0) {
            model.addAttribute("scoreAvg",0);
        }else {
            int scoreAvg = scoreSum / reviewList.size();
            model.addAttribute("scoreAvg", scoreAvg);
        }

        model.addAttribute("dto", dto);
        return "product";
    }
    @RequestMapping("/productAction")
    @ResponseBody
    public String productAction( @RequestParam("item_price") int item_price,
                                 @RequestParam("item_idx") int item_idx){
        ProductResponseDto dto = productService.findById(item_idx);
        dto.setItem_PRICE(item_price);
        Product product = new Product();
        product.toSaveEntity(dto);
        productService.save(product);
        return "";
    }


    @RequestMapping("/product/delete")
    @ResponseBody
    public String productDelete(@RequestParam("id") int id){

        try {
            productService.delete(id);
        } catch (Exception e) {
            return "<script>alert('삭제 실패');location.href='/admin/list/product/';</script>";
        }
        return "<script>alert('삭제 성공');location.href='/admin/list/product/';</script>";

    }

    @RequestMapping("/products/delete")
    @ResponseBody
    public String productListDelete(@RequestParam("reviewNo") String reviewNo){
        System.out.println(reviewNo);
        String[] arrIdx = reviewNo.split(",");
        for (int i=0; i<arrIdx.length; i++) {
            System.out.println(Integer.valueOf(arrIdx[i]));
            productDelete(Integer.valueOf(arrIdx[i]));
        }
        return "1";
    }

    @RequestMapping("/product/insert")
    public String productInsert(Model model){
        searchService.categoryInsertAndFilter();
        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));
        return "product-insert";
    }

    @RequestMapping("/product/saveAction")
    public String productInsert(ProductResponseDto responseDto,
                                @RequestParam("IMAGE") MultipartFile item_IMAGE) throws Exception{
        String url = awsS3Service.upload(item_IMAGE);

        new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(url).
                build(), HttpStatus.OK);

        responseDto.setItem_IMAGE(url);
        Product product = new Product();
        product.toSaveEntity(responseDto);
        service.save(product);
        int item_idx = product.getItem_idx();
        return "redirect:/admin/list/product";
    }


    @RequestMapping("/product/modify")
    public String productModify(@RequestParam("item_CATEGORY1") String cate1,
                                @RequestParam("item_CATEGORY2") String cate2,
                                @RequestParam("item_NAME") String name,
                                @RequestParam("item_DETAIL") String detail,
                                @RequestParam("item_DISCOUNT") int discount,
                                @RequestParam("item_ORIGINAL") int original,
                                @RequestParam("item_idx") int idx,
                                @RequestParam("item_PRICE") int price) throws Exception{

        ProductResponseDto productResponseDto = service.findById(idx);
        productResponseDto.setItem_CATEGORY1(cate1);
        productResponseDto.setItem_CATEGORY2(cate2);
        productResponseDto.setItem_DETAIL(detail);
        productResponseDto.setItem_NAME(name);
        productResponseDto.setItem_PRICE(price);
        productResponseDto.setItem_ORIGINAL(original);
        productResponseDto.setItem_DISCOUNT(discount);

        Product product = new Product();
        product.toUpdateEntity(productResponseDto);
        service.save(product);


        return "redirect:/admin/list/product"; // responseBody 없이도 되는지 시험
    }

    @RequestMapping("/product/changeImage")
    public String changeImage( @RequestParam("IMAGE") MultipartFile item_IMAGE, @RequestParam("idx") int idx) throws Exception{
        String url = awsS3Service.upload(item_IMAGE);
        new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(url).
                build(), HttpStatus.OK);
        ProductResponseDto responseDtoSmall = service.findById(idx);
        responseDtoSmall.setItem_IMAGE(url);
        Product product = new Product();
        product.toUpdateEntity(responseDtoSmall);
        service.save(product);
        return "redirect:/admin/product" +"?item_idx="+idx;
    }

    @PostMapping("/imgUpload")
    @ResponseBody
    public ResponseEntity<FileResponse> imgUpload(
            @RequestPart(value = "upload", required = false) MultipartFile fileload) throws Exception {
        return new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(mainService.upload(fileload)).
                build(), HttpStatus.OK);
    }
}
