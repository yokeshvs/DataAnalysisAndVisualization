<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Regression Analysis</title>
</head>
<body>
	<f:view>
		<br />
		<br />
		<center>
			<h1>Regression Analysis</h1>
		</center>
		<div align="right">
			<h:form>
				<h:commandButton type="submit" value="Logout" id="logout"
					action="#{regressionAnalysisBean.logout}" />
			</h:form>
		</div>
		<hr />
		<br />
		<div align="center">
			<h:form>
				<h:commandButton type="submit" value="Home" id="home"
					action="#{regressionAnalysisBean.reset}" />
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
						<h:outputText value="Regression Analysis Menu" style="align:center" />
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
						value="#{regressionAnalysisBean.predictorColumn}">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectOneMenu>

					<h:outputLabel value="Select the response column:"
						for="responseColumn" rendered="#{dbAccess.columnRendered}" />
					<h:selectOneMenu id="responseColumn"
						rendered="#{dbAccess.columnRendered}"
						value="#{regressionAnalysisBean.responseColumn}">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectOneMenu>

					<h:commandButton type="submit" value="Show Regression Analysis"
						id="showScatterPlot" rendered="#{dbAccess.columnRendered}"
						action="#{regressionAnalysisBean.generateRegressionReport}" />


				</h:panelGrid>

				<br />
				<center>
					<h:outputText rendered="#{regressionAnalysisBean.messageRendered}"
						style="color:red" value="#{regressionAnalysisBean.message}"></h:outputText>
					<br /> &#160;
					<h:outputText value="#{regressionAnalysisBean.regressionEquation}"
						rendered="#{regressionAnalysisBean.renderRegressionResult}">
					</h:outputText>
				</center>

				<br />

				<h:panelGrid columns="5" cellspacing="0" cellpadding="5"
					rendered="#{regressionAnalysisBean.renderRegressionResult}"
					border="3"
					style="text-align:center; background-color: LightBlue;border:1px solid black;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
					<h:outputText value="Predictor" />
					<h:outputText value="Co-efficient" />
					<h:outputText value="Standard Error Co-efficient" />
					<h:outputText value="T-Statistic" />
					<h:outputText value="P-Value" />
					<h:outputText value="Constant" />
					<h:outputText value="#{regressionAnalysisBean.intercept}" />
					<h:outputText
						value="#{regressionAnalysisBean.interceptStandardError}" />
					<h:outputText value="#{regressionAnalysisBean.tStatistic }" />
					<h:outputText value="#{regressionAnalysisBean.interceptPValue }" />
					<h:outputText value="#{regressionAnalysisBean.predictorColumn}" />
					<h:outputText value="#{regressionAnalysisBean.slope}" />
					<h:outputText value="#{regressionAnalysisBean.slopeStandardError}" />
					<h:outputText
						value="#{regressionAnalysisBean.tStatisticPredictor }" />
					<h:outputText value="#{regressionAnalysisBean.pValuePredictor }" />
				</h:panelGrid>
				<br />
				<br />
				<h:panelGrid columns="2" border="3" cellspacing="0" cellpadding="5"
					style="text-align:center; background-color: LightBlue;border:1px solid black;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%"
					rendered="#{regressionAnalysisBean.renderRegressionResult}">
					<h:outputText value="Model Standard Error:" />
					<h:outputText value="#{regressionAnalysisBean.standardErrorModel}" />
					<h:outputText value="R Square(Co-efficient of Determination)" />
					<h:outputText value="#{regressionAnalysisBean.rSquare}" />
					<h:outputText
						value="R Square Adjusted(Co-efficient of Determination)" />
					<h:outputText value="#{regressionAnalysisBean.rSquareAdjusted}" />
				</h:panelGrid>
				<br />
				<br />
				<h:outputText value="Analysis of Variance"
					rendered="#{regressionAnalysisBean.renderRegressionResult}" />
				<br />
				<h:panelGrid columns="6" border="3" cellspacing="0" cellpadding="5"
					rendered="#{regressionAnalysisBean.renderRegressionResult}"
					style="text-align:center; background-color: LightBlue; border:1px solid black;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
					<h:outputText value="Source" />
					<h:outputText value="Degrees of Freedom(DF)" />
					<h:outputText value="Sum of Squares" />
					<h:outputText value="Mean of Squares" />
					<h:outputText value="F-Statistic" />
					<h:outputText value="P-Value" />
					<h:outputText value="Regression" />
					<h:outputText value="#{regressionAnalysisBean.predictorDF}" />
					<h:outputText
						value="#{regressionAnalysisBean.regressionSumSquares}" />
					<h:outputText value="#{regressionAnalysisBean.meanSquare }" />
					<h:outputText value="#{regressionAnalysisBean.fValue }" />
					<h:outputText value="#{regressionAnalysisBean.pValue}" />
					<h:outputText value="Residual Error" />
					<h:outputText value="#{regressionAnalysisBean.residualErrorDF}" />
					<h:outputText value="#{regressionAnalysisBean.sumSquaredErrors }" />
					<h:outputText value="#{regressionAnalysisBean.meanSquareError }" />
					<h:outputText value="" />
					<h:outputText value="" />
					<h:outputText value="Total" />
					<h:outputText value="#{regressionAnalysisBean.totalDF}" />
				</h:panelGrid>

			</h:form>
		</center>
	</f:view>
</body>
</html>