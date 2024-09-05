package com.musinsa.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musinsa.product.model.dto.BrandPrice;
import com.musinsa.product.model.dto.CategoryLowPrice;
import com.musinsa.product.model.dto.CategoryMinMaxPrice;
import com.musinsa.product.model.dto.CategoryPrice;
import com.musinsa.product.model.dto.LowPrice;
import com.musinsa.product.model.dto.OneBrand;
import com.musinsa.product.model.dto.ProductDto;
import com.musinsa.product.model.entity.Product;
import com.musinsa.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}
	
	public Optional<Product> getProduct(long id) {
		return productRepository.findById(id);
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product updateProduct(long id, Product productDto) {
		Product product = productRepository.findById(id).orElseThrow();
		
		product.setBrandName(productDto.getBrandName());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		
		return productRepository.save(product);
	}
	
	public void deleteProduct(long id) {
		productRepository.deleteById(id);
	}
	
	public CategoryLowPrice getLowPriceByCategory() {
		List<ProductDto> products = productRepository.findLowPriceByCategory();
		
		return CategoryLowPrice.builder()
				.products(products)
				.total(products.stream()
						.mapToInt(ProductDto::getPrice)
						.sum())
				.build();
	}
	
	public OneBrand getLowPriceByOneBrand() {
		List<ProductDto> products = productRepository.findLowPriceByBrandName();

		return OneBrand.builder()
				.최저가(LowPrice.builder()
						.브랜드(products.get(0).getBrandName())
						.카테고리(products.stream()
								.map(m -> {
									return CategoryPrice.builder()
											.카테고리(m.getCategory())
											.가격(m.getPrice())
											.build();
								}).toList())
						.총액(products.stream()
								.mapToInt(ProductDto::getPrice)
								.sum())
						.build())
				.build();
	}
	
	public CategoryMinMaxPrice getMinMaxPriceByCategory(String category) {
		List<ProductDto> products = productRepository.findMinMaxPriceByCategory(category);
		
		if (products.size() == 0) {
			return null;
		}

		return CategoryMinMaxPrice.builder()
						.카테고리(category)
						.최저가(List.of(BrandPrice.builder()
									.브랜드(products.get(0).getBrandName())
										.가격(products.get(0).getPrice())
								.build()))
						.최고가(List.of(BrandPrice.builder()
										.브랜드(products.get(1).getBrandName())
										.가격(products.get(1).getPrice())
								.build()))
				.build();
	}
}
