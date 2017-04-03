<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="endorsers" id="row" requestURI="endorser/nutritionist/list.do" pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
			<a href="endorser/nutritionist/edit.do?endorserId=${row.id}"><spring:message code="endorser.edit" /></a>
		</display:column>			
	</security:authorize>
		
	<display:column property="name" titleKey="endorser.name" sortable="true" />
	<display:column property="homepage" titleKey="endorser.homepage" />		
	
</display:table>
