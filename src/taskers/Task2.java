/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.util.ArrayList;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses a Notification functional interface.
 * This allows the use of anonymous inner classes or
 * lambda expressions to define the method that gets called
 * when a notification is to be made.
 */
public class Task2 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    private StateOfTheThread currentStateOfTheThread;
    
    private Notifiable notificationTarget;
    private ArrayList<Notification> notifications = new ArrayList<>();
    
    public Task2(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        currentStateOfTheThread = StateOfTheThread.RUNNING;
        doNotify("Task2 start.");
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task2: " + i);
            }
            
            if (exit) {
                currentStateOfTheThread = StateOfTheThread.INTERCEPTED;
                return;
            }
        }
        doNotify("Task2 done.");
    }
    
    public void end() {
        currentStateOfTheThread = StateOfTheThread.INTERCEPTED;
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
        // this method allows a notification handler to be registered to receive notifications
    public void setOnNotification(Notification notification) {
        this.notifications.add(notification);
    }
    
    private void doNotify(String message) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> {
                notificationTarget.notify(message);
            });
        }
    }
    
    public StateOfTheThread getCurrentState() {
        return currentStateOfTheThread;
    }
    public void setCurrentState(StateOfTheThread state){
        this.currentStateOfTheThread = state;
    }
}