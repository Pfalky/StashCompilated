package Common;

/**
 * Created by drygoi on 03.06.17.
 */

public class AutotestError extends Error {
    public AutotestError() {
        super();
    }

    public AutotestError(String message) {
        super(message);
    }

    public AutotestError(String message, Throwable cause) {
        super(message, cause);
    }

    public AutotestError(Throwable cause) {
        super(cause);
    }
}
