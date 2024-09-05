package com.musinsa.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.musinsa.product.model.dto.CategoryLowPrice;
import com.musinsa.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FrontendController {
	private final ProductService productService;
	
	@GetMapping("/")
	public String frontend(Model model) {
		CategoryLowPrice categoryLowPrice = productService.getLowPriceByCategory();
		
		model.addAttribute("categoryLowPrice", categoryLowPrice);
		return "frontend";
	}
}
