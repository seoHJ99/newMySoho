package com.study.springboot.admin.controller;


import com.study.springboot.client.service.ClientQnaService_JunSeok;
import com.study.springboot.entity.Qna;
import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.admin.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/qna")
    public String index ( int idx, Model model){
        QnaResponseDto qnaResponseDto = qnaService.findById(idx);

        if(qnaResponseDto.getQna_SECRET() == 1){
            model.addAttribute("secret", "비밀");
        }
        else{
            model.addAttribute("secret","공개");
        }
        if(qnaResponseDto.getQna_ANSWERED().equals("답변")){
            model.addAttribute("answer", "답변");
        }else{
            model.addAttribute("answer", "미답변");
        }
        model.addAttribute("dto", qnaResponseDto);
        return "qna"; //qna.html로 리턴
    }

    @RequestMapping("/qna/saveaction")
    @ResponseBody
    public String qnaSave(@RequestParam("qna_IDX") int qna_IDX, @RequestParam("qna_REACT") String qna_REACT){
        QnaResponseDto qnaResponseDto = qnaService.findById(qna_IDX);
        qnaResponseDto.setQna_REACT(qna_REACT);
        qnaResponseDto.setQna_ANSWERED("답변");
        Qna qna = new Qna();
        qna.toSaveEntity(qnaResponseDto);
        qnaService.save(qna);
        return "<script>alert('답변이 성공적으로 등록되었습니다.'); location.href = '/admin/list/QnA';</script>";
    }

    @RequestMapping("/qnas/delete")
    @ResponseBody
    public String qnaListDelete(@RequestParam("reviewNo") String reviewNo){
        System.out.println(reviewNo);
        String[] arrIdx = reviewNo.split(",");
        for (int i=0; i<arrIdx.length; i++) {
            System.out.println(Integer.valueOf(arrIdx[i]));
            qnaService.delete(Integer.valueOf(arrIdx[i]));
        }
        return "1";
    }
}
