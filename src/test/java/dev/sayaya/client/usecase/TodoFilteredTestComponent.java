package dev.sayaya.client.usecase;


import javax.inject.Singleton;

@Singleton
@dagger.Component
interface TodoFilteredTestComponent {
    TodoListStore store();
    StatStore stat();
    static TodoFilteredTestComponent create() {
        return DaggerTodoFilteredTestComponent.create();
    }
}
