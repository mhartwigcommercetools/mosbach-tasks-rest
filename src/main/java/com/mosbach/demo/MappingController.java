package com.mosbach.demo;

import com.mosbach.demo.data.api.TaskManager;
import com.mosbach.demo.data.api.UserManager;
import com.mosbach.demo.data.impl.*;
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

import java.util.ArrayList;
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
                propertyFileUserManager.logUserOff(propertyFileUserManager.getUserEmailFromToken(token.getToken()));

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
            path = "/task",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public com.mosbach.demo.model.user.MessageAnswer loginUser(@RequestBody TokenTask tokenTask) {

        Logger myLogger = Logger.getLogger("UserLoggingOn");
        myLogger.info("Received a POST request on task with email " + tokenTask.getToken());

        String email = propertyFileUserManager.getUserEmailFromToken(tokenTask.getToken());
        boolean couldCreateTask = propertyFileTaskManager
                .addTask(
                        new TaskImpl(
                                tokenTask.getTask().getName(),
                                tokenTask.getTask().getPriority(),
                                email
                        )
                );

        myLogger.info("User created " + couldCreateTask);

        com.mosbach.demo.model.user.MessageAnswer messageAnswer = new com.mosbach.demo.model.user.MessageAnswer("User created.");

        // TODO
        // Fehlerfall behandeln

        return
                messageAnswer;
    }

    @GetMapping("/task")
    public TaskList getTasks(@RequestParam(value = "token", defaultValue = "123") String token) {

        Logger myLogger = Logger.getLogger("TaskLogger");
        myLogger.info("Received a GET request on task with token " + token);

        String email = propertyFileUserManager.getUserEmailFromToken(token);
        List<com.mosbach.demo.data.api.Task> tasks = propertyFileTaskManager.getAllTasksPerEmail(email);
        List<Task> result = new ArrayList<>();
        for (com.mosbach.demo.data.api.Task t : tasks)
            result.add(new Task(t.getName(), t.getPriority()));
        TaskList tl = new TaskList(result);

        // TODO
        // Fehlerfall behandeln

        return
                tl;
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
