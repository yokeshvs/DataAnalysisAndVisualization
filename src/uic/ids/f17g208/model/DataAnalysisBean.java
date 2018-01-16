package uic.ids.f17g208.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.math3.stat.StatUtils;

import uic.ids.f17g208.connection.DbUtil;

@ManagedBean(name = "dataAnalysisBean")
@SessionScoped
public class DataAnalysisBean {

	private DbAccess dbAccess;
	private DbUtil selectedUtil;
	private ResultSet resultSet;
	private int columnCount;
	private int rowCount;
	private List<DataDescriptionBean> descriptiveAnalysisBeanList;
	private boolean descRender;
	private ArrayList<String> descColumns;
	private String message;
	private boolean messageRendered;
	private int fileNameCount;

	public DataAnalysisBean() {
		descRender = false;
		fileNameCount = 1;
		descColumns = new ArrayList<>();
		descColumns.add("Table");
		descColumns.add("No. of Observations");
		descColumns.add("Column Selected");
		descColumns.add("Minimum Value");
		descColumns.add("Maximum Value");
		descColumns.add("Mean");
		descColumns.add("Median");
		descColumns.add("Variance");
		descColumns.add("Standard Deviation");
		descColumns.add("Q1");
		descColumns.add("Q3");
		descColumns.add("Range");
		descColumns.add("IQR");
		messageRendered = false;
		descriptiveAnalysisBeanList = new ArrayList<DataDescriptionBean>();
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> m = facesContext.getExternalContext().getSessionMap();
		dbAccess = (DbAccess) m.get("dbAccess");
	}

