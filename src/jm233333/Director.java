package jm233333;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import jm233333.ui.CodeTracker;

import java.util.ArrayList;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
public class Director {

    private static Director instance = new Director();
    private Stage primaryStage;
    private CodeTracker boundCodeTracker;
    private ArrayList<Timeline> animationWaitingList, animationPlayingList;
    private int animationCurrentIndex;
    private BooleanProperty animationPlayingProperty;

    /**
     * Creates the unique instance of Director.
     */
    private Director() {
        animationWaitingList = new ArrayList<>();
        animationPlayingList = null;
        animationCurrentIndex = -1;
        animationPlayingProperty().setValue(false);
    }

    /**
     * Gets the unique instance of Director.
     * @return reference to the unique instance of Director
     */
    public static Director getInstance() {
        return instance;
    }

    /**
     * Initialize the unique instance of Director.
     */
    public void initialize(Stage primaryStage) {
        setPrimaryStage(primaryStage);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public final Stage getPrimaryStage() {
        return primaryStage;
    }

    public void addTimeline(Timeline timeline) {
        animationWaitingList.add(timeline);
    }
    public void clearAllTimeline() {
        animationWaitingList.clear();
    }
    public final Timeline getLastTimeline() {
        if (animationWaitingList.isEmpty()) {
            return null;
        }
        return animationWaitingList.get(animationWaitingList.size() - 1);
    }
    public void playAnimation() {
        // check
        if (isAnimationPlaying()) {
            animationPlayingList.get(animationCurrentIndex).play();
            return;
        }
        // switch buffer
        animationPlayingList = animationWaitingList;
        animationWaitingList = new ArrayList<>();
        // check empty
        if (animationPlayingList.isEmpty()) {
            return;
        }
        // set event listeners
        for (int i = 0; i < animationPlayingList.size(); i ++) {
            final int index = i;
            Timeline timeline = animationPlayingList.get(index);
            final EventHandler<ActionEvent> oldHandler = timeline.getOnFinished();
            timeline.setOnFinished((event) -> {
                if (oldHandler != null) {
                    oldHandler.handle(event);
                }
                if (index + 1 < animationPlayingList.size()) {
                    animationPlayingList.get(index + 1).play();
                    animationCurrentIndex = index + 1;
                    animationPlayingProperty.setValue(true);
                } else {
                    animationCurrentIndex = -1;
                    animationPlayingProperty().setValue(false);
                }
            });
        }
        // play the first timeline
        animationPlayingList.get(0).play();
        animationCurrentIndex = 0;
        animationPlayingProperty.setValue(true);
    }
    public void pauseAnimation() {
        if (isAnimationPlaying() && animationCurrentIndex != -1) {
            animationPlayingList.get(animationCurrentIndex).pause();
        }
    }
    public final BooleanProperty animationPlayingProperty() {
        if (animationPlayingProperty == null) {
            animationPlayingProperty = new BooleanPropertyBase(false) {
                @Override
                public Object getBean() {
                    return this;
                }
                @Override
                public String getName() {
                    return "animationPlaying";
                }
            };
        }
        return animationPlayingProperty;
    }
    public boolean isAnimationPlaying() {
        return animationPlayingProperty().getValue();
    }
}
