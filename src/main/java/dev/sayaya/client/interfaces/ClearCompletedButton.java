package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoListStore;
import org.jboss.elemento.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class ClearCompletedButton extends TodoActionButton {
    @Inject
    ClearCompletedButton(TodoListStore listStore) {
        super("Clear Completed");
        on(EventType.click, evt -> {
            evt.preventDefault();
            listStore.clearCompleted();
        });
    }
}
