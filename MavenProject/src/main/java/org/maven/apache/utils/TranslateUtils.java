package org.maven.apache.utils;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.val;

/**
 * TranslateTransition can make the node move in a definite cycle count by setCycleCount method.
 * e.g. translateTransition.setCycleCount(2);
 *
 * TranslateTransition can make the node move back to its original place by setAutoReverse method.
 * e.g. translateTransition.setAutoReverse(true);
 *      translateTransition.setCycleCount(2);
 */

public class TranslateUtils {
    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param x of the distance to translate horizontally
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionByX(Node node, double duration, double x) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setByX(x);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in moving a node to a
     * specific X position
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param x of the horizontal position  translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionToX(Node node, double duration, double x) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setToX(x);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in moving a node to a
     * specific X position
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param fromX of the horizontal position translate from
     * @param toX of the horizontal position translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionFromToX(Node node, double duration, double fromX, double toX) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param y of the distance to translate vertically
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionByY(Node node, double duration, double y) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setByY(y);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in moving a node to a
     * specific Y position
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param y of the vertical position  translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionToY(Node node, double duration, double y) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setToY(y);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in moving a node to a
     * specific Y position
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param fromY of the vertical position translate from
     * @param toY of the vertical position translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionFromToY(Node node, double duration, double fromY, double toY) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param x of the distance to translate horizontally
     * @param y of the distance to translate vertically
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionByXY(Node node, double duration, double x ,double y) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setByX(x);
        translateTransition.setByY(y);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param x of the horizontal position  translate to
     * @param y of the vertical position  translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionToXY(Node node, double duration, double x ,double y) {
        // Set translate animation duration and the node
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setToX(x);
        translateTransition.setToY(y);
        return translateTransition;
    }

    /**
     * This method generates a custom TranslateTransition instance to be used in a transition
     *
     * @param node that undergoes to translate transition
     * @param duration of the transition
     * @param fromX of the horizontal position translate from
     * @param toX of the horizontal position  translate to
     * @param fromY of the vertical position translate from
     * @param toY of the vertical position  translate to
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition getTranslateTransitionFromToXY(Node node, double duration, double fromX, double toX , double fromY, double toY) {
        // Set translate animation duration and the node,
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);
        return translateTransition;
    }

    /**
     * This method modifies a custom TranslateTransition instance to be used in a transition
     *
     * @param translateTransition to add Ease Out interpolator(Ease Out means translate from fast to slow)
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition addEaseOutTranslateInterpolator(TranslateTransition translateTransition){
        translateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return ( t == 0 )? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        return translateTransition;
    }

    /**
     * This method modifies a custom TranslateTransition instance to be used in a transition
     *
     * @param translateTransition to add Ease In interpolator(Ease In means translate from slow to fast)
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition addEaseInTranslateInterpolator(TranslateTransition translateTransition){
        translateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return ( t == 0 )? 0.0 : Math.pow(2.0, 10 * (t - 1));
            }
        });
        return translateTransition;
    }

    /**
     * Don't know the effect of this...
     * This method modifies a custom TranslateTransition instance to be used in a transition
     *
     * @param translateTransition to add Ease InOut interpolator
     * @return the custom TranslateTransition instance
     */
    public static TranslateTransition addEaseInOutTranslateInterpolator(TranslateTransition translateTransition){
        translateTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                if((t == 0.0) || (t == 1.0)){
                    return t;
                }

                val r = t * 2 - 1;
                if(r < 0){
                    return 0.5 * Math.pow(2.0,10 * r);
                }
                return 1 - 0.5 * Math.pow(2.0,-10 * r);
            }
        });
        return translateTransition;
    }

}
