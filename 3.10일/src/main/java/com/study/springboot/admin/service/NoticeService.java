package com.study.springboot.admin.service;

import com.study.springboot.admin.dto.NoticeResponseDTO;
import com.study.springboot.admin.dto.NoticeSaveDto;
import com.study.springboot.entity.Notice;
import com.study.springboot.entity.NoticeListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeListRepository noticeRepository;

    public NoticeResponseDTO findNotice(int notice_idx){
        Notice temp = noticeRepository.findById(notice_idx).get();
        NoticeResponseDTO dto = new NoticeResponseDTO(temp);
        return dto;
    }

    public void saveNotice(NoticeSaveDto dto){
        Notice entity = dto.toSaveEntity();
        noticeRepository.save(entity);
    }

    public void updateNotice(NoticeSaveDto dto){
        Notice entity = dto.toUpdateEntity();
        noticeRepository.save(entity);
    }

    public List<String> dateTimeMaker(NoticeResponseDTO noticeResponseDTO){
        List<String> dateTime = new ArrayList<>();
        int year = noticeResponseDTO.getNoticeREGDATE().getYear();
        String month =""+ noticeResponseDTO.getNoticeREGDATE().getMonth().getValue();
        String day =""+ noticeResponseDTO.getNoticeREGDATE().getDayOfMonth();
        if(Integer.valueOf(month)<10){
            month = "0"+month;
        }
        if(Integer.valueOf(day)<10){
            day = "0"+ day;
        }
       String date = year+"-"+ month+'-'+day;

        String hour = ""+noticeResponseDTO.getNoticeREGDATE().getHour();
        String minute = ""+noticeResponseDTO.getNoticeREGDATE().getMinute();
        if(Integer.valueOf(hour)<10){
            hour = "0"+hour;
        }
        if(Integer.valueOf(minute)<10){
            minute = "0" + minute;
        }
        String time = hour+ ":" +minute;

        dateTime.add(date);
        dateTime.add(time);

        return dateTime ;
    }

    public void noticeDeleteById(int id){
        noticeRepository.deleteById(id);
    }

}
