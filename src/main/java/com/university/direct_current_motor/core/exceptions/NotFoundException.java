package com.university.direct_current_motor.core.exceptions;

public final class NotFoundException extends AppException {

    public NotFoundException() {
        super(RequestStatus.NOT_FOUND);
    }

}
