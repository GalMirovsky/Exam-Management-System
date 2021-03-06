package org.args.Entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExecutedExam {

    private Subject studentExamSubject;

    private Course studentExamCourse;

    private List<Question> studentExamQuestionsList;

    private List<Double> studentExamQuestionsScores;

    private Teacher author;

    private String studentExamId;

    private int studentExamGrade;

    private int studentExamDuration; // in minutes

    private String studentExamDescription;

    private String teacherPrivateNotes; // only for the teacher
    @ManyToOne
    private Student student;

    public ExecutedExam(Exam exam, Student student) {

        this.studentExamSubject = exam.getExamSubject();
        this.studentExamCourse = exam.getExamCourse();
        this.studentExamQuestionsList = new ArrayList<>();
        this.studentExamQuestionsList.addAll(exam.getExamQuestionsList());
        this.studentExamQuestionsScores = new ArrayList<>();
        this.studentExamQuestionsScores.addAll(exam.getExamQuestionsScores());
        this.author = exam.getAuthor();
        this.studentExamId = exam.getExamId();
        this.studentExamGrade = 0;
        this.studentExamDuration = exam.getExamDuration();
        this.studentExamDescription = exam.getExamDescription();
        this.teacherPrivateNotes = exam.getTeacherPrivateNotes();
        this.student = student;
        this.student.addStudentExam(this);

    }

    public Subject getStudentExamSubject() {
        return studentExamSubject;
    }

    public Course getStudentExamCourse() {
        return studentExamCourse;
    }

    public List<Question> getStudentExamQuestionsList() {
        return studentExamQuestionsList;
    }

    public List<Double> getStudentExamQuestionsScores() {
        return studentExamQuestionsScores;
    }

    public Teacher getAuthor() {
        return author;
    }

    public String getStudentExamId() {
        return studentExamId;
    }

    public int getStudentExamGrade() {
        return studentExamGrade;
    }

    public int getStudentExamDuration() {
        return studentExamDuration;
    }

    public String getStudentExamDescription() {
        return studentExamDescription;
    }

    public String getTeacherPrivateNotes() {
        return teacherPrivateNotes;
    }

    public Student getStudent() {
        return student;
    }

    public void setOverTime() {
        this.studentExamDuration += 0.25 * this.studentExamDuration;
    }
}
