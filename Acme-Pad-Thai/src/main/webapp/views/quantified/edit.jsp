<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="quantified/user/edit.do" modelAttribute="quantified">
	<form:hidden path="id" />
	<form:hidden path="version" />

	
<form:label path="ingredient">
		<spring:message code="quantified.ingredients" />:
</form:label>
<form:select path="ingredient" modelAttribute="ingredient">
	<form:option label="-----" value="0"/>
	<form:options items="${ingredients}" 
	itemLabel="name"
	itemValue="id"/>
</form:select>

<form:label path="recipe">
		<spring:message code="quantified.recipes" />:
</form:label>
<form:select path="recipe">
	<form:option label="-----" value="0"/>
	<form:options items="${recipes}" 
	itemLabel="title"
	itemValue="id"/>
</form:select>

<br/><br/>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="quantityInteger" />
	
	<form:label path="quantityDouble">
	<spring:message code="quantified.quantity" />:
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
	
	<br/><br/>
	<input type="submit" name="save"
		value="<spring:message code="quantidied.save" />" />&nbsp; 
	<jstl:if test="${quantified.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="quantified.delete" />"
			onclick="return confirm('<spring:message code="quantified.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="quantified.cancel" />"
		onclick="javascript: window.location.replace('quantified/user/list.do')" />
	
</form:form>	
	