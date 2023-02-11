package Service;

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
        
    }

