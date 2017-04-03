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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="spam.words" />: <jstl:out value="${spamkeywords}" />
<br />
<br />
<spring:message code="spam.other" />:
<br />
<display:table name="words" id="row" requestURI="spam/administrator/manage.do" class="displaytag" pagesize="5">
				
		<display:column titleKey="spam.words">
		<jstl:out value="${row}" />
		<form:form method="POST" modelAttribute="folderList" action="spam/administrator/manage.do">
			<input type="hidden" name="fols" value="${row}" />
			<input type="submit" name="delete"
			value="<spring:message code="spam.delete" />"
			onclick="return confirm('<spring:message code="spam.confirm.delete" />')" />
		</form:form>
		</display:column>
	
	
</display:table>
<br />
<br />
<div>
	<form:form method="POST" action="spam/administrator/manage.do" modelAttribute="folderList">
		<form:input path="fols" /> <input type="submit" name="save" value="<spring:message code="spam.save" />"/>
	</form:form>
</div>
