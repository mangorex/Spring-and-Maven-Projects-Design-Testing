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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('SPONSOR')">

	<display:table name="campaigns" id="row"
		requestURI="/campaign/sponsor/list.do" pagesize="5" class="displaytag">

		<display:column property="name" titleKey="campaign.title"
			sortable="${true}">
		</display:column>

		<display:column property="startDate" titleKey="campaign.startDate"
			format="{0,date,dd/MM/yyyy HH:mm}">
		</display:column>

		<display:column property="endDate" titleKey="campaign.endDate"
			format="{0,date,dd/MM/yyyy HH:mm}">
		</display:column>

		<display:column property="numBanners" titleKey="campaign.numBanners">
		</display:column>


		<display:column titleKey="campaign.images">

			<jstl:set var="splitBanners" value="${fn:split(row.banners, ',')}" />
			<jstl:forEach var="array" items="${splitBanners}">
				<img src="${array}" alt="Your banner."
					style="width: 90px; height: 90px;">
			</jstl:forEach>

		</display:column>

		<display:column property="maxBannersDisplayed"
			titleKey="campaign.maxBanner">
		</display:column>

		<display:column property="star" titleKey="campaign.star">
		</display:column>

		<display:column>
			<a href="campaign/sponsor/create.do?campaignId=${row.id}"><spring:message
					code="campaign.edit" /> </a>

		</display:column>


	</display:table>

</security:authorize>

<a href="campaign/sponsor/create.do"><spring:message
		code="campaign.create" /></a>


