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
<title>Application Menu</title>
</head>
<body>
	<f:view>
		<center>
			<h1>Welcome to Database Menu</h1>
		</center>
		<div align="right">
			<h:form>
				<h:commandButton type="submit" value="Logout" id="logout"
					action="#{dbAccess.logout}" />
			</h:form>
		</div>
		<hr />
		<br />
		<center>
			<p>
				<a href="index.jsp">Home</a>
			</p>
		</center>
		<br />
		<br />
		<center>

			<p>
				<a href="home.jsp">Database operation</a>
			</p>
			<br />
			<p>
				<a href="queryUpload.jsp">File Upload operation</a>
			</p>
			<br />
			<p>
				<a href="dataAnalysis.jsp">Statistical Analysis</a>
			</p>
			<br />
			<p>
				<a href="dataVisualization.jsp">Data Visualization Analysis</a>
			</p>
			<br />
			<p>
				<a href="regressionAnalysis.jsp">Regression Analysis</a>
			</p>
		</center>
	</f:view>
</body>
</html>