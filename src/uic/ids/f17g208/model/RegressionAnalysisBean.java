package uic.ids.f17g208.model;

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

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@ManagedBean
@SessionScoped
public class RegressionAnalysisBean {

	private String message;
	private boolean messageRendered;
	private String predictorColumn;
	private String responseColumn;
	private XYSeries predictorSeries;
	private XYSeries responseSeries;
	private XYSeries xySeries;
	private XYSeriesCollection xySeriesVariable;
	private XYSeriesCollection xyTimeSeriesCollection;

	private double intercept;
	private double interceptStandardError;
	private double tStatistic;
	private double interceptPValue;
	private double slope;
	private double predictorDF;
	private double residualErrorDF;
	private double totalDF;
	private double regressionSumSquares;
	private double sumSquaredErrors;
	private double totalSumSquares;
	private double meanSquare;
	private double meanSquareError;
	private double fValue;
	private double pValue;
	private double slopeStandardError;
	private double tStatisticPredictor;
	private double pValuePredictor;
	private double standardErrorModel;
	private double rSquare;
	private double rSquareAdjusted;

	private String regressionEquation;
	private boolean renderRegressionResult;
	private boolean renderNumberOfObservations;
	private boolean renderNumberOfColumns;
	private DbAccess dbAccess;
	private ResultSet resultSet;

	public RegressionAnalysisBean() {
		xySeries = new XYSeries("Random");
		xySeriesVariable = new XYSeriesCollection();
		xyTimeSeriesCollection = new XYSeriesCollection();
		predictorSeries = new XYSeries("Predictor");
		responseSeries = new XYSeries("Response");
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> m = facesContext.getExternalContext().getSessionMap();
		dbAccess = (DbAccess) m.get("dbAccess");
	}

	public String getPredictorColumn() {
		return predictorColumn;
	}

	public String getResponseColumn() {
		return responseColumn;
	}

	public void setPredictorColumn(String predictorValue) {
		this.predictorColumn = predictorValue;
	}

	public void setResponseColumn(String responseValue) {
		this.responseColumn = responseValue;
	}

	public XYSeries getPredictorSeries() {
		return predictorSeries;
	}

	public void setPredictorSeries(XYSeries predictorSeries) {
		this.predictorSeries = predictorSeries;
	}

	public XYSeries getResponseSeries() {
		return responseSeries;
	}

	public void setResponseSeries(XYSeries responseSeries) {
		this.responseSeries = responseSeries;
	}

	public XYSeries getXySeries() {
		return xySeries;
	}

	public void setXySeries(XYSeries xySeries) {
		this.xySeries = xySeries;
	}

	public XYSeriesCollection getXySeriesVariable() {
		return xySeriesVariable;
	}

	public void setXySeriesVariable(XYSeriesCollection xySeriesVariable) {
		this.xySeriesVariable = xySeriesVariable;
	}

	public XYSeriesCollection getXyTimeSeriesCollection() {
		return xyTimeSeriesCollection;
	}

	public void setXyTimeSeriesCollection(XYSeriesCollection xyTimeSeriesCollection) {
		this.xyTimeSeriesCollection = xyTimeSeriesCollection;
	}

