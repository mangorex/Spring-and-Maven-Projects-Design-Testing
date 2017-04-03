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

<jstl:set var="isCustomer" value="false"/>
<security:authorize access="hasRole('USER') or hasRole('NUTRITIONIST')">
	<jstl:set var="isCustomer" value="true" />
</security:authorize>

<form action="recipe/search.do?keyword=">
	<spring:message code="recipe.search.requirement" /> <br>
	<spring:message code="recipe.insert.keyword" />
	<input type="text" name="keyword"><br> <input
		type="submit" value="<spring:message code="recipe.search" />">
		
</form>

<display:table name="recipes" id="row" requestURI="${requestUri}"
	class="displaytag">
	
	<display:column>
		<a href="recipe/view.do?recipeId=${row.id}&isCustomer=${isCustomer}"><spring:message
				code="recipe.view" /></a>
	</display:column>
	
	<display:column titleKey="recipe.category">
		<jstl:forEach items="${row.categories}" var="category">
			<jstl:out value="${category.name}"></jstl:out>
		</jstl:forEach>
	</display:column>

	<display:column property="ticker" titleKey="recipe.ticker" />
	
	<display:column property="title" titleKey="recipe.title" />
	<display:column property="summary" titleKey="recipe.summary" />

	<display:column titleKey="recipe.author">
		<jstl:out value="${row.author.name}"></jstl:out>
	</display:column>

	<display:column titleKey="recipe.picture">
		<jstl:forEach items="${row.pictures}" var="picture">
			<img src="${picture}" style="width: 50 px; height: 50px" />
		</jstl:forEach>
	</display:column>
</display:table>

