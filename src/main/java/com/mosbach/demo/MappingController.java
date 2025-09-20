package com.mosbach.demo;

import com.mosbach.demo.data.api.TaskManager;
import com.mosbach.demo.data.api.UserManager;
import com.mosbach.demo.data.impl.PostgresTaskManagerImpl;
import com.mosbach.demo.data.impl.PropertyFileTaskManagerImpl;
import com.mosbach.demo.data.impl.PropertyFileUserManagerImpl;
import com.mosbach.demo.model.alexa.AlexaRO;
import com.mosbach.demo.model.alexa.OutputSpeechRO;
import com.mosbach.demo.model.alexa.ResponseRO;
import com.mosbach.demo.model.auth.LogOn;
import com.mosbach.demo.model.student.Student;
import com.mosbach.demo.model.task.*;
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
    public TokenAnswer loginUser(@RequestBody LogOn logOn) {

        Logger myLogger = Logger.getLogger("UserLogOn");
        myLogger.info("Received a POST request on logon with email " + logOn.getEmail());

        String email = logOn.getEmail();
        String password = logOn.getPassword();

        String token = propertyFileUserManager.logUserOn(email, password);
        myLogger.info("token generated " + token);

        TokenAnswer myAnswer = new TokenAnswer();
        if (token.equals("NOT-FOUND")) {
            myAnswer.setToken("We could not log you in.");
        }
        else {
            myAnswer.setToken("We logged you on with token " + token);
        }
        return
                myAnswer;
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
            Student tempStudent = new Student("Tom", "Tom");
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
