package jm233333.visual;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jm233333.Director;

public class Visual extends Group {
    public static <T> void createAnimation(double scaleRate, WritableValue<T> property, T value) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(Director.getInstance().getAnimationRate() * scaleRate), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        Director.getInstance().addTimeline(timeline);
    }
    public static <T> void updateAnimation(double scaleRate, WritableValue<T> property, T value) {
        Timeline timeline = Director.getInstance().getLastTimeline();
        assert timeline != null;
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(Director.getInstance().getAnimationRate() * scaleRate), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
    public static void createAnimationText(Text text, String str) {
        // change text
        createAnimation(0.1, text.textProperty(), str);
        updateAnimation(0.1, text.fillProperty(), Color.RED);
        // emphasize
        updateAnimation(0.5, text.scaleXProperty(), 2.0);
        updateAnimation(0.5, text.scaleYProperty(), 2.0);
        // resume
        createAnimation(0.5, text.scaleXProperty(), 1.0);
        updateAnimation(0.5, text.scaleYProperty(), 1.0);
        updateAnimation(0.5, text.fillProperty(), Color.BLACK);
    }
}
