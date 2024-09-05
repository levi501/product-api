package com.musinsa.product.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryLowPrice {
	private List<ProductDto> products;
	
	private int total;
}
