package com.victormoraes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleValidatorTest {

    private ScheduleValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ScheduleValidator();
    }

    // --- README examples ---

    @Test
    void shouldReturnTrue_whenLMovesLeftAndRMovesRight() {
        assertTrue(validator.canTransform("_L__R__R_", "L______RR"));
    }

    @Test
    void shouldReturnFalse_whenPiecesWouldCrossEachOther() {
        assertFalse(validator.canTransform("R_L_", "__LR"));
    }

    @Test
    void shouldReturnFalse_whenRTriesToMoveLeft() {
        assertFalse(validator.canTransform("_R", "R_"));
    }

    @Test
    void shouldReturnTrue_whenSchedulesAreAlreadyIdentical() {
        assertTrue(validator.canTransform("_L__R", "_L__R"));
    }

    // --- L movement rules ---

    @Test
    void shouldReturnTrue_whenLMovesLeft() {
        assertTrue(validator.canTransform("_L", "L_"));
    }

    @Test
    void shouldReturnFalse_whenLTriesToMoveRight() {
        assertFalse(validator.canTransform("L_", "_L"));
    }

    // --- R movement rules ---

    @Test
    void shouldReturnTrue_whenRMovesRight() {
        assertTrue(validator.canTransform("R_", "_R"));
    }

    // --- Relative order rules ---

    @Test
    void shouldReturnFalse_whenRelativeOrderOfPiecesChanges() {
        assertFalse(validator.canTransform("RL", "LR"));
    }

    @Test
    void shouldReturnFalse_whenDifferentNumberOfPieces() {
        assertFalse(validator.canTransform("R_R", "_R_"));
    }
}
