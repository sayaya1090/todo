package dev.sayaya.client.interfaces;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import dev.sayaya.client.domain.State;
import dev.sayaya.client.usecase.StateFilter;
import org.jboss.elemento.EventType;

class StateFilterButton extends TodoActionButton {
    @AssistedInject StateFilterButton(@Assisted String label, @Assisted State value, StateFilter filter) {
        super(label);
        on(EventType.click, evt -> {
            evt.preventDefault();
            filter.filter(value);
        });
        filter.subscribe(state -> {
            if (state == value) css("selected");
            else element().classList.remove("selected");
        });
    }
    @AssistedFactory
    interface FilterButtonFactory {
        StateFilterButton create(@Assisted String label, @Assisted State value);
    }
}
