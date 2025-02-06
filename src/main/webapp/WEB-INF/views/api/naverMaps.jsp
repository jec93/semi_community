<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=hac2hbk7dd"></script>

<style>
	#map{
		
		width: 800px;
		height: 600px;
		justify-content: center;
	}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content">
			<section class="section">
				<div class="page-title">네이버지도</div>
				<div id="map">
					
				</div>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
	<script>
	
		 $(function(){
		 map=new naver.maps.Map('map',{
		 	center:new naver.maps.LatLng(37.49899300000001,127.032909)
		 })
		 });
	</script>
</body>
</html>