package com.franchise.api.domain.exception;

public class BranchNotFoundException extends BusinessException {
    public BranchNotFoundException(String message) {
        super(message);
    }
}
