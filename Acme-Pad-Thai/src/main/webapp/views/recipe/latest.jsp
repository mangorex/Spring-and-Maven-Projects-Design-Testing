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

<display:table name="recipes" id="row"
	requestURI="recipe/customer/latest.do" class="displaytag">

	<display:column>
		<a href="recipe/view.do?recipeId=${row.id}&isCustomer=true"><spring:message
				code="recipe.view" /></a>
	</display:column>

	<display:column property="ticker" titleKey="recipe.ticker" />
	<display:column property="title" titleKey="recipe.title" />

	<display:column titleKey="recipe.author">
		<a href="actor/create.do?actorId=${row.author.id}"><jstl:out
				value="${row.author.name}" /> <jstl:out
				value="${row.author.surname}" /></a>
	</display:column>

	<display:column property="creationDate" titleKey="recipe.creationDate" />

</display:table>