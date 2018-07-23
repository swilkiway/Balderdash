package coolness.balderdashserver.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import coolness.balderdashserver.Server.Database;
import coolness.balderdashserver.Model.User;
import coolness.balderdashserver.Server.ServerInterface;

public class UserDAO {
    public void addUser(User username) throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "insert into User " + "values (?, ?)";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, username.getUsername());
            stmt.setInt(2, 0);
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
    public void addPoint(String username, int points) throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "update User " + "set points=? where username=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setInt(1, points);
            stmt.setString(2, username);
        } catch (Exception e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.InvalidInput();
        }
        finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public int getUserPoints(String username) throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int points;
        boolean commit = true;
        String sql = "select Points from user " + "where Username=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            points = rs.getInt(1);
        } catch (SQLException e) {
            commit = false;
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        }
        finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return points;
    }
    public User[] getScores() throws ServerInterface.ServerError {
        ArrayList<User> scores = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean commit = true;
        String sql = "select * from user";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString(1));
                user.setPoints(rs.getInt(2));
                scores.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.ServerError();
        }
        finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return scores.toArray(new User[scores.size()]);
    }
    public void clearTable() throws ServerInterface.ServerError {
        PreparedStatement stmt = null;
        String sql = "delete from User";
        boolean commit = true;
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.ServerError();
        }
        finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public void deleteUser(String username) throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "delete from User " + "where Username=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
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
    private Connection connection;
}

