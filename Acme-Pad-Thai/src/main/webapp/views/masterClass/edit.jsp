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
	<form:form action="masterClass/cook/edit.do"
		modelAttribute="masterClass" method="POST">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="promoted" />
		<form:hidden path="cook" />
		<form:hidden path="learningMaterials" />
		<form:hidden path="actors" />


		<form:label path="title">
			<spring:message code="masterClass.title" />
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<form:label path="description">
			<spring:message code="masterClass.description" />
		</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />


		<br />

		<input type="submit" name="save"
			value="<spring:message code="masterClass.save" />" />&nbsp; 
		
	<jstl:if test="${masterClass.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="masterClass.delete" />"
				onclick="return confirm('<spring:message code="masterClass.confirm.delete" />')" />&nbsp;
	</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="category.cancel" />"
			onclick="javascript: window.location.replace('masterClass/cook/list.do')" />

	</form:form>

</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<form:form action="masterClass/edit.do" modelAttribute="masterClass"
		method="POST">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="promoted" />
		<form:hidden path="cook" />
		<form:hidden path="learningMaterials" />
		<form:hidden path="actors" />
		<form:label path="title">
			<spring:message code="masterClass.title" />
		</form:label>
		<form:input path="title" readonly="true" />
		<form:errors cssClass="error" path="title" />
		<br />
		<form:label path="description">
			<spring:message code="masterClass.description" />
		</form:label>
		<form:textarea path="description" readonly="true" />
		<form:errors cssClass="error" path="description" />
		<br />
		<jstl:if test="${masterClass.promoted==false}">
			<input type="submit" name="promote"
				value="<spring:message code="masterClass.promote" />" />&nbsp; 
	</jstl:if>

		<jstl:if test="${masterClass.promoted==true}">
			<input type="submit" name="demote"
				value="<spring:message code="masterClass.demote" />" />&nbsp; 
	</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="masterClass.cancel" />"
			onclick="javascript: window.location.replace('../masterClass/list.do')" />
		<br />
	</form:form>
</security:authorize>






