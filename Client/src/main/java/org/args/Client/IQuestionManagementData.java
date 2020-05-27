package org.args.Client;

import DatabaseAccess.Responses.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IQuestionManagementData {

    void setSubjectsAndCourses(HashMap<String, List<String>> mapFromResponse);

    Set<String> getSubjects();

    List<String> getCoursesOfSubject(String subject);

    String getCurrentSubject();

    void setCurrentSubject(String currentSubject);

    String getCurrentCourse();

    void setCurrentCourse(String currentCourse);


    boolean dataWasAlreadyInitialized();

    //questions list data

    ObservableList<String> observableQuestionsList = FXCollections.observableArrayList();

    private void addToObservableList(String text) {
        observableQuestionsList.add(text);
    }

    void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList);

    ObservableList<String> getObservableQuestionsList();


    void setSelectedIndex(int selectedIndex);


    void clearQuestionsList();

    void fillQuestionsList(String text);

    void saveQuestionDetails(String questionId);
}