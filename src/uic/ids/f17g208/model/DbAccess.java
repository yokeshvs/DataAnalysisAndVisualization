package uic.ids.f17g208.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import uic.ids.f17g208.connection.DbUtil;

@ManagedBean(name = "dbAccess")
@SessionScoped
public class DbAccess {

	private EndPoint endPoint;
	private DbUtil worldUtil;
	private DbUtil customUtil;
	private DbUtil selectedUtil;
	private ArrayList<String> worldTables;
	private ArrayList<String> customTables;
	private ArrayList<String> menuTables;
	private String sqlErrorMessage;
	private String sqlErrorCode;
	private String sqlState;
	private int fileNameCount;
	private boolean tableRendered;
	private boolean columnRendered;
	private boolean tableMenuRendered;
	private String selectedTable;
	private String selectedSchema;
	private ArrayList<String> menuColumns;
	private ArrayList<String> displayColumns;
	private ArrayList<String> selectedColumns;
	private ResultSet resultSet;
	private String updateQuery;

	public DbAccess() {
		super();
		fileNameCount = 1;
		worldUtil = new DbUtil();
		customUtil = new DbUtil();
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> m = facesContext.getExternalContext().getSessionMap();
		endPoint = (EndPoint) m.get("endPoint");
	}

	public EndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}

	public DbUtil getWorldUtil() {
		return worldUtil;
	}

	public void setWorldUtil(DbUtil worldUtil) {
		this.worldUtil = worldUtil;
	}

	public String getSqlErrorMessage() {
		return sqlErrorMessage;
	}

	public void setSqlErrorMessage(String sqlErrorMessage) {
		this.sqlErrorMessage = sqlErrorMessage;
	}

	public String getSqlErrorCode() {
		return sqlErrorCode;
	}

	public void setSqlErrorCode(String sqlErrorCode) {
		this.sqlErrorCode = sqlErrorCode;
	}

	public String getSqlState() {
		return sqlState;
	}

	public void setSqlState(String sqlState) {
		this.sqlState = sqlState;
	}

	public DbUtil getCustomUtil() {
		return customUtil;
	}

	public void setCustomUtil(DbUtil customUtil) {
		this.customUtil = customUtil;
	}

	public ArrayList<String> getWorldTables() {
		return worldTables;
	}

	public void setWorldTables(ArrayList<String> worldTables) {
		this.worldTables = worldTables;
	}

	public ArrayList<String> getCustomTables() {
		return customTables;
	}

	public void setCustomTables(ArrayList<String> customTables) {
		this.customTables = customTables;
	}

	public boolean isTableRendered() {
		return tableRendered;
	}

	public void setTableRendered(boolean tableRendered) {
		this.tableRendered = tableRendered;
	}

	public boolean isColumnRendered() {
		return columnRendered;
	}

	public void setColumnRendered(boolean columnRendered) {
		this.columnRendered = columnRendered;
	}

	public String getSelectedTable() {
		return selectedTable;
	}

	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}

	public String getSelectedSchema() {
		return selectedSchema;
	}

	public void setSelectedSchema(String selectedSchema) {
		this.selectedSchema = selectedSchema;
	}

	public ArrayList<String> getMenuTables() {
		return menuTables;
	}

	public void setMenuTables(ArrayList<String> menuTables) {
		this.menuTables = menuTables;
	}

	public boolean isTableMenuRendered() {
		return tableMenuRendered;
	}

	public void setTableMenuRendered(boolean tableMenuRendered) {
		this.tableMenuRendered = tableMenuRendered;
	}

	public ArrayList<String> getSelectedColumns() {
		return selectedColumns;
	}

	public void setSelectedColumns(ArrayList<String> selectedColumns) {
		this.selectedColumns = selectedColumns;
	}

	public ArrayList<String> getMenuColumns() {
		return menuColumns;
	}

	public void setMenuColumns(ArrayList<String> menuColumns) {
		this.menuColumns = menuColumns;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public ArrayList<String> getDisplayColumns() {
		return displayColumns;
	}

	public void setDisplayColumns(ArrayList<String> displayColumns) {
		this.displayColumns = displayColumns;
	}

	public DbUtil getSelectedUtil() {
		return selectedUtil;
	}

	public void setSelectedUtil(DbUtil selectedUtil) {
		this.selectedUtil = selectedUtil;
	}

	public int getFileNameCount() {
		return fileNameCount;
	}

	public void setFileNameCount(int fileNameCount) {
		this.fileNameCount = fileNameCount;
	}

	public String getUpdateQuery() {
		return updateQuery;
	}

	public void setUpdateQuery(String updateQuery) {
		this.updateQuery = updateQuery;
	}

	public String login() {
		endPoint.processHost();
		endPoint.initializeDriver();
		try {
			sqlErrorMessage = "";
			sqlErrorCode = "";
			sqlState = "";
			endPoint.setSchema("world");
			endPoint.initializeURL();
			Connection worldConnection = getWorldUtil().getConnection(endPoint);

			endPoint.setSchema("f17x321");
			endPoint.initializeURL();
			Connection customConnection = getCustomUtil().getConnection(endPoint);

			if (null != worldConnection && null != customConnection) {
				generateTables();
				return updateTransaction("Login");
			}
		} catch (SQLException e) {
			sqlErrorMessage = e.getMessage();
			sqlErrorCode = Integer.toString(e.getErrorCode());
			sqlState = e.getSQLState();
		} catch (Exception e) {
			e.printStackTrace();
			sqlErrorMessage = e.getMessage();
			sqlErrorCode = "INVALID";
			sqlState = "INVALID";
		}
		return "FAIL";
	}

	private void generateTables() {
		worldTables = getWorldUtil().generateTablesList();
		customTables = getCustomUtil().generateTablesList();
	}

	public String showTableMenu() {
		if (selectedSchema.equalsIgnoreCase("world")) {
			menuTables = worldTables;
		} else {
			menuTables = customTables;
		}
		tableMenuRendered = true;
		return "SUCCESS";
	}

	public String showTableColumns() {
		try {
			selectUtil();
			menuColumns = selectedUtil.generateColumnList(selectedSchema, selectedTable);
			columnRendered = true;
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
		}
		columnRendered = false;
		return "FAIL";
	}

	public void selectUtil() {
		if (selectedSchema.equalsIgnoreCase("world")) {
			selectedUtil = getWorldUtil();
		} else {
			selectedUtil = getCustomUtil();
		}
	}

	public String showCompleteTable() {
		try {
			selectUtil();
			selectedUtil.setQueryString("select * from " + selectedSchema + "." + selectedTable + " ;");
			resultSet = selectedUtil.executeSelect();
			showTableColumns();
			displayColumns = menuColumns;
			columnRendered = false;
			tableRendered = true;
			if (null != selectedColumns && !selectedColumns.isEmpty()) {
				selectedColumns.clear();
			}
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableRendered = false;
		columnRendered = false;
		return "FAIL";
	}

	public String showSelectedColumnTable() {
		try {
			if (!selectedColumns.isEmpty()) {
				selectUtil();
				selectedUtil.setQueryString("select " + selectedColumns.toString().replace("[", "").replace("]", "")
						+ " from " + selectedSchema + "." + selectedTable + " ;");
				resultSet = selectedUtil.executeSelect();
				displayColumns = selectedColumns;
				tableRendered = true;
				return "SUCCESS";
			}
		} catch (Exception e) {

		}
		tableRendered = false;
		columnRendered = false;
		return "FAIL";
	}

	public String processFileDownload() {
		if (selectedTable.length() != 0) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				FileOutputStream fos = null;
				String path = fc.getExternalContext().getRealPath("/temp");
				String fileNameBase = selectedSchema + "_" + selectedTable + "_" + Integer.toString(fileNameCount)
						+ ".csv";
				String fileName = path + "/" + fileNameBase;
				File f = new File(fileName);

				if (null != selectedColumns && !selectedColumns.isEmpty()) {
					selectUtil();
					selectedUtil.setQueryString("select " + selectedColumns.toString().replace("[", "").replace("]", "")
							+ " from " + selectedSchema + "." + selectedTable + " ;");
					resultSet = selectedUtil.executeSelect();
				} else {
					selectedUtil.setQueryString("select * from " + selectedSchema + "." + selectedTable + " ;");
					resultSet = selectedUtil.executeSelect();
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
				} catch (IOException io) {
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

				} finally {
					try {
						in.close();
					} catch (IOException e) {

					}
				}
				fc.responseComplete();
				fileNameCount++;
				return updateTransaction("Table - Export to CSV");
			} catch (Exception e) {
				e.printStackTrace();
				return "FAIL";
			}
		} else {
			return "FAIL";

		}
	}

	public String getUpdateQuery(String activity) {
		try {
			String base = "INSERT into f17x321.f17g208_transactionlog (userName, ipAddress, activity, date) VALUES (";
			String input = "\"" + getEndPoint().getUserName() + "\",\""
					+ Inet4Address.getLocalHost().getHostAddress().toString() + "\",\"" + activity + "\",\""
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\")";
			updateQuery = base + input;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateQuery;
	}

	public String updateTransaction(String activity) {
		try {
			String tempSchema = getSelectedSchema();
			setSelectedSchema("f17x321");
			selectUtil();
			getSelectedUtil().setQueryString(getUpdateQuery(activity));
			getSelectedUtil().executeInsert();
			setSelectedSchema(tempSchema);
		} catch (Exception e) {
			e.printStackTrace();
			return "LOG FAIL";
		}
		return "SUCCESS";
	}

	public String reset() {
		selectedTable = "";
		if (null != selectedColumns && !selectedColumns.isEmpty()) {
			selectedColumns.clear();
		}
		if (null != displayColumns && !displayColumns.isEmpty()) {
			displayColumns.clear();
		}
		if (null != menuColumns && !menuColumns.isEmpty()) {
			menuColumns.clear();
		}
		columnRendered = false;
		tableRendered = false;
		tableMenuRendered = false;
		selectedSchema = "";

		return "RESET";
	}

	public String logout() {
		reset();
		try {
			updateTransaction("Logout");
			getWorldUtil().closeConnection();
			getCustomUtil().closeConnection();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "LOGOUT";
	}
}
