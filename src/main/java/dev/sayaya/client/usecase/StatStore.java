package dev.sayaya.client.usecase;

import dev.sayaya.client.domain.Stat;
import dev.sayaya.client.domain.State;
import dev.sayaya.rx.Observable;
import dev.sayaya.rx.subject.BehaviorSubject;
import lombok.experimental.Delegate;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dev.sayaya.rx.subject.BehaviorSubject.behavior;

@Singleton
public class StatStore {
    private final BehaviorSubject<Stat> _this = behavior(Stat.empty());
    @Delegate private final Observable<Stat> observable = _this.asObservable().distinctUntilChanged();
    private final TodoListStore listStore;
    @Inject StatStore(TodoListStore listStore) {
        this.listStore = listStore;
    }
    Observable<Stat> asObservable() {
        return observable;
    }
    void update() {
        var todos = listStore.values().map((store, idx)->store.value());
        int total = todos.length;
        int active = 0;
        int completed = 0;
        for(var todo: todos.asList()) {
            if (todo.state() == State.ACTIVE) active++;
            else if (todo.state() == State.COMPLETED) completed++;
        }
        _this.next(Stat.of(total, active, completed));
    }
}