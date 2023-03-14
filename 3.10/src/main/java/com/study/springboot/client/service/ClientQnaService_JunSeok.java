package com.study.springboot.client.service;

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

@Service
@RequiredArgsConstructor
public class ClientQnaService_JunSeok {

    private final QnaRepository qnARepository;

    @Transactional(readOnly = true)
    public Page<Qna> findSearchClientQnaList (String keyword, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("qna_REGDATE"));
        Pageable pageable = PageRequest.of(page, 12,Sort.by(sorts));
        Page<Qna> a = null;
        a = qnARepository.searchQnaContent(keyword, pageable);
        return a;
    }

    @Transactional
    public void delete(int qna_IDX) {qnARepository.deleteById(qna_IDX);}

}
