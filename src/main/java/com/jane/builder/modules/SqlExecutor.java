package com.jane.builder.modules;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.jane.builder.common.util.SqlX;
import com.jane.builder.config.Env;
import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.model.StepModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class SqlExecutor {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Env env;
    
    public ExcuteResult execute(String script){
        List<SQLStatement> stmtList = SQLUtils.toStatementList(script, env.getDbType());
        Connection con = null;
        Statement st = null;
        List<String> infos = new ArrayList<>();
        List<List<Object[]>> results = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            for (SQLStatement sqlStatement: stmtList) {
                String sql = sqlStatement.toString();
                infos.add(sql);
                long start = System.currentTimeMillis();
                st.execute(sql);
                int count = st.getUpdateCount();
                if(count==-1){
                    infos.add("OK, time: "+(System.currentTimeMillis()-start)+"ms");
                }else{
                    infos.add("OK, Affected rows: "+count+", time: "+(System.currentTimeMillis()-start)+"ms");
                }
                do {
                    ResultSet resultSet = st.getResultSet();
                    if(resultSet!=null){
                        List<Object[]> result = new ArrayList<>();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int cls = resultSetMetaData.getColumnCount();
                        Object[] header = new Object[cls];
                        for(int i=0; i<cls; i++){
                            header[i] = resultSetMetaData.getColumnLabel(i+1);
                        }
                        result.add(header);
                        while (resultSet.next()){
                            Object[] row = new Object[cls];
                            for(int i=0; i<cls; i++){
                                row[i] = resultSet.getObject(i+1);
                            }
                            result.add(row);
                        }
                        results.add(result);
                    }
                }while (st.getMoreResults());
            }
            ExcuteResult res = new ExcuteResult();
            res.setExcuteInfos(infos);
            res.setResults(results);
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            infos.add(e.getMessage());
            ExcuteResult res = new ExcuteResult();
            res.setExcuteInfos(infos);
            res.setResults(results);
            return res;
        }finally {
            SqlX.close(st);
            SqlX.close(con);
        }
    }

    public List<Map<String, Object>> excuteStdQuery(String sql, List<Object> params) throws SQLException {
        Connection connection = null;
        List<Map<String, Object>> res = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0; i<params.size(); i++){
            	preparedStatement.setObject(i+1, params.get(i));
            }
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            res = readResult(resultSet);
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
            SqlX.close(preparedStatement);
            SqlX.close(connection);
        }
        return res;
    }

    public Map<String, List<Map<String, Object>>> standardQuery(List<StepModel> steps, Map<String, List<Map<String, Object>>> params){
    	
    	return null;
    }

    public Map<String, List<Map<String, Object>>> executeStepsInTransaction(List<StepModel> steps, Map<String, List<Map<String, Object>>> params) throws SQLException {
        return executeSteps(steps, params, false);
    }

    public Map<String, List<Map<String, Object>>> executeStepsRollback(List<StepModel> steps, Map<String, List<Map<String, Object>>> params) throws SQLException {
        return executeSteps(steps, params, true);
    }

    public Map<String, List<Map<String, Object>>> executeSteps(List<StepModel> steps, Map<String, List<Map<String, Object>>> params, boolean mustRollback) throws SQLException {
        Connection connection = null;
        Map<String, List<Map<String, Object>>> res = null;
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            List<List<Map<String, Object>>> results = new ArrayList<>();
            for (StepModel stepModel : steps) {
                String script = stepModel.getScript();
                List<SQLStatement> sqlStatements = SQLUtils.toStatementList(script, env.getDbType());
                List<Map<String, Object>> stepParams = params.get(stepModel.getStepNo());
                for (SQLStatement sqlStatement : sqlStatements) {
                    String sql = SqlX.filterNotes(sqlStatement.toString());
                    List<String> fields = new ArrayList<>();
                    sql = SqlX.replacePlaceholders(sql, fields);
                    prepareExcute(connection, sql, stepParams, fields, results, stepModel.getBatch());
                }
            }
            res = new HashMap<>();
            for(int i=0; i<results.size(); i++){
                res.put("list"+i, results.get(i));
            }
        }catch (SQLException e){
            if(!mustRollback){
                connection.rollback();
            }
            throw e;
        }finally {
            if(mustRollback){
                connection.rollback();
            }
            connection.setAutoCommit(true);
            SqlX.close(connection);
        }
        return res;
    }

    public Map<String, List<Map<String, Object>>> getMateDataAndSetKeyMap(
            List<StepModel> steps,
            Map<String, List<Map<String, Object>>> params,
            Map<String, Object> keyMap) throws SQLException {
        Connection connection = null;
        Map<String, List<Map<String, Object>>> res = null;
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            List<List<Map<String, Object>>> results = new ArrayList<>();
            for (StepModel stepModel : steps) {
                String script = stepModel.getScript();
                System.out.println(script);
                List<SQLStatement> sqlStatements = SQLUtils.toStatementList(script, env.getDbType());
                List<Map<String, Object>> stepParams = params.get(stepModel.getStepNo());
                for (SQLStatement sqlStatement : sqlStatements) {
                    System.out.println(sqlStatement.toString());
                    String sql = SqlX.filterNotes(sqlStatement.toString());
                    List<String> fields = new ArrayList<>();
                    sql = SqlX.replacePlaceholders(sql, fields);
                    prepareExcuteGetMateData(connection, sql, stepParams, fields, results, keyMap, stepModel.getBatch());
                }
            }
            res = new HashMap<>();
            for(int i=0; i<results.size(); i++){
                res.put("list"+i, results.get(i));
            }
        }catch (SQLException e){
            throw e;
        }finally {
            connection.rollback();
            connection.setAutoCommit(true);
            SqlX.close(connection);
        }
        return res;
    }

    private void prepareExcute(
            Connection connection,
            String sql,
            List<Map<String, Object>> params,
            List<String> fields,
            List<List<Map<String, Object>>> result,
            Boolean batch) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(batch){
                for (Map<String, Object> param : params) {
                    for(int i=0; i<fields.size(); i++){
                        preparedStatement.setObject(i+1, param.get(fields.get(i)));
                    }
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }else{
                if(params!=null&&params.size()>0){
                    Map<String, Object> param = params.get(0);
                    for(int i=0; i<fields.size(); i++){
                        preparedStatement.setObject(i+1, param.get(fields.get(i)));
                    }
                }
                preparedStatement.execute();
            }
            readResultBatch(preparedStatement, result);
        }catch (Exception e){
            throw e;
        }finally {
            SqlX.close(preparedStatement);
        }
    }

    private void readResultBatch(PreparedStatement preparedStatement, List<List<Map<String, Object>>> result) throws SQLException {
        ResultSet resultSet = null;
        try{
            do {
                resultSet = preparedStatement.getResultSet();
                if(resultSet!=null){
                    result.add(readResult(resultSet));
                    resultSet.close();
                }
            }while (preparedStatement.getMoreResults());
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
        }
    }

    private List<Map<String, Object>> readResult(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int cls = resultSetMetaData.getColumnCount();
        while (resultSet.next()){
            Map<String, Object> row = new HashMap<>(cls);
            for(int j=1; j<=cls; j++) {
                row.put(resultSetMetaData.getColumnLabel(j), resultSet.getObject(j));
            }
            list.add(row);
        }
        return list;
    }

    private void prepareExcuteGetMateData(
            Connection connection,
            String sql,
            List<Map<String, Object>> params,
            List<String> fields,
            List<List<Map<String, Object>>> result,
            Map<String, Object> keyMap,
            Boolean batch) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            if(batch){
                for (Map<String, Object> param : params) {
                    for(int i=0; i<fields.size(); i++){
                        preparedStatement.setObject(i+1, param.get(fields.get(i)));
                    }
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }else{
                if(params!=null&&params.size()>0){
                    Map<String, Object> param = params.get(0);
                    for(int i=0; i<fields.size(); i++){
                        preparedStatement.setObject(i+1, param.get(fields.get(i)));
                    }
                }
                preparedStatement.execute();
            }

            readResultMateDataBatch(preparedStatement, result, keyMap);
        }catch (Exception e){
            throw e;
        }finally {
            SqlX.close(preparedStatement);
        }
    }

    private void readResultMateDataBatch(
            PreparedStatement preparedStatement,
            List<List<Map<String, Object>>> result,
            Map<String, Object> keyMap) throws SQLException {
        ResultSet resultSet = null;
        try{
            do {
                resultSet = preparedStatement.getResultSet();
                if(resultSet!=null){
                    result.add(readResultMateData(resultSet, keyMap));
                    resultSet.close();
                }
            }while (preparedStatement.getMoreResults());
        }catch (SQLException e){
            throw e;
        }finally {
            SqlX.close(resultSet);
        }
    }

    private List<Map<String, Object>> readResultMateData(ResultSet resultSet, Map<String, Object> keyMap) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int cls = resultSetMetaData.getColumnCount();
        Map<String, Object> row = new HashMap<>(cls);
        if(resultSet.next()){
            for(int i=1; i<=cls; i++) {
                row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
            }
        }else{
            for(int i=1; i<=cls; i++) {
                row.put(resultSetMetaData.getColumnLabel(i), SqlX.getDefaultValue(resultSetMetaData.getColumnClassName(i)));
            }
        }
        keyMap.putAll(row);
        list.add(row);
        return list;
    }

    public Map<String, List<Map<String, Object>>> getDefParam(List<StepModel> steps, Map<String, DictionaryModel> dictionaryMap){
        Map<String, List<Map<String, Object>>> jsonMap = new HashMap<>();
        for(int i=0; i<steps.size(); i++) {
            StepModel stepModel = steps.get(i);
            Map<String, Object> map = SqlX.searchParamsMap(stepModel.getScript());
            if(!map.isEmpty()) {
                for (String key : map.keySet()) {
                    Object value = dictionaryMap.get(key).getExample();
                    if(value==null) {
                        value = "";
                    }
                    map.put(key, value);
                }
                List<Map<String, Object>> item = new ArrayList<>(1);
                item.add(map);
                jsonMap.put(stepModel.getStepNo(), item);
            }
        }
        return jsonMap;
    }
    @Getter
    @Setter
    private class ExcuteResult{
        private List<String> excuteInfos;
        private List<List<Object[]>> results;
        //private List<List<Map<String, Object>>> results;
    }
}
