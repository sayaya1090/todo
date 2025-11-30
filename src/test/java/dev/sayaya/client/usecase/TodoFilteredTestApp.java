package dev.sayaya.client.usecase;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.client.domain.State;

import static elemental2.dom.DomGlobal.console;

public class TodoFilteredTestApp implements EntryPoint {
    private TodoListStore todoListStore;
    private StateFilter stateFilter;
    private TodoFiltered todoFiltered;

    @Override
    public void onModuleLoad() {
        var components = TodoFilteredTestComponent.create();
        todoListStore = components.store();
        stateFilter = new StateFilter();
        todoFiltered = new TodoFiltered(todoListStore, stateFilter, components.stat());

        testFiltered();
    }

    private void testFiltered() {
        // TodoFiltered 결과 길이를 로깅
        todoFiltered.subscribe(arr -> {
            console.log("TodoFiltered Emitted: " + arr.length);
        });

        // 1) 필터가 null 인 상태에서 TODO 3개 추가 (ACTIVE 2, COMPLETED 1)
        todoListStore.add(TodoFactory.create("Active 1"));                                   // ACTIVE
        todoListStore.add(TodoFactory.create("Active 2"));                                   // ACTIVE
        todoListStore.add(TodoFactory.create("Completed 1").withState(State.COMPLETED));     // COMPLETED
        console.log("TodoFiltered After Null Filter");

        // 2) 필터를 COMPLETED 로 설정
        stateFilter.filter(State.COMPLETED);
        console.log("TodoFiltered After COMPLETED Filter");
    }
}