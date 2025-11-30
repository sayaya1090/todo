package dev.sayaya.client;

import com.google.gwt.core.client.EntryPoint;

import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

public class Application implements EntryPoint {
    @Override
    public void onModuleLoad() {
        var components = Component.create();
        body().css("todo-app")
              .add(div().style("""
                      display: flex;
                      gap: 1rem;
                      align-items: center;
                      width: 100%;
                      box-sizing: border-box;
                      padding: 16px 16px 16px 16px;
                      """).add(components.toggle()).add(components.newTodo()))
              .add(components.list())
              .add(components.toolbar());
    }
}
