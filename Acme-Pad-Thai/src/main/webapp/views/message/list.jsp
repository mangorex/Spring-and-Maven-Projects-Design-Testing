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

<form:form method="POST" action="message/actor/view.do" modelAttribute="folderList">
<form:select id="foldersDropdown" path="fols">
	<form:option value="0" label="----"/>
	<jstl:forEach items="${folders}" var="fol">
            <option <jstl:if test="${selectedFolder==fol.id}">selected="selected"</jstl:if>     value="${fol.id}">${fol.name} </option>
        </jstl:forEach>
</form:select>
<input type="submit" name="view" value="<spring:message code="message.changefolder" />"/>
</form:form>
<br />

<jstl:if test="${sysFolder !=1 }">
	<a href="folder/actor/edit.do?selectedFolder=${selectedFolder}"><spring:message code="message.folder.edit" /></a>
	<br />
</jstl:if>

<display:table name="messages" id="row" requestURI="message/actor/list.do" class="displaytag" pagesize="5">
	
		<display:column>
			<a href="message/actor/read.do?id=${row.id}"><spring:message code="message.read" /></a>
		</display:column>			
		<display:column titleKey="message.sender">
		${row.sender}
		</display:column>
	
	<display:column titleKey="message.recipient">	
		${row.recipient}
		</display:column>
	
	<display:column property="moment" titleKey="message.date" sortable="true" />
	<display:column property="subject" titleKey="message.subject" sortable="false" />
	
	
	<display:column titleKey="message.folder" sortable="true" >
	<form:form method="POST" action="message/actor/edit.do" modelAttribute="folderList">
	<form:select id="foldersDropdown" path="fols">
	<jstl:forEach items="${folders}" var="fol">
            <option <jstl:if test="${row.folder.name==fol.name && row.folder.systemFolder==fol.systemFolder}">selected="selected"</jstl:if>    value="${fol.id}">${fol.name} </option>
        </jstl:forEach>
	</form:select>
	<input type="hidden" name="message" value="${row.id}" />
	<input type="hidden" name="whereAmI" value="${selectedFolder}" />
	<input type="submit" name="edit" value="<spring:message code="message.changefolder" />"/>
	</form:form>
	</display:column>
	
	<display:column property="priority" titleKey="message.priority" sortable="true" />	
	<display:column>
	<form:form method="POST" action="message/actor/delete.do">
			<input type="hidden" name="message" value="${row.id}" />
			<input type="hidden" name="whereAmI" value="${selectedFolder}" />
			<input type="submit" name="delete"
			value="<spring:message code="message.delete" />"
			onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
	</form:form>
	</display:column>
</display:table>

<div>
	<a href="message/actor/create.do"> 
		<spring:message	code="message.create" />
	</a>
	<br/>
	<a href="folder/actor/create.do"> 
		<spring:message	code="message.folder.create" />
	</a>
</div>
