package com.musinsa.product.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryMinMaxPrice {
	private String 카테고리;
	
	private List<BrandPrice> 최저가;
	
	private List<BrandPrice> 최고가;
}
