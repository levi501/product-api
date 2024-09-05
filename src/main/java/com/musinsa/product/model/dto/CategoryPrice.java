package com.musinsa.product.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryPrice {
	private String 카테고리;
	
	private int 가격;
}
