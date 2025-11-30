package dev.sayaya.client.interfaces;

import dev.sayaya.client.domain.State;
import elemental2.dom.HTMLDivElement;
import lombok.experimental.Delegate;
import org.jboss.elemento.HTMLContainerBuilder;
import org.jboss.elemento.IsElement;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.jboss.elemento.Elements.div;

@Singleton
public class TodoToolbarElement implements IsElement<HTMLDivElement> {
    @Delegate private final HTMLContainerBuilder<HTMLDivElement> container = div();

    @Inject TodoToolbarElement(
            StateFilterButton.FilterButtonFactory buttonFactory,
            TodoCountActiveLabel lblCnt,
            ClearCompletedButton btnClear) {
        var btnAll = buttonFactory.create("All", null);
        var btnActive = buttonFactory.create("Active", State.ACTIVE);
        var btnCompleted = buttonFactory.create("Completed", State.COMPLETED);

        container.css("todo-toolbar")
                .add(lblCnt)
                .add(btnAll)
                .add(btnActive)
                .add(btnCompleted)
                .add(btnClear);
    }
}