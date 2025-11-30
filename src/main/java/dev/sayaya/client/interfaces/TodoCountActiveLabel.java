package dev.sayaya.client.interfaces;

import dev.sayaya.client.domain.State;
import dev.sayaya.client.usecase.StatStore;
import dev.sayaya.client.usecase.TodoListStore;
import dev.sayaya.client.usecase.TodoStore;
import dev.sayaya.rx.Observable;
import elemental2.core.JsArray;
import elemental2.dom.HTMLLabelElement;
import lombok.experimental.Delegate;
import org.jboss.elemento.HTMLContainerBuilder;
import org.jboss.elemento.IsElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.jboss.elemento.Elements.label;

@Singleton
public class TodoCountActiveLabel implements IsElement<HTMLLabelElement> {
    @Delegate private final HTMLContainerBuilder<HTMLLabelElement> label = label().css("todo-count");

    @Inject TodoCountActiveLabel(StatStore store) {
        store.map(stat -> switch (stat.active()) {
            case 0 -> "Done!";
            case 1 -> "1 item left!";
            default -> stat.active() + " items left!";
        }).subscribe(label::text);
    }
}