/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
public class Config {
    public static String USER_NAME = "root";
    public static String PASSWORD = "12345";
    
    public static String URL = "jdbc:mysql://localhost:3306/qlcuahanggiaydb";
    
    
    //Test thu ket noi database
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String sql = "SELECT * from tblmausac";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberCols = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numberCols; i++) {
                    System.out.print(resultSet.getObject(i));
                }
                System.out.println();
            }
        }
    }
}
