package com.musinsa.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.product.model.entity.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class ProductApiApplicationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Order(1)
	@DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
	@Test
	void lowPriceByCategoryTest() throws Exception {
		mvc.perform(get("/api/products/low-price/category"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.products.length()").value("8"))
				.andExpect(jsonPath("$.data.total").value("34100"));
	}
	
	@Order(2)
	@DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
	@Test
	void lowPriceByOneBrandTest() throws Exception {
		mvc.perform(get("/api/products/low-price/one-brand"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.최저가.브랜드").value("D"))
				.andExpect(jsonPath("$.data.최저가.총액").value("36100"));
	}
	
	@Order(3)
	@DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
	@Test
	void minMaxPriceByCategoryTest() throws Exception {
		mvc.perform(get("/api/products/min-max-price/category?category=상의"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.카테고리").value("상의"))
				.andExpect(jsonPath("$.data.최저가[0].브랜드").value("C"))
				.andExpect(jsonPath("$.data.최저가[0].가격").value("10000"))
				.andExpect(jsonPath("$.data.최고가[0].브랜드").value("I"))
				.andExpect(jsonPath("$.data.최고가[0].가격").value("11400"));
	}
	
	@Order(4)
	@DisplayName("상품 추가 테스트")
	@Test
	void saveProductTest() throws Exception {
		Product product = Product.builder()
							.brandName("Z")
							.category("모자")
							.price(20000)
							.build();
        String json = objectMapper.writeValueAsString(product);
		
		mvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Z"))
				.andExpect(jsonPath("$.data.category").value("모자"))
				.andExpect(jsonPath("$.data.price").value("20000"));
	}
	
	@Order(5)
	@DisplayName("상품 등록 후 조회 테스트")
	@Test
	void getProductAfterSaveTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Z"))
				.andExpect(jsonPath("$.data.category").value("모자"))
				.andExpect(jsonPath("$.data.price").value("20000"));
	}
	
	@Order(6)
	@DisplayName("상품 업데이트 테스트")
	@Test
	void updateProductTest() throws Exception {
		Product product = Product.builder()
							.brandName("Y")
							.category("스니커즈")
							.price(30000)
							.build();
        String json = objectMapper.writeValueAsString(product);
		
		mvc.perform(put("/api/products/73")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Y"))
				.andExpect(jsonPath("$.data.category").value("스니커즈"))
				.andExpect(jsonPath("$.data.price").value("30000"));
	}
	
	@Order(7)
	@DisplayName("상품 업데이트 후 조회 테스트")
	@Test
	void getProductAfterUpdateTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
					.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Y"))
				.andExpect(jsonPath("$.data.category").value("스니커즈"))
				.andExpect(jsonPath("$.data.price").value("30000"));
	}
	
	@Order(8)
	@DisplayName("상품 삭제 테스트")
	@Test
	void deleteProductTest() throws Exception {		
		mvc.perform(delete("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("73 를 삭제했습니다"));
	}
	
	@Order(9)
	@DisplayName("상품 삭제 후 조회 테스트")
	@Test
	void getProductAfterDeleteTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("404"))
				.andExpect(jsonPath("$.message").value("상품을 조회할 수 없습니다"));
	}
}
