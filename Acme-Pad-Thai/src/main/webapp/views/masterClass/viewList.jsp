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


<form:form action="masterClass/viewList.do"
	modelAttribute="masterClassViews" method="POST">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="promoted" />

	<form:hidden path="cook" />
	<form:hidden path="learningMaterials" />
	<form:hidden path="actors" />
	<form:hidden path="title" />
	<form:hidden path="description" />
	<form:hidden path="promoted" />

	<b><spring:message code="masterClass.title" /></b>
	<br>
	<jstl:out value="${masterClassViews.title}" />
	<br>
	<br>
	<b><spring:message code="masterClass.description" /></b>
	<br>
	<jstl:out value="${masterClassViews.description}" />
	<br>
	<br>
	<b><spring:message code="masterClass.cook" /></b>
	<br>
	<jstl:out value="${masterClassViews.cook.name}" />
	<br>
	<br>
	<b><spring:message code="masterClass.promoted" /></b>
	<br>
	<jstl:out value="${masterClassViews.promoted}" />
	<br>
	<br>


	<jstl:if test="${isnotregistered == true && isnotcook==true}">


		<input type="submit" name="register"
			value="<spring:message code="masterClass.register" />" />&nbsp; 
		
		 </jstl:if>
	<jstl:if test="${isnotregistered == false && isnotcook==true}">


		<input type="submit" name="unregister"
			value="<spring:message code="masterClass.unregister" />"
			onclick="return confirm('<spring:message code="masterClass.confirm.unregister" />')" />&nbsp;
	
	 
	 </jstl:if>
</form:form>




