# Chat Room

This project is intended to build a chat room application using WebSocket and STOMP, with the Spring Boot MVC (Model, View, Controller) framework.

## Table of Contents

* [Description of the Project](#description-of-the-project)
* [Testing code coverage](#testing-code-coverage)
* [Getting Started](#getting-started)
* [Contributing](#contributing)

## Description of the Project

As has already been mentioned, this project develops a chat room application. Both a login and a chat page are implemented. The work that has been done is best described by explaining its main steps:

* src/main/java/edu/udacity/java/nano/Message.java: the message model has been added. Message model is the payload exchanged between client and server. The basic actions of ENTER, CHAT, and LEAVE are covered.
* src/main/resources/templates/login.html file: the login function has been completed, so that the user is redirected to the chat page.
* src/main/resources/templates/chat.html file: a number of tasks have been completed:
    * A send button has been added so that the messages can be sent.
    * The number of users which are online at a given time is shown.
    * A message container has been added where the exchanged messages can be viewed.
* src/main/java/edu/udacity/java/nano/WebSocketChatApplication.java: code has been added for login to chat room.
* src/main/java/edu/udacity/java/nano/WebSocketChatServer.java: a number of methods have been defined:
    * onOpen: user name and session are registered for the newly established connection. In addition, a message is sent to all participants communicating the fact that a user has just joined the chat.
    * onMessage: the message sent by a participant is communicated to all participants.
    * onClose: the registration of user name and session is removed. In addition, a message is sent to all participants communicating the fact that a user has just left the chat.
    * sendMessageToAll: this method sends a given message to all participants.
* src/main/resources/application.yml file: a number of configuration parameters have been added with regards to thymeleaf.
* src/test/java/edu/udacity/java/WebSocketChatApplicationTestLogin: a number of web driver tests have been added within the existing framework JUnit, covering login, join, chat, and leave. All tests pass, and the application is then repackaged.

## Testing code coverage

In this section, the four tests covered are listed:

* Login: the title of the page before (Chat Room Login) and after (Chat Room) login has been checked.
* Join: it is checked that a given user joins the chat after login. To this end, the user name at both login and chat page is verified.
* Chat: it is checked that a given user sends a message to the server and then receives it back from the server.
* Leave: a second window tab is opened and then the user exits the chat page (Chat Room), i.e. leaves the chat. Then, it is checked that the leave message is received on the first tab.

## Getting Started

The procedure to obtain functional a copy of the project on your local machine so that you can further develop and/or test it is explained in this section. These are the steps to be followed:

* Firstly, you have to download/clone the project files from this repository onto your local machine. Then, cd into the root folder where the project files are located.
* Secondly, you can execute the packaged application. This is the result of the execution of the packaging step:
![package](/ScreenShots/package.png)
which creates the *jar* file at *target/chatroom-starter-0.0.1-SNAPSHOT.jar*:
![jarfile](/ScreenShots/jarfile.png)
Just run the *jar* file on your terminal shell window by typing `java -jar chatroom-starter-0.0.1-SNAPSHOT.jar`. Please, cd to the *target* directory first:
![executejar](/ScreenShots/executejar.png)
* Thirdly, the application can be manually tested at *http://localhost:8080/*:
    * The user is initially shown the login page:
    ![screen1](/ScreenShots/screen1.png)
    * They enter their desired user name, and join the chat:
    ![screen2](/ScreenShots/screen2.png)
    * They receive messages from other users (please, note how the number of online users is now 2 after a new participant has joined the chat):
    ![screen3](/ScreenShots/screen3.png)
    * When one of the participants leaves the chat, participants are informed, and the number of online users is decreased in accordance with it:
    ![screen4](/ScreenShots/screen4.png)
* Finally, if you want to run the supporting unit tests yourself, you have to:
    * Make sure the application is running. For instance, as described above, by typing `java -jar chatroom-starter-0.0.1-SNAPSHOT.jar` at the target folder.  
    * Make sure you have the latest version of the Chrome browser (version 76), as these test are web driver ones (under the existing framework JUnit).
    * Open a new terminal shell window and type, for instance, `mvn test`. Please, cd to the root directory of the project first, as this action requires a project to be executed, and a POM file must be present.
    * This is the result that you would obtain. All four tests pass:
    ![testsresult](/ScreenShots/testsresult.png)
    ![testsresult2](/ScreenShots/testsresult2.png)
    ![testsresult3](/ScreenShots/testsresult3.png)
## Contributing

This repository contains all the work that makes up the project. Individuals and I myself are encouraged to further improve this project. As a result, I will be more than happy to consider any pull requests.
