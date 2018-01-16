<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:t="http://myfaces.apache.org/tomahawk">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Database Home</title>
</head>
<body>
	<f:view>
		<center>
			<h1>Welcome to Database Home</h1>
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
		<center>
			<h:panelGrid columns="2" title="Database Login" cellpadding="10"
				cellspacing="2"
				style="text-align:center; background-color: LightBlue;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
				<f:facet name="header">
					<h:outputText value="Database Home" style="align:center" />
				</f:facet>
				<h:outputText value="World Schema"></h:outputText>
				<h:outputText value="f17x321 Schema"></h:outputText>
				<h:selectOneListbox styleClass="selectOneListbox_mono" size="10">
					<f:selectItems value="#{dbAccess.worldTables}" />
				</h:selectOneListbox>

				<h:selectOneListbox styleClass="selectOneListbox_mono" size="10">
					<f:selectItems value="#{dbAccess.customTables}" />
				</h:selectOneListbox>
			</h:panelGrid>
		</center>
		<br />
		<br />
		<center>
			<h1>Table Operation</h1>
		</center>
		<hr />
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


					<h:commandButton type="submit" value="Display Table"
						id="showCompleteTable" rendered="#{dbAccess.tableMenuRendered}"
						action="#{dbAccess.showCompleteTable}" />


					<h:commandButton type="submit" value="Show Columns"
						id="showColumns" rendered="#{dbAccess.tableMenuRendered}"
						action="#{dbAccess.showTableColumns}" />

					<h:outputLabel value="Select the columns you want to view:"
						for="selectedColumns" rendered="#{dbAccess.columnRendered}" />
					<h:selectManyListbox size="10" value="#{dbAccess.selectedColumns}"
						rendered="#{dbAccess.columnRendered}" id="selectedColumns">
						<f:selectItems value="#{dbAccess.menuColumns}" />
					</h:selectManyListbox>

					<h:commandButton type="submit"
						value="Show Table with selected columns" id="showColumnTable"
						rendered="#{dbAccess.columnRendered}"
						action="#{dbAccess.showSelectedColumnTable}" />

					<h:commandButton type="submit" value="Export to CSV"
						id="exportToCSV" rendered="#{dbAccess.tableRendered}"
						action="#{dbAccess.processFileDownload}" />

				</h:panelGrid>

				<br />
				<br />
				<center>
					<h:outputText rendered="#{dbAccess.tableRendered}"
						value="Viewing the content of - #{dbAccess.selectedTable} table in #{dbAccess.selectedSchema} schema"></h:outputText>
				</center>
				<br />
				<br />
				<div
					style="background-attachment: scroll; overflow: auto; height: 400px; background-repeat: repeat">
					<t:dataTable value="#{dbAccess.resultSet}" var="row"
						rendered="#{dbAccess.tableRendered}" border="1" cellspacing="0"
						cellpadding="1" width="60%"
						style="align:center;border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
						<t:columns style="color:black;" var="col"
							value="#{dbAccess.displayColumns}"
							rendered="#{dbAccess.tableRendered}">
							<f:facet name="header">
								<t:outputText style="color:blue" styleClass="outputHeader"
									value="#{col}" />
							</f:facet>
							<div align="center">
								<t:outputText styleClass="outputText" value="#{row[col]}" />
							</div>
						</t:columns>
					</t:dataTable>
				</div>

			</h:form>
		</center>
	</f:view>
</body>
</html>