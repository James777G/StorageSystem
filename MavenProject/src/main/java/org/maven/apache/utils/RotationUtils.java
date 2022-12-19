package org.maven.apache.utils;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * In RotationUtils, directions of rotation is stored as enums
 * To enter directions ,just type "CLOCKWISE" to choose directions
 *
 * The reason for this:
 * In the RotationTransition class, the direction of rotation is not specified (clockwise by default)
 * We can only reach counterclockwise rotation by passing in -ve angles
 */

public class RotationUtils {
    public enum Direction{
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node that undergoes to rotate transition
     * @param duration of the transition
     * @param direction of rotation
     * @param by the angle of rotation
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionBy(Node node, int duration, Direction direction, double by) {
        // Set rotating animation duration and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration),node);
        if(direction==Direction.CLOCKWISE){
            // Set angle of rotation
            rotate.setByAngle(by);
        } else if(direction==Direction.COUNTERCLOCKWISE){
            // Set angle of rotation
            rotate.setByAngle(0-by);
        }


        return rotate;
    }
    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node that undergoes to rotate transition
     * @param duration of the transition
     * @param from initial position
     * @param direction of rotation
     * @param by the angle of rotation
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionFromBy(Node node, int duration, double from, Direction direction,  double by) {
        // Set rotating animation duration and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration),node);
        // Set the stating position and the angle of rotation
        rotate.setFromAngle(from);
        if(direction==Direction.CLOCKWISE){
            // Set angle of rotation
            rotate.setByAngle(by);
        } else if(direction==Direction.COUNTERCLOCKWISE){
            // Set angle of rotation
            rotate.setByAngle(0-by);
        }
        return rotate;
    }
    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node that undergoes to rotate transition
     * @param duration of the transition
     * @param from initial position
     * @param to final position
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionFromTo(Node node, int duration, double from, double to) {
        // Set rotating animation duration time and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration),node);
        // Set starting and ending position of the rotation
        rotate.setFromAngle(from);
        rotate.setToAngle(to);
        return rotate;
    }

}
