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
				.message("����")
				.data(productService.saveProduct(product))
				.build();
	}
	
	
	@PutMapping("/{id}")
	public Response updateProduct(@PathVariable(name = "id") Long id,
							     @RequestBody Product productDto) {
		return Response.builder()
				.code(200)
				.message("����")
				.data(productService.updateProduct(id, productDto))
				.build();
	}
	
	@DeleteMapping("/{id}")
	public Response deleteupdateProduct(@PathVariable(name = "id") Long id) {
		productService.deleteProduct(id);
		
		return Response.builder()
				.code(200)
				.message(id + " �� �����߽��ϴ�")
				.build();
	}
	
	@GetMapping("/{id}")
	public Response getProduct(@PathVariable(name = "id") Long id) {
		return productService.getProduct(id)
				.map(m -> {
					return Response.builder()
							.code(200)
							.message("����")
							.data(m)
							.build();
				})
				.orElse(Response.builder()
						.code(404)
						.message("��ǰ�� ��ȸ�� �� �����ϴ�")
						.build());
	}
	
	@GetMapping("/category/low-price")
	public Response getLowPriceByCategory() {
		return Response.builder()
				.code(200)
				.message("����")
				.data(productService.getLowPriceByCategory())
				.build();
	}
	
	@GetMapping("/one-brand/low-price")
	public Response getLowPriceByOneBrand() {
		return Response.builder()
				.code(200)
				.message("����")
				.data(productService.getLowPriceByOneBrand())
				.build();
	}
	
	@GetMapping("/category/min-max-price")
	public Response getMinMaxPriceByCategory(@RequestParam("category") String category) {
		CategoryMinMaxPrice categoryMinMaxPrice = productService.getMinMaxPriceByCategory(category);
		
		if (categoryMinMaxPrice == null) {
			return Response.builder()
					.code(404)
					.message("ī�װ��� �������� �ʽ��ϴ�")
					.build();
		}
		
		return Response.builder()
				.code(200)
				.message("����")
				.data(categoryMinMaxPrice)
				.build();
	}
}
