package dev.sayaya.client.usecase;

import dev.sayaya.client.domain.Todo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TodoFactory {
    public Todo create(String title) {
        return Todo.create(Crypto.randomUUID(), title);
    }
}
