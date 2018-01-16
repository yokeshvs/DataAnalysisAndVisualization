<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Data Analysis</title>
</head>
<body>
	<f:view>
		<br />
		<br />
		<center>
			<h1>Statistical Data Analysis</h1>
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
					<h:outputLabel value="Click to view columns for analysis:"
						for="showColumns" rendered="#{dbAccess.tableMenuRendered}" />
					<h:commandButton type="submit" value="Show Columns"
						id="showColumns" rendered="#{dbAccess.tableMenuRendered}"
						action="#{dbAccess.showTableColumns}" />

					<h:outputLabel value="Select the columns you want to analyze:"
						for="selectedColumns" rendered="#{dbAccess.columnRendered}" />
					<h:selectManyListbox size="10" value="#{dbAccess.selectedColumns}"
						rendered="#{dbAccess.columnRendered}" id="selectedColumns">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectManyListbox>

					<h:commandButton type="submit" value="Show Descriptive Stats"
						id="showDescStats" rendered="#{dbAccess.columnRendered}"
						action="#{dataAnalysisBean.generateDescriptiveStats}" />

					<h:commandButton type="submit" value="Export to CSV"
						id="exportToCSV" rendered="#{dataAnalysisBean.descRender}"
						action="#{dataAnalysisBean.exportToCSV}" />

				</h:panelGrid>

				<br />
				<center>
					<h:outputText rendered="#{dataAnalysisBean.messageRendered}"
						style="color:red" value="#{dataAnalysisBean.message}"></h:outputText>
				</center>
				<br />
				<center>
					<h:outputText rendered="#{dataAnalysisBean.descRender}"
						value="Viewing the statistical analysis of - #{dbAccess.selectedTable} table in #{dbAccess.selectedSchema} schema"></h:outputText>
				</center>
				<br />
				<br />
				<div
					style="background-attachment: scroll; overflow: auto; height: 400px; background-repeat: repeat">
					<h:dataTable
						value="#{dataAnalysisBean.descriptiveAnalysisBeanList}"
						var="rowNumber" rendered="#{dataAnalysisBean.descRender}"
						border="1" cellspacing="0" cellpadding="1" width="60%"
						style="align:center;border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
						<h:column>
							<f:facet name="header">
								<h:outputText value="Table" />
							</f:facet>
							<h:outputText value="#{dbAccess.selectedTable}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="No. of Observations" />
							</f:facet>
							<h:outputText value="#{dataAnalysisBean.rowCount}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Column Selected" />
							</f:facet>
							<h:outputText value="#{rowNumber.columnSelected}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Minimum Value" />
							</f:facet>
							<h:outputText value="#{rowNumber.minValue}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Maximum Value" />
							</f:facet>
							<h:outputText value="#{rowNumber.maxValue}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Mean" />
							</f:facet>
							<h:outputText value="#{rowNumber.mean}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Median" />
							</f:facet>
							<h:outputText value="#{rowNumber.median}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Variance" />
							</f:facet>
							<h:outputText value="#{rowNumber.variance}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Standard Deviation" />
							</f:facet>
							<h:outputText value="#{rowNumber.std}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Q1" />
							</f:facet>
							<h:outputText value="#{rowNumber.q1}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Q3" />
							</f:facet>
							<h:outputText value="#{rowNumber.q3}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="Range" />
							</f:facet>
							<h:outputText value="#{rowNumber.range}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText value="IQR" />
							</f:facet>
							<h:outputText value="#{rowNumber.iqr}" />
						</h:column>
					</h:dataTable>
				</div>

			</h:form>
		</center>
	</f:view>
</body>
</html>