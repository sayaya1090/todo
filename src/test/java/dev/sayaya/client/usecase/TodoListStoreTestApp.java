package dev.sayaya.client.usecase;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import jsinterop.base.JsArrayLike;

public class TodoListStoreTestApp implements EntryPoint {
    private TodoListStore todoListStore;

    @Override
    public void onModuleLoad() {
        todoListStore = new TodoListStore(TodoListStoreTestComponent.create().storeFactory());
        testSubscription();
    }
    private void testSubscription() {
        todoListStore.map(JsArrayLike::asList).subscribe(todos -> {
            if (!todos.isEmpty()) {
                DomGlobal.console.log("TodoStore Updated: Count=" + todos.size());
                DomGlobal.console.log("Last Item: " + todos.get(todos.size() - 1).value().title());
            }
        });
        todoListStore.add(TodoFactory.create("New Task via Delegate"));
    }
}