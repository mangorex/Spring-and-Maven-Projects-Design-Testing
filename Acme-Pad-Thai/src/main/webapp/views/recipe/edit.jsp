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

<form:form action="recipe/user/edit.do" modelAttribute="recipe">
	<form:hidden path="id" />
	<form:hidden path="author" />
	<form:hidden path="version" />
	<form:hidden path="comments" />
	<form:hidden path="contests" />
	<form:hidden path="dislikedCustomers" />
	<form:hidden path="likedCustomers" />
	<form:hidden path="quantifieds" />
	<form:hidden path="steps" />
	<form:hidden path="creationDate" />
	<form:hidden path="updateDate" />

	<form:label path="author">
		<spring:message code="recipe.author" />:
	</form:label>
	<form:input path="author.name" readonly="true" />
	<br />

	<form:label path="ticker">
		<spring:message code="recipe.ticker" />:
	</form:label>
	<form:input path="ticker" readonly="true" />

	<br />

	<form:label path="title">
		<spring:message code="recipe.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="categories">
		<spring:message code="recipe.category" />:
	</form:label>
	<form:select path="categories" name="category" id="category"
		multiple="true">
		<jstl:forEach items="${categories}" var="category">
			<jstl:choose>
				<jstl:when test="${categoriesSelected.contains(category)}">
					<form:option label="${category.name}" value="${category.id}"
						selected="true"></form:option>
				</jstl:when>
				<jstl:otherwise>
					<form:option label="${category.name}" value="${category.id}"></form:option>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</form:select>

	<br />
	<form:label path="pictures">
		<spring:message code="recipe.step.picture" />:
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />


	<br />

	<form:label path="hints">
		<spring:message code="recipe.hints" />:
	</form:label>
	<form:textarea path="hints" />
	<form:errors cssClass="error" path="hints" />
	<br />

	<form:label path="summary">
		<spring:message code="recipe.summary" />:
	</form:label>
	<form:textarea path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />
	<br />
	<input type="submit" name="save"
		value="<spring:message code="recipe.save" />" />&nbsp; 
	
	<jstl:if test="${recipe.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="recipe.delete" />"
			onclick="return confirm('<spring:message code="recipe.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="recipe.cancel" />"
		onclick="javascript: window.location.replace('recipe/user/list.do')" />
	<br />
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
					value="${step.description}" />. <jstl:out value="${step.hints}" />
				<a
				href="recipe/user/edit.do?recipeId=${recipe.id}&stepId=${step.id}&quantifiedId=0"><spring:message
						code="recipe.edit" /></a></li>
		</ul>
	</jstl:forEach>

	<form:label path="quantifieds">
		<b><spring:message code="recipe.listIngredients" /></b>
	</form:label>
	<br />

	<jstl:forEach items="${recipe.quantifieds}" var="quantified">
		<ul>
			<li><jstl:out value="${quantified.ingredient.name}" />: <jstl:if
					test="${!empty quantified.quantityDouble and quantified.quantityDouble!=0.0}">

					<jstl:out value="${quantified.quantityDouble}" />
				</jstl:if> <jstl:if test="${!empty quantified.quantityInteger and quantified.quantityInteger!=0}">

					<jstl:out value="${quantified.quantityInteger}" />
				</jstl:if> <jstl:out value="${quantified.unit}" /> <a
				href="recipe/user/edit.do?recipeId=${recipe.id}&stepId=0&quantifiedId=${quantified.id}"><spring:message
						code="recipe.edit" /></a></li>
		</ul>
	</jstl:forEach>
</form:form>

<jstl:if test='${invisible != true}'>
	<form:form action="recipe/user/edit.do" modelAttribute="step">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="number" />
		<form:hidden path="recipe" />

		<br />
		<form:label path="description">
			<spring:message code="recipe.step.description" />:
	</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
		<br />

		<form:label path="hints">
			<spring:message code="recipe.step.hints" />:
	</form:label>
		<form:textarea path="hints" />
		<form:errors cssClass="error" path="hints" />
		<br />

		<form:label path="picture">
			<spring:message code="recipe.step.picture" />:
	</form:label>
		<form:input path="picture" />
		<form:errors cssClass="error" path="picture" />
		<br />

		<input type="submit" name="saveStep"
			value="<spring:message code="recipe.step.save" />" />&nbsp; 

			<jstl:if test="${step.id != 0}">
			<input type="submit" name="deleteStep"
				value="<spring:message code="recipe.step.delete" />"
				onclick="return confirm('<spring:message code="step.confirm.delete" />')" />&nbsp;
			</jstl:if>
		<input type="button" name="cancelStep"
			value="<spring:message code="recipe.step.cancel" />"
			onclick="javascript: window.location.replace('recipe/user/edit.do?recipeId=${step.recipe.id}&stepId=0&quantifiedId=0')" />
	</form:form>

	<br />
	<br />

	<form:form action="recipe/user/edit.do" modelAttribute="quantified">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="recipe" />
		<form:hidden path="quantityInteger" />
		
		<form:label path="ingredient">
			<spring:message code="recipe.quantified.ingredient" />:
		</form:label>
		<form:select path="ingredient" modelAttribute="ingredient"
			multiple="false">
			<form:option label="-----" value="0" />
			<form:options items="${ingredients}" itemLabel="name" itemValue="id" />
		</form:select>

		<br />

		<form:label path="quantityDouble">
			<spring:message code="recipe.quantified.quantity.Double" />:
	</form:label>
		<form:input path="quantityDouble" />
		<form:errors cssClass="error" path="quantityDouble" />

		<form:label path="unit">
			<spring:message code="quantified.unit" />:
	</form:label>
		<form:select path="unit" modelAttribute="unit">
			<option value="grams">grams</option>
			<option value="kilo-grams">kilo-grams</option>
			<option value="ounces">ounces</option>
			<option value="pounds">pounds</option>
			<option value="millilitres">millilitres</option>
			<option value="litres">litres</option>
			<option value="spoons">spoons</option>
			<option value="cups">cups</option>
			<option value="pieces">pieces</option>
		</form:select>

		<br />
		<br />
		<br />
		<input type="submit" name="saveQuantified"
			value="<spring:message code="recipe.quantified.save" />" />&nbsp; 
	<jstl:if test="${quantified.id != 0}">
			<input type="submit" name="deleteQuantified"
				value="<spring:message code="recipe.quantified.delete" />"
				onclick="return confirm('<spring:message code="quantified.confirm.delete" />')" />&nbsp;
	</jstl:if>
		<input type="button" name="cancelQuantified"
			value="<spring:message code="recipe.quantified.cancel" />"
			onclick="javascript: window.location.replace('recipe/user/edit.do?recipeId=${step.recipe.id}&stepId=0&quantifiedId=0')" />

	</form:form>
</jstl:if>


