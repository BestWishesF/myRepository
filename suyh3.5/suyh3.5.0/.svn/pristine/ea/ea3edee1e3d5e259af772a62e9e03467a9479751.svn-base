package cn.hotol.wechat.domain.mybatis;

import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.Date;

/**
 * login: Hill Pan
 * Date: 6/20/12
 * Time: 5:03 PM
 */
@Alias("w13DateTypeHandler")
@MappedTypes(Date.class)
public class W13DateTypeHandler extends BaseTypeHandler<Date> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setTimestamp(i, new Timestamp((parameter).getTime()));
	}

	@Override
	public Date getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
		if (sqlTimestamp != null) {
			return new Date(sqlTimestamp.getTime());
		}
		return null;
	}


	public Date getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
		if (sqlTimestamp != null) {
			return new Date(sqlTimestamp.getTime());
		}
		return null;
	}

	@Override
	public Date getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
		if (sqlTimestamp != null) {
			return new Date(sqlTimestamp.getTime());
		}
		return null;
	}

	@Override
	public Date getResult(ResultSet rs, String columnName) throws SQLException {
		try {
			String value = rs.getString(columnName);
			if (value == null || value.equals("0000-00-00")) {
				return null;
			} else {
				return super.getResult(rs, columnName);
			}
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
		try {
			String value = cs.getString(columnIndex);
			if (value == null || value.equals("0000-00-00")) {
				return null;
			} else {
				return super.getResult(cs, columnIndex);
			}
		} catch (SQLException e) {
			return null;
		}
	}
}
