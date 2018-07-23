package coolness.balderdashserver.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coolness.balderdashserver.Model.Guess;
import coolness.balderdashserver.Server.Database;
import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Server.ServerInterface;

/**
 * Table values: Message, Username
 */
public class VotesDAO {
    public void addVote(Guess g) throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "insert into Votes " + "values (?, ?)";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, g.getMessage());
            stmt.setString(2, g.getUserName());
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
    public Guess[] getVotes() throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Guess> guesses = new ArrayList<>();
        boolean commit = true;
        String sql = "select * from votes";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Guess g = new Guess();
                g.setMessage(rs.getString(1));
                g.setUserName(rs.getString(2));
                guesses.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        } finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return guesses.toArray(new Guess[guesses.size()]);
    }
    public void clearTable() throws ServerInterface.ServerError {
        PreparedStatement stmt = null;
        String sql = "delete from votes";
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


