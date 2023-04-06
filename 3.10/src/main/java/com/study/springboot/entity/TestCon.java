package com.study.springboot.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class TestCon {
    private final TestRepo testRepo;

    @RequestMapping("/test")
    public String test11(){
        com.study.springboot.entity.Test test = new Test();
        int a[] = {1,2,3,4,5};
        String str = Arrays.stream(a)
                .mapToObj(String::valueOf)
                .reduce((x, y) -> x + ", " + y)
                .get();
        test.setTest2(str);
        testRepo.save(test);
        return "sss";
    }
    @RequestMapping("/test2")
    public String test22(){
        com.study.springboot.entity.Test test = new Test();
        test = testRepo.findById(1).get();
        String str = test.getTest2();
        String onlyInt = str.replaceAll(" ","");
        System.out.println(onlyInt);
        String[]b = onlyInt.split(",");
        return "sss";
    }
}
