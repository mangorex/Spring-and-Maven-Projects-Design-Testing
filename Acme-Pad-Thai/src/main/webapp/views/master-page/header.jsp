<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Pad-Thai Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li><a class="fNiv" href="/"><spring:message
					code="master.page.home" /></a></li>

		<li><a class="fNiv"><spring:message
					code="master.page.recipes" /></a>
			<ul>



				<li class="arrow"></li>
				<li><a href="recipe/browse.do"><spring:message
							code="master.page.browse.recipe" /></a></li>
				<li><a href="recipe/search.do?keyword="><spring:message
							code="master.page.search.recipe" /></a></li>
				<security:authorize access="hasRole('USER')">
					<li><a href="recipe/user/list.do"><spring:message
								code="master.page.user.list.recipe" /></a></li>

					<li><a href="recipe/customer/latest.do"><spring:message
								code="master.page.recipe.latest" /></a></li>
					<li><a href="quantified/user/list.do"><spring:message
								code="master.page.quantified.list" /></a></li>
				</security:authorize>

				<security:authorize
					access="hasRole('USER') || hasRole('NUTRITIONIST')">
					<li><a href="recipe/customer/latest.do"><spring:message
								code="master.page.recipe.latest" /></a></li>
				</security:authorize>
			</ul></li>

		<li><a class="fNiv"><spring:message
					code="master.page.title.users" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="user/search.do?keyword="><spring:message
							code="master.page.search.user" /></a></li>
				<li><a href="user/list.do"><spring:message
							code="master.page.users" /></a></li>
			</ul></li>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.actor.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/create.do"><spring:message
								code="master.page.actor.registerUser" /></a></li>
					<li><a href="nutritionist/create.do"><spring:message
								code="master.page.actor.registerNutritionist" /></a></li>
					<li><a href="sponsor/create.do"><spring:message
								code="master.page.actor.registerSponsor" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.actor.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="cook/administrator/create.do"><spring:message
								code="master.page.actor.registerCook" /></a></li>

				</ul></li>
		</security:authorize>

		<li><a class="fNiv"><spring:message
					code="master.page.title.contest" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="contest/list.do"><spring:message
							code="master.page.contest.list" /></a></li>
				<security:authorize access="hasRole('ADMINISTRATOR')">
					<li><a href="contest/administrator/setWinners.do"><spring:message
								code="master.page.setWinners" /></a></li>
				</security:authorize>

			</ul></li>

		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.curriculum" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/nutritionist/list.do"><spring:message
								code="master.page.curriculum.list" /></a></li>
					<li><a href="curriculum/nutritionist/create.do"><spring:message
								code="master.page.curriculum.create" /></a></li>
					<li><a href="endorser/nutritionist/list.do"><spring:message
								code="master.page.endorser.list" /></a></li>
					<li><a href="endorser/nutritionist/create.do"><spring:message
								code="master.page.endorser.create" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.ingredient" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="ingredient/nutritionist/list.do"><spring:message
								code="master.page.ingredient.list" /></a></li>
					<li><a href="ingredient/nutritionist/create.do"><spring:message
								code="master.page.ingredient.create" /></a></li>
					<li><a href="property/nutritionist/list.do"><spring:message
								code="master.page.property.list" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.spam" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="spam/administrator/manage.do"><spring:message
								code="master.page.spam.manage" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.category" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.category.list" /></a></li>
				</ul></li>

		</security:authorize>


		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv" href="report/administrator/dashboard.do"><spring:message
						code="master.page.report" /></a></li>

		</security:authorize>


		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.campaign" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="campaign/sponsor/list.do"><spring:message
								code="master.page.campaign.list" /></a></li>
					<li><a href="bill/sponsor/browse.do"><spring:message
								code="master.page.bill.browse" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.title.campaign" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fee/administrator/edit.do"><spring:message
								code="master.page.fee.edit" /></a></li>
					<li><a href="bill/administrator/compute.do"><spring:message
								code="master.page.bill.compute" /></a></li>
				</ul></li>
		</security:authorize>





		<li><a class="fNiv"><spring:message
					code="master.page.title.masterclass" /></a>
			<ul>
				<li class="arrow"></li>


				<li><a href="masterClass/list.do"><spring:message
							code="master.page.masterClass.list" /></a></li>

				<security:authorize access="isAuthenticated()">
					<li><a href="masterClass/registerList.do"><spring:message
								code="master.page.masterClass.register" /></a></li>
				</security:authorize>
				<security:authorize access="hasRole('COOK')">

					<li><a href="masterClass/cook/list.do"><spring:message
								code="master.page.masterClass.manage" /></a></li>
				</security:authorize>
			</ul></li>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="user/profile.do"><spring:message
									code="master.page.actor.profile" /></a></li>
						<li><a href="customer/followersList.do"><spring:message
									code="master.page.actor.followers" /></a></li>
						<li><a href="customer/followingList.do"><spring:message
									code="master.page.actor.following" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('NUTRITIONIST')">
						<li><a href="nutritionist/profile.do"><spring:message
									code="master.page.actor.profile" /></a></li>
						<li><a href="customer/followersList.do"><spring:message
									code="master.page.actor.followers" /></a></li>
						<li><a href="customer/followingList.do"><spring:message
									code="master.page.actor.following" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="administrator/profile.do"><spring:message
									code="master.page.actor.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/profile.do"><spring:message
									code="master.page.actor.profile" /></a></li>
						<li><a href="creditCard/sponsor/list.do"><spring:message
									code="master.page.creditCard.list" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COOK')">
						<li><a href="cook/profile.do"><spring:message
									code="master.page.actor.profile" /></a></li>
					</security:authorize>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>

				</ul></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/actor/list.do"><spring:message
								code="master.page.message.list" /></a></li>
					<li><a href="message/actor/create.do"><spring:message
								code="master.page.message.send" /></a></li>


				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

