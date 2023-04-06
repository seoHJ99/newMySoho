package com.study.springboot.admin.service;



import com.study.springboot.entity.Product;
import com.study.springboot.entity.Review;
import com.study.springboot.entity.ReviewRepository;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.entity.ProductRepository;
import com.study.springboot.admin.dto.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void save(Product entity){
        productRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(int item_idx){
        Product product = productRepository.findById(item_idx).get();
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        return productResponseDto;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> findReviewScore(int item_idx){
        List<ReviewResponseDTO> dtoList = new ArrayList<>();
        List<Review> entityList = reviewRepository.findByItemIDX(item_idx);
        for(int i = 0; entityList.size()>i; i++){
            ReviewResponseDTO temp = new ReviewResponseDTO(entityList.get(i));
            dtoList.add(temp);
        }
        return dtoList;
    }

    @Transactional
    public void delete(int item_idx){
        Product product = productRepository.findById(item_idx).get();
        productRepository.delete(product);
    }

    @Transactional
    public void plusSell(int item_idx, int item_QTY){
        Product product = productRepository.findById(item_idx).get();
        ProductResponseDto dto = new ProductResponseDto(product);
        dto.setItem_SELL(dto.getItem_SELL()+item_QTY);
        product.toUpdateEntity(dto);
        save(product);
    }
}
