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


<spring:message code="masterClass.learningMaterial" />:

<form action="masterClass/cook/learningMaterial.do" method="POST">
<input type="hidden" id="masterClass" name="masterClass" value="${masterClass}" />

<select name="learningMaterial" id="learningMaterial" multiple>
			<jstl:if test="${size>0}">
			<jstl:forEach var="i" begin="0" end="${size}">
					<jstl:if test="${selected[i]}">
          			<option value="${learningMaterials[i].id}"  selected ><jstl:out value="${learningMaterials[i].title}"/></option>
          			</jstl:if>
          			<jstl:if test="${!selected[i]}">
          			<option value="${learningMaterials[i].id}"><jstl:out value="${learningMaterials[i].title}"/></option>
          			</jstl:if>
          	</jstl:forEach>
          	</jstl:if>
</select>
<br />

<input type="submit" name="lm" value="<spring:message code="masterClass.save" />">
<input type="button" name="cancel"
		value="<spring:message code="bill.cancel" />"
		onclick="javascript: window.location.replace('masterClass/cook/list.do')" />
		<div>	<a href="learningMaterial/create.do"> 
			<spring:message	code="learningMaterial.create" />
		</a></div>
</form>


