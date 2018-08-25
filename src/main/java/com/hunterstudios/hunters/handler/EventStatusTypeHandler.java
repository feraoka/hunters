package com.hunterstudios.hunters.handler;

import com.hunterstudios.hunters.entity.EventStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EventStatusTypeHandler extends BaseTypeHandler<EventStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EventStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public EventStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EventStatus.getEventStatus(rs.getInt(columnName));
    }

    @Override
    public EventStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EventStatus.getEventStatus(rs.getInt(columnIndex));
    }

    @Override
    public EventStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EventStatus.getEventStatus(cs.getInt(columnIndex));
    }
}
