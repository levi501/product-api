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
				.������(LowPrice.builder()
						.�귣��(products.get(0).getBrandName())
						.ī�װ�(products.stream()
								.map(m -> {
									return CategoryPrice.builder()
											.ī�װ�(m.getCategory())
											.����(m.getPrice())
											.build();
								}).toList())
						.�Ѿ�(products.stream()
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
						.ī�װ�(category)
						.������(List.of(BrandPrice.builder()
									.�귣��(products.get(0).getBrandName())
										.����(products.get(0).getPrice())
								.build()))
						.�ְ�(List.of(BrandPrice.builder()
										.�귣��(products.get(1).getBrandName())
										.����(products.get(1).getPrice())
								.build()))
				.build();
	}
}
