package brew.cmm.exception;

import org.springframework.context.MessageSource;

import java.text.MessageFormat;
import java.util.Locale;

public class BrewFdlException extends BrewBaseException {

    private static final long serialVersionUID = 1L;

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

    public BrewFdlException() {
        this((String)"FdlException without message", (Object[])null, (Throwable)null);
    }

    public BrewFdlException(String defaultMessage) {
        this((String)defaultMessage, (Object[])null, (Throwable)null);
    }

    public BrewFdlException(String defaultMessage, Throwable wrappedException) {
        this((String)defaultMessage, (Object[])null, wrappedException);
    }

    public BrewFdlException(String defaultMessage, Object[] messageParameters, Throwable wrappedException) {
        super(wrappedException);
        String userMessage = defaultMessage;
        if (messageParameters != null) {
            userMessage = MessageFormat.format(defaultMessage, messageParameters);
        }

        this.message = userMessage;
    }

    public BrewFdlException(MessageSource messageSource, String messageKey) {
        this(messageSource, messageKey, (Object[])null, (String)null, Locale.getDefault(), (Throwable)null);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
        this(messageSource, messageKey, (Object[])null, (String)null, Locale.getDefault(), wrappedException);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
        this(messageSource, messageKey, (Object[])null, (String)null, locale, wrappedException);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, (String)null, locale, wrappedException);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Object[] messageParameters, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, (String)null, Locale.getDefault(), wrappedException);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
    }

    public BrewFdlException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Throwable wrappedException) {
        super(wrappedException);
        this.messageKey = messageKey;
        this.messageParameters = messageParameters;
        this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
    }
}