	public double getIntercept() {
		return intercept;
	}

	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}

	public double getInterceptStandardError() {
		return interceptStandardError;
	}

	public void setInterceptStandardError(double interceptStandardError) {
		this.interceptStandardError = interceptStandardError;
	}

	public double gettStatistic() {
		return tStatistic;
	}

	public void settStatistic(double tStatistic) {
		this.tStatistic = tStatistic;
	}

	public double getInterceptPValue() {
		return interceptPValue;
	}

	public void setInterceptPValue(double interceptPValue) {
		this.interceptPValue = interceptPValue;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public double getPredictorDF() {
		return predictorDF;
	}

	public void setPredictorDF(double predictorDF) {
		this.predictorDF = predictorDF;
	}

	public double getResidualErrorDF() {
		return residualErrorDF;
	}

	public void setResidualErrorDF(double residualErrorDF) {
		this.residualErrorDF = residualErrorDF;
	}

	public double getTotalDF() {
		return totalDF;
	}

	public void setTotalDF(double totalDF) {
		this.totalDF = totalDF;
	}

	public double getRegressionSumSquares() {
		return regressionSumSquares;
	}

	public void setRegressionSumSquares(double regressionSumSquares) {
		this.regressionSumSquares = regressionSumSquares;
	}

	public double getSumSquaredErrors() {
		return sumSquaredErrors;
	}

	public void setSumSquaredErrors(double sumSquaredErrors) {
		this.sumSquaredErrors = sumSquaredErrors;
	}

	public double getTotalSumSquares() {
		return totalSumSquares;
	}

	public void setTotalSumSquares(double totalSumSquares) {
		this.totalSumSquares = totalSumSquares;
	}

	public double getMeanSquare() {
		return meanSquare;
	}

	public void setMeanSquare(double meanSquare) {
		this.meanSquare = meanSquare;
	}

	public double getMeanSquareError() {
		return meanSquareError;
	}

	public void setMeanSquareError(double meanSquareError) {
		this.meanSquareError = meanSquareError;
	}

	public double getfValue() {
		return fValue;
	}

	public void setfValue(double fValue) {
		this.fValue = fValue;
	}

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}

	public double getSlopeStandardError() {
		return slopeStandardError;
	}

	public void setSlopeStandardError(double slopeStandardError) {
		this.slopeStandardError = slopeStandardError;
	}

	public double gettStatisticPredictor() {
		return tStatisticPredictor;
	}

	public void settStatisticPredictor(double tStatisticPredictor) {
		this.tStatisticPredictor = tStatisticPredictor;
	}

	public double getpValuePredictor() {
		return pValuePredictor;
	}

	public void setpValuePredictor(double pValuePredictor) {
		this.pValuePredictor = pValuePredictor;
	}

	public double getStandardErrorModel() {
		return standardErrorModel;
	}

	public void setStandardErrorModel(double standardErrorModel) {
		this.standardErrorModel = standardErrorModel;
	}

	public double getrSquare() {
		return rSquare;
	}

	public void setrSquare(double rSquare) {
		this.rSquare = rSquare;
	}

	public double getrSquareAdjusted() {
		return rSquareAdjusted;
	}

	public void setrSquareAdjusted(double rSquareAdjusted) {
		this.rSquareAdjusted = rSquareAdjusted;
	}

	public String getRegressionEquation() {
		return regressionEquation;
	}

	public void setRegressionEquation(String regressionEquation) {
		this.regressionEquation = regressionEquation;
	}

	public boolean isRenderRegressionResult() {
		return renderRegressionResult;
	}

	public void setRenderRegressionResult(boolean renderRegressionResult) {
		this.renderRegressionResult = renderRegressionResult;
	}

	public boolean isRenderNumberOfObservations() {
		return renderNumberOfObservations;
	}

	public void setRenderNumberOfObservations(boolean renderNumberOfObservations) {
		this.renderNumberOfObservations = renderNumberOfObservations;
	}

	public boolean isRenderNumberOfColumns() {
		return renderNumberOfColumns;
	}

	public void setRenderNumberOfColumns(boolean renderNumberOfColumns) {
		this.renderNumberOfColumns = renderNumberOfColumns;
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

	public DbAccess getDbAccess() {
		return dbAccess;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public String generateRegressionReport() {
		try {
			message = "";
			messageRendered = false;
			responseSeries.clear();
			predictorSeries.clear();
			xySeries.clear();
			xySeriesVariable.removeAllSeries();
			xyTimeSeriesCollection.removeAllSeries();

			dbAccess.selectUtil();
			dbAccess.getSelectedUtil().setQueryString("select " + predictorColumn + "," + responseColumn + " from "
					+ dbAccess.getSelectedSchema() + "." + dbAccess.getSelectedTable() + ";");
			resultSet = dbAccess.getSelectedUtil().executeSelect();

			resultSet = dbAccess.getSelectedUtil().executeSelect();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int resultCount = metaData.getColumnCount();

			for (int i = 1; i <= resultCount; i++) {
				if (Types.VARCHAR == metaData.getColumnType(i) || Types.CHAR == metaData.getColumnType(i)
						|| Types.BOOLEAN == metaData.getColumnType(i)) {
					renderRegressionResult = false;
					renderNumberOfColumns = false;
					renderNumberOfObservations = false;
					message = "Please select only numeric values";
					messageRendered = true;
					return "FAIL";
				}
			}

			if (null != resultSet) {
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

					for (int i = 0; i < predictorArray.length; i++) {
						predictorSeries.add(i + 1, (double) predictorArray[i]);
						responseSeries.add(i + 1, (double) responseArray[i]);
					}

					xyTimeSeriesCollection.addSeries(predictorSeries);
					xyTimeSeriesCollection.addSeries(responseSeries);
					SimpleRegression sr = new SimpleRegression();
					if (responseArray.length > predictorArray.length) {
						for (int i = 0; i < predictorArray.length; i++) {
							sr.addData(predictorArray[i], responseArray[i]);
							xySeries.add(predictorArray[i], responseArray[i]);
						}
					} else {
						for (int i = 0; i < responseArray.length; i++) {
							sr.addData(predictorArray[i], responseArray[i]);
							xySeries.add(predictorArray[i], responseArray[i]);
						}
					}
					xySeriesVariable.addSeries(xySeries);
					totalDF = responseArray.length - 1;
					TDistribution tDistribution = new TDistribution(totalDF);
					intercept = sr.getIntercept();
					interceptStandardError = sr.getInterceptStdErr();
					tStatistic = 0;
					predictorDF = 1;
					residualErrorDF = totalDF - predictorDF;
					rSquare = sr.getRSquare();
					rSquareAdjusted = rSquare - (1 - rSquare) / (totalDF - predictorDF - 1);
					if (interceptStandardError != 0) {
						tStatistic = (double) intercept / interceptStandardError;
					} else
						tStatistic = Double.NaN;
					interceptPValue = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatistic));
					slope = sr.getSlope();
					slopeStandardError = sr.getSlopeStdErr();
					double tStatisticpredict = 0;
					if (slopeStandardError != 0) {
						tStatisticpredict = (double) slope / slopeStandardError;
					}
					pValuePredictor = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatisticpredict));
					standardErrorModel = Math.sqrt(StatUtils.variance(responseArray))
							* (Math.sqrt(1 - rSquareAdjusted));
					regressionSumSquares = sr.getRegressionSumSquares();
					sumSquaredErrors = sr.getSumSquaredErrors();
					totalSumSquares = sr.getTotalSumSquares();
					meanSquare = 0;
					if (predictorDF != 0) {
						meanSquare = regressionSumSquares / predictorDF;
					}
					meanSquareError = 0;
					if (residualErrorDF != 0) {
						meanSquareError = (double) (sumSquaredErrors / residualErrorDF);
					}
					fValue = 0;
					if (meanSquareError != 0) {
						fValue = meanSquare / meanSquareError;
					}
					regressionEquation = responseColumn + " = " + intercept + " + (" + slope + ") " + predictorColumn;
					FDistribution fDistribution = new FDistribution(predictorDF, residualErrorDF);
					pValue = (double) (1 - fDistribution.cumulativeProbability(fValue));
					renderRegressionResult = true;
					renderNumberOfColumns = true;
					renderNumberOfObservations = true;
					return dbAccess.updateTransaction("Generate Regression Analysis");
				} else {

					return "FAIL";
				}
			}

			return "FAIL";

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			messageRendered = true;
		}
		return "FAIL";
	}

	public String reset() {
		message = "";
		messageRendered = false;
		responseSeries.clear();
		predictorSeries.clear();
		xySeries.clear();
		xySeriesVariable.removeAllSeries();
		xyTimeSeriesCollection.removeAllSeries();

		dbAccess.reset();
		return "RESET";
	}

	public String logout() {
		reset();
		dbAccess.logout();
		return "LOGOUT";
	}

}
