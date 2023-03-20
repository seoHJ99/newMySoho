package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.admin.service.ListService;
import com.study.springboot.client.service.Cl_ProductService_HyungMin;
import com.study.springboot.client.service.ClientQnaService_JunSeok;
import com.study.springboot.entity.Qna;
import com.study.springboot.entity.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ClientQnaController_JunSeok {

    private final QnaRepository qnaRepository;
    private final ListService listService;
    private final ClientQnaService_JunSeok qnaService;
    private final Cl_ProductService_HyungMin clProductService;


    @RequestMapping("/client/qna/write")
    public String qnaWrite() {
        return "/client/theOthers/QnA-write";
    }

    @RequestMapping("/client/qna/write2")
    public String qnaWrite2(Model model, @RequestParam("idx") int item_idx) {
        ProductResponseDto dto = clProductService.findById(item_idx);
        model.addAttribute("product", dto);
        return "/client/theOthers/QnA-write2";
    }

    @RequestMapping("/client/qna/write/save")
    public String qnaSave(QnaResponseDto qnaResponseDto, @RequestParam(value = "check1",required = false) String check1) {
        System.out.println(qnaResponseDto.getQna_PW());
        qnaResponseDto.setQna_CATE("게시판");
        qnaService.save(qnaResponseDto,check1);
        return "redirect:/client/qna/list"; // redirect 하여 리스트페이지로 반환한다.
    }

    @RequestMapping("/client/qna/write/save2")
    @ResponseBody
    public String qnaSave2(QnaResponseDto qnaResponseDto, @RequestParam(value = "check1",required = false) String check1) {
        qnaResponseDto.setQna_CATE("상품");
        qnaService.save(qnaResponseDto, check1);
        return "<script>alert('등록되었습니다');opener.parent.location.reload();window.close();</script>";
    }


    @RequestMapping("/client/qna/list")
    public String clientQnaList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Qna> qnaList = listService.findQnAList(page);
        model.addAttribute("paging",qnaList);
        List<QnaResponseDto> list = new ArrayList<>();
        for(Qna entity : qnaList){
            list.add(new QnaResponseDto(entity));
        }
        model.addAttribute("list", list);

        return "/client/theOthers/QnAList"; // QnAList.html로 반환한다.

    }

    @RequestMapping("/client/qna/list/search")
    public String clientQnaListSearch(@RequestParam("search") String keyword,
                                      @RequestParam(value = "page", defaultValue = "0") int page, Model model){
        List<QnaResponseDto> list = new ArrayList<>();
        Page<Qna> paging = qnaService.findSearchClientQnaList(keyword, page);
        for(Qna a : paging){
            list.add(new QnaResponseDto(a));
        }
        model.addAttribute("paging", paging);
        model.addAttribute("list", list);

        return "/client/theOthers/QnAList";
    }
    @RequestMapping("/client/qna/modify")
    public String clientQnaListModify(@RequestParam("idx") int qna_IDX, Model model) {
        Qna qna = qnaRepository.findById(qna_IDX).get();
        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);
        model.addAttribute("qna", qna);
        return "/client/theOthers/QnA-modify";
    }

    @PostMapping("/checkPw2")
    @ResponseBody
    public String checkPw(HttpServletRequest request, QnaResponseDto dto)  {
        List<Qna> list = qnaRepository.findByQnaIdx(dto.getQna_IDX());
        Qna entity = list.get(0);
        request.getSession().setAttribute("qnaEntity", entity);
        String realPw = entity.getQna_PW();
        String ppw = request.getParameter("qna_PW");
        int idx = entity.getQna_IDX();
        boolean checkPw = realPw.equals(ppw);
        if (checkPw) {
            return "<script>location.href='client/qna/modify?idx=" + idx + "';</script>";
        }
        return "<script>alert('비밀번호가 다릅니다.');history.go(-1);</script>";
    }

    @RequestMapping("/client/qna/list/modify/save")
    public String clientQnaListModifySave(QnaResponseDto dto, @RequestParam(value = "check1",required = false) String check1) {
        qnaService.qnaModify(dto, check1);
        return "redirect:/client/qna/list"; // list 페이지로 redirect
    }


    @RequestMapping("/client/qna/list/modify/save2")
    public String clientQnaListModifySave2(QnaResponseDto dto, @RequestParam(value = "check1",required = false) String check1) {
       QnaResponseDto qnaResponseDto = qnaService.qnaModify(dto, check1);
        return "redirect:/product?idx=" + qnaResponseDto.getItem_IDX();
    }

    //    삭제버튼
    @RequestMapping("/client/qna/list/delete")
    public String clientQnaListDelete(int idx){
        qnaService.delete(idx);
            return "redirect:/client/qna/list"; // list 페이지로 redirect

    }

    @RequestMapping("/client/qna/list/delete2")
    public String clientQnaListDelete2(int idx){
        QnaResponseDto qnaResponseDto = qnaService.findById(idx);
        int itemIdx = qnaResponseDto.getItem_IDX();
        qnaService.delete(idx);
        return "redirect:/product?idx=" + itemIdx;
    }

    @RequestMapping("/qna/answer-check")
    @ResponseBody
    public String checkAnswer(int idx, HttpSession session){
        QnaResponseDto qnaResponseDto = qnaService.findById(idx);
        if( qnaResponseDto.getQna_SECRET() == 0) {
            if(qnaResponseDto.getMember_IDX() == null){//비밀글이고 비회원 글일때
                return "<script>location.href='/open/qna/pw?idx="+idx+"'</script>";
            } // 비밀글이고 접근자의 글이 아닐때
            if (session.getAttribute("member_IDX") == null || (int)session.getAttribute("member_IDX") != qnaResponseDto.getMember_IDX()) {
                return "<script>alert('비밀글은 작성자만 확인 가능합니다.'); history.back();</script>";
            }else { // 비회원이 아니고 자기글일때
                return "<script>location.href='/qna/answer?idx=" + idx + "';</script>";
            }
        }else { // 비밀글이 아니면 다 보임
            return "<script>location.href='/qna/answer?idx=" + idx + "';</script>";
        }
    }

    @RequestMapping("/open/qna/pw")
    public String nonMemQnACheck(int idx, Model model){
        model.addAttribute("qnaIDX", idx);
        return "/client/theOthers/qnaPWcheck";
    }
    @RequestMapping("/check/qna/pw")
    @ResponseBody
    public String qnacheckPW(@RequestParam("idx") int idx, @RequestParam("Pw")String pw){
        QnaResponseDto dto = qnaService.findById(idx);
        if(dto.getQna_PW().equals(pw)){
           return "<script>location.href='/qna/answer?idx=" + idx + "&Pw=" + pw +"';</script>";
        }else {
            return "<script>alert('비밀번호가 틀립니다.'); history.back();</script>";
        }
    }

    @RequestMapping("/qna/answer")
    public String ClientQnaAnswer(int idx, Model model, @RequestParam(value = "Pw", required = false) String pw){
        QnaResponseDto qnaResponseDto = qnaService.findById(idx);
        if(qnaResponseDto.getQna_SECRET() == 0) {
            if (qnaResponseDto.getMember_IDX() != null) {
                model.addAttribute("dto", qnaResponseDto);
                return "/client/theOthers/qnaView";
            } else {
                if (qnaResponseDto.getQna_PW().equals(pw)) {
                    model.addAttribute("dto", qnaResponseDto);
                    return "/client/theOthers/qnaView";
                } else {
                    return "";
                }
            }
        }else {
            model.addAttribute("dto", qnaResponseDto);
            return "/client/theOthers/qnaView";
        }
    }
}

