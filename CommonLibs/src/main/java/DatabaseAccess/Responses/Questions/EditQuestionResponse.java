package DatabaseAccess.Responses.Questions;

import DatabaseAccess.Requests.DatabaseRequest;
import DatabaseAccess.Responses.DatabaseResponse;

/**
 * Request: asks to edit a question
 * Response: just status
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - Question wasn't found
 *  3 - trying to delete a question that wasn't written by the user
 *  4 - there are already 1000 question for this course (in case we need to add a new question)
 */
public class EditQuestionResponse extends DatabaseResponse {

    public EditQuestionResponse(int status, DatabaseRequest request) {
        super(status, request);
    }
}