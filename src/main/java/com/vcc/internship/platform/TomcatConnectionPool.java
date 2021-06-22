package com.vcc.internship.platform;

import com.vcc.internship.common.config.Properties;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class TomcatConnectionPool implements ConnectionProvider {

    private DataSource ds;

    @Override
    public void init(JdbcConfig jdbcConfig){
        ds = new org.apache.tomcat.jdbc.pool.DataSource(
                new MyPoolProperties(jdbcConfig, JdbcConfigHelper.getConnectionPoolProperties()));
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static class MyPoolProperties extends PoolProperties {

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        public MyPoolProperties(JdbcConfig jdbcConfig, Properties p) {
            this.setTestOnBorrow(p.getBoolProperty("pool.testOnBorrow", true));
            this.setTestOnReturn(p.getBoolProperty("pool.testOnReturn", true));
            this.setTestWhileIdle(p.getBoolProperty("pool.testWhileIdle", true));
            this.setValidationInterval(p.getLongProperty("pool.validationInterval", 10000));
            this.setValidationQuery(p.getProperty("pool.validationQuery", "SELECT 1"));
            this.setTimeBetweenEvictionRunsMillis(p.getIntProperty("pool.timeBetweenEvictionRunsMillis", 60000));
            this.setMinEvictableIdleTimeMillis(p.getIntProperty("pool.minEvictableIdleTimeMillis", 10000));
            this.setMinIdle(p.getIntProperty("pool.minIdle", 2));
            this.setMaxIdle(p.getIntProperty("pool.maxIdle", 2));
            this.setInitialSize(p.getIntProperty("pool.initialSize", 2));
            this.setMaxActive(p.getIntProperty("pool.maxActive", 10));
            this.setMaxWait(p.getIntProperty("pool.maxWait", 6));
            this.setRemoveAbandoned(p.getBoolProperty("pool.removeAbandoned", true));
            this.setRemoveAbandonedTimeout(p.getIntProperty("pool.removeAbandonedTimeout", 600));
            this.setDriverClassName("com.mysql.cj.jdbc.Driver");
            this.setLogAbandoned(false);

            this.setUrl(jdbcConfig.getConnectionUrl());
            this.setUsername(jdbcConfig.getUser());
            this.setPassword(jdbcConfig.getPassword());
        }
    }
}
