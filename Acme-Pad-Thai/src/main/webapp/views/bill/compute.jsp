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

<form:form method="POST" action="bill/administrator/compute.do">
	<input type="submit" name="saveBill"
		value="<spring:message code="recipe.bill.compute" />" />&nbsp;  
		
	<input type="submit" name="sendBulkMessage"
		value="<spring:message code="recipe.bill.messages" />" />&nbsp; 
</form:form>

<jstl:if test="${correctCompute==true}">
	<spring:message code="bill.compute.correct"></spring:message>
</jstl:if>

<jstl:if test="${correctSend==true}">
	<spring:message code="bill.sendMessage.correct"></spring:message>
</jstl:if>