package dev.sayaya.client.domain;

public record Stat (
        int total,
        int active,
        int completed
) {
    public Stat {
        if (total < 0) throw new IllegalArgumentException("total cannot be negative");
        if (active < 0) throw new IllegalArgumentException("active cannot be negative");
        if (completed < 0) throw new IllegalArgumentException("completed cannot be negative");
        if (active + completed != total) {
            throw new IllegalArgumentException(
                    "total must equal active + completed (total=" + total +
                            ", active=" + active + ", completed=" + completed + ")"
            );
        }
    }
    public static Stat of(int total, int active, int completed) {
        return new Stat(total, active, completed);
    }

    public static Stat empty() {
        return new Stat(0, 0, 0);
    }
}
