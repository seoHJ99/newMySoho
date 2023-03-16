package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.admin.service.ListService;
import com.study.springboot.admin.service.QnaService;
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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ClientQnaController_JunSeok {

    private final QnaRepository qnaRepository;
    private final ListService listService;
    private final ClientQnaService_JunSeok clientQnaService;
    private final QnaRepository qnaListRepository;
    private final ClientQnaService_JunSeok clientQnaService_junSeok;
    private final QnaService qnaService;


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
        qnaResponseDto.setQna_CATE("게시판");
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        // 비밀글 여부 옆의 체크박스에 체크여부 판단하기 위한 코드, 0이면 비밀글, 1이면 공개글
        qnaResponseDto.setQna_ANSWERED("미답변");
        // 위의 로직과 비슷함. 답변 여부를 확인하기 위한 코드,
        Qna entity = new Qna();
        entity.toSaveEntity(qnaResponseDto);
        qnaRepository.save(entity);
        return "redirect:/client/qna/list"; // redirect 하여 리스트페이지로 반환한다.
    }

    @RequestMapping("/client/qna/write/save2")
    @ResponseBody
    public String qnaSave2(QnaResponseDto qnaResponseDto, @RequestParam(value = "check1",required = false) String check1) {
        qnaResponseDto.setQna_CATE("상품");
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        // 비밀글 여부 옆의 체크박스에 체크여부 판단하기 위한 코드, 0이면 비밀글, 1이면 공개글

        qnaResponseDto.setQna_ANSWERED("미답변");
        // 위의 로직과 비슷함. 답변 여부를 확인하기 위한 코드,

        Qna entity = new Qna();
        entity.toSaveEntity(qnaResponseDto);
        qnaRepository.save(entity);
        return "<script>alert('등록되었습니다');opener.parent.location.reload();window.close();</script>";
    }


    @RequestMapping("/client/qna/list")
    public String clientQnaList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Qna> qnaList = listService.findQnAList(page);
        // entity QNA 타입만 받는 qnalist에 listservice의 findqnalist 함수를 이용하여 페이지마다 12개씩만 출력되도록 한다.
        model.addAttribute("paging",qnaList);
        List<QnaResponseDto> list = new ArrayList<>();
        // dto 타입만 받는 list를 생성후
        for(Qna entity : qnaList){
            list.add(new QnaResponseDto(entity));
        }
        // entity를 향상된 for문을 이용하여 dto로 변환 후 list에 넣어준다.
        model.addAttribute("list", list);

        return "/client/theOthers/QnAList"; // QnAList.html로 반환한다.

    }

    @RequestMapping("/client/qna/list/search")
    public String clientQnaListSearch(@RequestParam("search") String keyword,
                                      @RequestParam(value = "page", defaultValue = "0") int page, Model model){
        List<QnaResponseDto> list = new ArrayList<>();
        Page<Qna> paging = clientQnaService.findSearchClientQnaList(keyword, page);
        for(Qna a : paging){
            list.add(new QnaResponseDto(a));
        }
        model.addAttribute("paging", paging);
        model.addAttribute("list", list);

        return "/client/theOthers/QnAList";
    }
    @RequestMapping("/client/qna/modify")
    public String clientQnaListModify(@RequestParam("idx") int qna_IDX, Model model) {
        //      원래 있던 데이터 가져오기
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

        //        set 함수 사용하여 원래 있던 데이터에 새로운 데이터를 집어넣음
        QnaResponseDto qnaResponseDto = qnaService.findById(dto.getQna_IDX());
        qnaResponseDto.setQna_CONTENT(dto.getQna_CONTENT());
        qnaResponseDto.setQna_SORT(dto.getQna_SORT());
        qnaResponseDto.setQna_PW(dto.getQna_PW());
        qnaResponseDto.setQna_WRITER(dto.getQna_WRITER());

        // 공개글을 비밀글로 수정했을 때 비밀글 체크를 위한 코드
        // 비밀글 여부 옆의 체크박스에 체크여부 판단하기 위한 코드, 0이면 비밀글, 1이면 공개글
        System.out.println(check1);
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        // dto를 entity타입으로 변환 후 저장
        Qna qna = new Qna();
        qna.toSaveEntity(qnaResponseDto);
        qnaService.save(qna);

        return "redirect:/client/qna/list"; // list 페이지로 redirect
    }


    @RequestMapping("/client/qna/list/modify/save2")
    public String clientQnaListModifySave2(QnaResponseDto dto, @RequestParam(value = "check1",required = false) String check1) {
        //        set 함수 사용하여 원래 있던 데이터에 새로운 데이터를 집어넣음
        QnaResponseDto qnaResponseDto = qnaService.findById(dto.getQna_IDX());
        qnaResponseDto.setQna_CONTENT(dto.getQna_CONTENT());
        qnaResponseDto.setQna_SORT(dto.getQna_SORT());
        qnaResponseDto.setQna_PW(dto.getQna_PW());
        qnaResponseDto.setQna_WRITER(dto.getQna_WRITER());
        // 공개글을 비밀글로 수정했을 때 비밀글 체크를 위한 코드
        // 비밀글 여부 옆의 체크박스에 체크여부 판단하기 위한 코드, 0이면 비밀글, 1이면 공개글
        System.out.println(check1);
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        // dto를 entity타입으로 변환 후 저장
        Qna qna = new Qna();
        qna.toSaveEntity(qnaResponseDto);
        qnaService.save(qna);

        return "redirect:/product?idx=" + qnaResponseDto.getItem_IDX();
    }

    //    삭제버튼
    @RequestMapping("/client/qna/list/delete")
    public String clientQnaListDelete(int idx){
        clientQnaService_junSeok.delete(idx);
            return "redirect:/client/qna/list"; // list 페이지로 redirect

    }

    @RequestMapping("/client/qna/list/delete2")
    public String clientQnaListDelete2(int idx){
        QnaResponseDto qnaResponseDto = qnaService.findById(idx);
        int itemIdx = qnaResponseDto.getItem_IDX();
        clientQnaService_junSeok.delete(idx);
        return "redirect:/product?idx=" + itemIdx;
    }
}

