package brew.cmm.exception;

import org.springframework.context.MessageSource;

import java.text.MessageFormat;
import java.util.Locale;

public class BrewBaseException extends Exception {

    private static final long serialVersionUID = 1L;
    protected String message;
    protected String messageKey;
    protected Object[] messageParameters;
    protected Exception wrappedException;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Object[] getMessageParameters() {
        return this.messageParameters;
    }

    public void setMessageParameters(Object[] messageParameters) {
        this.messageParameters = messageParameters;
    }

    public Throwable getWrappedException() {
        return this.wrappedException;
    }

    public void setWrappedException(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    public BrewBaseException() {
        this((String)"BaseException without message", (Object[])null, (Throwable)null);
    }

    public BrewBaseException(String defaultMessage) {
        this((String)defaultMessage, (Object[])null, (Throwable)null);
    }

    public BrewBaseException(Throwable wrappedException) {
        this((String)"BaseException without message", (Object[])null, wrappedException);
    }

    public BrewBaseException(String defaultMessage, Throwable wrappedException) {
        this((String)defaultMessage, (Object[])null, wrappedException);
    }

    public BrewBaseException(String defaultMessage, Object[] messageParameters, Throwable wrappedException) {
        super(wrappedException);
        this.message = null;
        this.messageKey = null;
        this.messageParameters = null;
        this.wrappedException = null;
        String userMessage = defaultMessage;
        if (messageParameters != null) {
            userMessage = MessageFormat.format(defaultMessage, messageParameters);
        }

        this.message = userMessage;
    }

    public BrewBaseException(MessageSource messageSource, String messageKey) {
        this(messageSource, messageKey, (Object[])null, (String)null, Locale.getDefault(), (Throwable)null);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
        this(messageSource, messageKey, (Object[])null, (String)null, Locale.getDefault(), wrappedException);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
        this(messageSource, messageKey, (Object[])null, (String)null, locale, wrappedException);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, (String)null, locale, wrappedException);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, (String)null, Locale.getDefault(), wrappedException);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
    }

    public BrewBaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Throwable wrappedException) {
        super(wrappedException);
        this.message = null;
        this.messageKey = null;
        this.messageParameters = null;
        this.wrappedException = null;
        this.messageKey = messageKey;
        this.messageParameters = messageParameters;
        this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
    }
}
