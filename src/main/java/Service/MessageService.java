package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    /**
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    /**
     * Constructor for a AuthorService when a AuthorDAO is provided.
     * This is used for when a mock AuthorDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AuthorService independently of AuthorDAO.
     * There is no need to modify this constructor.
     * @param authorDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    /**
     * TODO: Use the AuthorDAO to retrieve all authors.
     *
     * @return all authors
     */
    public List<Message> retrieveAllMessages() {
        return messageDAO.retrieveAllMessages();
    }
   
    /**
     * TODO: Use the AuthorDAO to persist an author. The given Author will not have an id provided.
     *
     * @param author an author object.
     * @return The persisted author if the persistence is successful.
     */
    public Message addMessage(Message message) {
        if(message.message_text ==""){
            return null;
        } else if (
            message.message_text.length() > 250){
                return null;
            }
            return messageDAO.insertMessage(message);
        }

           /**
     * TODO: Use the FlightDAO to update an existing flight from the database.
     * You should first check that the flight ID already exists. To do this, you could use an if statement that checks
     * if flightDAO.getFlightById returns null for the flight's ID, as this would indicate that the flight id does not
     * exist.
     *
     * @param flight_id the ID of the flight to be modified.
     * @param flight an object containing all data that should replace the values contained by the existing flight_id.
     * @return the newly updated flight if the update operation was successful. Return null if the update operation was
     *         unsuccessful. We do this to inform our application about successful/unsuccessful operations. (eg, the
     *         user should have some insight if they attempted to edit a nonexistent flight.)
     */

        public Message updateMessage(int message_id, Message message){

             if(message.message_text ==""){
            return null;
        } else if (
            message.message_text.length() > 250){
                return null;
            }
           messageDAO.updateMessage(message_id, message);
           return this.messageDAO.getMessageById(message_id);      
            

        }  
        
public Message getMessageById(int message_id) {
    return messageDAO.getMessageById(message_id);
}

        
public Message removeMessageById(int message_id) {
    return messageDAO.removeMessageById(message_id);
}
        
    }

