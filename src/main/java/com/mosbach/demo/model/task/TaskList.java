package com.mosbach.demo.model.task;

import com.mosbach.demo.data.api.TaskManager;
import com.mosbach.demo.data.impl.PostgresTaskManagerImpl;
import com.mosbach.demo.model.student.Student;


import java.util.Collection;

public class TaskList {
	
	private Student student;
	private Collection<Task> tasks;

	public TaskList() { }

	public TaskList(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks() {
		// TaskManager taskManager = PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/TaskList.properties");
		TaskManager taskManager = PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
		// tasks = taskManager.getAllTasks("email");
	}

	@SuppressWarnings("deprecation")
	public void addTask(Task task) {
		// TaskManager taskManager = PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/TaskList.properties");
		TaskManager taskManager = PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
		// taskManager.addTask(task, "meATtest.com");

		// Region euCentral = Region.getRegion(Regions.US_EAST_1);
		// sqs.setRegion(euCentral);
        // .withDelaySeconds(1);    
		
/*	
		AWSCredentials awsCredentials = new SimpleAWSCredentials();         
		AmazonSQS sqs = new AmazonSQSClient(awsCredentials);
		
		SendMessageRequest send_msg_request = new SendMessageRequest()
		        .withQueueUrl("https://sqs.us-east-1.amazonaws.com/887927861730/Mosbach-task-organizer-Created-new-task")
		        .withMessageBody("Added the following task: " + task.getName() + " with priority: " + task.getPriority());
		sqs.sendMessage(send_msg_request);
*/	
		
		
	}


}