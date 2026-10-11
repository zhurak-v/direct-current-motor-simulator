package com.university.direct_current_motor.core.exceptions;

import java.util.Objects;

public class AppException extends RuntimeException {

    public static final class AppExceptionBuilder {

        private static final RequestStatus DEFAULT_REQUEST_STATUS;
        private static final Throwable DEFAULT_THROWABLE;

        static {
            DEFAULT_REQUEST_STATUS = RequestStatus.INTERNAL_ERROR;
            DEFAULT_THROWABLE = null;
        }

        private RequestStatus requestStatus;
        private Throwable cause;

        private AppExceptionBuilder() {
            this.requestStatus = DEFAULT_REQUEST_STATUS;
            this.cause = DEFAULT_THROWABLE;
        }

        public AppExceptionBuilder setRequestStatus(final RequestStatus requestStatus) {
            this.requestStatus = Objects.requireNonNull(requestStatus);
            return this;
        }

        public AppExceptionBuilder setCause(final Throwable cause) {
            this.cause = Objects.requireNonNull(cause);
            return this;
        }

        public AppException build() {
            Objects.requireNonNull(this.requestStatus);
            return Objects.nonNull(this.cause)
                    ? new AppException(this.requestStatus, this.cause)
                    : new AppException(this.requestStatus);
        }
    }

    private final RequestStatus requestStatus;

    protected AppException(
            final Throwable cause
    ) {
        super(cause);
        this.requestStatus = RequestStatus.INTERNAL_ERROR;
    }

    protected AppException(
            final RequestStatus requestStatus
    ) {
        super();
        this.requestStatus = Objects.requireNonNull(requestStatus);
    }

    protected AppException(
            final RequestStatus requestStatus,
            final Throwable cause
    ) {
        super(cause);
        this.requestStatus = Objects.requireNonNull(requestStatus);
    }

    public static AppExceptionBuilder builder() {
        return new AppExceptionBuilder();
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

}
