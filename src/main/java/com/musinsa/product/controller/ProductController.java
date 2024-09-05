package com.musinsa.product.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.product.model.Response;
import com.musinsa.product.model.entity.Product;
import com.musinsa.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public Response saveProduct(@RequestBody Product product) {
		return Response.builder()
				.code(200)
				.message("성공")
				.data(productService.saveProduct(product))
				.build();
	}
	
	
	@PutMapping("/{id}")
	public Response updateProduct(@PathVariable(name = "id") Long id,
							     @RequestBody Product productDto) {
		return Response.builder()
				.code(200)
				.message("성공")
				.data(productService.updateProduct(id, productDto))
				.build();
	}
	
	@DeleteMapping("/{id}")
	public Response deleteupdateProduct(@PathVariable(name = "id") Long id) {
		productService.deleteProduct(id);
		
		return Response.builder()
				.code(200)
				.message(id + " 를 삭제했습니다")
				.build();
	}
	
	@GetMapping("/{id}")
	public Response getProduct(@PathVariable(name = "id") Long id) {
		return productService.getProduct(id)
				.map(m -> {
					return Response.builder()
							.code(200)
							.message("성공")
							.data(m)
							.build();
				})
				.orElse(Response.builder()
						.code(404)
						.message("상품을 조회할 수 없습니다")
						.build());
	}
	
	@GetMapping("/category/low-price")
	public Response getLowPriceByCategory() {
		return Response.builder()
				.code(200)
				.message("성공")
				.data(productService.getLowPriceByCategory())
				.build();
	}
	
	@GetMapping("/one-brand/low-price")
	public Response getLowPriceByOneBrand() {
		return Response.builder()
				.code(200)
				.message("성공")
				.data(productService.getLowPriceByOneBrand())
				.build();
	}
	
	@GetMapping("/category/min-max-price")
	public Response getMinMaxPriceByCategory(@RequestParam("category") String category) {
		CategoryMinMaxPrice categoryMinMaxPrice = productService.getMinMaxPriceByCategory(category);
		
		if (categoryMinMaxPrice == null) {
			return Response.builder()
					.code(404)
					.message("카테고리가 존재하지 않습니다")
					.build();
		}
		
		return Response.builder()
				.code(200)
				.message("성공")
				.data(categoryMinMaxPrice)
				.build();
	}
}
