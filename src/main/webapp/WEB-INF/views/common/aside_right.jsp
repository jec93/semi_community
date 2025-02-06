<%@page import="kr.or.iei.search.word.vo.Word"%>
<%@page import="kr.or.iei.aside.model.vo.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
ArrayList<Product> ProductList = (ArrayList<Product>)session.getAttribute("productList");
ArrayList<Word> wordList = (ArrayList<Word>)getServletContext().getAttribute("wordList");
%>

<aside class="right-sidebar">
	<div class="search-rank">
		<div class="rank-text">
			<span>인기 검색어</span>
		</div>
		<div class="rank-bottom"></div>
		<div class="rank-wrapper">
			<ul class="rank-box">
				<%for(int i=0; i<wordList.size(); i++) {%>
				<li class="rank-item"><a href="">
				<span class="keyword-rank-title"><%=wordList.get(i).getSrchRank() %></span> <span><%=wordList.get(i).getSrchWord() %></span>
				</a></li>
				<%} %>
			</ul>
		</div>
	</div>
	<div class="aside-container" style="border: 1px solid black; width: 300px; background-color: gray;">
		<h2>핫딜!!(임시로 크기 정상화)</h2>
		<% for(int i = 0; i < ProductList.size(); i++) { %>
		<div>
			<a href="<%= ProductList.get(i).getShopLink() %>"><%= ProductList.get(i).getShopTitle() %></a>
		</div>
		<div class="aside-content" style="border:1px solid white; dispaly:flex;">
			<img src="<%= ProductList.get(i).getShopImg() %>"
				class="aside-image-box" style="width: 80px; height: 80px;">
			<div class="aside-tags" style="margin-left: 20px; display:flex;">
				<div class="aside-tag" style="margin-bottom: 8px; font-size : 16px;"><%= ProductList.get(i).getShopLowPrice() %>원!!
				</div>
				<div class="aside-tag" style="margin-bottom: 8px; font-size : 16px;">
					판매처 :
					<%= ProductList.get(i).getShopName() %></div>
				<div class="aside-tag" style="margin-bottom: 8px; font-size : 16px;">
					제품 카테고리 :
					<%= ProductList.get(i).getShopCategory1() %></div>
			</div>
		</div>
		<% } %>
	</div>
</aside>
