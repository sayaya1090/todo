package dev.sayaya.client.usecase;

import dev.sayaya.client.domain.State;
import dev.sayaya.client.domain.Todo;
import dev.sayaya.rx.Observable;
import dev.sayaya.rx.subject.BehaviorSubject;
import elemental2.core.JsArray;
import lombok.experimental.Delegate;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dev.sayaya.rx.subject.BehaviorSubject.behavior;

/**
 * Manages a list of TodoStores.
 * Each TodoStore represents a single Todo item and ensures non-null invariant.
 */
@Singleton
public class TodoListStore {
    private final BehaviorSubject<JsArray<TodoStore>> _this = behavior(new JsArray<>());
    @Delegate private final Observable<JsArray<TodoStore>> observable = _this;
    private final TodoStore.TodoStoreFactory storeFactory;
    @Inject public TodoListStore(TodoStore.TodoStoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    /**
     * Adds a new todo to the list.
     * @param todo the todo to add (must be non-null)
     * @throws IllegalArgumentException if todo is null
     */
    public void add(Todo todo) {
        if (todo == null) throw new IllegalArgumentException("Todo cannot be null");
        var current = _this.getValue();
        var next = current.slice();
        var store = storeFactory.create(todo);
        next.push(store);
        _this.next(next);
    }

    /**
     * Removes a todo store from the list.
     * @param store the TodoStore to remove
     */
    public void remove(TodoStore store) {
        if (store == null) return;
        var current = _this.getValue();
        var next = current.filter((t, idx) -> t != store);
        _this.next(next);
    }

    public void clearCompleted() {
        var current = _this.getValue();
        var next = new JsArray<TodoStore>();
        current.forEach((store, idx) -> {
            if (store.value().state() == State.COMPLETED) store.dispose();
            else next.push(store);
            return null;
        });
        _this.next(next);
    }
    public JsArray<TodoStore> values() {
        return _this.getValue();
    }
}