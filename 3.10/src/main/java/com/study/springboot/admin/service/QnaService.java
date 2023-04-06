package com.study.springboot.admin.service;


import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.entity.Qna;
import com.study.springboot.entity.QnaRepository;
import com.study.springboot.admin.dto.QnaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public QnaResponseDto findById (int qna_IDX){
        Qna qna = qnaRepository.findById(qna_IDX).get();
        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);
        return qnaResponseDto;

    }

    @Transactional
    public void save(Qna qna){
        qnaRepository.save(qna);
    }

    @Transactional
    public void delete(int id){
        qnaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<QnaResponseDto> findByMemberId(int id){
       List <Qna> entities = qnaRepository.findByMemberIDX(id);
       List<QnaResponseDto> dtoList = new ArrayList<>();
       for(int i=0; i<entities.size(); i++){
           QnaResponseDto dto = new QnaResponseDto( entities.get(i));
           dtoList.add(dto);
       }
       return dtoList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findProductConnected(int productIdx){
        List<ProductResponseDto> temp = new ArrayList<>();
        ProductResponseDto pdDto = productService.findById(productIdx);
        return pdDto;
    }
}
