package com.musinsa.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musinsa.product.model.dto.ProductDto;
import com.musinsa.product.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query(value = """
			WITH low_price AS (
			    SELECT 
			        id,
			        category,
			        brand_name,
			        price, 
			        ROW_NUMBER() OVER (PARTITION BY category ORDER BY price ASC, id ASC) AS rn
			    FROM 
			        product
			)
			SELECT 
			    id, 
			    category,
			    brand_name,	
			    price
			FROM 
			    low_price
			WHERE 
			    rn = 1
			""", nativeQuery = true)
	List<ProductDto> findLowPriceByCategory();
	
	@Query(value = """
			SELECT
			    p.*
			FROM
				product p
			WHERE
			    p.brand_name = 
					(SELECT
						low_price.brand_name
					FROM
						(SELECT 
							brand_name, 
							ROW_NUMBER() OVER (ORDER BY SUM(price)) AS rn
						FROM 
							product
						GROUP BY 
							brand_name) AS low_price
					WHERE
						low_price.rn = 1)
			""", nativeQuery = true)
	List<ProductDto> findLowPriceByBrandName();
	
	@Query(value = """
			SELECT
				* 
			FROM
				product
			WHERE
			        category = :category
				AND price = (SELECT
							MIN(price)
						FROM
							product
						WHERE
							category = :category)
			UNION ALL
			SELECT
				* 
			FROM
				product
			WHERE
			        category = :category
				AND price = (SELECT 
							MAX(price)
						FROM
							product
						WHERE
							category = :category)
			""", nativeQuery = true)
	List<ProductDto> findMinMaxPriceByCategory(@Param("category") String category);
}
