package org.maven.apache.utils;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.val;

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

    /**
     * This method modifies a custom ScaleTransition instance to be used in a transition
     *
     * @param scaleTransition to add Ease Out interpolator(Ease Out means translate from fast to slow)
     * @return the custom ScaleTransition instance
     */
    public static ScaleTransition addEaseOutTranslateInterpolator(ScaleTransition scaleTransition){
        scaleTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return ( t == 0 )? 1.0 : 1 - Math.pow(2.0, -10 * t);
            }
        });
        return scaleTransition;
    }

    /**
     * This method modifies a custom ScaleTransition instance to be used in a transition
     *
     * @param scaleTransition to add Ease In interpolator(Ease In means translate from slow to fast)
     * @return the custom ScaleTransition instance
     */
    public static ScaleTransition addEaseInTranslateInterpolator(ScaleTransition scaleTransition){
        scaleTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return ( t == 0 )? 0.0 : Math.pow(2.0, 10 * (t - 1));
            }
        });
        return scaleTransition;
    }

    /**
     * Don't know the effect of this...
     * This method modifies a custom ScaleTransition instance to be used in a transition
     *
     * @param scaleTransition to add Ease InOut interpolator
     * @return the custom ScaleTransition instance
     */
    public static ScaleTransition addEaseInOutTranslateInterpolator(ScaleTransition scaleTransition){
        scaleTransition.setInterpolator(new Interpolator() {
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
        return scaleTransition;
    }
}
