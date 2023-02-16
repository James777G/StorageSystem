package org.maven.apache.utils;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.val;

/**
 * In RotationUtils, directions of rotation is stored as enums
 * To enter directions ,just type "CLOCKWISE" to choose directions
 * <p>
 * The reason for this:
 * In the RotationTransition class, the direction of rotation is not specified (clockwise by default)
 * We can only reach counterclockwise rotation by passing in -ve angles
 */

@SuppressWarnings("all")
public class RotationUtils {
    public enum Direction {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node      that undergoes to rotate transition
     * @param duration  of the transition
     * @param direction of rotation
     * @param by        the angle of rotation
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionBy(Node node, double duration, Direction direction, double by) {
        // Set rotating animation duration and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration), node);
        if (direction == Direction.CLOCKWISE) {
            // Set angle of rotation
            rotate.setByAngle(by);
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            // Set angle of rotation
            rotate.setByAngle(0 - by);
        }


        return rotate;
    }

    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node      that undergoes to rotate transition
     * @param duration  of the transition
     * @param from      initial position
     * @param direction of rotation
     * @param by        the angle of rotation
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionFromBy(Node node, double duration, double from, Direction direction, double by) {
        // Set rotating animation duration and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration), node);
        // Set the stating position and the angle of rotation
        rotate.setFromAngle(from);
        if (direction == Direction.CLOCKWISE) {
            // Set angle of rotation
            rotate.setByAngle(by);
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            // Set angle of rotation
            rotate.setByAngle(0 - by);
        }
        return rotate;
    }

    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node     that undergoes to rotate transition
     * @param duration of the transition
     * @param from     initial position
     * @param to       final position
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionFromTo(Node node, double duration, double from, double to) {
        // Set rotating animation duration time and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration), node);
        // Set starting and ending position of the rotation
        rotate.setFromAngle(from);
        rotate.setToAngle(to);
        return rotate;
    }

    /**
     * This method generates a custom RotateTransition instance to be used in a transition
     *
     * @param node     that undergoes to rotate transition
     * @param duration of the transition
     * @param to       final position
     * @return the custom RotateTransition instance
     */
    public static RotateTransition getRotationTransitionTo(Node node, double duration, double to) {
        // Set rotating animation duration time and the node
        RotateTransition rotate = new RotateTransition(Duration.millis(duration), node);
        // Set starting and ending position of the rotation
        rotate.setToAngle(to);
        return rotate;
    }

    /**
     * This method modifies a custom RotateTransition instance to be used in a transition
     *
     * @param rotateTransition to add Ease Out interpolator(Ease Out means translate from fast to slow)
     * @return the custom RotateTransition instance
     */
    public static RotateTransition addEaseOutTranslateInterpolator(RotateTransition rotateTransition) {
        rotateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        return rotateTransition;
    }

    /**
     * This method modifies a custom RotateTransition instance to be used in a transition
     *
     * @param rotateTransition to add Ease In interpolator(Ease In means translate from slow to fast)
     * @return the custom RotateTransition instance
     */
    public static RotateTransition addEaseInTranslateInterpolator(RotateTransition rotateTransition) {
        rotateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t == 0) ? 0.0 : Math.pow(2.0, 10 * (t - 1));
            }
        });
        return rotateTransition;
    }

    /**
     * Don't know the effect of this...
     * This method modifies a custom RotateTransition instance to be used in a transition
     *
     * @param rotateTransition to add Ease InOut interpolator
     * @return the custom RotateTransition instance
     */
    public static RotateTransition addEaseInOutTranslateInterpolator(RotateTransition rotateTransition) {
        rotateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                if ((t == 0.0) || (t == 1.0)) {
                    return t;
                }

                val r = t * 2 - 1;
                if (r < 0) {
                    return 0.5 * Math.pow(2.0, 10 * r);
                }
                return 1 - 0.5 * Math.pow(2.0, -10 * r);
            }
        });
        return rotateTransition;
    }

}
