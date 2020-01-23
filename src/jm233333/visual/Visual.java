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

class Visual extends Group {
    static <T> void createAnimation(double deltaTime, WritableValue<T> property, T value) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(deltaTime), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        Director.getInstance().addTimeline(timeline);
    }
    static <T> void updateAnimation(double deltaTime, WritableValue<T> property, T value) {
        Timeline timeline = Director.getInstance().getLastTimeline();
        assert timeline != null;
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(deltaTime), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
    static void createAnimationText(Text text, String str) {
        // change text
        createAnimation(50, text.textProperty(), str);
        updateAnimation(50, text.fillProperty(), Color.RED);
        // emphasize
        updateAnimation(250, text.scaleXProperty(), 2.0);
        updateAnimation(250, text.scaleYProperty(), 2.0);
        // resume
        createAnimation(250, text.scaleXProperty(), 1.0);
        updateAnimation(250, text.scaleYProperty(), 1.0);
        updateAnimation(250, text.fillProperty(), Color.BLACK);
    }
}
