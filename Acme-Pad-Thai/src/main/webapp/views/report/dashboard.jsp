<%--
 * list.jsp
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<h2>
	<spring:message code="report.recipesperuser" />
	:
</h2>

<table>
	<tr>
		<th><spring:message code="report.min" /></th>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.max" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${rpsMin}" /></td>
		<td><jstl:out value="${rpsAvg}" /></td>
		<td><jstl:out value="${rpsMax}" /></td>
	</tr>
</table>

<h2>
	<spring:message code="report.userwithmorerecipes" />
	:
</h2>

<display:table name="usersmorerecipes" id="row" class="displaytag">

	<display:column property="name" titleKey="actor.name" sortable="true">

	</display:column>

	<display:column property="surname" titleKey="actor.surname">

	</display:column>

	<display:column property="emailAddress" titleKey="actor.emailAddress">

	</display:column>

	<display:column property="phoneNumber" titleKey="actor.phoneNumber">

	</display:column>

	<display:column property="postalAddress" titleKey="actor.postalAddress">
	</display:column>
</display:table>

<h2>
	<spring:message code="report.recipescontest" />
	:
</h2>

<table>
	<tr>
		<th><spring:message code="report.min" /></th>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.max" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${rcMin}" /></td>
		<td><jstl:out value="${rcAvg}" /></td>
		<td><jstl:out value="${rcMax}" /></td>
	</tr>
</table>

<h2>
	<spring:message code="report.morequalified" />
	:
</h2>

<display:table name="contestmore" id="row" class="displaytag">

	<display:column property="title" titleKey="contest.title">
	</display:column>


	<display:column property="opening" titleKey="contest.openingDate"
		format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>

	<display:column property="closing" titleKey="contest.closingDate"
		format="{0,date,dd/MM/yyyy HH:mm}">
	</display:column>

	<display:column titleKey="contest.recipes">

		<jstl:forEach items="${row.recipes}" var="recipe">

			<jstl:out value="${recipe.title}" />


		</jstl:forEach>

	</display:column>


	<display:column titleKey="contest.winners">
		<jstl:forEach items="${row.winners}" var="recipe">

			<jstl:out value="${recipe.title}" />


		</jstl:forEach>
	</display:column>
</display:table>

<h2>
	<spring:message code="report.stepsrecipe" />
	:
</h2>

<table>
	<tr>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.std" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${srAvg}" /></td>
		<td><jstl:out value="${srDv}" /></td>
	</tr>
	
</table>


<h2>
	<spring:message code="report.ingrecipe" />
	:
</h2>

<table>
	<tr>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.std" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${irAvg}" /></td>
		<td><jstl:out value="${irDv}" /></td>
	</tr>
	
</table>

<h2>
	<spring:message code="report.popularity" />
	:
</h2>

<display:table name="userPop" id="row" class="displaytag">

	<display:column property="name" titleKey="actor.name" sortable="true">

	</display:column>

	<display:column property="surname" titleKey="actor.surname">

	</display:column>

	<display:column property="emailAddress" titleKey="actor.emailAddress">

	</display:column>

	<display:column property="phoneNumber" titleKey="actor.phoneNumber">

	</display:column>

	<display:column property="postalAddress" titleKey="actor.postalAddress">
	</display:column>
</display:table>

<h2>
	<spring:message code="report.descendinglikes" />
	:
</h2>

<display:table name="userlikes" id="row" class="displaytag">

	<display:column property="name" titleKey="actor.name" sortable="true">

	</display:column>

	<display:column property="surname" titleKey="actor.surname">

	</display:column>

	<display:column property="emailAddress" titleKey="actor.emailAddress">

	</display:column>

	<display:column property="phoneNumber" titleKey="actor.phoneNumber">

	</display:column>

	<display:column property="postalAddress" titleKey="actor.postalAddress">
	</display:column>
</display:table>



<h2>
	<spring:message code="report.campaignPerSponsor" />
	:
</h2>
<table>
	<tr>
		<th><spring:message code="report.min" /></th>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.max" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${cpsMin}" /></td>
		<td><jstl:out value="${cpsAvg}" /></td>
		<td><jstl:out value="${cpsMax}" /></td>
	</tr>
</table>

<h2>
	<spring:message code="report.activeCampaignPerSponsor" />
	:
</h2>
<table>
	<tr>
		<th><spring:message code="report.min" /></th>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.max" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${acpsMin}" /></td>
		<td><jstl:out value="${acpsAvg}" /></td>
		<td><jstl:out value="${acpsMax}" /></td>
	</tr>
</table>

<h2>
	<spring:message code="report.sponsorInactive" />
	:
</h2>
<display:table name="sponsors" id="row" class="displaytag">

	<display:column titleKey="sponsor.id">
		<jstl:out value="${row.id}" />
	</display:column>
	<display:column titleKey="sponsor.name">
		<jstl:out value="${row.name}" />
		<jstl:out value="${row.surname}" />
	</display:column>
	<display:column titleKey="sponsor.companyName" >
		<jstl:out value="${row.companyName}" />
	</display:column>

</display:table>

