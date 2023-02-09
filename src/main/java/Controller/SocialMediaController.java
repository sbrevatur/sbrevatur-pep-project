package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    AccountService accountService;
	//MessageService messageService;	

	public SocialMediaController (){
		this.accountService = new AccountService();
		this.messageService = new MessageService();
	}   

    public void startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccount);
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
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(newAccountObject);
		if (addedAccount ==null))
            ctx.status(400);
        else
			ctx.json(mapper.writeValueAsString(addedAccount));
    }
}


