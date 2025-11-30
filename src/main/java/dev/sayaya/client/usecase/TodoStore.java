package dev.sayaya.client.usecase;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import dev.sayaya.client.domain.Todo;
import dev.sayaya.rx.Observable;
import dev.sayaya.rx.Subscription;
import dev.sayaya.rx.scheduler.Scheduler;
import dev.sayaya.rx.subject.BehaviorSubject;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.util.Objects;

import static dev.sayaya.rx.subject.BehaviorSubject.behavior;

public class TodoStore {
    private final BehaviorSubject<Todo> _this;
    @Delegate private final Observable<Todo> observable;
    private final TodoListStore listStore;
    private final Subscription subscription;
    private final StatStore statStore;
    @AssistedInject TodoStore(@Assisted Todo todo, TodoListStore listStore, StatStore statStore) {
        Objects.requireNonNull(todo, "Initial todo cannot be null");
        _this = behavior(todo);
        observable = _this;
        this.listStore = listStore;
        this.statStore = statStore;
        this.subscription = _this.subscribe(t-> statStore.update());
        Scheduler.asapScheduler().schedule(statStore::update);
    }

    public void update(Todo newTodo) {
        if (newTodo == null) throw new IllegalArgumentException("Todo cannot be null. Use TodoListStore.remove() to delete a todo.");
        _this.next(newTodo);
    }

    public void remove() {
        listStore.remove(this);
        statStore.update();
        dispose();
    }

    void dispose() {
        subscription.unsubscribe();
    }

    /**
     * Returns the current todo value.
     * @return current todo (never null due to constructor validation)
     */
    public @NonNull Todo value() {
        return _this.getValue();
    }
    @AssistedFactory
    public interface TodoStoreFactory {
        TodoStore create(@Assisted Todo todo);
    }
}