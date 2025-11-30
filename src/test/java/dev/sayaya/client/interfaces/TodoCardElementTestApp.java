package dev.sayaya.client.interfaces;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.client.domain.State;
import dev.sayaya.client.domain.Todo;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.ul;

public class TodoCardElementTestApp implements EntryPoint {
    private final TodoCardElementTestComponent components = TodoCardElementTestComponent.create();
    @Override
    public void onModuleLoad() {
        var storeFactory = components.storeFactory();
        var elementFactory = components.elementFactory();
        var ul = ul();
        body().add(ul);
        // Test Case 1: Active Todo
        var todo1 = Todo.create("test-id-1", "Buy groceries");
        var card1 = elementFactory.create(storeFactory.create(todo1));
        ul.add(card1);
        console.log("Created Active Todo: " + todo1.title());

        // Test Case 2: Completed Todo
        var todo2 = Todo.create("test-id-2", "Finish homework").withState(State.COMPLETED);
        var card2 = elementFactory.create(storeFactory.create(todo2));
        ul.add(card2);
        console.log("Created Completed Todo: " + todo2.title());

        // Test Case 3: Todo with long title
        var todo3 = Todo.create("test-id-3", "This is a very long todo title to test text wrapping");
        var card3 = elementFactory.create(storeFactory.create(todo3));
        ul.add(card3);
        console.log("Created Long Title Todo: " + todo3.title());
    }
}