package com.study.springboot.admin.controller;


import com.study.springboot.entity.ProductRepository;
import com.study.springboot.admin.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SiteController {

    public static MultiValueMap<String, String> cateMap = new LinkedMultiValueMap<>();


    private final ProductRepository productRepository;
    private final SearchService searchService;
    @RequestMapping("/category-manager")
    public String categoryManger(Model model){
        searchService.categoryInsertAndFilter();
        model.addAttribute("cate1",cateMap.get("cate1"));
        model.addAttribute("cate2",cateMap.get("cate2"));
        return "category";
    }

    @RequestMapping("/category/insert")
    @ResponseBody
    public String categoryInsert(Model model, @RequestParam("cate1") String cate1,
                                 @RequestParam("cate2") String cate2) {
        if (!cateMap.containsKey("cate1") && !cateMap.containsKey("cate2")) {
            cateMap.add("cate1", cate1);
            cateMap.add("cate2", cate2);
        } else {
            if (!cate1.equals("")) {
                if (!cateMap.get("cate1").contains(cate1)) {
                    cateMap.add("cate1", cate1);
                }
            }
            if (!cate2.equals("")) {
                if (!cateMap.get("cate2").contains(cate2)) {
                    cateMap.add("cate2", cate2);
                }
            }
        }
        return "<script>alert('상품 카테고리가 추가되었습니다. 추가된 카테고리에 상품을 등록하지 않으면 해당 카테고리는 사라집니다.'); location.href='/admin/category-manager'</script>";
    }

}
