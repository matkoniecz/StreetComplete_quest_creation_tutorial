package de.westnordost.streetcomplete.data.elementfilter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class StringWithCursorTest {
    @Test fun advance() {
        val x = StringWithCursor("ab")
        assertEquals(0, x.cursorPos)
        assertEquals('a', x.advance())
        assertEquals(1, x.cursorPos)
        assertEquals('b', x.advance())
        assertEquals(2, x.cursorPos)

        try {
            x.advance()
            fail()
        } catch (ignore: IndexOutOfBoundsException) {
        }
    }

    @Test fun advanceBy() {
        val x = StringWithCursor("wundertuete")
        assertEquals("wunder", x.advanceBy(6))
        assertEquals("", x.advanceBy(0))
        try {
            x.advanceBy(-1)
            fail()
        } catch (ignore: IndexOutOfBoundsException) {
        }

        assertEquals("tuete", x.advanceBy(99999))
    }

    @Test fun nextIsAndAdvance() {
        val x = StringWithCursor("test123")
        assertTrue(x.nextIsAndAdvance("te"))
        assertEquals(2, x.cursorPos)
        assertFalse(x.nextIsAndAdvance("te"))
        x.advanceBy(3)
        assertTrue(x.nextIsAndAdvance("23"))
        assertEquals(7, x.cursorPos)
        assertTrue(x.isAtEnd())
    }

    @Test fun nextIsAndAdvanceChar() {
        val x = StringWithCursor("test123")
        assertTrue(x.nextIsAndAdvance('t'))
        assertEquals(1, x.cursorPos)
        assertFalse(x.nextIsAndAdvance('t'))
        x.advanceBy(3)
        assertTrue(x.nextIsAndAdvance('1'))
        assertEquals(5, x.cursorPos)
    }

    @Test fun nextIsAndAdvanceIgnoreCase() {
        val x = StringWithCursor("test123")
        assertTrue(x.nextIsAndAdvanceIgnoreCase("TE"))
        assertTrue(x.nextIsAndAdvanceIgnoreCase("st"))
    }

    @Test fun findNext() {
        val x = StringWithCursor("abc abc")
        assertEquals("abc abc".length.toLong(), x.findNext("wurst").toLong())

        assertEquals(0, x.findNext("abc").toLong())
        x.advance()
        assertEquals(3, x.findNext("abc").toLong())
    }

    @Test fun findNextChar() {
        val x = StringWithCursor("abc abc")
        assertEquals("abc abc".length.toLong(), x.findNext('x').toLong())

        assertEquals(0, x.findNext('a').toLong())
        x.advance()
        assertEquals(3, x.findNext('a').toLong())
    }

    @Test fun nextIsChar() {
        val x = StringWithCursor("abc")
        assertTrue(x.nextIs('a'))
        assertFalse(x.nextIs('b'))
        x.advance()
        assertTrue(x.nextIs('b'))
        x.advance()
        assertTrue(x.nextIs('c'))
        x.advance()
        assertFalse(x.nextIs('c'))
    }

    @Test fun nextIsString() {
        val x = StringWithCursor("abc")
        assertTrue(x.nextIs("abc"))
        assertTrue(x.nextIs("ab"))
        assertFalse(x.nextIs("bc"))
        x.advance()
        assertTrue(x.nextIs("bc"))
        x.advance()
        x.advance()
        assertFalse(x.nextIs("c"))
    }

    @Test fun nextMatchesString() {
        val x = StringWithCursor("abc123")
        assertNotNull(x.nextMatches(Regex("abc[0-9]")))
        assertNotNull(x.nextMatches(Regex("abc[0-9]{3}")))
        assertNull(x.nextMatches(Regex("abc[0-9]{4}")))
        assertNull(x.nextMatches(Regex("bc[0-9]")))
        x.advance()
        assertNotNull(x.nextMatches(Regex("bc[0-9]")))
    }

    @Test fun nextIsStringIgnoreCase() {
        val x = StringWithCursor("abc")
        assertTrue(x.nextIsIgnoreCase("A"))
        assertTrue(x.nextIsIgnoreCase("a"))
    }
}
