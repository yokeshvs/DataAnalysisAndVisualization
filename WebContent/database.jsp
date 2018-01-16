<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Database Login</title>
</head>
<body>
	<f:view>
		<center>
			<h1>Welcome to Database Login</h1>
		</center>
		<hr />
		<br />
		<center>
			<p>
				<a href="index.jsp">Home</a>
			</p>
		</center>
		<br />
		<center>
			<p>Please enter your credentials to proceed to world schema</p>
		</center>
		<br />
		<br />
		<br />
		<h:form>
			<center>
				<h:panelGrid columns="2" bgcolor="lightBlue" title="Database Login"
					columnClasses="left,centered" cellpadding="10" cellspacing="2">
					<f:facet name="header">
						<h:outputText value="Database Login" />
					</f:facet>
					<h:outputLabel value="Select the database type:" for="dataBaseType" />
					<h:selectOneMenu value="#{endPoint.dataBaseType}" id="dataBaseType">
						<f:selectItem itemValue="MYSQL" itemLabel="MYSQL" />
						<f:selectItem itemValue="DB2" itemLabel="DB2" />
						<f:selectItem itemValue="Oracle" itemLabel="Oracle" />
					</h:selectOneMenu>
					<h:outputLabel value="Select the server:" for="host" />
					<h:selectOneMenu id="host" value="#{endPoint.host}">
						<f:selectItem itemValue="131.193.209.54"
							itemLabel="Server 54" />
						<f:selectItem itemValue="131.193.209.57"
							itemLabel="Server 57" />
						<f:selectItem itemValue="131.193.209.68"
							itemLabel="Server 68" />
						<f:selectItem itemValue="131.193.209.69"
							itemLabel="Server 69" />
						<f:selectItem itemValue="localhost" itemLabel="LocalHost" />
						<f:selectItem itemValue="Other" itemLabel="Other" />
					</h:selectOneMenu>
					<h:outputLabel for="optionalHost" value="Alternate Host:" />
					<h:inputText id="optionalHost" value="#{endPoint.optionalHost}" />
					<h:outputLabel for="userName" value="Username:" />
					<h:inputText id="userName" value="#{endPoint.userName}"
						required="true" requiredMessage="Enter the user name" />
					<h:outputLabel for="password" value="Password:" />
					<h:inputSecret id="password" value="#{endPoint.password}"
						required="true" requiredMessage="Enter the password" />
					<h:outputLabel for="login" value=" " />
					<h:commandButton type="submit" value="Login" id="login"
						action="#{dbAccess.login}" />
				</h:panelGrid>
			</center>
			<br />
			<hr />
			<br />
			<br />
			<h:panelGroup>
			</h:panelGroup>
			<center>
				<h:outputLabel for="sqlErrorMessage" value="Status Message:" />
				<h:outputText id="sqlErrorMessage"
					value="#{dbAccess.sqlErrorMessage}" />
				<br />
				<h:outputLabel for="sqlErrorCode" value="Status Code:" />
				<h:outputText id="sqlErrorCode" value="#{dbAccess.sqlErrorCode}" />
				<br />
				<h:outputLabel for="sqlState" value="Output State:" />
				<h:outputText id="sqlState" value="#{dbAccess.sqlState}" />
			</center>
		</h:form>
	</f:view>
</body>
</html>