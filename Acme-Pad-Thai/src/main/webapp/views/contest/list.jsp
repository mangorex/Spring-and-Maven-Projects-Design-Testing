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


<display:table name="contests" id="row" requestURI="${requestUri}" pagesize="5"
	class="displaytag">



	


	<display:column property="title" titleKey="contest.title">
	</display:column>


	<display:column property="opening" titleKey="contest.openingDate"
		format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>

	<display:column property="closing" titleKey="contest.closingDate"
		format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>

	<display:column titleKey="contest.recipes">
		
		<jstl:forEach items="${row.recipes}" var="recipe">
			<security:authorize access="hasRole('USER') or hasRole('NUTRITIONIST')">
				<a href="recipe/view.do?recipeId=${recipe.id}&isCustomer=true">
					<jstl:out value="${recipe.title}" />
				</a>
			</security:authorize>
			<security:authorize access="!hasRole('USER') and !hasRole('NUTRITIONIST')">
				<a href="recipe/view.do?recipeId=${recipe.id}&isCustomer=false">
					<jstl:out value="${recipe.title}" />
				</a>
			</security:authorize>

		</jstl:forEach>

	</display:column>


	<display:column titleKey="contest.winners">
		<jstl:forEach items="${row.winners}" var="recipe">
			<security:authorize access="hasRole('USER') or hasRole('NUTRITIONIST')">
				<a href="recipe/view.do?recipeId=${recipe.id}&isCustomer=true">
					<jstl:out value="${recipe.title}" />
				</a>
			</security:authorize>
			<security:authorize access="!hasRole('USER') and !hasRole('NUTRITIONIST')">
				<a href="recipe/view.do?recipeId=${recipe.id}&isCustomer=false">
					<jstl:out value="${recipe.title}" />
				</a>
			</security:authorize>

		</jstl:forEach>
	</display:column>


<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="contest/administrator/edit.do?contestId=${row.id}"><spring:message
					code="contest.edit" /></a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	
		<a href="contest/administrator/create.do"><spring:message
				code="contest.create" /></a>
	
</security:authorize>



