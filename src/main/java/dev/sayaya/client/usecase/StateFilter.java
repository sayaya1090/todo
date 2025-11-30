package dev.sayaya.client.usecase;

import dev.sayaya.client.domain.State;
import dev.sayaya.rx.Observable;
import dev.sayaya.rx.subject.BehaviorSubject;
import lombok.experimental.Delegate;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dev.sayaya.rx.subject.BehaviorSubject.behavior;

@Singleton
public class StateFilter {
    private BehaviorSubject<State> _this = behavior(null);
    @Delegate private final Observable<State> observable = _this;
    @Inject public StateFilter() {}
    public Observable<State> asObservable() {
        return observable;
    }
    public void filter(State state) {
        _this.next(state);
    }
}
