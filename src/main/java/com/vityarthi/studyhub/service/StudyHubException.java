package com.vityarthi.studyhub.service;

public class StudyHubException extends RuntimeException {
    public StudyHubException(String message) { super(message); }
    public StudyHubException(String message, Throwable cause) { super(message, cause); }
}
