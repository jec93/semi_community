package kr.or.iei.aside.model.vo;

import java.io.Serializable;

public class Product implements Serializable {
	private String shopTitle;
	private String shopLink;
	private String shopImg;
	private int shopLowPrice;
	private String shopName;
	private String shopCategory1; //4까지 있음
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String shopTitle, String shopLink, String shopImg, int shopLowPrice, String shopName,
			String shopCategory1) {
		super();
		this.shopTitle = shopTitle;
		this.shopLink = shopLink;
		this.shopImg = shopImg;
		this.shopLowPrice = shopLowPrice;
		this.shopName = shopName;
		this.shopCategory1 = shopCategory1;
	}
	public String getShopTitle() {
		return shopTitle;
	}
	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}
	public String getShopLink() {
		return shopLink;
	}
	public void setShopLink(String shopLink) {
		this.shopLink = shopLink;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public int getShopLowPrice() {
		return shopLowPrice;
	}
	public void setShopLowPrice(int shopLowPrice) {
		this.shopLowPrice = shopLowPrice;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopCategory1() {
		return shopCategory1;
	}
	public void setShopCategory1(String shopCategory1) {
		this.shopCategory1 = shopCategory1;
	}
	@Override
	public String toString() {
		return "제품이름 : " + shopTitle + ", shopLink : " + shopLink + ", 상품이미지 주소 : " + shopImg + ", 최저가 : "
				+ shopLowPrice + ", 판매몰 : " + shopName + ", 제품 카테고리" + shopCategory1;
	}
	
	
}
