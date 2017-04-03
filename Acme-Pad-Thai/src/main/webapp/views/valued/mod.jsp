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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<label>
		<spring:message code="property.name" />:
	</label>
		
	<label>
		<jstl:out value="${property.name}" />
	</label>
	<br />
<form:form action="valued/nutritionist/mod.do" modelAttribute="valued">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ingredient" />
	<form:hidden path="property" />	
	
	<form:label path="value">
		<spring:message code="valued.field" />:
	</form:label>
	<form:input path="value" />
	<form:errors cssClass="error" path="value" />
	<br />
	
	<input type="hidden" name="ingredientId" value="${ingredientId}">
	<input type="submit" name="save2"
		value="<spring:message code="valued.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="valued.cancel" />"
		onclick="javascript: window.location.replace('ingredient/nutritionist/edit.do?ingredientId=${ingredientId}')" />
</form:form>