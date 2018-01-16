package uic.ids.f17g208.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import uic.ids.f17g208.connection.DbUtil;

@ManagedBean(name = "endPoint")
@SessionScoped
public class EndPoint {
	private String host;
	private String userName;
	private String password;
	private String url;
	private String jdbcDriver;
	private String schema;
	private String dataBaseType;
	private String optionalHost;

	private DbUtil dbUtil;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getOptionalHost() {
		return optionalHost;
	}

	public void setOptionalHost(String optionalHost) {
		this.optionalHost = optionalHost;
	}

	public DbUtil getDbUtil() {
		return dbUtil;
	}

	public void setDbUtil(DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public void initializeDriver() {
		switch (getDataBaseType()) {
		case "MYSQL":
			setJdbcDriver("com.mysql.jdbc.Driver");
		case "DB2":
			setJdbcDriver("com.ibm.db2.jcc.DB2Driver");
		case "Oracle":
			setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		default:
			setJdbcDriver("com.mysql.jdbc.Driver");
		}
	}

	public void initializeURL() {
		switch (getDataBaseType()) {
		case "MYSQL":
			setUrl("jdbc:mysql://" + getHost() + ":3306/" + getSchema() + "?useSSL=false");
		case "DB2":
			setUrl("jdbc:db2://" + getHost() + ":50000/" + getSchema());
		case "Oracle":
			setUrl("jdbc:oracle:thin:@" + getHost() + ":1521:" + getSchema());
		default:
			setUrl("jdbc:mysql://" + getHost() + ":3306/" + getSchema() + "?useSSL=false");
		}
	}

	public void processHost() {
		if (host.equalsIgnoreCase("Other")) {
			host = optionalHost;
		}
	}
}
