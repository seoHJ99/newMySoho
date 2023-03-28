package com.study.springboot.admin.controller;

import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.admin.dto.NoticeSaveDto;
import com.study.springboot.admin.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoticeController {

    private final NoticeService noticeService;

    @RequestMapping("/notice/insertForm")
    public String noticeInsertForm(Model model){
        return "notCreate";
    }


    @RequestMapping("/notice/insert") // 생성
    public String noticeInsertAction(NoticeSaveDto noticeSaveDto,
                                     @RequestParam(value = "date", required = false) String date,
                                     @RequestParam(value = "time", required = false) String time){
        if(noticeSaveDto.getNotice_REGTYPE().equals("일반")){
            noticeSaveDto.setNoticeREGDATE(LocalDateTime.now());
            noticeService.saveNotice(noticeSaveDto);
        }else {
            String temp = date+"-"+time;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
            LocalDateTime setDate = LocalDateTime.parse(temp,timeFormatter);
            noticeSaveDto.setNoticeREGDATE(setDate);
            noticeService.saveNotice(noticeSaveDto);
        }
        return "redirect:/admin/list/notice";
    }

    @RequestMapping("/notice") // 조회
    public String noticeDetail(int idx, Model model){

        NoticeResponseDTO noticeResponseDTO = noticeService.findNotice(idx);
        List<String> dateTime = noticeService.dateTimeMaker(noticeResponseDTO);

        model.addAttribute("notice", noticeResponseDTO);
        model.addAttribute("date",dateTime.get(0));
        model.addAttribute("time",dateTime.get(1));

        return "noticeModify";
    }


    @RequestMapping("/notice/modify") // 수정
    @ResponseBody
    public String modifyNotice(NoticeSaveDto noticeSaveDto,
                               @RequestParam("date") String date,
                               @RequestParam("time") String time){
        if(noticeSaveDto.getNotice_REGTYPE().equals("일반")){
            noticeSaveDto.setNoticeREGDATE(LocalDateTime.now());
            noticeService.updateNotice(noticeSaveDto);
        }else {
            String temp = date+"-"+time;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
            LocalDateTime setDate = LocalDateTime.parse(temp,timeFormatter);
            noticeSaveDto.setNoticeREGDATE(setDate);
            noticeService.updateNotice(noticeSaveDto);
        }
        return "<script>alert('수정완료');location.href='/admin/list/notice';</script>";
    }

    @RequestMapping("/notice/delete")
    @ResponseBody
    public String deleteNotice(@RequestParam("notice_idx") int id){
        noticeService.noticeDeleteById(id);
        return "1";
    }

    @RequestMapping("/notices/delete")
    @ResponseBody
    public String productListDelete(@RequestParam("reviewNo") String reviewNo){
        System.out.println(reviewNo);
        String[] arrIdx = reviewNo.split(",");
        for (int i=0; i<arrIdx.length; i++) {
            System.out.println(Integer.valueOf(arrIdx[i]));
            deleteNotice((Integer.valueOf(arrIdx[i])));
        }
        return "1";
    }
}
