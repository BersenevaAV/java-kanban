package managers;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException( final String mes, final Throwable cause) {
        super(mes, cause);
    }
}
