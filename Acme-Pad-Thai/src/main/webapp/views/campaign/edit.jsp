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

<form:form action="campaign/sponsor/edit.do" modelAttribute="campaign">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor" />
	<form:hidden path="bills" />
	<form:hidden path="numBanners" />

	<form:label path="name">
		<spring:message code="campaign.title" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="startDate">
		<spring:message code="campaign.startDate" />:
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />

	<form:label path="endDate">
		<spring:message code="campaign.endDate" />:
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />


	<form:label path="maxBannersDisplayed">
		<spring:message code="campaign.maxBannersDisplayed" />:
	</form:label>
	<form:input path="maxBannersDisplayed" />
	<form:errors cssClass="error" path="maxBannersDisplayed" />
	<br />

	<form:label path="banners">
		<spring:message code="campaign.banners" />:
	</form:label>
	<form:input path="banners" />
	<form:errors cssClass="error" path="banners" />
	<br />

	<form:label path="star">
		<spring:message code="campaign.star" />:
	</form:label>
	<form:checkbox path="star" titleKey="campaign.star" />
	<br />
	<input type="submit" name="save"
		value="<spring:message code="campaign.save" />" />&nbsp; 
	
	<jstl:if test="${campaign.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="campaign.delete" />"
			onclick="return confirm('<spring:message code="campaign.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="campaign.cancel" />"
		onclick="javascript: window.location.replace('campaign/sponsor/list.do')" />
	<br />
</form:form>
