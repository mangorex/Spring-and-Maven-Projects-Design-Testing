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

<display:table name="bills" id="row" requestURI="${requestUri}"
	class="displaytag">
	<display:column property="campaign.name" titleKey="bill.campaign.name" />
	<display:column property="cost" titleKey="bill.cost" />
	<display:column property="created" titleKey="bill.created" />
	<display:column property="description" titleKey="bill.description" />
	<display:column titleKey="bill.paid">
		<jstl:out value="${row.paid}"></jstl:out>
		<jstl:if test="${empty row.paid}">
			<a href="bill/sponsor/edit.do?billId=${row.id}"> <spring:message
					code="bill.paid" />
			</a>
		</jstl:if>

	</display:column>
</display:table>