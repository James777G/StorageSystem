package org.maven.apache.utils;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleUtils {

    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param size to scale to
     * @return the custom ScaleTransition instance
     */
    public static ScaleTransition getScaleTransitionBy(Node node, int duration, double size){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration),node);
        scaleTransition.setToX(size);
        scaleTransition.setToY(size);
        return scaleTransition;
    }
}
