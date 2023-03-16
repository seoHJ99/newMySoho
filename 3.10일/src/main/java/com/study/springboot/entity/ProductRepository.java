package com.study.springboot.entity;

import com.study.springboot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0", nativeQuery = true)
    List<Product> findAllNoRepeat();

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 ORDER BY item_REGDATE DESC", nativeQuery = true)
    List<Product> pdOrderByDate();

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 ORDER BY item_SELL DESC", nativeQuery = true)
    List<Product> pdOrderBySelling();

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 ORDER BY item_PRICE, item_DISCOUNT", nativeQuery = true)
    List<Product> pdOrderByPrice();

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 AND item_NAME LIKE CONCAT('%',:keyword,'%') ORDER BY item_REGDATE DESC", nativeQuery = true)
    List<Product> pdOrderByDate(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 AND item_NAME LIKE CONCAT('%',:keyword,'%') ORDER BY item_SELL DESC", nativeQuery = true)
    List<Product> pdOrderBySelling(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = 0 AND item_NAME LIKE CONCAT('%',:keyword,'%') ORDER BY item_PRICE, item_DISCOUNT DESC", nativeQuery = true)
    List<Product> pdOrderByPrice(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM item WHERE item_ORIGINAL = :idx OR item_IDX = :idx AND item_OPTION IS NOT NULL", nativeQuery = true)
    List<Product>findByItem_OPTION(@Param(value = "idx") int idx);

    @Query(value = "SELECT * FROM item WHERE item_CATEGORY1 =:category AND item_ORIGINAL = 0", nativeQuery = true)
    List<Product> findByCategory(@Param(value = "category") String category);

    @Query(value = "SELECT * FROM item WHERE item_CATEGORY2 =:category AND item_ORIGINAL = 0", nativeQuery = true)
    List<Product> findByCategory2(@Param(value = "category") String category);

    @Query(value = "SELECT * FROM item WHERE item_NAME LIKE CONCAT('%',:keyword,'%') AND item_ORIGINAL =0", nativeQuery = true)
    List<Product> findByKeyword(@Param(value = "keyword") String keyword);

    @Query(value = "SELECT * FROM item WHERE item_NAME LIKE CONCAT('%',:keyword,'%') OR item_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Product> searchAll( @Param(value = "keyword") String keyword, Pageable pageable);

    // 옵션 둘 다 선택했을때
    @Query(value = "SELECT * FROM item WHERE item_CATEGORY1 =:category1 AND item_CATEGORY2 =:category2 AND item_IDX LIKE CONCAT('%',:keyword,'%') OR item_CATEGORY1 =:category1 AND item_CATEGORY2 =:category2 AND item_NAME LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Product> searchWithCate1AndCate2 (@Param(value = "category1") String category1,
                                           @Param(value = "category2") String category2,
                                           @Param(value = "keyword") String keyword,
                                           Pageable pageable);

    //옵션 1번만 선택했을때
    @Query(value = "SELECT * FROM item WHERE item_CATEGORY1 =:category1 AND item_NAME LIKE CONCAT('%',:keyword,'%') OR item_CATEGORY1 =:category1 AND item_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Product> searchWithCate1 (@Param(value = "category1") String category1,
                                   @Param(value = "keyword") String keyword,
                                   Pageable pageable);

    //옵션 2번만 선택했을때
    @Query(value = "SELECT * FROM item WHERE item_CATEGORY2 =:category2 AND item_NAME LIKE CONCAT('%',:keyword,'%') OR item_CATEGORY2 =:category2 AND item_IDX LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    Page<Product> searchWithCate2 (@Param(value = "category2") String category2,
                                   @Param(value = "keyword") String keyword,
                                   Pageable pageable);
}
