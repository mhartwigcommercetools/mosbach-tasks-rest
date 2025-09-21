package com.mosbach.demo;

import com.mosbach.demo.data.api.TaskManager;
import com.mosbach.demo.data.api.UserManager;
import com.mosbach.demo.data.impl.PostgresTaskManagerImpl;
import com.mosbach.demo.data.impl.PropertyFileTaskManagerImpl;
import com.mosbach.demo.data.impl.PropertyFileUserManagerImpl;
import com.mosbach.demo.data.impl.UserImpl;
import com.mosbach.demo.model.alexa.AlexaRO;
import com.mosbach.demo.model.alexa.OutputSpeechRO;
import com.mosbach.demo.model.alexa.ResponseRO;
import com.mosbach.demo.model.user.Token;
import com.mosbach.demo.model.user.TokenAnswer;
import com.mosbach.demo.model.user.User;
import com.mosbach.demo.model.task.*;
import com.mosbach.demo.model.user.UserWithName;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {


    TaskManager propertyFileTaskManager =
            PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/tasks.properties");
    UserManager propertyFileUserManager =
            PropertyFileUserManagerImpl.getPropertyFileUserManagerImpl("src/main/resources/users.properties");

    @PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public TokenAnswer loginUser(@RequestBody User user) {

        Logger myLogger = Logger.getLogger("UserLoggingOn");
        myLogger.info("Received a POST request on login with email " + user.getEmail());

        String token = propertyFileUserManager.logUserOn(user.getEmail(), user.getPassword());
        myLogger.info("Token generated " + token);

        TokenAnswer tokenAnswer = new TokenAnswer(token,"200");

        // TODO
        // Fehlerfall behandeln

        return
                tokenAnswer;
    }


    @DeleteMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public com.mosbach.demo.model.user.MessageAnswer loginUser(@RequestBody Token token) {

        Logger myLogger = Logger.getLogger("UserLoggingOff");
        myLogger.info("Received a DELETE request on login with token " + token.getToken());

        boolean couldLogoffUser =
                propertyFileUserManager.logUserOff(propertyFileUserManager.getUserEmailFromToken(token));

        myLogger.info("User logged off " + couldLogoffUser);

        com.mosbach.demo.model.user.MessageAnswer messageAnswer = new com.mosbach.demo.model.user.MessageAnswer("User logged out.");

        // TODO
        // Fehlerfall behandeln

        return
                messageAnswer;
    }


    @PostMapping(
            path = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public com.mosbach.demo.model.user.MessageAnswer loginUser(@RequestBody UserWithName userWithName) {

        Logger myLogger = Logger.getLogger("UserLoggingOn");
        myLogger.info("Received a POST request on login with email " + userWithName.getEmail());

        boolean couldCreateUser = propertyFileUserManager
                        .createUser(
                            new UserImpl(
                                    userWithName.getName(),
                                    userWithName.getEmail(),
                                    userWithName.getPassword(),
                                    "OFF"
                            )
                        );
        myLogger.info("User created " + couldCreateUser);

        com.mosbach.demo.model.user.MessageAnswer messageAnswer = new com.mosbach.demo.model.user.MessageAnswer("User created.");

        // TODO
        // Fehlerfall behandeln

        return
                messageAnswer;
    }







    @PostMapping(
            path = "/hszg-tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer createTask(@RequestBody TokenTask tokenTask) {

        Logger myLogger = Logger.getLogger("CreateTaskLogger");
        myLogger.info("Received a POST request on hszg-tasks with token " + tokenTask.getToken());

        String token = tokenTask.getToken();

        String userEmail = propertyFileUserManager.getUserEmailFromToken(token);
        if (userEmail.length() > 2) {
            String name = tokenTask.getTask().getName();
            MessageAnswer myAnswer = new MessageAnswer();
            myAnswer.setMessage("Taskname " + name + " with token " + token);
            User tempStudent = new User("Tom", "Tom");
            //propertyFileTaskManager.addTask(tokenTask.getTask(), userEmail);
            return
                    myAnswer;
        }
        else {
            MessageAnswer myAnswer = new MessageAnswer();
            myAnswer.setMessage("Log on, you dummy!");
            return
                    myAnswer;
        }
    }



    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public AlexaRO createTask(@RequestBody AlexaRO alexaRO) {

        String myAnswer = "";
        if (alexaRO.getRequest().getIntent().getName().equals("TaskReadIntent")) {
            myAnswer += "You have to do the following tasks: ";
            myAnswer += "1. Learn for the math exam.";
            myAnswer += "2. Buy milk for mom.";
            myAnswer += "3. Continue my work for web engineering.";
        }
        else {
            myAnswer += "I do not know what to say.";
        }

        return
                prepareResponse(alexaRO, myAnswer, true);
    }




    @GetMapping("/hszg-tasks")
    public List<Task> getTasks(@RequestParam(value = "email", defaultValue = "email") String email,
                             @RequestParam(value = "token", defaultValue = "123") String token) {

        Logger myLogger = Logger.getLogger("GetTaskLogger");
        myLogger.info("Received a GET request on hszg-tasks with token " + token);

        // check token
        // TokenManager

        // final List<Task> allTasks = propertyFileTaskManager.getAllTasks(email);

        return null;
    }






    @PostMapping(
            path = "/task/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createTask() {

        final PostgresTaskManagerImpl postgresTaskManagerImpl =
                PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
        postgresTaskManagerImpl.createTableTask();

        return "Database Table created";
    }



    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO getTasks(@RequestBody AlexaRO alexaRO) {

        String outText = "";


        return alexaRO;
    }

    private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {

        alexaRO.setRequest(null);
        alexaRO.setSession(null);
        alexaRO.setContext(null);
        OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
        outputSpeechRO.setType("PlainText");
        outputSpeechRO.setText(outText);
        ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
        alexaRO.setResponse(response);
        return alexaRO;
    }

}
