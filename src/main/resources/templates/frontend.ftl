<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API</title>
	<style>
		table, th, td {
			border: 1px solid black;
			border-collapse: collapse;
		}
		th, td {
			text-align: center;
		}
	</style>
</head>
<body>
	<table width="300">
		<tr bgcolor="#D9E5FF">
			<th>카테고리</th>
			<th>브랜드</th>
			<th>가격</th>
		</tr>
		<#list categoryLowPrice.products as product>
			<tr>
				<td>${product.category}</td>
				<td>${product.brandName}</td>
				<td>${product.price}</td>
			</tr>
		</#list>
		<tr>
			<td>총액</td>
			<td colspan="2">${categoryLowPrice.total}</td>
		</tr>
	</table>
</body>
</html>