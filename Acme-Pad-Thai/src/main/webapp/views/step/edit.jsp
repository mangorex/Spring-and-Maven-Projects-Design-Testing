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

<form:form action="step/user/edit.do" modelAttribute="step">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="picture" />

	<form:label path="recipe">
		<spring:message code="contest.qualifieds" />:
	</form:label>
	<form:select path="recipe">
		<form:option label="-----" value="0" />
		<form:options items="${recipes}" itemLabel="title" itemValue="id" />
	</form:select>

	<br />
	<br />

	<form:label path="description">
		<spring:message code="step.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />

	<br />

	<form:label path="hints">
		<spring:message code="step.hints" />:
	</form:label>
	<form:textarea path="hints" />
	<form:errors cssClass="error" path="hints" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="step.save" />" />&nbsp; 
		
	<jstl:if test="${step.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="step.delete" />"
			onclick="return confirm('<spring:message code="step.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="step.cancel" />"
		onclick="javascript: window.location.replace('recipe/user/list.do')" />

</form:form>
