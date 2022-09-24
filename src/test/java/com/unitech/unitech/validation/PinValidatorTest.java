package com.unitech.unitech.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PinValidatorTest {
    @ParameterizedTest(name = "#{index} - Run test with pin = {0}")
    @MethodSource("validPinProvider")
    void test_pin_regex_valid(String pin) {
        assertTrue(PinValidator.isValid(pin));
    }

    @ParameterizedTest(name = "#{index} - Run test with pin = {0}")
    @MethodSource("invalidPinProvider")
    void test_pin_regex_invalid(String pin) {
        assertFalse(PinValidator.isValid(pin));
    }

    static Stream<String> validPinProvider() {
        return Stream.of(
                "kjhj5Tg"
        );
    }

    static Stream<String> invalidPinProvider() {
        return Stream.of(
                "123As678",
                "12345P");
    }
}
