package org.panacea.drmp.ree.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class REEException extends RuntimeException {

    protected Throwable throwable;

    public REEException(String message) {
        super(message);
    }

    public REEException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
        log.error("[REE]: ", message);
    }

    public Throwable getCause() {
        return throwable;
    }
}