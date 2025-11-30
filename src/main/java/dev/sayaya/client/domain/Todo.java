package dev.sayaya.client.domain;

public record Todo (
        String id,
        String title,
        State state
) {
    public Todo {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        if (title.trim().length() < 2) throw new IllegalArgumentException("title must be at least 2 characters long");
        if (state == null) throw new IllegalArgumentException("state cannot be null");
    }
    public static Todo create(String id, String title) {
        return new Todo(id, title, State.ACTIVE);
    }
    public Todo withTitle(String title) {
        return new Todo(id, title, state);
    }
    public Todo withState(State state) {
        return new Todo(id, title, state);
    }
}
