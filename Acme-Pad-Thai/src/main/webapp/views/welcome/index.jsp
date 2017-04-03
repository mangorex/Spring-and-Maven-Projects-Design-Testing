<%--
 * index.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${banner!='ERROR'}">
	<img src="${banner}" style="width:500px; height:250px;">
</jstl:if>
<jstl:if test="${banner=='ERROR'}">
	<spring:message code="welcome.emptyBanner" />
</jstl:if>

<h2><spring:message code="welcome.masterclass.promoted" /></h2>
<table id="mc">
	<tr>
		<td><h3><spring:message code="welcome.masterclass.title" /></h3></td>
		<td><h3><spring:message code="welcome.masterclass.cook" /></h3></td>
	</tr>
	<jstl:forEach var="val" items="${masterClasses}">
		<tr>
			<td> <jstl:out value="${val.title}" /> </td>
			<td> <jstl:out value="${val.cook.name}" /> </td>
		</tr>
	</jstl:forEach>
</table>

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 
