package com.musinsa.product.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LowPrice {	
	private String 브랜드;
	
	private List<CategoryPrice> 카테고리;
	
	private int 총액;
}
