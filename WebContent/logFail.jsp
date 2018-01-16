<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Failed - Log Update</title>
</head>
<body>
	<f:view>
		<br />
		<br />
		<center>
			<h1>Transaction Log Failed</h1>
		</center>
		<div align="right">
			<h:form>
				<h:commandButton type="submit" value="Logout" id="logout"
					action="#{dbAccess.logout}" />
			</h:form>
		</div>
		<hr />
		<br />
		<div align="center">
			<h:form>
				<h:commandButton type="submit" value="Home" id="home"
					action="#{dbAccess.reset}" />
			</h:form>
		</div>
		<br />
		<br />
		<br />
	</f:view>
</body>
</html>