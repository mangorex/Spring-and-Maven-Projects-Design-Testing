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

<form:form action="message/actor/send.do" modelAttribute="message" method="POST">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folder" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />

	<jstl:if test="${create == null}">
		<form:label path="sender">
			<spring:message code="message.sender" />:
	</form:label>
		<form:input path="sender"  disabled="true" />
		<br />
	</jstl:if>


	<form:label path="recipient">
		<spring:message code="message.recipient" />:
	</form:label>
	<jstl:if test="${create == null}">
		<form:input disabled="true" path="recipient" />
	</jstl:if>
	<jstl:if test="${create==true}">
		<input name="recipient" value='<jstl:out value="${message.recipient}" />' />
	</jstl:if>
	<form:errors cssClass="error" path="recipient" />
	<br />

	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	
	
	<jstl:if test="${message.id != 0  && !create}">
	<form:select id="priority" path="priority"  disabled="true">
		<jstl:if test="${message.priority != 'HIGH'}">
		<option value="HIGH">HIGH</option>
		</jstl:if>
		<jstl:if test="${message.priority == 'HIGH'}">
		<option value="HIGH" selected="selected">HIGH</option>
		</jstl:if>
		<jstl:if test="${message.priority != 'NEUTRAL'}">
		<option value="NEUTRAL">NEUTRAL</option>
		</jstl:if>
		<jstl:if test="${message.priority == 'NEUTRAL'}">
		<option value="NEUTRAL" selected="selected">NEUTRAL</option>
		</jstl:if>
		<jstl:if test="${message.priority != 'LOW'}">
		<option value="LOW">LOW</option>
		</jstl:if>
		<jstl:if test="${message.priority == 'LOW'}">
		<option value="LOW" selected="selected">LOW</option>
		</jstl:if>
	</form:select>
	</jstl:if>
	<jstl:if test="${message.id == 0 || create}">
	<form:select id="priority" path="priority">
		<option value="HIGH">HIGH</option>
		<option value="NEUTRAL" selected="selected">NEUTRAL</option>
		<option value="LOW">LOW</option>
	</form:select>
	</jstl:if>
	<br />

	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<jstl:if test="${message.id != 0 && !create}">
		<form:input disabled="true" path="subject" />
	</jstl:if>
	<jstl:if test="${message.id == 0 || create}">
		<input name="subject" value='<jstl:out value="${message.subject}" />' />
	</jstl:if>
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<jstl:if test="${message.id != 0 && !create}">
		<form:textarea disabled="true" path="body" />
	</jstl:if>
	<jstl:if test="${message.id == 0 || create}">
		<textarea name="body" ><jstl:out value="${message.body}" /></textarea>
	</jstl:if>
	<form:errors cssClass="error" path="body" />
	<br />


	<jstl:if test="${message.id == 0 || create}">
		<input type="submit" name="save"
			value="<spring:message code="message.send" />" />&nbsp; 
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: window.location.replace('message/actor/list.do')" />
	<br />
	
</form:form>