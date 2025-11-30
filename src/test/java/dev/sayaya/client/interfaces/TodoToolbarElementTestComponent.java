package dev.sayaya.client.interfaces;

import javax.inject.Singleton;

@Singleton
@dagger.Component
interface TodoToolbarElementTestComponent {
    StateFilterButton.FilterButtonFactory factory();
    TodoCountActiveLabel lblCnt();
    ClearCompletedButton btnClear();
    static TodoToolbarElementTestComponent create() {
        return DaggerTodoToolbarElementTestComponent.create();
    }
}
