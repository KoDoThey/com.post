package com.vcc.internship.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyResultSet  {
    public ResultSet rs;

    public MyResultSet(ResultSet executeQuery) {
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

    public boolean next() throws SQLException {
        return this.rs.next();
    }

    public boolean first() throws SQLException {
        return this.rs.first();
    }
}
