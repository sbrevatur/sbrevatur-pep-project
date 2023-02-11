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

/**
     * TODO: Update the flight identified by the flight id to the values contained in the flight object.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * Remember that the format of an update PreparedStatement written as a Java String looks something like this:
     * String sql = "update TableName set ColumnName1=?, ColumnName2=? where ColumnName3 = ?;";
     * The question marks will be filled in by the preparedStatement setString, setInt, etc methods. they follow
     * this format, where the first argument identifies the question mark to be filled (left to right, starting
     * from zero) and the second argument identifies the value to be used:
     * preparedStatement.setString(1,string1);
     * preparedStatement.setString(2,string2);
     * preparedStatement.setInt(3,int1);
     *
     * @param id a flight ID.
     * @param flight a flight object.
     */
    public void updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text=? where message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      

    }


    /**
     * TODO: Retrieve a specific flight using its flight ID.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * Remember that the format of a select where statement written as a Java String looks something like this:
     * String sql = "select * from TableName where ColumnName = ?";
     * The question marks will be filled in by the preparedStatement setString, setInt, etc methods. they follow
     * this format, where the first argument identifies the question mark to be filled (left to right, starting
     * from zero) and the second argument identifies the value to be used:
     * preparedStatement.setInt(1,int1);
     *
     * @param id a flight ID.
     */
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message_id);


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message removeMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message messageToBeRemoved = null;
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message_id);


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                        messageToBeRemoved = message;

                
            } 
            if (messageToBeRemoved !=null) {
                try {
                    String deleteMessage = "delete from message where message_id = ?";
                    PreparedStatement ps = connection.prepareStatement(deleteMessage);

                    ps.setInt(1, message_id);

                    ps.executeUpdate();
        return messageToBeRemoved;
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }

                    
                }
                return null;
            } catch (SQLException err) {
                System.out.println(err.getMessage()); 
            }
            return null;
        
    }

 /**
     * TODO: Retrieve all flights following a particular flight path.
     *
     * you only need to change the sql string and set preparedStatement parameters.
     *
     * Remember that the format of a select where statement written as a Java String looks something like this:
     * "select * from TableName where ColumnName1 = ? and ColumnName2 = ?;";
     * The question marks will be filled in by the preparedStatement setString, setInt, etc methods. they follow
     * this format, where the first argument identifies the question mark to be filled (left to right, starting
     * from zero) and the second argument identifies the value to be used:
     * preparedStatement.setString(1,"column 1 value");
     * preparedStatement.setInt(2,123);
     *
     * @param departure_city the departing city.
     * @param arrival_city the arriving city.
     * @return all flights from departure_city to arrival_city.
     */
    public List<Message> getAllMessagesByUserAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by=?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1, account_id);


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs. getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


}


