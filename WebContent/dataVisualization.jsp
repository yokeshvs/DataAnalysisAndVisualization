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
<title>Data Visualization</title>
</head>
<body>
	<f:view>
		<br />
		<br />
		<center>
			<h1>Data Visualization Analysis</h1>
		</center>
		<div align="right">
			<h:form>
				<h:commandButton type="submit" value="Logout" id="logout"
					action="#{dataAnalysisBean.logout}" />
			</h:form>
		</div>
		<hr />
		<br />
		<div align="center">
			<h:form>
				<h:commandButton type="submit" value="Home" id="home"
					action="#{dataAnalysisBean.reset}" />
			</h:form>
		</div>
		<br />
		<br />
		<br />
		<center>
			<h:form>
				<h:panelGrid columns="2" title="Table Operation" cellpadding="10"
					cellspacing="2"
					style="text-align:center; background-color: LightBlue;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
					<f:facet name="header">
						<h:outputText value="Data Visualization Menu" style="align:center" />
					</f:facet>
					<h:outputLabel value="Select the schema you want to operate:"
						for="selectedSchema" />
					<h:selectOneMenu value="#{dbAccess.selectedSchema}"
						id="selectedSchema">
						<f:selectItem itemValue="world" itemLabel="World" />
						<f:selectItem itemValue="f17x321" itemLabel="f17X321" />
					</h:selectOneMenu>
					<h:outputLabel for="showTable" value="Do you want to show tables:" />
					<h:commandButton type="submit" value="Show Table List"
						id="showTable" action="#{dbAccess.showTableMenu}" />
					<h:outputLabel value="Select the table you want to operate:"
						for="selectedTable" rendered="#{dbAccess.tableMenuRendered}" />
					<h:selectOneListbox size="10" value="#{dbAccess.selectedTable}"
						rendered="#{dbAccess.tableMenuRendered}" id="selectedTable">
						<f:selectItems value="#{dbAccess.menuTables}" />
					</h:selectOneListbox>

					<h:outputLabel for="showColumns"
						rendered="#{dbAccess.tableMenuRendered}"
						value="Click to select Predictor and Response variables:" />
					<h:commandButton type="submit" value="Show Columns"
						rendered="#{dbAccess.tableMenuRendered}" id="showColumns"
						action="#{dbAccess.showTableColumns}" />

					<h:outputLabel value="Select the predictor column:"
						for="predictorColumn" rendered="#{dbAccess.columnRendered}" />
					<h:selectOneMenu id="predictorColumn"
						rendered="#{dbAccess.columnRendered}"
						value="#{dataVisualizationBean.predictorColumn}">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectOneMenu>

					<h:outputLabel value="Select the response column:"
						for="responseColumn" rendered="#{dbAccess.columnRendered}" />
					<h:selectOneMenu id="responseColumn"
						rendered="#{dbAccess.columnRendered}"
						value="#{dataVisualizationBean.responseColumn}">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectOneMenu>

					<h:commandButton type="submit" value="Show Scatter Plot"
						id="showScatterPlot" rendered="#{dbAccess.columnRendered}"
						action="#{dataVisualizationBean.generateScatterPlot}" />


				</h:panelGrid>

				<br />
				<center>
					<h:outputText rendered="#{dataVisualizationBean.messageRendered}"
						style="color:red" value="#{dataVisualizationBean.message}"></h:outputText>
				</center>

				<br />
				<center>
					<h:graphicImage
						value="#{dataVisualizationBean.scatterPlotChartFile}"
						rendered="#{dataVisualizationBean.renderChart}" alt="Scatter Plot" />
				</center>
			</h:form>
		</center>
	</f:view>
</body>
</html>