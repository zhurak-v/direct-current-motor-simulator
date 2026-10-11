package com.university.direct_current_motor.core.exceptions;

import java.util.Objects;

public final class RequestStatus {

    public static final RequestStatus NOT_FOUND;
    public static final RequestStatus INTERNAL_ERROR;

    static {
        NOT_FOUND = RequestStatus.builder()
                .setStatus(404)
                .setMessage("Resource not found")
                .setCode("NOT_FOUND")
                .build();
        INTERNAL_ERROR = RequestStatus.builder()
                .setStatus(500)
                .setMessage("Internal error")
                .setCode("INTERNAL_ERROR")
                .build();
    }

    public static final class RequestStatusBuilder {

        private static final Integer DEFAULT_STATUS;
        private static final String DEFAULT_MESSAGE;
        private static final String DEFAULT_CODE;

        static {
            DEFAULT_STATUS = null;
            DEFAULT_MESSAGE = null;
            DEFAULT_CODE = null;
        }

        private Integer status;
        private String message;
        private String code;

        private RequestStatusBuilder() {
            this.status = DEFAULT_STATUS;
            this.message = DEFAULT_MESSAGE;
            this.code = DEFAULT_CODE;
        }

        public RequestStatusBuilder setStatus(final int status) {
            this.status = status;
            return this;
        }

        public RequestStatusBuilder setMessage(final String message) {
            this.message = Objects.requireNonNull(message);
            return this;
        }

        public RequestStatusBuilder setCode(final String code) {
            this.code = Objects.requireNonNull(code);
            return this;
        }

        public RequestStatus build() {
            return new RequestStatus(
                    Objects.requireNonNull(this.status),
                    Objects.requireNonNull(this.message),
                    Objects.requireNonNull(this.code)
            );
        }

    }

    private final int status;
    private final String message;
    private final String code;

    private RequestStatus(
            final int status,
            final String message,
            final String code
    ) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public static RequestStatusBuilder builder() {
        return new RequestStatusBuilder();
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

}


