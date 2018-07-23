package coolness.balderdashserver.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coolness.balderdashserver.Server.Database;
import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Server.ServerInterface;

/**
 * Table values: Message, Username, Correct
 */
public class AnswersDAO {
    public void addAnswer(Answer answer) throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "insert into Answers " + "values (?, ?, ?)";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, answer.getMessage());
            stmt.setString(2, answer.getUserName());
            stmt.setString(3, answer.isCorrectAnswer());
        } catch (Exception e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.InvalidInput();
        }
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.InvalidUsername();
        } finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public Answer getCorrectAnswer() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean commit = true;
        String sql = "select * from answers " + "where correct=yes";
        try {
            Database.startTransaction();;
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            Answer a = new Answer();
            a.setMessage(rs.getString(1));
            a.setUserName(rs.getString(2));
            a.setCorrect(rs.getString(3));
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return null;
    }
    public String getUser(String message) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean commit = true;
        String sql = "select username from answers " + "where message=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, message);
            rs = stmt.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return null;
    }
    public Answer[] getAnswers() throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Answer> answers = new ArrayList<>();
        boolean commit = true;
        String sql = "select * from answers";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setMessage(rs.getString(1));
                a.setUserName(rs.getString(2));
                a.setCorrect(rs.getString(3));
                answers.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return answers.toArray(new Answer[answers.size()]);
    }
    public void clearTable() throws ServerInterface.ServerError {
        PreparedStatement stmt = null;
        String sql = "delete from Answers";
        boolean commit = true;
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.ServerError();
        } finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
}

