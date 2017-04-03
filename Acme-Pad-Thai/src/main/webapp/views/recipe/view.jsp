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

<jstl:if test="${banner!='ERROR'}">
	<img src="${banner}" style="width: 500px; height: 250px;">
</jstl:if>
<jstl:if test="${banner=='ERROR'}">
	<spring:message code="recipe.emptyBanner" />
</jstl:if>

<form:form action="recipe/view.do" modelAttribute="recipe">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="comments" />
	<form:hidden path="contests" />
	<form:hidden path="dislikedCustomers" />
	<form:hidden path="likedCustomers" />
	<form:hidden path="steps" />
	<form:hidden path="contests" />
	<form:hidden path="author" />
	<form:hidden path="categories" />
	<form:hidden path="quantifieds" />

	<form:label path="categories">
		<b><spring:message code="recipe.listCategories" /></b>
	</form:label>
	<br />
	<jstl:forEach items="${recipe.categories}" var="category">
		<ul>
			<li><jstl:out value="${category.name}" /></li>
		</ul>
	</jstl:forEach>

	<form:label path="title">
		<spring:message code="recipe.title" />:
	</form:label>
	<form:input path="title" readonly="true" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="ticker">
		<spring:message code="recipe.ticker" />:
	</form:label>
	<form:input path="ticker" readonly="true" />
	<br />

	<jstl:forEach items="${recipe.pictures}" var="picture">
		<img src="${picture}" style="width: 50 px; height: 50px" />
	</jstl:forEach>
	<br />

	<form:label path="author">
		<spring:message code="recipe.author" />:
	</form:label>
	<form:input path="author.name" readonly="true" />
	<a href="actor/create.do?actorId=${recipe.author.id}"> <spring:message
			code="recipe.view.author" />
	</a>
	<br />
	<br />

	<form:label path="creationDate">
		<spring:message code="recipe.creationDate" />
	</form:label>
	<form:input path="creationDate" readonly="true" />
	<br />

	<form:label path="updateDate">
		<spring:message code="recipe.updateDate" />:
	</form:label>
	<form:input path="updateDate" readonly="true" />
	<br />

	<form:label path="hints">
		<spring:message code="recipe.hints" />:
	</form:label>
	<form:textarea path="hints" readonly="true" />
	<form:errors cssClass="error" path="hints" />
	<br />

	<form:label path="summary">
		<spring:message code="recipe.summary" />:
	</form:label>
	<form:textarea path="summary" readonly="true" />
	<form:errors cssClass="error" path="summary" />
	<br />
	<br />

	<form:label path="quantifieds">
		<b><spring:message code="recipe.listIngredients" />:</b>
	</form:label>
	<br />

	<jstl:forEach items="${recipe.quantifieds}" var="quantified">
		<ul>
			<li><jstl:out value="${quantified.ingredient.name}" />: <jstl:if
					test="${!empty quantified.quantityDouble}">

					<jstl:out value="${quantified.quantityDouble}" />
				</jstl:if> <jstl:if test="${!empty quantified.quantityInteger}">

					<jstl:out value="${quantified.quantityInteger}" />
				</jstl:if> <jstl:out value="${quantified.unit}" /></li>
		</ul>
	</jstl:forEach>

	<br />
	<form:label path="steps">
		<b><spring:message code="recipe.listSteps" /></b>
	</form:label>
	<br />
	<jstl:forEach items="${recipe.steps}" var="step">
		<ul>
			<li><jstl:if test="${!empty step.picture}">
					<img src="${step.picture}" style="width: 50 px; height: 50px" />
				</jstl:if> <b><jstl:out value="${step.number}" />:</b> <jstl:out
					value="${step.description}" />. <jstl:out value="${step.hints}" /></li>
		</ul>
	</jstl:forEach>

	<form:label path="comments">
		<b><spring:message code="recipe.comments" /></b>
	</form:label>
	<br />

	<jstl:forEach items="${recipe.comments}" var="comment">
		<spring:message code="recipe.comment.stars"/>:&nbsp;<jstl:out value="${comment.stars}" />
		<br />
		<jstl:out value="${comment.customer.name}" />
		<jstl:out value="${comment.customer.surname}" />
		<br />
		<jstl:out value="${comment.title}" />
		<br />
		<jstl:out value="${comment.text}" />
		<br />
		<br />

	</jstl:forEach>

	<b><spring:message code="recipe.listLikedCustomers" /></b>

	<jstl:forEach items="${recipe.likedCustomers}" var="likedCustomer">
		<a href="actor/create.do?actorId=${likedCustomer.id}"> <jstl:out
				value="${likedCustomer.name}" /> <jstl:out
				value="${likedCustomer.surname}" />
		</a>
		&nbsp; 
	</jstl:forEach>
	<br />
	<b><spring:message code="recipe.listDislikedCustomers" /></b>
	<jstl:forEach items="${recipe.dislikedCustomers}"
		var="dislikedCustomer">
		<a href="actor/create.do?actorId=${likedCustomer.id}"> <jstl:out
				value="${dislikedCustomer.name}" /> <jstl:out
				value="${dislikedCustomer.surname}" />
		</a>
		&nbsp; 
	</jstl:forEach>
	<br />


	<security:authorize access="hasRole('USER') or hasRole('NUTRITIONIST')">
		<input type="submit" name="saveLike"
			value="<spring:message code="recipe.like" />" />
		<input type="submit" name="saveDislike"
			value="<spring:message code="recipe.dislike" />" />
	</security:authorize>
</form:form>

<br />

<security:authorize access="hasRole('USER') or hasRole('NUTRITIONIST')">
	<form:form action="recipe/view.do" modelAttribute="comment">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="customer" />
		<form:hidden path="recipe" />

		<form:label path="stars">
			<spring:message code="recipe.comment.stars" />:
	</form:label>
	<form:select path="stars" modelAttribute="stars">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</form:select>
		<form:errors cssClass="error" path="stars" />
		<br />
		<form:label path="title">
			<spring:message code="recipe.comment.title" />:
	</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		<form:label path="text">
			<spring:message code="recipe.comment.text" />:
	</form:label>
		<form:textarea path="text" />
		<form:errors cssClass="error" path="text" />

		<br />
		<input type="submit" name="saveComment"
			value="<spring:message code="recipe.comment" />" />&nbsp; 
</form:form>
</security:authorize>
