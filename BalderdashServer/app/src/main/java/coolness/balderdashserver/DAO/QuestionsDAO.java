package coolness.balderdashserver.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coolness.balderdashserver.Server.Database;
import coolness.balderdashserver.Model.Question;
import coolness.balderdashserver.Server.ServerInterface;

public class QuestionsDAO {
    public void addQuestion(Question question) throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "insert into Questions " + "values (?, ?)";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, question.getMessage());
            stmt.setString(2, question.getUserName());
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
        }
        finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public String[] getQuestions() throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> questions = new ArrayList<>();
        boolean commit = true;
        String sql = "select * from Questions";
        try {
            String question;
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                question = rs.getString(1);
                questions.add(question);
            }
        } catch (SQLException e) {
            commit = false;
            questions = null;
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return questions.toArray(new String[questions.size()]);
    }
    public Question getNextQuestion() throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Question q = new Question();
        boolean commit = true;
        String sql = "select * from Questions";
        try {
            String question;
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs == null) {
                return null;
            }
            q.setMessage(rs.getString(1));
            q.setUserName(rs.getString(2));
        } catch (SQLException e) {
            commit = false;
            q = null;
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return q;
    }
    public void deleteQuestion(String username) {
        PreparedStatement stmt = null;
        String sql = "delete from Questions " + "where username=?";
        boolean commit = true;
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public void clearTable() throws ServerInterface.ServerError {
        PreparedStatement stmt = null;
        String sql = "delete from Questions";
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


