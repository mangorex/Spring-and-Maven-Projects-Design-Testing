<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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

<security:authorize access="hasRole('COOK')">
<display:table name="masterClasses" id="row"
	requestURI="/masterClass/cook/list.do" pagesize="5" class="displaytag">
	
	<display:column property="title" titleKey="masterClass.title" sortable="${true}">
	</display:column>

	<display:column property="description" titleKey="masterClass.description">
	</display:column>

	
	<display:column property = "cook.name" titleKey="masterClass.cook">
		
	</display:column>
	<display:column titleKey="masterClass.content">
			<a href="learningMaterial/list.do?masterClassId=${row.id}"><spring:message
					code="masterClass.content" /></a>
		</display:column>
	<display:column>
			<a href="masterClass/edit.do?masterClassId=${row.id}"><spring:message
					code="masterClass.edit" /></a>
		</display:column>
	
	<display:column titleKey="masterClass.learningMaterial">
	<form:form method="POST" action="masterClass/cook/learningMaterial.do">
			<input type="hidden" name="masterClass" value="${row.id}" />
			<input type="submit" name="edit"
			value="<spring:message code="masterClass.edit" />"/>&nbsp;
	</form:form>
	</display:column>
	
	</display:table>

		<a href="masterClass/cook/create.do"><spring:message
				code="masterClass.create" /></a>
	</security:authorize>




