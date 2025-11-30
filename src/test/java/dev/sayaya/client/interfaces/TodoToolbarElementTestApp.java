package dev.sayaya.client.interfaces;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.client.usecase.StateFilter;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;

class TodoToolbarElementTestApp implements EntryPoint {
    @Override
    public void onModuleLoad() {
        var components = TodoToolbarElementTestComponent.create();
        var filter = new StateFilter();
        var element = new TodoToolbarElement(components.factory(), components.lblCnt(), components.btnClear());

        filter.asObservable().subscribe(state -> {
            if (state == null) console.log("StateFilter changed: ALL");
            else console.log("StateFilter changed: " + state.name());
        });

        body().add(element);
        console.log("TodoToolbarElement READY");
    }
}