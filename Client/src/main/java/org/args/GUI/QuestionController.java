package org.args.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.args.Client.IQuestionData;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuestionController {


    private boolean isEditing = false;
    private boolean isNew = false;
    private IQuestionData model;
    private int correctAnswer;

    public void setModel(IQuestionData dataModel) {
        if (model == null)
            this.model = dataModel;
    }

    @FXML
    private TextField LastModified;

    @FXML
    private TextField Author;

    @FXML
    private TextArea Content;

    @FXML
    private TextField Answer1;

    @FXML
    private TextField Answer2;

    @FXML
    private TextField Answer3;

    @FXML
    private TextField Answer4;

    @FXML
    private ChoiceBox<String> correctAnswerChoice;

    @FXML
    private Button CancelButton;

    @FXML
    private Button EditButton;

    @FXML
    private Button DeleteButton;


    @FXML
    private void initialize() {
        setModel(ClientApp.getModel());
        ObservableList<String> choiceItems = model.getChoiceItems();
        if (choiceItems == null) {
            String[] answers = {"Answer 1", "Answer 2", "Answer 3", "Answer 4"};
            model.setChoiceItems(FXCollections.observableArrayList(answers));
            choiceItems = model.getChoiceItems();
        }
        correctAnswerChoice.getItems().addAll(choiceItems);
        if (!model.isCreating()) {
            LastModified.setText(model.getLastModified());
            Author.setText(model.getAuthor());
            Content.setText(model.getContent());
            List<String> answers = model.getAnswers();
            Answer1.setText(answers.get(0));
            Answer2.setText(answers.get(1));
            Answer3.setText(answers.get(2));
            Answer4.setText(answers.get(3));
            correctAnswer = model.getCorrectAnswer();
            switch (correctAnswer) {
                case 0:
                    Answer1.setStyle("-fx-background-color: #00ff00 ;");
                    correctAnswerChoice.setValue("Answer 1");
                    break;
                case 1:
                    Answer2.setStyle("-fx-background-color: #00ff00 ;");
                    correctAnswerChoice.setValue("Answer 2");
                    break;
                case 2:
                    Answer3.setStyle("-fx-background-color: #00ff00 ;");
                    correctAnswerChoice.setValue("Answer 3");
                    break;
                case 3:
                    Answer4.setStyle("-fx-background-color: #00ff00 ;");
                    correctAnswerChoice.setValue("Answer 4");
                    break;
                default:
                    System.out.println("Undefined correct answer");
                    break;
            }
            // if the current logged user is not the author of the question disable edit and delete buttons
            if(!model.getUserName().equals(model.getAuthor())){
                EditButton.setDisable(true);
                DeleteButton.setDisable(true);
            }

        }else{
            // when creating new question set the edit button to save from the beginning
            Author.setText(model.getName());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LastModified.setText(LocalDateTime.now().format(formatter));
            Content.setEditable(true);
            Answer1.setEditable(true);
            Answer2.setEditable(true);
            Answer3.setEditable(true);
            Answer4.setEditable(true);
            EditButton.setText("Save");
            DeleteButton.setDisable(true);
            correctAnswerChoice.setDisable(false);
        }


    }

    @FXML
    void choiceBoxClicked(MouseEvent event) {
        correctAnswerChoice.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0:
                    Answer1.setStyle("-fx-background-color: #00ff00 ;");
                    Answer2.setStyle("-fx-background-color: #ffffff ;");
                    Answer3.setStyle("-fx-background-color: #ffffff ;");
                    Answer4.setStyle("-fx-background-color: #ffffff ;");
                    correctAnswer = 0;
                    break;
                case 1:
                    Answer1.setStyle("-fx-background-color: #ffffff ;");
                    Answer2.setStyle("-fx-background-color: #00ff00 ;");
                    Answer3.setStyle("-fx-background-color: #ffffff ;");
                    Answer4.setStyle("-fx-background-color: #ffffff ;");
                    correctAnswer = 1;
                    break;
                case 2:
                    Answer1.setStyle("-fx-background-color: #ffffff ;");
                    Answer2.setStyle("-fx-background-color: #ffffff ;");
                    Answer3.setStyle("-fx-background-color: #00ff00 ;");
                    Answer4.setStyle("-fx-background-color: #ffffff ;");
                    correctAnswer = 2;
                    break;
                case 3:
                    Answer1.setStyle("-fx-background-color: #ffffff ;");
                    Answer2.setStyle("-fx-background-color: #ffffff ;");
                    Answer3.setStyle("-fx-background-color: #ffffff ;");
                    Answer4.setStyle("-fx-background-color: #00ff00 ;");
                    correctAnswer = 3;
                    break;
                default:
                    System.out.println("Undefined correct answer");
                    break;
            }
            model.setCorrectAnswer(t1.intValue());

        });
    }


    @FXML
    void CancelButtonClicked(ActionEvent event) {
        if(model.isCreating())
            model.setCreating(false);
        ClientApp.backToLastScene();
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {
        model.deleteQuestion(model.getQuestionId());

    }

    @FXML
    void EditButtonClicked(ActionEvent event) {
        if (model.isCreating()) {
            // null as question id indicates of new question being created
            if(!Answer1.getText().equals("") && !Answer2.getText().equals("") && !Answer3.getText().equals("") && !Answer4.getText().equals("") && !Content.getText().equals("") && correctAnswerChoice.getValue() != null){
                model.saveQuestion(null, Answer1.getText(), Answer2.getText(), Answer3.getText(), Answer4.getText(), Content.getText());
                model.setCreating(false);
            }else{
                model.alert("Invalid Question Or Some Data Fields May Be Missing.");
            }

        } else {
            if (!isEditing) {
                Content.setEditable(true);
                Answer1.setEditable(true);
                Answer2.setEditable(true);
                Answer3.setEditable(true);
                Answer4.setEditable(true);
                EditButton.setText("Save");
                isEditing = true;
                correctAnswerChoice.setDisable(false);
                switch (this.correctAnswer) {
                    case 0:
                        Answer1.setStyle("-fx-background-color: #ffffff ;");
                        break;
                    case 1:
                        Answer2.setStyle("-fx-background-color: #ffffff ;");
                        break;
                    case 2:
                        Answer3.setStyle("-fx-background-color: #ffffff ;");
                        break;
                    case 3:
                        Answer4.setStyle("-fx-background-color: #ffffff ;");
                        break;
                    default:
                        System.out.println("Undefined correct answer");
                        break;
                }

            } else {
                Content.setEditable(false);
                Answer1.setEditable(false);
                Answer2.setEditable(false);
                Answer3.setEditable(false);
                Answer4.setEditable(false);
                EditButton.setText("Edit");
                isEditing = false;
                correctAnswerChoice.setDisable(true);
                String questionId = model.getQuestionId();
                model.saveQuestion(questionId, Answer1.getText(), Answer2.getText(), Answer3.getText(), Answer4.getText(), Content.getText());
            }
        }
    }


}