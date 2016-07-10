package com.navid.goeuro.config;

import org.springframework.boot.ExitCodeGenerator;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */
public class ExitException extends RuntimeException implements ExitCodeGenerator {

    public ExitException(String message) {
        super(message);
    }

    public ExitException() {
    }

    @Override
    public int getExitCode() {
        return -1;
    }
}
