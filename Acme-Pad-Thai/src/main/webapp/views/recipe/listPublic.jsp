<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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

<display:table name="recipes" id="row" requestURI="recipe/listPublic.do"
	class="displaytag">

	<display:column>
		<security:authorize
			access="hasRole('USER') or hasRole('NUTRITIONIST')">
			<a href="recipe/view.do?recipeId=${row.id}&isCustomer=true"> <spring:message
					code="recipe.view" />
			</a>
		</security:authorize>
		<security:authorize
			access="!hasRole('USER') and !hasRole('NUTRITIONIST')">
			<a href="recipe/view.do?recipeId=${row.id}&isCustomer=false"> <spring:message
					code="recipe.view" />
			</a>
		</security:authorize>
	</display:column>

	<display:column titleKey="recipe.category">
		<jstl:forEach items="${row.categories}" var="category">
			<jstl:out value="${category.name}"></jstl:out>
		</jstl:forEach>
	</display:column>

	<display:column titleKey="recipe.ticker">
		<jstl:out value="${row.ticker}" />
	</display:column>

	<display:column property="title" titleKey="recipe.title" />

	<display:column titleKey="recipe.picture">
		<jstl:forEach items="${row.pictures}" var="picture">
			<img src="${picture}" style="width: 50 px; height: 50px" />
		</jstl:forEach>
	</display:column>
</display:table>