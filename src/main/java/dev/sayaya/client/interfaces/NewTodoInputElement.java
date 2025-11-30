package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoFactory;
import dev.sayaya.client.usecase.TodoListStore;
import dev.sayaya.ui.dom.MdTextFieldElement;
import dev.sayaya.ui.elements.TextFieldElementBuilder;
import elemental2.dom.KeyboardEvent;
import lombok.experimental.Delegate;
import org.jboss.elemento.EventType;
import org.jboss.elemento.IsElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;

@Singleton
public class NewTodoInputElement implements IsElement<MdTextFieldElement> {
    private final TodoListStore todoListStore;
    @Delegate private final TextFieldElementBuilder.OutlinedTextFieldElementBuilder element;
    @Inject NewTodoInputElement(TodoListStore todoListStore) {
        this.todoListStore = todoListStore;
        element = textField().outlined().css("new-todo")
                .placeholder("What needs to be done?")
                .on(EventType.keydown, this::handleKeyDown);
        element.element().focus();
    }
    private void handleKeyDown(KeyboardEvent evt) {
        if ("Enter".equals(evt.key)) {
            String value = element.value().trim();
            if (!value.isEmpty()) {
                todoListStore.add(TodoFactory.create(value));
                element.value("");
            }
        }
    }
}