<h2>Ranking:</h2>
<table>
	<tr>
		<td><spring:message code="report.rankingNumberCampaign" />:
			<table>
				<tr>
					<th>#</th>
					<th><spring:message code="sponsor.companyName" /></th>
				</tr>
				<jstl:if test="${rankinglength}>0 ">
				<jstl:forEach var="i" begin="0" end="${rankinglength}">
					<tr>
						<td>${i+1}</td>
						<td>${ranking[i].companyName}</td>
					</tr>
				</jstl:forEach>
				</jstl:if>
			</table></td>
		<td><spring:message code="report.rankingBillCampaign" />:
			<table>
				<tr>
					<th>#</th>
					<th><spring:message code="sponsor.companyName" /></th>
				</tr>
				<jstl:if test="${rankingBilllength}>0 ">
				<jstl:forEach var="i" begin="0" end="${rankingBilllenght}">
					<tr>
						<td>${i+1}</td>
						<td>${rankingBill[i].companyName}</td>
					</tr>
				</jstl:forEach>
				</jstl:if>
			</table></td>
	</tr>
</table>

<h2>
	<spring:message code="report.bill" />
	:
</h2>
<table>
	<tr>
		<th><spring:message code="report.avg" /></th>
		<th><spring:message code="report.std" /></th>
		<th><spring:message code="report.type" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${billPaid[0]}" /></td>
		<td><jstl:out value="${billPaid[1]}" /></td>
		<td><spring:message code="report.paid" /></td>
	</tr>
	<tr>
		<td><jstl:out value="${billUnpaid[0]}" /></td>
		<td><jstl:out value="${billUnpaid[1]}" /></td>
		<td><spring:message code="report.unpaid" /></td>
	</tr>
</table>

<h2>
	<spring:message code="report.sponsorAvg" />
	:
</h2>
<display:table name="sponsorAvg" id="row" class="displaytag">

	<display:column titleKey="sponsor.id" >
		<jstl:out value="${row.id}" />
	</display:column>
	<display:column titleKey="sponsor.companyName">
		<jstl:out value="${row.companyName}" />
	</display:column>

</display:table>

<h2>
	<spring:message code="report.sponsorSpend" />
	:
</h2>
<display:table name="sponsorSpend" id="row" class="displaytag">

	<display:column titleKey="sponsor.id">
		<jstl:out value="${row.id}" />
	</display:column>
	<display:column titleKey="sponsor.companyName">
		<jstl:out value="${row.companyName}" />
	</display:column>

</display:table>

<h2>
	<spring:message code="masterclass.minMaxAvgStdvMSCook" />:
</h2>
<table>
	<tr>
		<td><spring:message code="report.min" /></td>
		<td><spring:message code="report.max" /></td>
		<td><spring:message code="report.avg" /></td>
		<td><spring:message code="report.std" /></td>
		<td><spring:message code="actor.name" /></td>
	</tr>
	<jstl:forEach var="i" begin="0" end="${consulta11.size()}">
	<tr>
		<td><jstl:out value="${consulta11[i]}" /></td>
		<td><jstl:out value="${consulta12[i]}" /></td>
		<td><jstl:out value="${consulta13[i]}" /></td>
		<td><jstl:out value="${consulta14[i]}" /></td>
		<td><jstl:out value="${consulta15[i]}" /></td>
	</tr>
	</jstl:forEach>
</table>

<h2>
	<spring:message code="masterclass.avgLM" />:
</h2>
<table>
	<tr>
		<td>AVG
			<table><tr><td>
				<jstl:forEach var="val" items="${consulta21}">
					<tr><td> <jstl:out value="${val}" /> </td></tr>
				</jstl:forEach>
			</td></tr></table>
		</td>
		<td><spring:message code="masterclass.LMtipo" />
			<table><tr><td>
				<jstl:forEach var="val" items="${consulta22}">
					<tr><td> <jstl:out value="${val}" /> </td></tr>
				</jstl:forEach>
			</td></tr></table>
		</td>
	</tr>
</table>


<h2>
	<spring:message code="masterclass.nummcpromoted" />:
</h2>
<table>
	<tr><td><jstl:out value="${numMCPromoted}" /></td></tr>
</table>

<h2>
	<spring:message code="masterclass.listCookMCProm" />:
</h2>
<table>
	<tr>
		<td><spring:message code="actor.name" /></td>
		<td><spring:message code="actor.surname" /></td>
		<td><spring:message code="cook.num" /></td>
	</tr>
	<jstl:forEach var="i" begin="0" end="${consulta41.size()}">
	<tr>
		<td><jstl:out value="${consulta41[i]}" /></td>
		<td><jstl:out value="${consulta42[i]}" /></td>
		<td><jstl:out value="${consulta43[i]}" /></td>
	</tr>
	</jstl:forEach>
</table>

<h2>
	<spring:message code="masterclass.avgPromotedMC" />:
</h2>
<table>
	<tr>
		<td><spring:message code="report.avg" /></td>
		<td><spring:message code="actor.name" /></td>
	</tr>
	<jstl:forEach var="i" begin="0" end="${consulta51.size()}">
	<tr>
		<td><jstl:out value="${consulta51[i]}" /></td>
		<td><jstl:out value="${consulta52[i]}" /></td>
	</tr>
	</jstl:forEach>
</table>



