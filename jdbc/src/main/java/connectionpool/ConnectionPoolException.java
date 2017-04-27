package connectionpool;

/**
 * 23.03.2017 by K.N.K
 */
@SuppressWarnings("WeakerAccess")
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}
