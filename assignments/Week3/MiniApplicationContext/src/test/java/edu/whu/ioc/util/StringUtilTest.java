package edu.whu.ioc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void upperCaseFirst() {
        assertEquals("Name", StringUtil.upperCaseFirst("name"));
        assertEquals("Name", StringUtil.upperCaseFirst("Name"));
        assertEquals("123", StringUtil.upperCaseFirst("123"));
        assertEquals("!@#", StringUtil.upperCaseFirst("!@#"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.upperCaseFirst(""));
    }

    @Test
    void toBoolean() {
        assertTrue(StringUtil.toBoolean("true"));
        assertTrue(StringUtil.toBoolean("yes"));
        assertTrue(StringUtil.toBoolean("on"));
        assertTrue(StringUtil.toBoolean("1"));
        assertFalse(StringUtil.toBoolean("false"));
        assertFalse(StringUtil.toBoolean("no"));
        assertFalse(StringUtil.toBoolean("off"));
        assertFalse(StringUtil.toBoolean("0"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toBoolean("True"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toBoolean("2"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toBoolean(""));
    }

    @Test
    void toChar() {
        assertEquals('a', StringUtil.toChar("a"));
        assertEquals('A', StringUtil.toChar("A"));
        assertEquals(' ', StringUtil.toChar(" "));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toChar(""));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toChar("name"));
    }

    @Test
    void toLong() {
        assertEquals(0, StringUtil.toLong("0"));
        assertEquals(9, StringUtil.toLong("9"));
        assertEquals(100, StringUtil.toLong("100"));
        assertEquals(-100, StringUtil.toLong("-100"));
        assertEquals(100, StringUtil.toLong("000100"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toLong("123.5"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toLong("name"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toLong(""));
    }

    @Test
    void toDouble() {
        assertEquals(0, StringUtil.toDouble("0"));
        assertEquals(9, StringUtil.toDouble("9"));
        assertEquals(100, StringUtil.toDouble("100"));
        assertEquals(-100, StringUtil.toDouble("-100"));
        assertEquals(100, StringUtil.toDouble("000100"));
        assertEquals(3.14, StringUtil.toDouble("3.14"));
        assertEquals(-3.14, StringUtil.toDouble("-3.14"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toDouble("--123"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toDouble("1+2"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toDouble("name"));
        assertThrows(StringUtil.TransformStringException.class, ()-> StringUtil.toDouble(""));
    }
}