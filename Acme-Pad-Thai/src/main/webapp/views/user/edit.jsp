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

<form:form action="user/edit.do" modelAttribute="user">
	<!-- Para todos -->

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.Authorities" />

	<!-- Customer -->

	<form:hidden path="likedRecipes" />
	<form:hidden path="dislikedRecipes" />
	<form:hidden path="comments" />
	<form:hidden path="following" />
	<form:hidden path="followers" />

	<!-- User -->

	<form:hidden path="authoredRecipes" />

	<!-- Actor -->

	<form:hidden path="folders" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="masterClasses" />


	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="emailAddress">
		<spring:message code="actor.emailAddress" />:
	</form:label>
	<form:input path="emailAddress" />
	<form:errors cssClass="error" path="emailAddress" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="actor.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />

	<form:label path="postalAddress">
		<spring:message code="actor.postalAddress" />:
	</form:label>
	<form:input path="postalAddress" />
	<form:errors cssClass="error" path="postalAddress" />
	<br />

	<form:hidden path="userAccount" />

	<input type="submit" name="save"
		value="<spring:message code="actor.save" />" />&nbsp; 

	<br />

	<a href="socialIdentity/list.do"><spring:message code="actor.socialIdentities" /></a>

</form:form>
