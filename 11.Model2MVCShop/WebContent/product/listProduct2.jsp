
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"  %>

<%@ page import="com.model2.mvc.common.*" %>


<html>
<head>

<title>상품 목록조회</title>


<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">


function fncGetProductList(currentPage) {
	//document.getElementById("currentPage").value = currentPage;
	$("#currentPage").val(currentPage)
   	//document.detailForm.submit();		
	$("form").attr("method" , "POST").attr("action" , "/product/listProduct2?menu=${param.menu}").submit();
	//$("form").attr("method" , "POST").attr("action" , "/product/getProduct	?menu=${param.menu}").submit();
}
	
$(function() {
	 $( "td.ct_btn01:contains('검색')" ).on("click" , function() {			
			fncGetProductList(1);
		});
	 
	 $(".ct_list_pop td:nth-child(3)").on("click", function(){
		 
		 //입력한 인덱스 array 번호
		 //alert($(".ct_list_pop td:nth-child(3)").index(this));
		 
		 
		 //prodNo array 값
	//	 alert($(".ct_list_pop td:nth-child(1)").text());
		 //alert($(".ct_list_pop td:nth-child(1)").text());
		 
		 
		 var No = $(  $(".ct_list_pop td:nth-child(1)")[ $(".ct_list_pop td:nth-child(3)").index(this) ]).text().trim() ;
		 //alert ($(".ct_list_pop td:nth-child(1)"["$(.ct_list_pop td:nth-child(3)").index(this)]));
		 self.location = "/product/getProduct?prodNo="+No;
		});
	 
	 $(".ct_list_pop td:nth-child(1)").on("click" , function(){
		 
		 var prodNo = $(this).text().trim();
		 $.ajax(
			{
				url : "/product/json/getProduct/"+prodNo,
				method : "GET",
				dataType : "json",
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"					
				},
				success : function(JSONData , status){
					
					var displayValue = "<h4>"
												+"상품번호"+JSONData.prodNo+"<br/>"
												+"상 품 명"+JSONData.prodName+"<br/>"
												+"가		격"+JSONData.price+"<br/>"
												+"등 록 일"+JSONData.regDate+"<br/>"
												+"</hr4>";
					$("h4").remove();
					$("#"+prodNo+"").html(displayValue);
				}
			});
		 
	 });
	 
	 $(".ct_list_pop td:nth-child(3)").css("color","red");
	 $("h7").css("color","red");
	 
	 $(".ct_list_pop td:nth-child(1)").css("color","blue");
	 $("h8").css("color","blue");
	 
	 
});
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<!--  <form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post" >-->
<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>

		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
					

		${param.menu.equals("manage")? "상품관리":"상품목록조회" }


					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
	
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
					
			</select>
			<input type="text" name="searchKeyword"  value="${ !empty search.searchKeyword ? search.searchKeyword : ''}""
			class="ct_input_g" style="width:200px; height:19px" />
		</td>
		
		
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<!--  <a href="javascript:fncGetProductList('1');">검색</a>-->
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
			
	

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount }  건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No<br><h8>(no click:정보)</h8></td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명<br><h7>(상품명 click:구매)</h7></td>		
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	
	
	<c:forEach var="i"  items="${list}" varStatus="j">
		
	<tr class="ct_list_pop">
	
		<td align="center">${i.prodNo}</td>	
		<td></td>
		<td align="center">${i.prodName}</td>
		<td></td>
		<td align="left">${i.price}</td>
		<td></td>
		<td align="left">${i.regDate}</td>
		<td></td>
		<td align="left">
		
			판매중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		<td id="${i.prodNo }" colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	
	</c:forEach>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		  <input type="hidden" id="currentPage" name="currentPage" value=""/>
	
			<jsp:include page="../common/pageNavigator1.jsp"/>	
			
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
