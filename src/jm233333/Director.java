package jm233333;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
public class Director {

    private static Director instance = new Director();
    private Stage primaryStage;
    private ArrayList<Timeline> animationWaitingList, animationPlayingList;
    private BooleanProperty animationPlayingProperty;

    /**
     * Creates the unique instance of Director.
     */
    private Director() {
        animationWaitingList = new ArrayList<>();
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
    public final Timeline getLastTimeline() {
        if (animationWaitingList.isEmpty()) {
            return null;
        }
        return animationWaitingList.get(animationWaitingList.size() - 1);
    }
    public void playAnimation() {
        // check
        if (isAnimationPlaying()) {
            System.out.println("NOOOOOOO");
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
                } else {
                    animationPlayingProperty.setValue(false);
                }
            });
        }
        // play the first timeline
        animationPlayingList.get(0).play();
        animationPlayingProperty.setValue(true);
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
