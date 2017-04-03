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
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
	String currentDate = ft.format(dNow);
%>

<form:form action="bill/sponsor/edit.do" modelAttribute="bill">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="campaign" />
	<form:hidden path="description" />
	<form:hidden path="sponsor" />
	<form:hidden path="cost" />
	<form:hidden path="created" />
	<form:hidden path="paid" />

	<spring:message code="bill.insertPaid" />
	<br />
	<spring:message code="bill.cancelPaid" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="bill.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="bill.cancel" />"
		onclick="javascript: window.location.replace('bill/sponsor/browse.do')" />
</form:form>
