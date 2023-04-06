package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.client.service.ClientNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ClientNoticeController_JunTae {
    private final ClientNoticeService clientNoticeService;

    @RequestMapping("/notice")
    public String ClientNotice(int idx, Model model) {
        NoticeResponseDTO notice = clientNoticeService.findNoticeById(idx);
        String time = notice.getNoticeREGDATE().toString().replaceAll("T", " ");
        model.addAttribute("time", time);
        model.addAttribute("notice", notice);
        return "/client/theOthers/notice";
    }

    @RequestMapping("/notice/list")
    public String ClientNoticeList(Model model) {
        List<NoticeResponseDTO> listdto = clientNoticeService.findNoticeList();
        for (Iterator<NoticeResponseDTO> dtos = listdto.iterator(); dtos.hasNext(); ) {
            NoticeResponseDTO dto = dtos.next();
            if (dto.getNoticeREGDATE().isAfter(LocalDateTime.now())) { // 예약 날짜가 현재 날짜보다 뒤라면 제외
                dtos.remove();
            }
        }
        model.addAttribute("list", listdto);
        return "/client/theOthers/noticelist";
    }
}
