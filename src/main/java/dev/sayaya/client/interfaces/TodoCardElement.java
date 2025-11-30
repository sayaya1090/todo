
package dev.sayaya.client.interfaces;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import dev.sayaya.client.domain.State;
import dev.sayaya.client.domain.Todo;
import dev.sayaya.client.usecase.TodoStore;
import dev.sayaya.rx.Observable;
import dev.sayaya.rx.Subscription;
import dev.sayaya.rx.scheduler.Scheduler;
import dev.sayaya.rx.subject.BehaviorSubject;
import dev.sayaya.ui.elements.CheckboxElementBuilder;
import dev.sayaya.ui.elements.TextFieldElementBuilder;
import elemental2.dom.*;
import lombok.experimental.Delegate;
import org.jboss.elemento.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.sayaya.rx.subject.BehaviorSubject.behavior;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;
import static org.jboss.elemento.EventType.*;

class TodoCardElement implements IsElement<HTMLLIElement> {
    @Delegate private final HTMLContainerBuilder<HTMLLIElement> li = li();
    private final TodoStore todo;
    private final Observable<Todo> todoNotNull;
    private final BehaviorSubject<CardState> state = behavior(CardState.VIEW);
    private final List<Subscription> subscriptions = new ArrayList<>();

    private enum CardState {
        VIEW, EDITING
    }

    @AssistedInject TodoCardElement(@Assisted TodoStore todo) {
        this.todo = todo;
        this.todoNotNull = todo.filter(Objects::nonNull);
        li.css("todo-item")
          .add(div().css("view")
                     .add(iptState())
                     .add(lblTitle())
                     .add(button().icon("close").css("destroy")
                                  .onClick(evt-> remove())))
          .add(iptTitle());

        subscriptions.add(this.todoNotNull.subscribe(t -> toggleCss(li, "completed", t.state() == State.COMPLETED)));
        subscriptions.add(this.state.subscribe(state -> toggleCss(li, "editing", state == CardState.EDITING)));
    }

    private static void toggleCss(ElementClassListMethods<?, ?> elem, String css, boolean enabled) {
        if (enabled) elem.css(css);
        else elem.element().classList.remove(css);
    }

    private CheckboxElementBuilder iptState() {
        var checkbox = checkbox().css("toggle").on(change, evt-> {
            var current = this.todo.value();
            var next = current.withState(current.state() == State.COMPLETED ? State.ACTIVE : State.COMPLETED);
            todo.update(next);
        });
        subscriptions.add(this.todoNotNull.subscribe(todo->checkbox.select(todo.state() == State.COMPLETED)));
        return checkbox;
    }

    private HTMLContainerBuilder<HTMLLabelElement> lblTitle() {
        var label = label().css("todo-title").on(dblclick, evt->this.state.next(CardState.EDITING));
        subscriptions.add(this.todoNotNull.subscribe(todo->label.text(todo.title())));
        return label;
    }

    private TextFieldElementBuilder<?, ?> iptTitle() {
        var textField = textField().filled().css("edit");
        EventCallbackFn<FocusEvent> exitEditMode = evt -> {
            if (this.state.getValue() == CardState.VIEW) return;
            try {
                var current = this.todo.value();
                var next = current.withTitle(textField.value());
                todo.update(next);
            } catch (IllegalArgumentException ignore) {}
            this.state.next(CardState.VIEW);
        };
        textField.on(blur, exitEditMode)
                .on(keydown, evt->{
                    if ("Enter".equals(evt.key)) exitEditMode.onEvent(null);
                    else if ("Escape".equals(evt.key)) this.state.next(CardState.VIEW);
                });

        subscriptions.add(this.state.subscribe(state->{
            if (state == CardState.EDITING) {
                var current = todo.value();
                textField.value(current.title());
                Scheduler.asapScheduler().schedule(()->textField.element().focus());
            }
        }));
        return textField;
    }

    public void remove() {
        element().remove();
        dispose();
        todo.remove();
    }

    private void dispose() {
        subscriptions.forEach(Subscription::unsubscribe);
        subscriptions.clear();
    }

    @AssistedFactory
    interface TodoCardElementFactory {
        TodoCardElement create(@Assisted TodoStore todo);
    }
}