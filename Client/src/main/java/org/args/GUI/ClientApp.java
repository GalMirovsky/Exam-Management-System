package org.args.GUI;

import DatabaseAccess.Requests.QuestionRequest;
import DatabaseAccess.Responses.AllQuestionsResponse;
import DatabaseAccess.Responses.Pair;
import DatabaseAccess.Responses.QuestionResponse;
import DatabaseAccess.Responses.SubjectsAndCoursesResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.args.Client.EMSClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaFX App
 */
public class ClientApp extends Application {

    private static Scene scene;
    private static EMSClient client;
    private static String fullName;
    // specify the server defaults
    private static String host = "127.0.0.1";

    private static int port = 3000;

    static void setRoot(String fxml)  {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            System.out.println("Failed to change the root of the scene: "+e.toString());

        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("/org/args/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void init() {
        try {
            super.init();
            client = new EMSClient(host, port, this);
        } catch (Exception e) {
            System.out.println("Failed to init app.. exiting");
            e.printStackTrace();

        }
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = fxmlLoader("LoginScreen");
            scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
            stage.setScene(scene);
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            stage.setResizable(false);
            stage.setTitle("HSTS");

            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to start the app.. exiting: "+e.toString());


        }

    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

    public FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(getClass().getResource("/org/args/" + fxml + ".fxml"));
    }

    public static void sendRequest(Object data) {
        try {
            client.sendToServer(data);
        } catch (IOException e) {
            System.out.println("Failed to send request to server");
            client.loginFailed(); //hack until model is properly created.
            e.printStackTrace();

        }
    }

    public void fillSubjectsDropdown(SubjectsAndCoursesResponse response)  {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        Parent screen = null;
        try {
            screen = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        screenController.setSubjectsAndCoursesState(response.getSubjectsAndCourses()); //set the hashmap in the controllers state to later fill the courses dropdown list according to selected subject
        for (String subjectName : response.getSubjectsAndCourses().keySet()) //iterate through every subject in the hashmap
        {
            screenController.addSubjectToSubjectDropdown(subjectName);
        }
        scene.setRoot(screen);


    }

    public void fillQuestionsList(AllQuestionsResponse response)  {

        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        HashMap<String, Pair<LocalDateTime, String>> questions = response.getQuestionList();
        ObservableList<String> observableSet = FXCollections.observableArrayList();

        for (Map.Entry<String, Pair<LocalDateTime, String>> question : questions.entrySet()) {
            String questionId = question.getKey();
            String questionDescription = question.getValue().getSecond();
            String menuItemText = "#" + questionId + ": " + questionDescription;
            observableSet.add(menuItemText);
        }

        screenController.addToList(observableSet);
    }

    public void loginSuccess(String name) {
        try {
            fullName = name;
            FXMLLoader loader = fxmlLoader("TeacherMainScreen");
            Parent screen = loader.load();
            scene.setRoot(screen);
            Platform.runLater(() -> {
                ((Stage)scene.getWindow()).setResizable(true);
            });
        } catch (IOException e) {
            System.out.println("Failed to switch scene on login success");
            e.printStackTrace();

        }

    }

    public void fillEditQuestionScreen(QuestionResponse response)  {

        String questionId = ((QuestionRequest)response.getRequest()).getQuestionID();
        String lastModified = response.getLastModified().toString();
        String author = response.getAuthor();
        String content = response.getQuestionContent();
        List<String> answers = response.getAnswers();
        int correctAnswer = response.getCorrectAnswer();
        FXMLLoader loader = fxmlLoader("EditQuestionScreen");
        Parent screen = null;
        try {
            screen = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditQuestionScreenController screenController = loader.getController();
        screenController.initScreen(questionId,lastModified, author, content, answers, correctAnswer);
        scene.setRoot(screen);

    }

    public void updateEditedQuestionOnQuestionManagementScreen(String newContent){
        FXMLLoader loader = fxmlLoader("QuestionManagementScreen");
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        QuestionManagementScreenController screenController = loader.getController();
        screenController.changeQuestionContent(newContent);

    }

    public void popupAlert(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = fxmlLoader("AlertPopUp");
                    Scene scene = new Scene(loader.load());
                    scene.getStylesheets().add(getClass().getResource("/org/args/bootstrap3.css").toExternalForm());
                    AlertPopUpController popUpController = loader.getController();
                    popUpController.setShowText(message);
                    Stage popup = new Stage();
                    popup.setTitle("Alert");
                    popup.setResizable(false);
                    popup.setScene(scene);
                    popup.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static String getFullName() {
        return fullName;
    }

    public static void setHost(String host) {
        ClientApp.host = host;
        client.setHost(host);
    }

    public static void setPort(int port) {
        ClientApp.port = port;
        client.setPort(port);
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }
}