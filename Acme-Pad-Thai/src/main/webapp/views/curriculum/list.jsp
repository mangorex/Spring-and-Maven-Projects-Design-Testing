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

<b><spring:message code="curriculum.photo" /></b>
<br>
<img src="${curriculum.photo}" height="200" width="150">
<br>
<br>
<b><spring:message code="curriculum.educationSection" /></b>
<br>
<jstl:out value="${curriculum.educationSection}" />
<br>
<br>
<b><spring:message code="curriculum.experienceSection" /></b>
<br>
<jstl:out value="${curriculum.experienceSection}" />
<br>
<br>
<b><spring:message code="curriculum.hobbySection" /></b>
<br>
<jstl:out value="${curriculum.hobbySection}" />
<br>
<br>
<b><spring:message code="curriculum.endorsers" /></b>
<br>

<display:table name="endorsers" id="row"
	requestURI="curriculum/nutritionist/list.do" pagesize="5"
	class="displaytag">
	<display:column property="name" titleKey="endorser.name"
		sortable="true" />
	<display:column property="homepage" titleKey="endorser.homepage" />
</display:table>

<br>

<jstl:if test="${curriculum != null}">
	<a href="curriculum/nutritionist/edit.do?curriculumId=${curriculum.id}"><spring:message
			code="curriculum.edit" /></a>
</jstl:if>

