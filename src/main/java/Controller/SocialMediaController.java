package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    AccountService accountService;
	MessageService messageService;	

	public SocialMediaController (){
		this.accountService = new AccountService();
		this.messageService = new MessageService();
	}   

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccount);
        app.post("/login", this::postUserLogin);
        app.post("/messages", this::postNewMessage);
        app.get("/messages", this::getAllMessages);
        app.patch("/messages/{message_id}", this::updateMessageUsingID);
        //app.get("/messages/{message_id}", this::getMessageUsingMessageID);
		/*app.post("/login", this::postUserLogin);
		app.post("/messages", this::postNewMessage);
        app.get("/messages", this::getAllMessages);
		app.get("/messages/{message_id}", this::getMessageUsingMessageID);
		app.delete("/messages/{message_id}", this::deleteMessageUsingID);
		app.patch("/messages/{message_id}", this::updateMessageUsingID);
		app.get("/messages/{message_id}", this::allMessagesByAuser);*/
             
                return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response. 
     * 
     * 1: Our API should be able to process new User registrations. */
    private void postNewAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }
    private void postUserLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account login = mapper.readValue(ctx.body(), Account.class);
        Account getAccount = accountService.getAccount(login.username, login.password);
        if(getAccount !=null){
            ctx.json(mapper.writeValueAsString(getAccount));
        }else{
            ctx.status(401);
        }
    }
    
    private void postNewMessage(Context ctx) throws JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }
    
    public void getAllMessages(Context ctx){
        List<Message> messages = messageService.retrieveAllMessages();
        ctx.json(messages);
    }


     /**
     * Handler to update a flight.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into a Flight object.
     * to conform to RESTful standards, the flight that is being updated is identified from the path parameter,
     * but the information required to update a flight is retrieved from the request body.
     * If flightService returns a null flight (meaning updating a flight was unsuccessful), the API will return a 400
     * status (client error). There is no need to change anything in this method.
     *
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void updateMessageUsingID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    /**
     * Handler to retrieve all flights departing from a particular city and arriving at another city.
     * both cities are retrieved from the path. There is no need to change anything in this method.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
   
     /*private void getMessageUsingMessageID(Context ctx) {
        
        Message messageRetrievedById = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(messageRetrievedById == null){
            ctx.status(200);
            break;
        } 
            ctx.json(messageRetrievedById);
            }*/
}