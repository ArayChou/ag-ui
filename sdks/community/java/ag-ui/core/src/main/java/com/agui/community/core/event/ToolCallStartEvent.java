package com.agui.community.core.event;

import java.util.Objects;

/**
 * Signals the start of a streamed tool call.
 *
 * @param toolCallId      the unique tool call identifier (required)
 * @param toolCallName    the name of the tool being called (required)
 * @param parentMessageId the id of the message this tool call belongs to, or
 *                        {@code null} (optional)
 * @param timestamp       the event creation time in epoch milliseconds, or
 *                        {@code null} (optional)
 * @param rawEvent        the original event this was transformed from, or
 *                        {@code null} (optional)
 * @param metadata        可选的事件元数据，或 {@code null}
 * @see <a href="https://docs.ag-ui.com/concepts/events">AG-UI Events</a>
 */
public record ToolCallStartEvent(String toolCallId, String toolCallName, String parentMessageId,
                                 Long timestamp, Object rawEvent, Object metadata) implements Event {

    public ToolCallStartEvent {
        Objects.requireNonNull(toolCallId, "toolCallId must not be null");
        Objects.requireNonNull(toolCallName, "toolCallName must not be null");
    }

    public ToolCallStartEvent(String toolCallId, String toolCallName, String parentMessageId,
                              Long timestamp, Object rawEvent) {
        this(toolCallId, toolCallName, parentMessageId, timestamp, rawEvent, null);
    }

    /**
     * Creates a tool-call-start event with the required fields.
     *
     * @param toolCallId   the unique tool call identifier
     * @param toolCallName the name of the tool being called
     */
    public ToolCallStartEvent(String toolCallId, String toolCallName) {
        this(toolCallId, toolCallName, null, null, null, null);
    }

    @Override
    public EventType type() {
        return EventType.TOOL_CALL_START;
    }
}
