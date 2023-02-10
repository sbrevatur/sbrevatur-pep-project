package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {

     
         public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {

            String sql = "insert into account (username, password) values(?,?);";
           
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.username );
            preparedStatement.setString(2,account.password);


            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account (generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
       
  

}
