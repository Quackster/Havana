package org.alexdev.havana.game.messenger;

public class MessengerError {
    private String causer;
    private final MessengerErrorType error;
    private final MessengerErrorReason reason;

    public MessengerError(MessengerErrorType error) {
        this.error = error;
        this.reason = null;
    }

    public MessengerError(MessengerErrorType error, MessengerErrorReason reason) {
        this.error = error;
        this.reason = reason;
    }

    public String getCauser() {
        return causer;
    }

    public void setCauser(String causer) {
        this.causer = causer;
    }

    public MessengerErrorType getErrorType() {
        return error;
    }

    public MessengerErrorReason getErrorReason() {
        return reason;
    }
}