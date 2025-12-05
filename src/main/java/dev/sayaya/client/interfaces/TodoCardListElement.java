package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoFiltered;
import dev.sayaya.client.usecase.TodoStore;
import dev.sayaya.rx.Subscription;
import elemental2.core.JsArray;
import elemental2.dom.HTMLUListElement;
import lombok.experimental.Delegate;
import org.jboss.elemento.HTMLContainerBuilder;
import org.jboss.elemento.IsElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

import static org.jboss.elemento.Elements.ul;

@Singleton
public class TodoCardListElement implements IsElement<HTMLUListElement> {
    @Delegate private final HTMLContainerBuilder<HTMLUListElement> ul = ul();
    private final Map<TodoStore, TodoCardElement> cards = new HashMap<>();
    private final TodoCardElement.TodoCardElementFactory factory;
    @Inject TodoCardListElement(TodoFiltered todos, TodoCardElement.TodoCardElementFactory factory) {
        this.factory = factory;
        todos.subscribe(this::update);
    }

    private void update(JsArray<TodoStore> todos) {
        ul.element().textContent = "";
        todos.map((todo, idx)-> createElementIfAbsent(todo))
             .forEach((child, idx)-> ul.add(child));
    }

    private TodoCardElement createElementIfAbsent(TodoStore todo) {
        return cards.computeIfAbsent(todo, factory::create);
    }
}
