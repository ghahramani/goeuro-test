package com.navid.goeuro.config;

import org.springframework.boot.ExitCodeGenerator;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */
public class FinishException extends RuntimeException implements ExitCodeGenerator {

    public FinishException(String message) {
        super(message);
    }

    public FinishException() {
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
