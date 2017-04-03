<%--
 * edit.jsp
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

<form:form method="POST" action="recipe/user/qualify.do"
	modelAttribute="contestRecipe">
	
	<form:hidden path="recips" />
	
	<form:label path="ticker">
		<spring:message code="recipe.ticker" />:
	</form:label>
	<form:input path="ticker" readonly="true"/>
	<br/>
	
	<b><spring:message code="recipe.listLikedCustomers" /></b>
	<form:input path="likes" readonly="true"/>
	<br/>
	<b><spring:message code="recipe.listDislikedCustomers" /></b>
	<form:input path="dislikes" readonly="true"/>
	<br/>
	
	<form:select id="contsDropdown" path="conts">
		<form:option label="-----" value="0" />
		<form:options items="${contests}" itemLabel="title" itemValue="id" />
	</form:select>

	<input type="submit" name="savedQualified"
		value="<spring:message code="recipe.qualify" />" />&nbsp;
</form:form>

