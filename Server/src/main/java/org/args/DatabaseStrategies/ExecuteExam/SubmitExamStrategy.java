package org.args.DatabaseStrategies.ExecuteExam;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Requests.ExecuteExam.SubmitExamRequest;
import DatabaseAccess.Responses.DatabaseResponse;
import DatabaseAccess.Responses.ExecuteExam.SubmitExamResponse;
import org.args.DatabaseStrategies.DatabaseStrategy;
import org.args.Entities.*;
import org.args.OCSF.ConnectionToClient;
import org.hibernate.Session;

import java.util.List;

/**
 * status dictionary:
 * 0 - success
 * 1 - unauthorized access - user isn't logged in
 * 2 - exam wasn't found
 */

public class SubmitExamStrategy extends DatabaseStrategy {

    @Override
    public DatabaseResponse handle(DatabaseRequest request, ConnectionToClient client, Session session,
                                   List<String> loggedInUsers) {

        SubmitExamRequest request1 = (SubmitExamRequest) request;

        if (client.getInfo("userName") == null)
            return new SubmitExamResponse(UNAUTHORIZED, request);

        ConcreteExam concreteExam = getTypeById(ConcreteExam.class, request1.getExamID(), session);
        Student student = (Student) getUser((String) client.getInfo("userName"), session);
        ExecutedExam executedExam = getTypeById(ExecutedExam.class,
                String.valueOf(student.getCurrentlyExecutedID()), session);

        if (concreteExam == null || executedExam == null || concreteExam != executedExam.getConcreteExam())
            return new SubmitExamResponse(ERROR2, request);

        executedExam.setAnswersByStudent(request1.getAnswersList());
        concreteExam.addExecutedExam(executedExam);

        session.update(executedExam);
        session.flush();

        return new SubmitExamResponse(SUCCESS, request1);
    }
}
