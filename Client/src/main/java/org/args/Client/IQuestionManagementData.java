package org.args.Client;

import Util.Pair;
import javafx.beans.property.BooleanProperty;
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

    void prepareAddQuestion();

    BooleanProperty isCourseSelected();

    void setCourseSelected(boolean courseSelected);

    //questions list data





    void generateQuestionDescriptors(HashMap<String, Pair<LocalDateTime, String>> questionList);

    ObservableList<String> getObservableQuestionsList();


    void setSelectedIndex(int selectedIndex);


    void clearQuestionsList();

    void fillQuestionsList(String text);

    void saveQuestionDetails(String questionId);
}
