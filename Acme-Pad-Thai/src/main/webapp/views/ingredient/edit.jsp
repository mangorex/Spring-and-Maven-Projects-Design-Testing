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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="ingredient/nutritionist/edit.do" modelAttribute="ingredient">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="quantifieds"/>
	<form:hidden path="valueds"/>
	
	<form:label path="name">
		<spring:message code="ingredient.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="description">
		<spring:message code="ingredient.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="picture">
		<spring:message code="ingredient.picture" />:
		<img src="${ingredient.picture}" height="150" width="150"/>
	</form:label>
	<br />
	<form:input path="picture"  />
	<form:errors cssClass="error" path="picture" />
	<br />
	
	<jstl:if test="${ingredient.id == 0 }">
		<input type="submit" name="save2"
			value="<spring:message code="ingredient.save" />" />&nbsp; 
	</jstl:if>
	<jstl:if test="${ingredient.id != 0}">
		<input type="submit" name="save"
		value="<spring:message code="ingredient.save" />" />&nbsp; 
		<input type="submit" name="delete"
			value="<spring:message code="ingredient.delete" />"
			onclick="return confirm('<spring:message code="ingredient.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="ingredient.cancel" />"
		onclick="javascript: window.location.replace('ingredient/nutritionist/list.do')" />
</form:form>

<jstl:if test="${hayProperties != 0}">
	<display:table name="Properties" id="row" requestURI="ingredient/nutritionist/edit.do" pagesize="5" class="displaytag">
		<display:column property="name" titleKey="ingredient.property.name" />
		<display:column>
			<form:form method="POST" action="valued/nutritionist/mod.do">
				<input type="hidden" name="valuedId" value="${row.id}" />
				<input type="hidden" name="ingredientId" value="${ingredient.id}" />
				<input type="submit" name="mod" value="<spring:message code="ingredient.valued.edit" />"/>
			</form:form>
		</display:column>
		<display:column>
			<form:form method="POST" action="valued/nutritionist/delete.do">
				<input type="hidden" name="valuedId" value="${row.id}" />
				<input type="hidden" name="ingredientId" value="${ingredient.id}" />
				<input type="submit" name="delete"
				value="<spring:message code="ingredient.valued.delete" />"
				onclick="return confirm('<spring:message code="ingredient.valued.confirm.delete" />')" />&nbsp;
			</form:form>
		</display:column>
	</display:table>
</jstl:if>

<jstl:if test="${ingredient.id != 0 }">
	<a href="valued/nutritionist/create.do?ingredientId=${ingredient.id}"> 
		<spring:message	code="ingredient.valued.create" />
	</a>
</jstl:if>

