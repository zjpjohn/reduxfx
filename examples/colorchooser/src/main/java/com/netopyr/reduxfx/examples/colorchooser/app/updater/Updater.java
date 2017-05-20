package com.netopyr.reduxfx.examples.colorchooser.app.updater;

import com.netopyr.reduxfx.examples.colorchooser.app.actions.UpdateColorAction;
import com.netopyr.reduxfx.examples.colorchooser.app.state.AppState;
import javaslang.API;

import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * The {@code Updater} is the heart of every ReduxFX-application. This is where the main application logic resides.
 * <p>
 * An {@code Updater} consists of a single function ({@link #update(AppState, Object)} in this class), which takes
 * the current state (an instance of {@link AppState}) and an action and calculates the new state from that.
 * <p>
 * Please note that {@code Updater} has no internal state. Everything that is needed for {@code update} is passed in
 * the parameters.
 */
public class Updater {

    private Updater() {
    }

    /**
     * The method {@code update} is the central piece of the ColorChooser-application. The whole application logic is
     * implemented here.
     * <p>
     * This method takes the current state (an instance of {@link AppState}) and an action and calculates the
     * new state from that.
     * <p>
     * Please note that {@code update} does not require any internal state. Everything that is needed, is passed in the
     * parameters. Also {@code update} has no side effects. It is a pure function.
     * <p>
     * Also please note, that {@code AppState} is an immutable data structure. This means that {@code update} does not
     * modify the old state, but instead creates a new instance of {@code AppState}, if anything changes.
     *
     * @param state  the current state
     * @param action the {@code Action} that needs to be performed
     * @return the new state
     * @throws NullPointerException if state or action are {@code null}
     */
    public static AppState update(AppState state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        // This is part of Javaslang's pattern-matching API. It works similar to the regular switch-case
        // in Java, except that it is much more flexible and that it can be used as an expression.
        // We check which kind of action was received and in that case-branch we specify the value that
        // will be assigned to newState.
        return Match(action).of(

                // If the action is a UpdateColorAction, we return a new AppState with the
                // property color set to the new value.
                API.Case(instanceOf(UpdateColorAction.class),
                        updateColorAction -> state.withColor(updateColorAction.getValue())
                ),

                // This is the default branch of this switch-case. If an unknown Action was passed to the
                // updater, we simple return the old state. This is a convention, that is not needed right
                // now, but will help once you start to decompose your updater.
                Case($(), state)
        );
    }
}