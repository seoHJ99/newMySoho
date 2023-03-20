package com.study.springboot.client.service;

import com.study.springboot.admin.dto.QnaResponseDto;
import com.study.springboot.entity.Qna;
import com.study.springboot.entity.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientQnaService_JunSeok {

    private final QnaRepository qnaRepository;

    @Transactional
    public void save(QnaResponseDto qnaResponseDto, String check1){
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        qnaResponseDto.setQna_ANSWERED("미답변");
        Qna entity = new Qna();
        entity.toSaveEntity(qnaResponseDto);
        qnaRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Page<Qna> findSearchClientQnaList (String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("qna_REGDATE"));
        Pageable pageable = PageRequest.of(page, 12,Sort.by(sorts));
        Page<Qna> a = null;
        a = qnaRepository.searchQnaContent(keyword, pageable);
        return a;
    }

    @Transactional(readOnly = true)
    public QnaResponseDto findById (int qna_IDX){
        Qna qna = qnaRepository.findById(qna_IDX).get();
        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);
        return qnaResponseDto;

    }

    @Transactional
    public void delete(int qna_IDX) {
        qnaRepository.deleteById(qna_IDX);
    }

    @Transactional(readOnly = true)
    public List<QnaResponseDto> findAll() {
        List<Qna> list = qnaRepository.findAll();
        return list.stream().map(QnaResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public QnaResponseDto qnaModify(QnaResponseDto dto, String check1){
        Qna qna =qnaRepository.findById(dto.getQna_IDX()).get();
        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);
        qnaResponseDto.setQna_CONTENT(dto.getQna_CONTENT());
        qnaResponseDto.setQna_SORT(dto.getQna_SORT());
        qnaResponseDto.setQna_PW(dto.getQna_PW());
        qnaResponseDto.setQna_WRITER(dto.getQna_WRITER());
        if (check1 != null && check1.equals("on")) {
            qnaResponseDto.setQna_SECRET(0);
        }else{
            qnaResponseDto.setQna_SECRET(1);
        }
        Qna qna1 = new Qna();
        qna.toSaveEntity(qnaResponseDto);
        qnaRepository.save(qna1);
        return qnaResponseDto;
    }
}
