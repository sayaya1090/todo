package dev.sayaya.client.interfaces;

import com.google.gwt.core.client.EntryPoint;
import jsinterop.base.JsArrayLike;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;

public class NewTodoInputElementTestApp implements EntryPoint {

    @Override
    public void onModuleLoad() {
        var todoStore = NewTodoInputElementTestComponent.create().store();
        todoStore.map(JsArrayLike::asList).subscribe(todos -> {
            if (!todos.isEmpty()) {
                String lastTitle = todos.get(todos.size() - 1).value().title();
                console.log("TODO ADDED: " + lastTitle);
            }
        });
        var inputComponent = new NewTodoInputElement(todoStore);
        body().add(inputComponent);
        console.log("APP READY");
    }
}