	public DbAccess getDbAccess() {
		return dbAccess;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public DbUtil getSelectedUtil() {
		return selectedUtil;
	}

	public void setSelectedUtil(DbUtil selectedUtil) {
		this.selectedUtil = selectedUtil;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List<DataDescriptionBean> getDescriptiveAnalysisBeanList() {
		return descriptiveAnalysisBeanList;
	}

	public void setDescriptiveAnalysisBeanList(List<DataDescriptionBean> descriptiveAnalysisBeanList) {
		this.descriptiveAnalysisBeanList = descriptiveAnalysisBeanList;
	}

	public boolean isDescRender() {
		return descRender;
	}

	public void setDescRender(boolean descRender) {
		this.descRender = descRender;
	}

	public ArrayList<String> getDescColumns() {
		return descColumns;
	}

	public void setDescColumns(ArrayList<String> descColumns) {
		this.descColumns = descColumns;
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

	public int getFileNameCount() {
		return fileNameCount;
	}

	public void setFileNameCount(int fileNameCount) {
		this.fileNameCount = fileNameCount;
	}

	public String generateDescriptiveStats() {
		try {
			message = "";
			messageRendered = false;
			if (null != descriptiveAnalysisBeanList && !descriptiveAnalysisBeanList.isEmpty()) {
				descriptiveAnalysisBeanList.clear();
			}
			dbAccess.selectUtil();
			selectedUtil = dbAccess.getSelectedUtil();
			if (null != dbAccess.getSelectedColumns() && !dbAccess.getSelectedColumns().isEmpty()) {
				selectedUtil.setQueryString(
						"select " + dbAccess.getSelectedColumns().toString().replace("[", "").replace("]", "")
								+ " from " + dbAccess.getSelectedSchema() + "." + dbAccess.getSelectedTable() + " ;");
				resultSet = selectedUtil.executeSelect();
			} else {
				selectedUtil.setQueryString(
						"select * from " + dbAccess.getSelectedSchema() + "." + dbAccess.getSelectedTable() + " ;");
				resultSet = selectedUtil.executeSelect();
			}

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			columnCount = resultSetMetaData.getColumnCount();
			resultSet.last();
			rowCount = resultSet.getRow();
			resultSet.beforeFirst();

			String columnName;
			for (int j = 1; j < columnCount + 1; j++) {
				List<Double> columnValues = new ArrayList<Double>();
				columnName = resultSetMetaData.getColumnName(j);
				String columnType = resultSetMetaData.getColumnTypeName(j);
				resultSet.beforeFirst();
				while (resultSet.next()) {
					switch (columnType.toLowerCase()) {
					case "int":
						columnValues.add((double) resultSet.getInt(columnName));
						break;
					case "smallint":
						columnValues.add((double) resultSet.getInt(columnName));
						break;
					case "float":
						columnValues.add((double) resultSet.getFloat(columnName));
						break;
					case "double":
						columnValues.add((double) resultSet.getDouble(columnName));
						break;
					case "long":
						columnValues.add((double) resultSet.getLong(columnName));
						break;
					default:
						columnValues.add((double) resultSet.getDouble(columnName));
						break;
					}
				}
				double[] computeValues = new double[columnValues.size()];
				for (int i = 0; i < columnValues.size(); i++) {
					computeValues[i] = (double) columnValues.get(i);
				}

				double min = round(StatUtils.min(computeValues), 100);
				double max = round(StatUtils.max(computeValues), 100);
				double mean = round(StatUtils.mean(computeValues), 100);
				double median = round(StatUtils.percentile(computeValues, 50.0), 100);
				double variance = round(StatUtils.variance(computeValues, mean), 100);
				double standardDeviation = round(Math.sqrt(variance), 100);
				double q1 = round(StatUtils.percentile(computeValues, 25.0), 100);
				double q3 = round(StatUtils.percentile(computeValues, 75.0), 100);
				double iqr = q3 - q1;
				double range = max - min;
				descriptiveAnalysisBeanList.add(new DataDescriptionBean(columnName, min, max, mean, variance,
						standardDeviation, median, q1, q3, iqr, range));
				descRender = true;
			}
			return dbAccess.updateTransaction("Descriptive Stats");

		} catch (SQLException e) {
			message = "Please select only numeric columns for descriptive analysis";
			messageRendered = true;
		} catch (Exception e) {
			message = "Error occured while generating descriptive statistical information";
			messageRendered = true;
		}
		descRender = false;
		return "FAIL";
	}

	private static double round(double value, double precision) {
		return Math.round(value * precision) / precision;
	}

	public String exportToCSV() {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			FileOutputStream fos = null;
			String path = fc.getExternalContext().getRealPath("/temp");
			String fileNameBase = "StatisticalAnalysis_" + dbAccess.getSelectedTable() + "_"
					+ Integer.toString(fileNameCount) + ".csv";
			String fileName = path + "/" + fileNameBase;
			fileNameCount++;
			File f = new File(fileName);
			StringBuffer sb = new StringBuffer();
			fos = new FileOutputStream(fileName);
			for (int i = 0; i < descColumns.size(); i++) {
				sb.append(descColumns.get(i).toString() + ",");
			}
			sb.append("\n");

			for (DataDescriptionBean dataDescriptionBean : descriptiveAnalysisBeanList) {
				sb.append(dbAccess.getSelectedTable() + "," + rowCount + "," + dataDescriptionBean.getColumnSelected()
						+ "," + dataDescriptionBean.getMinValue() + "," + dataDescriptionBean.getMaxValue() + ","
						+ dataDescriptionBean.getMean() + "," + dataDescriptionBean.getMedian() + ","
						+ dataDescriptionBean.getVariance() + "," + dataDescriptionBean.getStd() + ","
						+ dataDescriptionBean.getQ1() + "," + dataDescriptionBean.getQ3() + ","
						+ dataDescriptionBean.getRange() + "," + dataDescriptionBean.getIqr() + ",");
				sb.append("\n");
			}
			fos.write(sb.toString().getBytes());
			fos.flush();
			fos.close();

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
			return dbAccess.updateTransaction("Stats - Export to CSV");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "FAIL";
	}

	public String reset() {
		message = "";
		messageRendered = false;
		descRender = false;
		if (null != descriptiveAnalysisBeanList && !descriptiveAnalysisBeanList.isEmpty()) {
			descriptiveAnalysisBeanList.clear();
		}
		dbAccess.reset();
		return "RESET";
	}

	public String logout() {
		reset();
		dbAccess.logout();
		return "LOGOUT";
	}
}
