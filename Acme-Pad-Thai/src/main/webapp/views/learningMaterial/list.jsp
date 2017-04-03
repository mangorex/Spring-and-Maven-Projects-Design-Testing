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


<display:table name="learningMaterials" id="row"
	requestURI="/learningMaterial/list.do" pagesize="5" class="displaytag">

	

	<security:authorize access="isAuthenticated()">
	
	<display:column property="title" titleKey="learningMaterial.title" sortable="${true}">
	</display:column>

	<display:column property="summary" titleKey="learningMaterial.summary">
	</display:column>

	
	<display:column property ="attachments" titleKey="learningMaterial.attachment">
		
	</display:column>
	
</security:authorize>
</display:table>

	

 
		
		 

<div>
	<security:authorize access="hasRole('COOK')">	
		<a href="masterClass/cook/learningMaterial.do?masterClassId=${masterClassId}"> 
			<spring:message	code="learningMaterial.add" />
		</a>
		</security:authorize>
	</div>



