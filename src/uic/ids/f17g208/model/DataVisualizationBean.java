package uic.ids.f17g208.model;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.math3.stat.StatUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import uic.ids.f17g208.connection.DbUtil;

@ManagedBean(name = "dataVisualizationBean")
@SessionScoped
public class DataVisualizationBean {

	private DbAccess dbAccess;
	private String predictorColumn;
	private String responseColumn;
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private DbUtil selectedUtil;
	private XYSeriesCollection xySeriesCollection;
	private XYSeries predictorColumnSeries;
	private XYSeries responseColumnSeries;
	private String message;
	private boolean messageRendered;
	private String chartPath;
	private boolean renderChart;
	private String scatterPlotChartFile;

	private double predictorMinValue;
	private double predictorMaxValue;
	private double responseMinValue;
	private double responseMaxValue;
	private double predictorMean;
	private double responseMean;
	private XYSeries xySeries;
	private int fileNameCount;

	public DataVisualizationBean() {
		xySeries = new XYSeries("Random");
		fileNameCount = 1;
		xySeriesCollection = new XYSeriesCollection();
		predictorColumnSeries = new XYSeries("Predictor");
		responseColumnSeries = new XYSeries("Response");
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

	public String getPredictorColumn() {
		return predictorColumn;
	}

	public void setPredictorColumn(String predictorColumn) {
		this.predictorColumn = predictorColumn;
	}

	public String getResponseColumn() {
		return responseColumn;
	}

	public void setResponseColumn(String responseColumn) {
		this.responseColumn = responseColumn;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}

	public DbUtil getSelectedUtil() {
		return selectedUtil;
	}

	public void setSelectedUtil(DbUtil selectedUtil) {
		this.selectedUtil = selectedUtil;
	}

	public XYSeriesCollection getXySeriesCollection() {
		return xySeriesCollection;
	}

	public void setXySeriesCollection(XYSeriesCollection xySeriesCollection) {
		this.xySeriesCollection = xySeriesCollection;
	}

	public XYSeries getPredictorColumnSeries() {
		return predictorColumnSeries;
	}

	public void setPredictorColumnSeries(XYSeries predictorColumnSeries) {
		this.predictorColumnSeries = predictorColumnSeries;
	}

	public XYSeries getResponseColumnSeries() {
		return responseColumnSeries;
	}

	public void setResponseColumnSeries(XYSeries responseColumnSeries) {
		this.responseColumnSeries = responseColumnSeries;
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

	public String getChartPath() {
		return chartPath;
	}

	public void setChartPath(String chartPath) {
		this.chartPath = chartPath;
	}

	public boolean isRenderChart() {
		return renderChart;
	}

	public void setRenderChart(boolean renderChart) {
		this.renderChart = renderChart;
	}

	public double getPredictorMinValue() {
		return predictorMinValue;
	}

	public void setPredictorMinValue(double predictorMinValue) {
		this.predictorMinValue = predictorMinValue;
	}

	public double getPredictorMaxValue() {
		return predictorMaxValue;
	}

	public void setPredictorMaxValue(double predictorMaxValue) {
		this.predictorMaxValue = predictorMaxValue;
	}

	public double getResponseMinValue() {
		return responseMinValue;
	}

	public void setResponseMinValue(double responseMinValue) {
		this.responseMinValue = responseMinValue;
	}

	public double getResponseMaxValue() {
		return responseMaxValue;
	}

	public void setResponseMaxValue(double responseMaxValue) {
		this.responseMaxValue = responseMaxValue;
	}

	public double getPredictorMean() {
		return predictorMean;
	}

	public void setPredictorMean(double predictorMean) {
		this.predictorMean = predictorMean;
	}

	public double getResponseMean() {
		return responseMean;
	}

	public void setResponseMean(double responseMean) {
		this.responseMean = responseMean;
	}

	public String getScatterPlotChartFile() {
		return scatterPlotChartFile;
	}

	public void setScatterPlotChartFile(String scatterPlotChartFile) {
		this.scatterPlotChartFile = scatterPlotChartFile;
	}

	public XYSeries getXySeries() {
		return xySeries;
	}

	public void setXySeries(XYSeries xySeries) {
		this.xySeries = xySeries;
	}

	public int getFileNameCount() {
		return fileNameCount;
	}

	public void setFileNameCount(int fileNameCount) {
		this.fileNameCount = fileNameCount;
	}

	public String generateScatterPlot() {

		try {
			xySeriesCollection = new XYSeriesCollection();
			xySeries.clear();
			message = "";
			messageRendered = false;
			dbAccess.selectUtil();
			selectedUtil = dbAccess.getSelectedUtil();
			selectedUtil.setQueryString("select " + predictorColumn + ", " + responseColumn + " from "
					+ dbAccess.getSelectedSchema() + "." + dbAccess.getSelectedTable());
			resultSet = selectedUtil.executeSelect();

			if (null != resultSet) {
				
				ResultSetMetaData metaData = resultSet.getMetaData();
				int resultCount = metaData.getColumnCount();

				for (int i = 1; i <= resultCount; i++) {
					if (Types.VARCHAR == metaData.getColumnType(i) || Types.CHAR == metaData.getColumnType(i)
							|| Types.BOOLEAN == metaData.getColumnType(i)) {
						message = "Please select only numeric values";
						messageRendered = true;
						return "FAIL";
					}
				}
				Result result = ResultSupport.toResult(resultSet);
				Object rowData[][] = result.getRowsByIndex();
				if (rowData != null) {
					double[] predictorArray = new double[rowData.length];
					double[] responseArray = new double[rowData.length];
					for (int r = 0; r < rowData.length; r++) {
						for (int c = 0; c < rowData[0].length; c += 2) {
							if (rowData[r][c].toString().equals("") || rowData[r][c].equals(null))
								predictorArray[r] = 0;
							else
								predictorArray[r] = Double.valueOf(rowData[r][c].toString());
							if (rowData[r][c + 1].toString().equals("") || rowData[r][c].equals(null))
								responseArray[r] = 0;
							else
								responseArray[r] = Double.valueOf(rowData[r][c + 1].toString());
						}
					}
					for (int r = 0; r < responseArray.length; r++)
						xySeries.add(predictorArray[r], responseArray[r]);
					xySeriesCollection.addSeries(xySeries);
					predictorMinValue = StatUtils.min(predictorArray);
					predictorMaxValue = StatUtils.max(predictorArray);
					responseMinValue = StatUtils.min(responseArray);
					responseMaxValue = StatUtils.max(responseArray);
					predictorMean = StatUtils.mean(predictorArray);
					responseMean = StatUtils.mean(responseArray);

					ScatterPlotAnalysis.generateRegressionEquation(predictorMean, responseMean, predictorArray.length,
							predictorArray, responseArray);
					JFreeChart chart = ScatterPlotAnalysis.generateScatterPlot(xySeriesCollection, predictorColumn,
							responseColumn, predictorMinValue, predictorMaxValue, responseMinValue, responseMaxValue);
					FacesContext context = FacesContext.getCurrentInstance();
					String path = context.getExternalContext().getRealPath("/temp");
					scatterPlotChartFile = "/temp/" + predictorColumn + "_" + responseColumn + "_"
							+ dbAccess.getSelectedTable() + "_" + Integer.toString(fileNameCount) + ".png";
					File out = null;
					try {
						out = new File(path + "/" + predictorColumn + "_" + responseColumn + "_"
								+ dbAccess.getSelectedTable() + "_" + Integer.toString(fileNameCount) + ".png");
					} catch (Exception e) {
						message = e.getMessage();
					}
					try {
						ChartUtilities.saveChartAsPNG(out, chart, 600, 450);
					} catch (IOException e) {
						e.printStackTrace();
					}
					renderChart = true;
					fileNameCount++;
					return dbAccess.updateTransaction("Generate Scatter Plot");
				}
			}
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		return "FAIL";
	}

	private void resetSeries() {
		if (null != xySeriesCollection && !xySeriesCollection.getSeries().isEmpty()) {
			xySeriesCollection = new XYSeriesCollection();
		}
		if (null != predictorColumnSeries && !predictorColumnSeries.isEmpty()) {
			predictorColumnSeries.clear();
		}
		if (null != responseColumnSeries && !responseColumnSeries.isEmpty()) {
			responseColumnSeries.clear();
		}
	}

	public String reset() {
		resetSeries();
		return "RESET";
	}

	public String logout() {
		reset();
		dbAccess.logout();
		return "LOGOUT";
	}
}
