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

<form:form action="actor/edit.do" modelAttribute="actor">
	<!-- Para todos -->

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.Authorities" />



	<form:hidden path="folders" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="masterClasses" />

	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" readonly="true" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" readonly="true" />
	<br />

	<form:label path="emailAddress">
		<spring:message code="actor.emailAddress" />:
	</form:label>
	<form:input path="emailAddress" readonly="true" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="actor.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" readonly="true" />
	<br />

	<form:label path="postalAddress">
		<spring:message code="actor.postalAddress" />:
	</form:label>
	<form:input path="postalAddress" readonly="true" />
	<br />
	<jstl:forEach var="ua" items="${actor.userAccount.authorities }">
		<jstl:if test="${ua.authority=='SPONSOR' }">
			<form:hidden path="campaigns" />
			<form:hidden path="creditCards" />
			<form:hidden path="bills" />


			<form:label path="companyName">
				<spring:message code="actor.companyName" />:
	</form:label>
			<form:input path="companyName" readonly="true" />
		</jstl:if>
	</jstl:forEach>

	<jstl:forEach var="ua" items="${actor.userAccount.authorities }">
		<jstl:if test="${ua.authority=='ADMINISTRATOR' }">

			<form:hidden path="contests" />
			<form:hidden path="promotedMasterClasses" />
			<form:hidden path="categories" />
			<form:hidden path="cooks" />
		</jstl:if>
	</jstl:forEach>
	<jstl:forEach var="ua" items="${actor.userAccount.authorities }">
		<jstl:if test="${ua.authority=='COOK' }">
			<form:hidden path="managedMasterClasses" />
		</jstl:if>
	</jstl:forEach>
	<security:authorize access="hasRole('NUTRITIONIST') || hasRole('USER')">

		<jstl:if test="${same==false }">
			<jstl:forEach var="ua" items="${actor.userAccount.authorities }">
				<jstl:if test="${ua.authority=='NUTRITIONIST' }">
					<form:hidden path="likedRecipes" />
					<form:hidden path="dislikedRecipes" />
					<form:hidden path="comments" />
					<form:hidden path="following" />
					<form:hidden path="followers" />
					<jstl:if test="${follows==true }">
						<input type="submit" name="unfollowNutritionist"
							value="<spring:message code="actor.unfollow" />" />&nbsp; 
			<form:hidden path="curriculum" />
						<form:hidden path="ingredients" />

					</jstl:if>

					<jstl:if test="${follows==false }">
						<input type="submit" name="followNutritionist"
							value="<spring:message code="actor.follow" />" />&nbsp; 
			<form:hidden path="curriculum" />
						<form:hidden path="ingredients" />

					</jstl:if>
				</jstl:if>


				<jstl:if test="${ua.authority=='USER' }">
					<form:hidden path="likedRecipes" />
					<form:hidden path="dislikedRecipes" />
					<form:hidden path="comments" />
					<form:hidden path="following" />
					<form:hidden path="followers" />
					<jstl:if test="${follows==true }">

						<input type="submit" name="unfollowUser"
							value="<spring:message code="actor.unfollow" />" />&nbsp; 
				<form:hidden path="authoredRecipes" />
					</jstl:if>

					<jstl:if test="${follows==false }">

						<input type="submit" name="followUser"
							value="<spring:message code="actor.follow" />" />&nbsp; 
				<form:hidden path="authoredRecipes" />
					</jstl:if>
				</jstl:if>
			</jstl:forEach>
		</jstl:if>

		<br />
		<br />
	</security:authorize>

	<a href="socialIdentity/publicList.do?actorId=${actor.id}"><spring:message
			code="actor.seeSocialIdentity" /></a>
	<br />

	<jstl:forEach var="ua" items="${actor.userAccount.authorities }">
		<jstl:if test="${ua.authority=='USER' }">
			<a href="recipe/listPublic.do?userId=${actor.id}"><spring:message
					code="actor.recipe.listPublic" /></a>
		</jstl:if>
	</jstl:forEach>

</form:form>
