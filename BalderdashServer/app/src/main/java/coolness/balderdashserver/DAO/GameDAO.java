package coolness.balderdashserver.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import coolness.balderdashserver.Server.Database;
import coolness.balderdashserver.Server.ServerInterface;

public class GameDAO {
    public void startGame() throws ServerInterface.InvalidInput, ServerInterface.InvalidUsername {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "update Game " + "set status=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setString(1, "Running");
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
    public int getPlayers() throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int players;
        boolean commit = true;
        String sql = "select players from game";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            players = rs.getInt(1);
        } catch (SQLException e) {
            commit = false;
            e.printStackTrace();
            throw new ServerInterface.InvalidInput();
        }
        finally {
            Database.safeClose(stmt, rs);
            Database.closeTransaction(commit);
        }
        return players;
    }
    public void addPlayer(int players) throws ServerInterface.InvalidInput {
        PreparedStatement stmt = null;
        boolean commit = true;
        String sql = "update Game " + "set players=?";
        try {
            Database.startTransaction();
            stmt = Database.getConnection().prepareStatement(sql);
            stmt.setInt(1, players);
        } catch (Exception e) {
            e.printStackTrace();
            commit = false;
            throw new ServerInterface.InvalidInput();
        } finally {
            Database.safeClose(stmt, null);
            Database.closeTransaction(commit);
        }
    }
    public void clearTable() throws ServerInterface.ServerError {
        PreparedStatement stmt = null;
        String sql = "delete from Game";
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


