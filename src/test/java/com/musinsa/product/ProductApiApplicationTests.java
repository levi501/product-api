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
	@DisplayName("ī�װ� �� �������� �귣��� ��ǰ ����, �Ѿ��� ��ȸ�ϴ� API")
	@Test
	void lowPriceByCategoryTest() throws Exception {
		mvc.perform(get("/api/products/low-price/category"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.products.length()").value("8"))
				.andExpect(jsonPath("$.data.total").value("34100"));
	}
	
	@Order(2)
	@DisplayName("���� �귣��� ��� ī�װ� ��ǰ�� ������ �� �������ݿ� �Ǹ��ϴ� �귣��� ī�װ��� ��ǰ����, �Ѿ��� ��ȸ�ϴ� API")
	@Test
	void lowPriceByOneBrandTest() throws Exception {
		mvc.perform(get("/api/products/low-price/one-brand"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.������.�귣��").value("D"))
				.andExpect(jsonPath("$.data.������.�Ѿ�").value("36100"));
	}
	
	@Order(3)
	@DisplayName("ī�װ� �̸����� ����, �ְ� ���� �귣��� ��ǰ ������ ��ȸ�ϴ� API")
	@Test
	void minMaxPriceByCategoryTest() throws Exception {
		mvc.perform(get("/api/products/min-max-price/category?category=����"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.ī�װ�").value("����"))
				.andExpect(jsonPath("$.data.������[0].�귣��").value("C"))
				.andExpect(jsonPath("$.data.������[0].����").value("10000"))
				.andExpect(jsonPath("$.data.�ְ�[0].�귣��").value("I"))
				.andExpect(jsonPath("$.data.�ְ�[0].����").value("11400"));
	}
	
	@Order(4)
	@DisplayName("��ǰ �߰� �׽�Ʈ")
	@Test
	void saveProductTest() throws Exception {
		Product product = Product.builder()
							.brandName("Z")
							.category("����")
							.price(20000)
							.build();
        String json = objectMapper.writeValueAsString(product);
		
		mvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Z"))
				.andExpect(jsonPath("$.data.category").value("����"))
				.andExpect(jsonPath("$.data.price").value("20000"));
	}
	
	@Order(5)
	@DisplayName("��ǰ ��� �� ��ȸ �׽�Ʈ")
	@Test
	void getProductAfterSaveTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Z"))
				.andExpect(jsonPath("$.data.category").value("����"))
				.andExpect(jsonPath("$.data.price").value("20000"));
	}
	
	@Order(6)
	@DisplayName("��ǰ ������Ʈ �׽�Ʈ")
	@Test
	void updateProductTest() throws Exception {
		Product product = Product.builder()
							.brandName("Y")
							.category("����Ŀ��")
							.price(30000)
							.build();
        String json = objectMapper.writeValueAsString(product);
		
		mvc.perform(put("/api/products/73")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Y"))
				.andExpect(jsonPath("$.data.category").value("����Ŀ��"))
				.andExpect(jsonPath("$.data.price").value("30000"));
	}
	
	@Order(7)
	@DisplayName("��ǰ ������Ʈ �� ��ȸ �׽�Ʈ")
	@Test
	void getProductAfterUpdateTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
					.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.brandName").value("Y"))
				.andExpect(jsonPath("$.data.category").value("����Ŀ��"))
				.andExpect(jsonPath("$.data.price").value("30000"));
	}
	
	@Order(8)
	@DisplayName("��ǰ ���� �׽�Ʈ")
	@Test
	void deleteProductTest() throws Exception {		
		mvc.perform(delete("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("73 �� �����߽��ϴ�"));
	}
	
	@Order(9)
	@DisplayName("��ǰ ���� �� ��ȸ �׽�Ʈ")
	@Test
	void getProductAfterDeleteTest() throws Exception {		
		mvc.perform(get("/api/products/73"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("404"))
				.andExpect(jsonPath("$.message").value("��ǰ�� ��ȸ�� �� �����ϴ�"));
	}
}
