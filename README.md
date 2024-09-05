## 설명

상품을 관리하는 REST API 서버
- 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
- 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
- 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
- 상품 저장, 조회, 수정, 삭제 API

- 데이터베이스 구성
    - schema.sql : 스키마 생성
    - data.sql : 데이터 초기화

## 사용기술

- Spring Boot
- H2 Databse
- JPA
- Freemarker
- Lombok
- JUnit

## 사전설치

- Java 17+
- Gradle 8.8

## 빌드 및 실행 방법

```bash
./gradlew bootJar

java -jar product-api-0.0.1-SNAPSHOT.java
```

## API Endpoints

### 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
- URI : /api/products/category/low-price
- METHOD : GET
- Example
```
Frontend : http://localhost:8080
GET http://localhost:8080/api/products/category/low-price
```

### 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
- URI : /api/products/one-brand/low-price
- METHOD : GET
- Example
```
GET http://localhost:8080/api/products/one-brand/low-price
```

### 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
- URI : /api/products/min-max-price/category
- METHOD : GET
- PARAM : RequestParam (category)
- Example
```
GET http://localhost:8080/api/products/category/min-max-price?category=상의
```

### 4. 상품 저장, 조회, 수정, 삭제 API
#### 4-1.상품 추가 API
- URI : /api/products
- METHOD : POST
- PARAM : RequestBody (Product)
- Example
```
POST http://localhost:8080/api/products
{
	"brandName": "A"
	"category": "모자"
	"price": "2000"
}
```

#### 4-2.상품 조회 API
- URI : /api/products/{id}
- METHOD : GET
- PARAM : PathVariable
- Example
```
GET http://localhost:8080/api/products/1
```

#### 4-3.상품 업데이트 API
- URI : /api/products/{id}
- METHOD : PUT
- PARAM : PathVariable, RequestBody (Product)
- Example
```
PUT http://localhost:8080/api/products/1
{
	"brandName": "A"
	"category": "모자"
	"price": "2000"
}
```

#### 4-4.상품 삭제 API
- URI : /api/products/{id}
- METHOD : DELETE
- PARAM : PathVariable
- Example
```
DELETE http://localhost:8080/api/products/1
```
