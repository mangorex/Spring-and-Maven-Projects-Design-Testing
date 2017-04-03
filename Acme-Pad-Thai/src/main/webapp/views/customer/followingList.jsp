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

<display:table name="customers" id="row"
	requestURI="customer/followingList.do" pagesize="5" class="displaytag">

	<display:column property="name" titleKey="follower.name"
		sortable="true">

	</display:column>

	<display:column property="surname" titleKey="follower.surname">

	</display:column>

	<display:column property="emailAddress"
		titleKey="follower.emailAddress">

	</display:column>

	<display:column >
		<a href="actor/createLogged.do?actorId=${row.id}"><spring:message code="follower.go" /></a>
	</display:column>

</display:table>
