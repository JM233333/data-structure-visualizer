package jm233333;

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.WritableValue;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
public class Director {

    private static Director instance = new Director();

    private Stage primaryStage;

    public static final double UNIT_ANIMATION_RATE = 100,
            MIN_ANIMATION_RATE = UNIT_ANIMATION_RATE,
            MAX_ANIMATION_RATE = 15 * UNIT_ANIMATION_RATE,
            DEFAULT_ANIMATION_RATE = 500;
    /**
     * animation waiting list.
     */
    private ArrayList<Timeline> animationWaitingList, animationPlayingList;
    private int animationCurrentIndex;
    private BooleanProperty animationPlayingProperty;
    private double animationRate;
    private HashSet<Integer> stepPointSet;
    private boolean isSingleStep;

    /**
     * Creates the unique instance of {@code Director}.
     */
    private Director() {
        primaryStage = null;
        animationWaitingList = new ArrayList<>();
        animationPlayingList = null;
        animationCurrentIndex = -1;
        animationPlayingProperty().setValue(false);
        animationRate = DEFAULT_ANIMATION_RATE;
        stepPointSet = new HashSet<>();
        isSingleStep = false;
    }

    /**
     * Gets the unique instance of {@code Director}.
     *
     * @return the unique instance of {@code Director}
     */
    public static Director getInstance() {
        return instance;
    }

    /**
     * Initializes the unique instance of {@code Director} with a specified {@code Stage}.
     *
     * @param primaryStage the {@code Stage} that is designated as the primary stage
     */
    public void initialize(Stage primaryStage) {
        setPrimaryStage(primaryStage);
    }

    /**
     * Sets the primary stage.
     *
     * @param primaryStage the new primary stage.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Gets the primary stage.
     *
     * @return the primary stage.
     */
    public final Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Creates a new timeline with an animation.
     * calls {@code addEmptyTimeline} and {@code updateAnimation}.
     */
    public <T> void createAnimation(double timeScaleRate, WritableValue<T> property, T value) {
        addEmptyTimeline();
        updateAnimation(timeScaleRate, property, value);
    }
    public <T> void updateAnimation(double timeScaleRate, WritableValue<T> property, T value) {
        Timeline timeline = Director.getInstance().getLastTimeline();
        assert timeline != null;
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(Director.getInstance().getAnimationRate() * timeScaleRate), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
    public void createAnimationText(Text text, String str) {
        // change text
        createAnimation(0.1, text.textProperty(), str);
        updateAnimation(0.1, text.fillProperty(), Color.RED);
        // emphasize
        updateAnimation(0.5, text.scaleXProperty(), 1.5);
        updateAnimation(0.5, text.scaleYProperty(), 1.5);
        // resume
        createAnimation(0.5, text.scaleXProperty(), 1.0);
        updateAnimation(0.5, text.scaleYProperty(), 1.0);
        updateAnimation(0.5, text.fillProperty(), Color.BLACK);
    }

    /**
     * Adds a {@code Timeline} to the animation waiting list.
     *
     * @param timeline the {@code Timeline} that will be added
     */
    private void addTimeline(Timeline timeline) {
        animationWaitingList.add(timeline);
    }

    /**
     * Adds an empty {@code Timeline} to the animation waiting list.
     */
    public void addEmptyTimeline() {
        animationWaitingList.add(new Timeline());
    }

    /**
     * Gets the last {@code Timeline} in the animation waiting list.
     * If the waiting list is empty, create an empty {@code Timeline} and return it.
     *
     * @return the last {@code Timeline}
     */
    public final Timeline getLastTimeline() {
        if (animationWaitingList.isEmpty()) {
            addEmptyTimeline();
        }
        return animationWaitingList.get(animationWaitingList.size() - 1);
    }

    /**
     * If the animation playing list is empty, swaps it with the animation waiting list;
     * Otherwise, sets the animation status to PLAYING.
     */
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
            System.out.println("EMPTY ANIMATION TIMELINE LIST");
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
                    animationPlayingProperty().setValue(true);
                    if (isSingleStep() && stepPointSet.contains(index)) {
                        pauseAnimation();
                    }
                } else {
                    animationCurrentIndex = -1;
                    animationPlayingProperty().setValue(false);
                    stepPointSet.clear();
                }
            });
        }
        // play the first timeline
        animationPlayingList.get(0).play();
        animationCurrentIndex = 0;
        animationPlayingProperty.setValue(true);
    }

    /**
     * If the animation playing list is not empty, sets the animation status to PAUSED.
     */
    public void pauseAnimation() {
        if (isAnimationPlaying() && animationCurrentIndex != -1) {
            animationPlayingList.get(animationCurrentIndex).pause();
        }
    }

    /**
     * a {@code BooleanProperty} that represents the animation status.
     * means PLAYING when the value is {@code true}, or PAUSED while {@code false}.
     */
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

    /**
     * Gets the judgement that if the animation status is PLAYING.
     *
     * @return if the animation status is PLAYING
     */
    public boolean isAnimationPlaying() {
        return animationPlayingProperty().getValue();
    }

    /**
     * Adds a step point after current last timeline.
     */
    public void addStepPoint() {
        stepPointSet.add(animationWaitingList.size() - 1);
    }

    /**
     * Sets the animation playing module.
     */
    public void setSingleStep(boolean flag) {
        this.isSingleStep = flag;
    }
    public boolean isSingleStep() {
        return isSingleStep;
    }

    public void setAnimationRate(double rate) {
        animationRate = rate;
    }
    public double getAnimationRate() {
        return animationRate;
    }
}
