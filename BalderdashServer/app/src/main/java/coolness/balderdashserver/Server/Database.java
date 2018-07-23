package coolness.balderdashserver.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import coolness.balderdashserver.DAO.AnswersDAO;
import coolness.balderdashserver.DAO.GameDAO;
import coolness.balderdashserver.DAO.QuestionsDAO;
import coolness.balderdashserver.DAO.UserDAO;
import coolness.balderdashserver.DAO.VotesDAO;

public class Database {
    public Database() {
        users = new UserDAO();
        answers = new AnswersDAO();
        openConnection();
    }
    public static void openConnection() {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void startTransaction() {
        try {
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeTransaction(boolean commit) {
        try {
            if (connection != null) {
                if (commit) { connection.commit(); }
                else { connection.rollback(); }
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void safeClose(PreparedStatement stmt, ResultSet rs) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public UserDAO getUsers() { return users; }
    public AnswersDAO getAnswers() { return answers; }
    public QuestionsDAO getQuestions() { return questions; }
    public GameDAO getGame() { return game; }
    public VotesDAO getVotes() { return votes; }
    public static Connection getConnection() {
        if (connection == null) { openConnection(); }
        return connection;
    }
    private static Connection connection;
    private UserDAO users;
    private AnswersDAO answers;
    private QuestionsDAO questions;
    private GameDAO game;
    private VotesDAO votes;
    private static String connectionURL = "jdbc:sqlite:C://sqlite/mydatabase.db";
}

