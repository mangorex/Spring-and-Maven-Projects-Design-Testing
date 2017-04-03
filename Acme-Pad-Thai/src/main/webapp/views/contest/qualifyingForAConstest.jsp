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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('USER')">
<display:table name = "contest" id="row" 
	requestURI="/contest/list.do"
	pagesize="5"
	class = "displaytag">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="summary" />
	<form:hidden path="updateDate" />
	<form:hidden path="pictures" />
	<form:hidden path="hints" />
	<form:hidden path="categories" />
	<form:hidden path="likedCustomers" />
	<form:hidden path="dislikedCustomers" />
	<form:hidden path="author" />
	<form:hidden path="contests" />
	<form:hidden path="steps" />
	<form:hidden path="quantifieds" />
	
		<spring:message code="recipe.title" var="recipeTitle"/>
		<a href="recipe/actor/findRecipe.do?recipeId=${row.id}">
			<display:column property="title" 
					title="${recipeTitle}">	
			</display:column>
		</a>

	
		<spring:message code="recipe.ticker" var="recipeTicker"/>
		<display:column property="ticker" 
				title="${recipeTicker}" 
				sortable="${true}">
		</display:column>
		
		<spring:message code="recipe.creationDate" var="creationDate"/>
		<display:column property="creationDate" 
				title="${creationDate}" 
				sortable="${true}"
				format="{0,date,dd/MM/yyyy HH:mm}">
		</display:column>
		
		<spring:message code="contest.active" var="activeContest"/>
		<display:column property="activeContest" 
				title="${activeContest}">
			<form:select id="contests" path="contest">
				<form:option value="0" label="----" />		
				<form:options items="${contest}" itemValue="id" itemLabel="title" />
			</form:select>
			<form:errors cssClass="error" path="contest" />
		</display:column>
		
		<display:column>
			<a href="contest/user/qualifyRecipe.do?recipeId=${row.id}&contestId=${contest.id}"><spring:message code="recipe.register"/></a>
		<form:errors cssClass="error" path="findrecipe" />
		</display:column>
	</display:table>
</security:authorize>



