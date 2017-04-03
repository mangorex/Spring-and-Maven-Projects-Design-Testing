<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="categories" id="row"
	requestURI="${requestUri}" class="displaytag">
	
	<display:column property="categoryFather.name" titleKey="category.fatherName">
	</display:column>
	
	<display:column property="name" titleKey="category.name">
	</display:column>

	<display:column property="description" titleKey="category.description"/>
	
	
	<display:column titleKey="category.picture">
		<img src="${row.picture}" style="width:50 px; height:50px"/>
	</display:column>


	<display:column property="tags" titleKey="category.tags">
	</display:column>

	
		 <display:column>
		<a href="category/administrator/edit.do?categoryId=${row.id}"><spring:message code="category.edit"/></a>
	</display:column> 
	
</display:table>
<div>
		<a href="category/administrator/create.do"> 
			<spring:message	code="category.create" />
		</a>
	</div>



