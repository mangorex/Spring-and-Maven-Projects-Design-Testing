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

<display:table name="socialIdentities" id="row"
	requestURI="socialIdentity/list.do" pagesize="5" class="displaytag">

	<display:column property="nick" titleKey="socialIdentity.nick"
		sortable="true">

	</display:column>

	<display:column titleKey="socialIdentity.link">
		<a href="${row.link }"><spring:message code="socialIdentity.click" /></a>

	</display:column>

	<display:column property="nameOfSocialNetwork"
		titleKey="socialIdentity.nameOfSocialNetwork">

	</display:column>

	<display:column titleKey="socialIdentity.picture">
		<jstl:if test="${!empty row.picture}">
			<img src="${row.picture}" alt="Your social identity picture."
				style="width: 304px; height: 228px;">
		</jstl:if>
	</display:column>




</display:table>

<security:authorize access="isAuthenticated()">
	<div>
		<a href="socialIdentity/create.do"> <spring:message
				code="socialIdentity.create" />
		</a>
	</div>
</security:authorize>
