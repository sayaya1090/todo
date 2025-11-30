package dev.sayaya.client.interfaces;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.client.domain.State;
import dev.sayaya.client.usecase.TodoFactory;
import dev.sayaya.client.usecase.TodoFiltered;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;

public class TodoCardListElementTestApp implements EntryPoint {
    private final TodoCardListElementTestComponent components = TodoCardListElementTestComponent.create();
    @Override
    public void onModuleLoad() {
        var store = components.store();   // Dagger 컴포넌트에서 TodoStore 주입
        var filtered = components.filtered();
        var list  = new TodoCardListElement(filtered, components.factory());

        // Test Case 1: ACTIVE Todo 2개 추가
        var todo1 = TodoFactory.create("List Active 1");
        var todo2 = TodoFactory.create("List Active 2");
        store.add(todo1);
        store.add(todo2);
        console.log("List Test: Added active todos:", todo1.title(), todo2.title());

        // Test Case 2: COMPLETED Todo 추가
        var todo3 = TodoFactory.create("List Completed 1").withState(State.COMPLETED);
        store.add(todo3);
        console.log("List Test: Added completed todo:", todo3.title());

        // 리스트 컴포넌트를 DOM 에 붙이기
        body().add(list);
        console.log("TodoCardListElement attached to body");

        // Test Case 3: remove() 동작 확인을 위한 Todo
        var removable = TodoFactory.create("Removable Todo");
        store.add(removable);
        console.log("List Test: Added removable todo:", removable.title());
    }
}