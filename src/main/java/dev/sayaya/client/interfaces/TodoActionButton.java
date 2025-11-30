package dev.sayaya.client.interfaces;

import dev.sayaya.ui.dom.MdButtonElement;
import dev.sayaya.ui.elements.ButtonElementBuilder;
import lombok.experimental.Delegate;
import org.jboss.elemento.IsElement;

import static dev.sayaya.ui.elements.ButtonElementBuilder.button;

/**
 * Base class for todo-related action buttons (filters, clear, etc.)
 */
abstract class TodoActionButton implements IsElement<MdButtonElement.MdTextButtonElement> {
    @Delegate private final ButtonElementBuilder.TextButtonElementBuilder btn = button().text().css("filter-button");
    protected TodoActionButton(String label) {
        btn.add(label);
    }
}
