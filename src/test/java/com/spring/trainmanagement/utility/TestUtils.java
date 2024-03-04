package com.spring.trainmanagement.utility;

import org.opentest4j.AssertionFailedError;

public class TestUtils {
    public static AssertionFailedError buildAssertionFailedError(String message, Object expected, Object actual, Throwable cause) {
     
        if (expected != null && actual != null) {
            return new AssertionFailedError(message, expected, actual, cause);
        } else if (expected != null) {
            return new AssertionFailedError(message, expected, cause);
        } else if (actual != null) {
            return new AssertionFailedError(message, actual, cause);
        } else {
            return new AssertionFailedError(message, cause);
        }
    }
}
