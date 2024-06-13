package com.nongratis.timetracker.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimerLogicTest {
    private TimerLogic timerLogic;

    @Before
    public void setUp() throws Exception {
        timerLogic = new TimerLogic();
    }

    @After
    public void tearDown() throws Exception {
        timerLogic = null;
    }

    @Test
    public void startTimer() {
        timerLogic.startTimer();
        assertTrue(timerLogic.isRunning());
        assertFalse(timerLogic.isPaused());
    }

    @Test
    public void stopTimer() {
        timerLogic.startTimer();
        timerLogic.stopTimer();
        assertFalse(timerLogic.isRunning());
        assertFalse(timerLogic.isPaused());
    }

    @Test
    public void pauseTimer() {
        timerLogic.startTimer();
        timerLogic.pauseTimer();
        assertFalse(timerLogic.isRunning());
        assertTrue(timerLogic.isPaused());
    }

    @Test
    public void isRunning() {
        timerLogic.startTimer();
        assertTrue(timerLogic.isRunning());
        timerLogic.stopTimer();
        assertFalse(timerLogic.isRunning());
    }

    @Test
    public void isPaused() {
        timerLogic.startTimer();
        timerLogic.pauseTimer();
        assertTrue(timerLogic.isPaused());
        timerLogic.startTimer();
        assertFalse(timerLogic.isPaused());
    }
}