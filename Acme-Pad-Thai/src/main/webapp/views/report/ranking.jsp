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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<table>
	<tr>
		<td>
		<spring:message code="report.rankingNumberCampaign" />: 
		<table>
			<tr>
			<th>#</th><th><spring:message code="sponsor.companyName" /></th>
          	</tr>
          	<jstl:forEach var="i" begin="0" end="${rankinglength}">
            	<tr>
          			<td>${i+1}</td><td>${ranking[i].companyName}</td>
          		</tr>
          	</jstl:forEach>
         </table>
		</td>
		<td>
			<spring:message code="report.rankingBillCampaign" />: 
			<table>
				<tr>
				<th>#</th><th><spring:message code="sponsor.companyName" /></th>
	          	</tr>
	          	<jstl:forEach var="i" begin="0" end="${rankingBilllenght}">
	            	<tr>
	          			<td>${i+1}</td><td>${rankingBill[i].companyName}</td>
	          		</tr>
	          	</jstl:forEach>
	         </table>
		</td>
	</tr>
</table>




