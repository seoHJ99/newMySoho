package com.study.springboot.client.service;


import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.entity.Notice;
import com.study.springboot.entity.NoticeListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientNoticeService {
    private final NoticeListRepository noticeListRepository;

    @Transactional(readOnly = true)
    public NoticeResponseDTO findNoticeById(int notice_IDX) {


        Notice notice = noticeListRepository.findById(notice_IDX).get();
        NoticeResponseDTO noticeResponseDTO  = new NoticeResponseDTO(notice);
        return noticeResponseDTO;
    }
    public List<NoticeResponseDTO> findNoticeList() {
        List<Notice> listAll = noticeListRepository.findAll();
        List<NoticeResponseDTO> dtoList = new ArrayList<>();
        for (Notice temp : listAll){
            NoticeResponseDTO noticeResponseDTO = new NoticeResponseDTO(temp);
            dtoList.add(noticeResponseDTO);
        }
        return dtoList;
    }
    public Optional< NoticeResponseDTO> findRecentNotice() {
        Notice notice = noticeListRepository.findRecentNotice();
            Optional< NoticeResponseDTO> noticeResponseDTO = Optional.of( new NoticeResponseDTO(notice));
            return noticeResponseDTO;
        }
    }


