package com.musinsa.product.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryMinMaxPrice {
	private String ī�װ�;
	
	private List<BrandPrice> ������;
	
	private List<BrandPrice> �ְ�;
}
