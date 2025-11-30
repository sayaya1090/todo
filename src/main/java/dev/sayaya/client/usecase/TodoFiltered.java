package dev.sayaya.client.usecase;

import dev.sayaya.client.domain.State;
import dev.sayaya.rx.Observable;
import elemental2.core.JsArray;
import lombok.experimental.Delegate;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoFiltered {
    @Delegate private final Observable<JsArray<TodoStore>> observable;
    @Inject public TodoFiltered(TodoListStore store, StateFilter filter, StatStore stat) {
        observable = store.combineLatest(filter.asObservable()).combineLatest(stat.asObservable()).map(objs->{
            Object[] obj = (Object[]) objs[0];
            @SuppressWarnings("unchecked") var todos = (JsArray<TodoStore>) obj[0];
            var state = (State) obj[1];
            if(state == null) return todos;
            else return todos.filter((todo, idx) -> {
                var todoValue = todo.value();
                return todoValue != null && todoValue.state() == state;
            });
        }).distinctUntilChanged();
    }
}
