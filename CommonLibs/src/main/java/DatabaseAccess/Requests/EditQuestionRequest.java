package DatabaseAccess.Requests;

import LightEntities.LightQuestion;

import java.util.List;

/**
 * Request: asks to edit a question
 * Response: just status
 */
public class EditQuestionRequest extends DatabaseRequest {
    private LightQuestion lightQuestion;

    public EditQuestionRequest(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }

    public LightQuestion getLightQuestion() {
        return lightQuestion;
    }

    public void setLightQuestion(LightQuestion lightQuestion) {
        this.lightQuestion = lightQuestion;
    }
}