package dev.sayaya.client.interfaces;

import dev.sayaya.client.domain.Stat;
import dev.sayaya.client.domain.State;
import dev.sayaya.client.usecase.StatStore;
import dev.sayaya.client.usecase.TodoListStore;
import dev.sayaya.ui.dom.MdIconButtonElement;
import dev.sayaya.ui.elements.IconButtonElementBuilder;
import dev.sayaya.ui.elements.IconElementBuilder;
import elemental2.dom.DomGlobal;
import elemental2.dom.MouseEvent;
import lombok.experimental.Delegate;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static dev.sayaya.ui.elements.ButtonElementBuilder.button;

@Singleton
public class ToggleAllButton implements IsElement<MdIconButtonElement> {
    private final IconElementBuilder icon = IconElementBuilder.icon("check");
    @Delegate private final IconButtonElementBuilder.PlainIconButtonElementBuilder btn = button().icon(icon).css("toggle-complete");
    private Consumer<MouseEvent> clickHandler;
    private final TodoListStore todoListStore;
    @Inject ToggleAllButton(StatStore statStore, TodoListStore todoListStore) {
        this.todoListStore = todoListStore;
        statStore.subscribe(this::update);
        btn.onClick(evt->clickHandler.accept(evt));
    }
    private void update(Stat stat) {
        if(stat.total() == stat.completed()) {
            icon.css("completed");
            clickHandler = evt-> this.setActive();
        } else {
            icon.element().classList.remove("completed");
            clickHandler = evt->this.setCompleted();
        }
    }
    private void setCompleted() {
        setState(State.COMPLETED);
    }
    private void setActive() {
        setState(State.ACTIVE);
    }
    private void setState(State state) {
        todoListStore.values().forEach((store, idx) -> {
            store.update(store.value().withState(state));
            return null;
        });
    }
}
