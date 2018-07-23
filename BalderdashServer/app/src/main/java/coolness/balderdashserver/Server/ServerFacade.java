package coolness.balderdashserver.Server;

import java.util.UUID;

import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Model.Guess;
import coolness.balderdashserver.Model.Question;
import coolness.balderdashserver.Model.User;
import coolness.balderdashserver.Model.ReadyRequest;
import coolness.balderdashserver.Model.ReadyResponse;

public class ServerFacade implements ServerInterface {
    public ServerFacade() { database = new Database(); }
    public void register(User u) {
        try {
            database.getUsers().addUser(u);
            int players = database.getGame().getPlayers();
            database.getGame().addPlayer(players + 1);
        } catch (InvalidUsername invalidUsername) {
            invalidUsername.printStackTrace();
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
    }
    public Question getNextQuestion() {
        try {
            Question q = database.getQuestions().getNextQuestion();
            if (q == null) { return null; }
            database.getQuestions().deleteQuestion(q.getUserName());
            return q;
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
        return null;
    }
    public void submitQuestion(Question q) {
        try {
            database.getQuestions().addQuestion(q);
        } catch (InvalidUsername invalidUsername) {
            invalidUsername.printStackTrace();
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
    }
    public void submitAnswer(Answer a) {
        try {
            database.getAnswers().addAnswer(a);
        } catch (InvalidUsername invalidUsername) {
            invalidUsername.printStackTrace();
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
    }
    public Answer[] getAnswers() {
        try {
            return database.getAnswers().getAnswers();
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
        return null;
    }
    public void submitGuess(Guess g) {
        try {
            database.getVotes().addVote(g);
            return;
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
            return;
        } catch (InvalidUsername invalidUsername) {
            invalidUsername.printStackTrace();
        }
    }
    public ReadyResponse testReady(ReadyRequest r) {
        int code = r.getCode();
        boolean ready = false;
        Question q = null;
        try {
            if (code == 0) {
                database.getGame().startGame();
                ready = true;
            } else if (code == 1) {
                ready = (database.getQuestions().getQuestions().length == database.getGame().getPlayers());
                if (ready) {
                    q = database.getQuestions().getNextQuestion();
                }
            } else if (code == 2) {
                ready = (database.getAnswers().getAnswers().length == database.getGame().getPlayers());
            } else if (code == 3) {
                ready = (database.getVotes().getVotes().length == database.getGame().getPlayers());
            } else if (code == 4) {

            }
        } catch (InvalidUsername invalidUsername) {
            invalidUsername.printStackTrace();
        } catch (InvalidInput invalidInput) {
            invalidInput.printStackTrace();
        }
        return new ReadyResponse(ready, q);
    }
    public String genID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    private Database database;
}

