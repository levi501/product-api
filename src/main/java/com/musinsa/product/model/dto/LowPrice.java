package com.musinsa.product.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LowPrice {	
	private String �귣��;
	
	private List<CategoryPrice> ī�װ�;
	
	private int �Ѿ�;
}
