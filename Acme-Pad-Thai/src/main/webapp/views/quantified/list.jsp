<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="quantifieds" id="row" requestURI="quantified/user/list.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="quantified.ingredient.name">
	<jstl:out value="${row.ingredient.name}" />
	</display:column>
	
	<display:column property="quantityDouble" titleKey="quantified.quantityDouble" />
	<display:column property="quantityInteger" titleKey="quantified.quantityInteger" />	
	<display:column property="unit" titleKey="quantified.unit" />

	<display:column titleKey="quantified.recipe.title">
	<jstl:out value="${row.recipe.title}" />
	</display:column>
	
	<display:column>
		<a href="quantified/user/commit.do?quantifiedId=${row.id}"><spring:message code="quantified.edit" /></a>
	</display:column>
</display:table>

	<a href="quantified/user/create.do"> 
		<spring:message	code="quantified.create" />
	</a>