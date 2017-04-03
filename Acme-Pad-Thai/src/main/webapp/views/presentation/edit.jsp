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
	<form:form action="presentation/edit.do"
		modelAttribute="presentation" method="POST">
		
		<form:hidden path="masterClasses" />
		
	
		<form:label path="title">
			<spring:message code="presentation.title" />
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<form:label path="summary">
			<spring:message code="presentation.summary" />
		</form:label>
		<form:input path="summary" />
		<form:errors cssClass="error" path="summary" />
		<br />
		<form:label path="attachments">
			<spring:message code="presentation.attachments" />
		</form:label>
		<form:input path="attachments" />
		<form:errors cssClass="error" path="attachments" />
		<br/>
		<form:label path="path">
			<spring:message code="presentation.path" />
		</form:label>
		<form:input path="path" />
		<form:errors cssClass="error" path="path" />
		<br/>
		<input type="submit" name="save"
			value="<spring:message code="presentation.save" />" />&nbsp; 
		
	<jstl:if test="${presentation.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="presentation.delete" />"
				onclick="return confirm('<spring:message code="presentation.confirm.delete" />')" />&nbsp;
	</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="presentation.cancel" />"
			onclick="javascript: window.location.replace('masterClass/cook/learningMaterial.do')" />

	</form:form>

</security:authorize>

