package com.musinsa.product.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BrandPrice {
	private String 브랜드;
	
	private int 가격;
}
