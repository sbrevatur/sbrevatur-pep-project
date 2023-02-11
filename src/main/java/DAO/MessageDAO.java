package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.MessageDAO;

public class MessageDAO {
    
    
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.posted_by, message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    /*TODO: retrieve all authors from the Author table.
    You only need to change the sql String.
    @return all Authors.
    */
   public List<Message> retrieveAllMessages(){
       Connection connection = ConnectionUtil.getConnection();
       List<Message> allMessages = new ArrayList<>();
       try {
           //Write SQL logic here
           String sql = "select * from message";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           ResultSet rs = preparedStatement.executeQuery();
           while(rs.next()){
               Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
               allMessages.add(message);
           }
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return allMessages;
   }
}
