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

<form:form action="creditCard/sponsor/add.do" modelAttribute="creditCard" method="POST">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor" />


		<form:label path="holderName">
			<spring:message code="creditCard.holderName" />:
	</form:label>
		<form:input path="holderName"/>
		<form:errors cssClass="error" path="holderName" />
		<br />

		<form:label path="brandName">
			<spring:message code="creditCard.brandName" />:
	</form:label>
		<form:input path="brandName"  />
		<form:errors cssClass="error" path="brandName" />
		<br />
		
		<form:label path="number">
			<spring:message code="creditCard.number" />:
	</form:label>
		<form:input path="number"  />
		<form:errors cssClass="error" path="number" />
		<br />
		
		<form:label path="expirationMonth">
			<spring:message code="creditCard.expirationMonth" />:
		</form:label>
		<form:select path="expirationMonth">
			<jstl:forEach var="i" begin="1" end="12">
            	<option <jstl:if test="${i==creditCard.expirationMonth}">selected="selected"</jstl:if>     value="${i}">${i} </option>
          	</jstl:forEach>
        </form:select>
        <form:errors cssClass="error" path="expirationMonth" />
        <br />
          
          <form:label path="expirationYear">
			<spring:message code="creditCard.expirationYear" />:
		</form:label>
		<form:select path="expirationYear">
          <jstl:forEach var="i" begin="2016" end="2999">
				<option <jstl:if test="${i==creditCard.expirationYear}">selected="selected"</jstl:if>     value="${i}">${i} </option>

          </jstl:forEach>
          </form:select>
          <form:errors cssClass="error" path="expirationYear" />
          <br />

		<form:label path="codeCVV">
			<spring:message code="creditCard.codeCVV" />:
	</form:label>
	<form:select path="codeCVV">
		<jstl:forEach var="i" begin="100" end="999">
				<option <jstl:if test="${i==creditCard.codeCVV}">selected="selected"</jstl:if>     value="${i}">${i} </option>
          </jstl:forEach>
          </form:select>
		<form:errors cssClass="error" path="codeCVV" />
		<br />
	<br />
		<input type="submit" name="save"
			value="<spring:message code="creditCard.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="creditCard.cancel" />"
		onclick="javascript: window.location.replace('creditCard/sponsor/list.do')" />
	<br />
	
</form:form>