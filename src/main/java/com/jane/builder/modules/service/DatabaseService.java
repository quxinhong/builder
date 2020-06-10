package com.jane.builder.modules.service;

import com.alibaba.druid.sql.SQLUtils;
import com.jane.builder.common.util.SqlX;
import com.jane.builder.config.Env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private Env env;

    public List<Map<String, Object>> getTables() throws SQLException {
        String sql = "SELECT TABLE_NAME, TABLE_TYPE FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE ()";
        return getTables(sql);
    }

    public List<String> getViews() throws SQLException {
        String sql = "SELECT TABLE_NAME FROM information_schema.VIEWS WHERE TABLE_SCHEMA = DATABASE ()";
        return getNames(sql);
    }

    public List<String> getFunctions() throws SQLException {
        String sql = "SELECT SPECIFIC_NAME FROM information_schema.ROUTINES WHERE ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_SCHEMA = DATABASE ()";
        return getNames(sql);
    }

    public List<String> getProcedures() throws SQLException {
        String sql = "SELECT SPECIFIC_NAME FROM information_schema.ROUTINES WHERE ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_SCHEMA = DATABASE ()";
        return getNames(sql);
    }

    public List<String> getTriggers() throws SQLException {
        String sql = "SELECT TRIGGER_NAME FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE ()";
        return getNames(sql);
    }

    private List<Map<String, Object>> getTables(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> res = new ArrayList<>();
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cls = resultSetMetaData.getColumnCount();
            while (resultSet.next()){
            	Map<String, Object> row = new HashMap<>();
            	for(int i=1; i<=cls; i++) {
            		row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
            	}
                res.add(row);
            }
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
            SqlX.close(statement);
            SqlX.close(connection);
        }
        return res;
    }
    
    private List<String> getNames(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> res = new ArrayList<>();
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            while (resultSet.next()){
                res.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
            SqlX.close(statement);
            SqlX.close(connection);
        }
        return res;
    }

    public List<Object[]> readTable(String table) throws SQLException {
        String sql = "SELECT * FROM `"+table+"`";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Object[]> res = new ArrayList<>();
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cls = resultSetMetaData.getColumnCount();
            Object[] header = new Object[cls];
            for(int i=0; i<cls; i++){
                header[i] = resultSetMetaData.getColumnLabel(i+1);
            }
            res.add(header);
            while (resultSet.next()){
                Object[] row = new Object[cls];
                for(int i=0; i<cls; i++){
                    row[i] = resultSet.getObject(i+1);
                }
                res.add(row);
            }
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
            SqlX.close(statement);
            SqlX.close(connection);
        }
        return res;
    }

    public String getFunctionDefinition(String function) throws SQLException {
        String sql = "SELECT ROUTINE_DEFINITION FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND SPECIFIC_NAME = ?";
        return getDefinition(sql, function);
    }

    public String getViewDefinition(String view) throws SQLException {
        String sql = "SELECT VIEW_DEFINITION FROM information_schema.VIEWS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        return getDefinition(sql, view);
    }

    public String getTriggerDefinition(String trigger) throws SQLException {
        String sql = "SELECT ACTION_STATEMENT FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE () AND TABLE_NAME = ?";
        return getDefinition(sql, trigger);
    }

    private String getDefinition(String sql, String name) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String res = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, name);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                res = resultSet.getString(1);
            }
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
            SqlX.close(statement);
            SqlX.close(connection);
        }
        return SQLUtils.format(res, env.getDbType());
    }
}
