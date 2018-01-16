package uic.ids.f17g208.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

@ManagedBean(name = "queryUploadBean")
@SessionScoped
public class QueryUploadBean {

	private DbAccess dbAccess;
	private UploadedFile uploadedFile;
	private String fileLabel;
	private String message;
	private boolean messageRendered;
	private boolean tableRendered;
	private int fileNameCount;
	private int fileDownloadCount;
	private String queryString;
	private ResultSet resultSet;
	private ArrayList<String> fileList;
	private boolean fileListRenderer;
	private File selectedFile;
	private String selectedFileName;

	public QueryUploadBean() {
		message = "";
		messageRendered = false;
		fileNameCount = 1;
		fileDownloadCount = 1;
		fileListRenderer = false;
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> m = facesContext.getExternalContext().getSessionMap();
		dbAccess = (DbAccess) m.get("dbAccess");
		updateFileList();
	}

	public DbAccess getDbAccess() {
		return dbAccess;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isMessageRendered() {
		return messageRendered;
	}

	public void setMessageRendered(boolean messageRendered) {
		this.messageRendered = messageRendered;
	}

	public boolean isTableRendered() {
		return tableRendered;
	}

	public void setTableRendered(boolean tableRendered) {
		this.tableRendered = tableRendered;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public int getFileNameCount() {
		return fileNameCount;
	}

	public void setFileNameCount(int fileNameCount) {
		this.fileNameCount = fileNameCount;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public int getFileDownloadCount() {
		return fileDownloadCount;
	}

	public void setFileDownloadCount(int fileDownloadCount) {
		this.fileDownloadCount = fileDownloadCount;
	}

	public ArrayList<String> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<String> fileList) {
		this.fileList = fileList;
	}

	public boolean isFileListRenderer() {
		return fileListRenderer;
	}

	public void setFileListRenderer(boolean fileListRenderer) {
		this.fileListRenderer = fileListRenderer;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public String getSelectedFileName() {
		return selectedFileName;
	}

	public void setSelectedFileName(String selectedFileName) {
		this.selectedFileName = selectedFileName;
	}

	public void updateFileList() {
		if (null == fileList) {
			fileList = new ArrayList<>();
		} else {
			fileList.clear();
		}
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("/temp");
		File file = new File(path);
		if (null != file) {
			File[] folder = file.listFiles();
			if (null != folder) {
				for (int i = 0; i < folder.length; i++) {
					if (folder[i].isFile()) {
						if (!(FilenameUtils.getExtension(folder[i].getName()).equalsIgnoreCase("csv")
								|| FilenameUtils.getExtension(folder[i].getName()).equalsIgnoreCase("png"))) {
							fileList.add(folder[i].getName());
						}
					}
				}
			}
		}
		if (!fileList.isEmpty()) {
			fileListRenderer = true;
		}else {
			fileListRenderer = false;
		}
	}

	public String processFileUpload() {
		File file = null;
		FileOutputStream fileOutputStream = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRealPath("/temp");
			if (null == fileLabel || fileLabel.length() == 0) {
				message = "Please enter file label";
				messageRendered = true;
				return "FAIL";
			}
			if (null != uploadedFile) {
				file = new File(path + "/" + fileLabel);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(uploadedFile.getBytes());
				fileOutputStream.close();

				fileNameCount++;
				fileLabel = "";
				updateFileList();
				tableRendered = false;
				message = "File uploaded successfully";
				messageRendered = true;
				return dbAccess.updateTransaction("Process File Upload");
			} else {
				message = "Please select the file to upload";
				messageRendered = true;
				return "FAIL";
			}
		} catch (Exception e) {
			message = e.getMessage();
		}
		messageRendered = true;
		return "FAIL";
	}

	public void chooseFile() {
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("/temp");
		File file = new File(path);
		if (null != file) {
			File[] folder = file.listFiles();
			if (null != folder) {
				for (int i = 0; i < folder.length; i++) {
					if (folder[i].isFile()) {
						if (folder[i].getName().equalsIgnoreCase(selectedFileName)) {
							selectedFile = folder[i];
						}
					}
				}
			}
		}
	}

	public String deleteSelectedFile() {
		try {
			if (null != selectedFileName && selectedFileName.length() > 0) {
				chooseFile();
			} else {
				message = "Select a file";
				messageRendered = true;
			}
			if (null != selectedFile) {
				selectedFile.delete();
				tableRendered = false;
				updateFileList();
				message = "File deleted successfully";
				messageRendered = true;
				queryString = "";
				return dbAccess.updateTransaction("Process File Delete");
			}
			message = "File Not found";
		} catch (Exception e) {
			message = e.getMessage();
		}
		messageRendered = true;
		return "FAIL";
	}
	
	public String processSelectedFile() {
		try {
			if (null != selectedFileName && selectedFileName.length() > 0) {
				chooseFile();
			} else {
				message = "Select a file";
				messageRendered = true;
			}

			queryString = "";
			if (null != selectedFile) {
				Scanner scanner = new Scanner(selectedFile);
				while (scanner.hasNext()) {
					queryString += scanner.nextLine();
				}
				scanner.close();
				dbAccess.setSelectedSchema("world");
				dbAccess.selectUtil();
				dbAccess.getSelectedUtil().setQueryString(queryString);
				resultSet = dbAccess.getSelectedUtil().executeSelect();
				if (null == resultSet) {
					message = "ResultSet is empty";
					messageRendered = true;
					return "FAIL";
				}
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				if (null == resultSetMetaData) {
					message = "ResultSetMetadata is empty";
					messageRendered = true;
					return "FAIL";
				}
				ArrayList<String> columnList = new ArrayList<>();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					columnList.add(resultSetMetaData.getColumnLabel(i));
				}
				dbAccess.setDisplayColumns(columnList);
				tableRendered = true;
				message = "File processed successfully";
				messageRendered = true;
				return dbAccess.updateTransaction("Process File Execute");
			}
			message = "File Not found";
		} catch (Exception e) {
			message = e.getMessage();
		}
		messageRendered = true;
		return "FAIL";
	}

	public String processFileDownload() {
		if (tableRendered) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos = null;
				String path = fc.getExternalContext().getRealPath("/temp");
				String fileNameBase = selectedFileName + "_" + Integer.toString(fileDownloadCount) + "" + ".csv";
				String fileName = path + "/" + fileNameBase;
				File f = new File(fileName);
				dbAccess.setSelectedSchema("world");
				dbAccess.selectUtil();
				dbAccess.getSelectedUtil().setQueryString(queryString);
				resultSet = dbAccess.getSelectedUtil().executeSelect();
				if (null == resultSet) {
					message = "ResultSet is empty";
					messageRendered = true;
					return "FAIL";
				}
				Result result = ResultSupport.toResult(resultSet);

				Object[][] sData = result.getRowsByIndex();
				String columnNames[] = result.getColumnNames();
				StringBuffer sb = new StringBuffer();
				try {
					fos = new FileOutputStream(fileName);
					for (int i = 0; i < columnNames.length; i++) {
						sb.append(columnNames[i].toString() + ",");

					}
					sb.append("\n");
					fos.write(sb.toString().getBytes());
					for (int i = 0; i < sData.length; i++) {
						sb = new StringBuffer();
						for (int j = 0; j < sData[0].length; j++) {
							if (sData[i][j] == null) {
								String value2 = "0";
								value2 = value2.replaceAll("[^A-Za-z0-9.]", " . ");
								if (value2.isEmpty()) {
									value2 = "0";
								}
								sb.append(value2 + ",");
							} else {
								String value = sData[i][j].toString();
								if (value.contains(",")) {
									int index = value.indexOf(",");
									String newValue = value.substring(0, index - 1);
									value = newValue + value.substring(index + 1, value.length());
								}
								value = value.replaceAll("[^A-Za-z0-9,.]", " ");
								if (value.isEmpty()) {
									value = "0";
								}
								sb.append(value + ",");
							}
						}
						sb.append("\n");
						fos.write(sb.toString().getBytes());
					}
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					message = e.getMessage();
					messageRendered = true;
					return "FAIL";
				} catch (IOException io) {
					message = io.getMessage();
					messageRendered = true;
					return "FAIL";
				}
				String mimeType = ec.getMimeType(fileName);
				FileInputStream in = null;
				byte b;
				ec.responseReset();
				ec.setResponseContentType(mimeType);
				ec.setResponseContentLength((int) f.length());
				ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileNameBase + "\"");
				try {
					in = new FileInputStream(f);
					OutputStream output = ec.getResponseOutputStream();
					while (true) {
						b = (byte) in.read();
						if (b < 0)
							break;
						output.write(b);
					}
				} catch (IOException e) {
					message = e.getMessage();
					messageRendered = true;
					return "FAIL";
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						message = e.getMessage();
						messageRendered = true;
						return "FAIL";
					}
				}
				fc.responseComplete();
				fileDownloadCount++;
				return dbAccess.updateTransaction("Table - Export to CSV");
			} catch (Exception e) {
				message = e.getMessage();
				messageRendered = true;
				return "FAIL";
			}
		} else {
			message = "Table not available";
			messageRendered = true;
			return "FAIL";
		}
	}

	public String reset() {
		message = "";
		fileNameCount = 1;
		fileDownloadCount = 1;
		fileLabel = "";
		messageRendered = false;
		tableRendered = false;
		dbAccess.reset();
		return "RESET";
	}

	public String logout() {
		reset();
		dbAccess.logout();
		return "LOGOUT";
	}
}
