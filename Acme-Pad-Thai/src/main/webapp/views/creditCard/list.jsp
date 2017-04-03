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



<display:table name="creditCards" id="row" requestURI="creditCard/sponsor/list.do" class="displaytag" pagesize="5">

		<display:column>
			<a href="creditCard/sponsor/edit.do?id=${row.id}"><spring:message code="creditCard.edit" /></a>
		</display:column>			
		<display:column titleKey="creditCard.holderName" sortable="true">
		${row.holderName}
		</display:column>
		<display:column titleKey="creditCard.brandName" sortable="true">
		${row.brandName}
		</display:column>
		<display:column titleKey="creditCard.number" sortable="true">
		${row.number}
		</display:column>
		<display:column titleKey="creditCard.expirationMonth" sortable="true">
		${row.expirationMonth}
		</display:column>
		<display:column titleKey="creditCard.expirationYear" sortable="true">
		${row.expirationYear}
		</display:column>
		<display:column titleKey="creditCard.codeCVV">
		${row.codeCVV}
		</display:column>
	
	
	<display:column>
	<form:form method="POST" action="creditCard/sponsor/delete.do">
			<input type="hidden" name="creditCard" value="${row.id}" />
			<input type="submit" name="delete"
			value="<spring:message code="creditCard.delete" />"
			onclick="return confirm('<spring:message code="creditCard.confirm.delete" />')" />&nbsp;
	</form:form>
	</display:column>
</display:table>

<div>
	<a href="creditCard/sponsor/add.do"> 
		<spring:message	code="creditCard.add" />
	</a>
</div>
