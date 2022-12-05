package org.maven.apache.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionUtils {

    /**
     * This method generates a custom FadeTransition instance to be used in a transition
     *
     * @param node that undergoes the fade transition
     * @param duration of the transition
     * @param from initial opacity
     * @param to final opacity
     * @return the custom FadeTransition instance
     */
    public static FadeTransition getFadeTransition(Node node, int duration, double from, double to) {
        FadeTransition ft = new FadeTransition();
        // set up the basic transition parameters of it
        ft.setDuration(Duration.millis(duration));
        ft.setNode(node);
        ft.setFromValue(from);
        // from the value of opacity to value of opacity
        ft.setToValue(to);
        return ft;
    }
}