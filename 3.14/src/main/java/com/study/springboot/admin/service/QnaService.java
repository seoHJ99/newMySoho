package com.study.springboot.admin.service;


import com.study.springboot.entity.Qna;
import com.study.springboot.entity.QnaRepository;
import com.study.springboot.admin.dto.QnaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

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
}
