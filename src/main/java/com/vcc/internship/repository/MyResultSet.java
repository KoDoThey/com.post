package com.vcc.internship.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MyResultSet  {
    public ResultSet rs;

    public MyResultSet(ResultSet rs) {
        this.rs = rs;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getString(String title) throws SQLException {
        String res = this.rs.getString(title);
        if (this.rs.wasNull()) {
            res = null;
        }
        return res;
    }

    public Timestamp getTimestamp(String column) throws SQLException {
        Timestamp res = this.rs.getTimestamp(column);
        if (this.rs.wasNull()) {
            res = null;
        }
        return res;
    }

    public Integer getInteger(String column) throws SQLException {
        Integer res = this.rs.getInt(column);
        if (this.rs.wasNull()) {
            res = null;
        }
        return res;
    }

    public Boolean getBoolean(String column) throws SQLException {
        Boolean res = this.rs.getBoolean(column);
        if (this.rs.wasNull()) {
            res = null;
        }
        return res;
    }

    public boolean next() throws SQLException {
        return this.rs.next();
    }

    public boolean first() throws SQLException {
        return this.rs.first();
    }
}
