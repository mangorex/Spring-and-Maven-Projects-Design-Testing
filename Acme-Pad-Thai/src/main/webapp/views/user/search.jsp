<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form action="user/search.do?keyword=">
	<spring:message code="actor.search.requirement" /><br>
	<spring:message code="actor.insert.keyword" />
	<input type="text" name="keyword"><br>
	<input type="submit" value="submit">
</form>

<display:table name="users" id="row" requestURI="${requestUri}" class="displaytag">
	<display:column>
		<a href="actor/create.do?actorId=${row.id}"><spring:message
				code="user.view" /></a>
	</display:column>
	<display:column property="name" titleKey="actor.name"/>
	<display:column property="surname" titleKey="actor.surname"/>
	<display:column property="emailAddress" titleKey="actor.emailAddress"/>
	<display:column property="phoneNumber" titleKey="actor.phoneNumber"/>
	<display:column property="postalAddress" titleKey="actor.postalAddress"/>
</display:table>

