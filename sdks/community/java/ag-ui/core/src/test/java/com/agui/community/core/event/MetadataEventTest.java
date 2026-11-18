package com.agui.community.core.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.agui.community.core.message.Role;
import java.util.List;
import org.junit.jupiter.api.Test;

class MetadataEventTest {

    private static final Object METADATA = List.of("logicalRunId", "run-1");

    @Test
    void canonicalConstructorsExposeMetadataOnAllTargetEvents() {
        RunStartedEvent runStarted =
                new RunStartedEvent("thread", "run", "parent", "input", 1L, "raw", METADATA);
        ToolCallStartEvent toolCallStart =
                new ToolCallStartEvent("call", "search", "message", 2L, "raw", METADATA);
        ToolCallArgsEvent toolCallArgs = new ToolCallArgsEvent("call", "{}", 3L, "raw", METADATA);
        ToolCallEndEvent toolCallEnd = new ToolCallEndEvent("call", 4L, "raw", METADATA);
        ToolCallResultEvent toolCallResult =
                new ToolCallResultEvent("result", "call", "output", Role.TOOL, 5L, "raw", METADATA);

        assertSame(METADATA, runStarted.metadata());
        assertSame(METADATA, toolCallStart.metadata());
        assertSame(METADATA, toolCallArgs.metadata());
        assertSame(METADATA, toolCallEnd.metadata());
        assertSame(METADATA, toolCallResult.metadata());
    }

    @Test
    void convenienceConstructorsLeaveMetadataNull() {
        assertNull(new RunStartedEvent("thread", "run").metadata());
        assertNull(new ToolCallStartEvent("call", "search").metadata());
        assertNull(new ToolCallArgsEvent("call", "{}").metadata());
        assertNull(new ToolCallEndEvent("call").metadata());
        assertNull(new ToolCallResultEvent("result", "call", "output").metadata());
    }

    @Test
    void metadataParticipatesInGeneratedRecordMethods() {
        RunStartedEvent first = new RunStartedEvent("thread", "run", null, null, null, null, METADATA);
        RunStartedEvent equal = new RunStartedEvent("thread", "run", null, null, null, null, METADATA);
        RunStartedEvent different =
                new RunStartedEvent("thread", "run", null, null, null, null, "other");

        assertEquals(first, equal);
        assertEquals(first.hashCode(), equal.hashCode());
        assertNotEquals(first, different);
        assertNotNull(first.toString());
        assertTrue(first.toString().contains("metadata="));
    }
}
