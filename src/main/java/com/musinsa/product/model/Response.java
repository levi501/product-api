package com.musinsa.product.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response<T> {
	private int code;
	
	private String message;
	
	private T data;
}
