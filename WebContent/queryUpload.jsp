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
<title>File Upload</title>
</head>
<body>
	<f:view>
		<br />
		<br />
		<center>
			<h1>File Upload Operation</h1>
		</center>
		<div align="right">
			<h:form>
				<h:commandButton type="submit" value="Logout" id="logout"
					action="#{queryUploadBean.logout}" />
			</h:form>
		</div>
		<hr />
		<br />
		<div align="center">
			<h:form>
				<h:commandButton type="submit" value="Home" id="home"
					action="#{queryUploadBean.reset}" />
			</h:form>
		</div>
		<br />
		<br />
		<br />
		<center>
			<h:form enctype="multipart/form-data">
				<h:panelGrid title="File Upload Operation" columns="2"
					cellpadding="10" cellspacing="2"
					style="text-align:center; background-color: LightBlue;
				border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
					<f:facet name="header">
						<h:outputText value="File Operation Menu" style="align:center" />
					</f:facet>
					<h:outputLabel value="Select file to upload:" for="selectedFile" />
					<t:inputFileUpload id="inputFile" storage="default"
						value="#{queryUploadBean.uploadedFile}"></t:inputFileUpload>
					<h:outputLabel value="Enter the file label:" for="fileLabel" />
					<h:inputText id="fileLabel" value="#{queryUploadBean.fileLabel}"></h:inputText>
					<h:outputLabel value="Click to upload file:" for="fileUpload" />
					<h:commandButton id="fileUpload" value="Upload File"
						action="#{queryUploadBean.processFileUpload}"></h:commandButton>
					<h:outputLabel value="Select the file you want to process:"
						for="selectFile" rendered="#{queryUploadBean.fileListRenderer}" />
					<h:selectOneMenu value="#{queryUploadBean.selectedFileName}"
						rendered="#{queryUploadBean.fileListRenderer}" id="selectFile">
						<f:selectItems value="#{queryUploadBean.fileList}" />
					</h:selectOneMenu>
					<h:outputLabel value="Click to process selected file:"
						rendered="#{queryUploadBean.fileListRenderer}" for="fileProcess" />
					<h:commandButton id="fileProcess" value="Process File"
						rendered="#{queryUploadBean.fileListRenderer}"
						action="#{queryUploadBean.processSelectedFile}"></h:commandButton>
					<h:outputLabel value="Click to delete selected file:"
						rendered="#{queryUploadBean.fileListRenderer}" for="fileDelete" />
					<h:commandButton id="fileDelete" value="Delete File"
						rendered="#{queryUploadBean.fileListRenderer}"
						action="#{queryUploadBean.deleteSelectedFile}"></h:commandButton>
					<h:outputLabel value="Click to export:" for="fileExport"
						rendered="#{queryUploadBean.tableRendered}" />
					<h:commandButton id="fileExport" value="Export" type="submit"
						rendered="#{queryUploadBean.tableRendered}"
						action="#{queryUploadBean.processFileDownload}"></h:commandButton>
				</h:panelGrid>

				<br />
				<center>
					<h:outputText rendered="#{queryUploadBean.messageRendered}"
						style="color:red" value="#{queryUploadBean.message}"></h:outputText>
				</center>
				<br />
				<center>
					<h:outputText rendered="#{queryUploadBean.tableRendered}"
						style="color:blue"
						value="Executed query: #{queryUploadBean.queryString}"></h:outputText>
				</center>
				<br />
				<div
					style="background-attachment: scroll; overflow: auto; height: 400px; background-repeat: repeat">
					<t:dataTable value="#{queryUploadBean.resultSet}" var="row"
						rendered="#{queryUploadBean.tableRendered}" border="1"
						cellspacing="0" cellpadding="1" width="60%"
						style="align:center;border-bottom-style: solid;
				border-top-style: solid;
				border-left-style: solid;
				border-right-style: solid; width : 60%">
						<t:columns style="color:black;" var="col"
							value="#{dbAccess.displayColumns}"
							rendered="#{queryUploadBean.tableRendered}">
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