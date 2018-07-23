package coolness.balderdashclient.Client;

import com.google.gson.Gson;

import coolness.balderdashclient.Model.Answer;
import coolness.balderdashclient.Model.Guess;
import coolness.balderdashclient.Model.Question;
import coolness.balderdashclient.Model.ReadyRequest;
import coolness.balderdashclient.Model.ReadyResponse;
import coolness.balderdashclient.Model.Register;
import coolness.balderdashclient.Model.RegisterRequest;


public class ServerProxy {

    public static void register(Register r) {
        Gson gson = new Gson();
        RegisterRequest rr = new RegisterRequest(r.getUserName());
        String json = gson.toJson(rr);
        String urlString = "http://" + r.getServerHost() + ":" + "7996" + "/register";
        WebClient.getConnection(urlString, json, null);
    }
    public static void submitQuestion(Question q) {
        Gson gson = new Gson();
        String json = gson.toJson(q);
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/submitquestion";
        WebClient.getConnection(urlString, json, null);
    }
    public static Question getNextQuestion() {
        Gson gson = new Gson();
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/getnextquestion";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, Question.class);
    }
    public static void submitAnswer(Answer a) {
        Gson gson = new Gson();
        String json = gson.toJson(a);
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/submitanswer";
        WebClient.getConnection(urlString, json, null);
    }
    public static void submitGuess(Guess g) {
        Gson gson = new Gson();
        String json = gson.toJson(g);
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/submitguess";
        WebClient.getConnection(urlString, json, null);
    }
    public static Answer[] getAnswers() {
        Gson gson = new Gson();
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/getanswers";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, Answer[].class);
    }
    public static ReadyResponse testIfReady(int code) {
        ReadyRequest rr = new ReadyRequest(code);
        Gson gson = new Gson();
        String json = gson.toJson(rr);
        String urlString = "http://" + User.get().getServerHost() + ":" + "7996" + "/testready";
        String response = WebClient.getConnection(urlString, json, null);
        return gson.fromJson(response, ReadyResponse.class);
    }
}

