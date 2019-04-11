package cn.crm.util;


import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * 连接数据库:ORACLE
 */
public class JDBCUtil {

    private static Properties property;

    static {
        String url = JDBCUtil.class.getClassLoader().getResource("application.properties").getPath();
        try {
            property = new Properties();
            property.load(new FileInputStream(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(property.getProperty("driverName"));
            String url = property.getProperty("url");
            String username = property.getProperty("username");
            String password = property.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void release(Connection con, Statement statement, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    statement = null;
                }
            }
            if (null != con) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    con = null;
                }
            }
        }
    }

    public static List<Map<String, Object>> select() {
        Connection connection = JDBCUtil.getConnection();
        Statement stm = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            stm = connection.createStatement();
            String sql = "select GH,DWH,XM,XBM,FILED2,FILED3,FILED4,FILED5,FILED6,FILED7,FILED8 from V_JZG ";
            resultSet = stm.executeQuery(sql);
            resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();

            int colnum = resultSetMetaData.getColumnCount();

            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
            while (resultSet.next()) {
                Map <String,Object> rowData=new HashMap<>();
                for(int i=1;i<colnum;i++) {
                    rowData.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                list.add(rowData);
            }
        return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(connection, stm, resultSet);
        }
        return null;
    }


    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }


    public static void main(String[] args) {
        List<Map<String, Object>> select = JDBCUtil.select();
        System.out.println(select.toString());
    }


}
