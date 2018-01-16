package uic.ids.f17g208.connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;

import uic.ids.f17g208.model.DbAccess;
import uic.ids.f17g208.model.EndPoint;

public class DbUtil {

	private Connection connection;
	private ResultSet resultSet;
	private Statement statement;
	private DbAccess dbAccess;
	private String queryString;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public DbAccess getEndPoint() {
		return dbAccess;
	}

	public void setEndPoint(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Connection getConnection(EndPoint endPoint) throws Exception {
		try {
			Class.forName(endPoint.getJdbcDriver());
			connection = (Connection) DriverManager.getConnection(endPoint.getUrl(), endPoint.getUserName(),
					endPoint.getPassword());
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Exception occured while creating connection");
			throw e;
		}
	}

	public void closeConnection() throws Exception {
		try {
			if (null != getConnection()) {
				if (null != getStatement()) {
					if (null != getResultSet()) {
						getResultSet().close();
					}
					getStatement().close();
				}
				getConnection().close();
			}
		} catch (SQLException e) {
			System.out.println("Exception occured while closing connection");
			throw e;
		}
	}

	public ResultSet executeSelect() throws Exception {
		ResultSet resultSet = null;
		if (null != getConnection()) {
			setStatement(getConnection().createStatement());
			resultSet = getStatement().executeQuery(getQueryString());
		}
		return resultSet;

	}
	
	public boolean executeInsert() throws Exception {
		boolean result = false;
		if (null != getConnection()) {
			setStatement(getConnection().createStatement());
			result = getStatement().execute(getQueryString());
		}
		return result;
	}

	public ArrayList<String> generateTablesList() {
		ArrayList<String> tablesList = new ArrayList<>();
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if (null != databaseMetaData) {
				ResultSet resultSet = databaseMetaData.getTables(null, null, "", null);
				while (resultSet.next()) {
					String tableName = (String) resultSet.getString("TABLE_NAME");
					tablesList.add(tableName);
					
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tablesList;
	}
	
	public ArrayList<String> generateColumnList(String selectedSchema, String selectedTable) {
		ArrayList<String> columnsList = new ArrayList<>();
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if (null != databaseMetaData) {
				ResultSet resultSet = databaseMetaData.getColumns(null, selectedSchema, selectedTable, null);
				while (resultSet.next()) {
					String columnName = (String) resultSet.getString("COLUMN_NAME");
					columnsList.add(columnName);		
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnsList;
	}
}