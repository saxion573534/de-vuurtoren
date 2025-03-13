package org.saxion.devuurtoren.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;

public class TimelineUtil {

    public static <T> Timeline fadeEffect(WritableValue<T> property, T fromValue, T toValue, double durationMillis) {
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(durationMillis),
                new javafx.animation.KeyValue(property, toValue)
        );

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();
        return timeline;
    }

    public static <T> Timeline fadeInOutEffect(WritableValue<T> property, T initialValue, T finalValue, double fadeInDuration, double fadeOutDuration) {
        KeyFrame fadeIn = new KeyFrame(Duration.millis(fadeInDuration), new javafx.animation.KeyValue(property, finalValue));


        KeyFrame fadeOut = new KeyFrame(Duration.millis(fadeInDuration + fadeOutDuration), new javafx.animation.KeyValue(property, initialValue));

        Timeline timeline = new Timeline(fadeIn, fadeOut);
        timeline.setCycleCount(1);
        timeline.play();
        return timeline;
    }
}
