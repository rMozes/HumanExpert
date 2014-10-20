package com.example.user.humanexpert;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by RICHI on 2014.10.19..
 */
public class JsonCaseDeserializer implements JsonDeserializer<CaseClass> {

    @Override
    public CaseClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject caseObject = (JsonObject) jsonObject.get("case");

        String text = "";
        if (caseObject.has("text")) {
            text = caseObject.get("text").getAsString();
        }

        int id = 0;
        if (caseObject.has("id")) {
            id = caseObject.get("id").getAsInt();
        }

        String url = "";
        if (caseObject.has("image")) {
            JsonElement jsonElement = caseObject.get("image");
            url = jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
        }

        ArrayList<Answer> answers = new ArrayList<Answer>();
        if (caseObject.has("answers")) {
            JsonArray jsonArray = (JsonArray) caseObject.get("answers");
                for (int i = 0; i < jsonArray.size(); i++) {
                    Answer answer = new Answer();
                    JsonObject answerJson = (JsonObject) jsonArray.get(i);
                    String textAnswer = answerJson.get("text").getAsString();
                    int idAnswer = answerJson.get("id").getAsInt();
                    int caseId = answerJson.get("caseId").getAsInt();
                    answer.setNewText(textAnswer);
                    answer.setNewId(idAnswer);
                    answer.setNewCaseId(caseId);
                    answers.add(answer);
            }
        }

        CaseClass cs = new CaseClass();
        cs.setText(text);
        cs.setImageUrl(url);
        cs.setId(id);
        cs.setList(answers);

        return cs;
    }
}
