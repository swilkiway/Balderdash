package coolness.balderdashserver.Server;

import coolness.balderdashserver.Model.User;

/**
 * The server takes in request objects, passes parameters to the DAOs, receives model objects from
 * the DAOs, and sends back response objects. It is mainly intended as a control center-- no data is
 * stored in this class, but all data must pass through it.
 */
public interface ServerInterface {
    public static class ServerError extends Exception {}
    public static class AlreadyExists extends Exception {}
    public static class InvalidInput extends Exception {}
    public static class InvalidUsername extends Exception {}
    public static class InvalidToken extends Exception {}
    public static class DoesNotExist extends Exception {}

    /**
     * Allows a user to enter their information and attempts to register them.
     * @return The response message.
     */
    public void register(User u);
}

