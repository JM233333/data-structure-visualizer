package jm233333.dsv;

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Class {@code Director} is a singleton class that controls all of the animations in the application.
 *
 * <p>
 *     User can define the animation with the prepare-play scheme.
 *     While preparing, user creates animations (e.g. put animation data into a cache) via the provided interfaces;
 *     After playing, the animation in the cache will be played as user sets, then the cache will be cleared for being used the next time.
 *     The animations can be set as concurrently-playing or successively-playing.
 * </p><p>
 *     Here are some more-in-depth details of {@code Director}.
 *     The animation mechanism of the application is implemented with {@link Timeline} and {@link KeyFrame} of JavaFX.
 *     {@code Director} maintains two lists of timelines, {@link Director#animationWaitingList} and {@link Director#animationPlayingList}.
 *     A timeline is bound with a series of keyframes, each of which represents an animation effect.
 *     Timelines are played successively, while keyframes in a timeline are played concurrently.
 * </p>
 */
public class Director {

    /**
     * The singleton of {@code Director}.
     */
    private static Director instance = new Director();

    public static final double UNIT_ANIMATION_RATE = 100,
            MIN_ANIMATION_RATE = UNIT_ANIMATION_RATE,
            MAX_ANIMATION_RATE = 15 * UNIT_ANIMATION_RATE,
            DEFAULT_ANIMATION_RATE = 500;

    private ArrayList<Timeline> animationWaitingList, animationPlayingList;
    private int animationCurrentIndex;
    private BooleanProperty animationPlayingProperty;
    private double animationRate;
    private HashSet<Integer> stepPointSet;
    private boolean isSingleStep;

    /**
     * Creates the singleton of {@code Director}.
     */
    private Director() {
        animationWaitingList = new ArrayList<>();
        animationPlayingList = null;
        animationCurrentIndex = -1;
        animationPlayingProperty().setValue(false);
        animationRate = DEFAULT_ANIMATION_RATE;
        stepPointSet = new HashSet<>();
        isSingleStep = false;
    }

    /**
     * Gets the singleton of {@code Director}.
     *
     * @return the singleton of {@code Director}
     */
    public static Director getInstance() {
        return instance;
    }

    /**
     * Creates a new {@link Timeline} into {@link Director#animationWaitingList} with an animation.
     * The added animation will be played in a new timeline after the last timeline.
     * Factually calls {@link Director#addEmptyTimeline} and {@link Director#updateAnimation(double, WritableValue, Object)}.
     *
     * @param timeScaleRate the rate of the animation duration (will be multiplied by {@link Director#animationRate})
     * @param property the animated property
     * @param value the target value of the animated property
     */
    public <T> void createAnimation(double timeScaleRate, WritableValue<T> property, T value) {
        addEmptyTimeline();
        updateAnimation(timeScaleRate, property, value);
    }
    /**
     * Updates the last {@link Timeline} in {@link Director#animationWaitingList} with an animation.
     * The added animation will be played concurrently with existed animations in the last timeline.
     *
     * @param timeScaleRate the rate of the animation duration (will be multiplied by {@link Director#animationRate})
     * @param property the animated property
     * @param value the target value of the animated property
     */
    public <T> void updateAnimation(double timeScaleRate, WritableValue<T> property, T value) {
        Timeline timeline = getLastTimeline();
        if (timeline == null) {
            addEmptyTimeline();
            timeline = getLastTimeline();
        }
        KeyValue keyValue = new KeyValue(property, value);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(getAnimationRate() * timeScaleRate), keyValue);
        timeline.getKeyFrames().add(keyFrame);
    }
    /**
     * Creates a delay (e.g. a new empty {@link Timeline} with a duration) into {@link Director#animationWaitingList}.
     * Factually calls {@link Director#addEmptyTimeline}.
     *
     * @param timeScaleRate the rate of the delay duration (will be multiplied by {@link Director#animationRate})
     * @param eventHandler the event triggered after the delay
     */
    public void createDelayInvocation(double timeScaleRate, final EventHandler<ActionEvent> eventHandler) {
        addEmptyTimeline();
        Timeline timeline = getLastTimeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(getAnimationRate() * timeScaleRate), eventHandler);
        timeline.getKeyFrames().add(keyFrame);
    }
    /**
     * Creates a series of animation (e.g. two {@link Timeline}s) that changes the content of a {@link Text}.
     * The added animation will be played in two continuous timelines after the last timeline.
     *
     * @param text the animated {@link Text}
     * @param str the new content of {@code text}
     */
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
     * Adds a {@link Timeline} into {@link Director#animationWaitingList}.
     *
     * @param timeline the timeline to be added
     */
    private void addTimeline(Timeline timeline) {
        animationWaitingList.add(timeline);
    }
    /**
     * Adds an empty {@link Timeline} into {@link Director#animationWaitingList}.
     */
    public void addEmptyTimeline() {
        animationWaitingList.add(new Timeline());
    }
    /**
     * Gets the last {@link Timeline} in {@link Director#animationWaitingList}.
     * If the waiting list is empty, creates an empty timeline and returns it.
     *
     * @return the last timeline in the waiting list
     */
    public final Timeline getLastTimeline() {
        if (animationWaitingList.isEmpty()) {
            addEmptyTimeline();
        }
        return animationWaitingList.get(animationWaitingList.size() - 1);
    }

    /**
     * Plays the animation.
     * If {@link Director#animationPlayingList} is empty, swaps it with {@link Director#animationWaitingList};
     * Otherwise, resumes the paused animation (does nothing if not paused).
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
//            System.out.println("EMPTY ANIMATION TIMELINE LIST");
            return;
        }
        // set event listeners
        for (int i = 0; i < animationPlayingList.size(); i ++) {
            final int index = i;
            Timeline timeline = animationPlayingList.get(index);
            final EventHandler<ActionEvent> oldHandler = timeline.getOnFinished();
            timeline.setOnFinished((event) -> {
                if (index + 1 < animationPlayingList.size()) {
                    animationPlayingList.get(index + 1).play();
                    animationCurrentIndex = index + 1;
                    animationPlayingProperty().setValue(true);
                    if (isSingleStep() && index != 0 && stepPointSet.contains(index)) {
                        pauseAnimation();
                    }
                } else {
                    animationCurrentIndex = -1;
                    animationPlayingProperty().setValue(false);
                    stepPointSet.clear();
                }
                if (oldHandler != null) {
                    oldHandler.handle(event);
                }
            });
        }
        // play the first timeline
        animationPlayingList.get(0).play();
        animationCurrentIndex = 0;
        animationPlayingProperty.setValue(true);
    }

    /**
     * Pauses the animation.
     * If {@link Director#animationPlayingList} is not empty, pauses the playing animation (does nothing if already paused);
     * Otherwise, does nothing.
     */
    public void pauseAnimation() {
        if (isAnimationPlaying() && animationCurrentIndex != -1) {
            animationPlayingList.get(animationCurrentIndex).pause();
        }
    }

    /**
     * Clear the waiting list and the playing list in force.
     */
    public void forceClearAllAnimation() {
        animationWaitingList.clear();
        if (isAnimationPlaying() && animationCurrentIndex != -1) {
            animationPlayingList.get(animationCurrentIndex).stop();
        }
        if (animationPlayingList != null) {
            animationPlayingList.clear();
        }
        animationCurrentIndex = -1;
        animationPlayingProperty().setValue(false);
        stepPointSet.clear();
    }

    /**
     * Extracts all cached animations in {@link Director#animationWaitingList}, e.g. finish them immediately.
     */
    public void extractAnimation(ActionEvent event) {
//        Circle test = new Circle();
//        Class<?> clz = test.layoutXProperty().getClass(); System.out.println(clz.getName());
//        Type tp = clz.getGenericSuperclass(); System.out.println(tp);
//        for (Type t : itfcs) System.out.print(t.getClass().getName() + " "); System.out.print('\n');
        for (Timeline timeline : animationWaitingList) {
            for (KeyFrame keyFrame : timeline.getKeyFrames()) for (KeyValue keyValue : keyFrame.getValues()) {
                WritableValue property = keyValue.getTarget();
                Object value = keyValue.getEndValue();
                extractAnimation(property, value);
            }
            if (timeline.getOnFinished() != null) {
                timeline.getOnFinished().handle(event);
            }
        }
        animationWaitingList.clear();
    }
    private <T> void extractAnimation(WritableValue<T> property, T value) {
//        if (property instanceof DoubleProperty) System.out.println(((DoubleProperty) property).getName() + " | " + property.getValue() + " -> " + value);
        property.setValue(value);
    }

    /**
     * A {@link BooleanProperty} that represents the animation status.
     * {@code true} means PLAYING while {@code false} mean STOPPED.
     * initializes {@link Director#animationPlayingProperty} if visits it the first time.
     *
     * @return {@link Director#animationPlayingProperty}
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
     * Asks if the animation status is PLAYING (e.g. {@link Director#animationPlayingProperty} is {@code true}).
     *
     * @return if the animation status is PLAYING (e.g. {@link Director#animationPlayingProperty} is {@code true})
     */
    public boolean isAnimationPlaying() {
        return animationPlayingProperty().getValue();
    }

    /**
     * Adds a step point after the last {@link Timeline} in {@link Director#animationWaitingList}.
     */
    public void addStepPoint() {
        stepPointSet.add(animationWaitingList.size() - 1);
    }

    /**
     * Sets the animation playing mode (e.g. {@link Director#isSingleStep}).
     * {@code true} means single-step mode while {@code false} means continuous mode.
     *
     * @param flag the new animation playing mode
     */
    public void setSingleStep(boolean flag) {
        this.isSingleStep = flag;
    }
    /**
     * Gets the animation playing mode (e.g. {@link Director#isSingleStep}).
     * {@code true} means single-step mode while {@code false} means continuous mode.
     *
     * @return the animation playing mode
     */
    public boolean isSingleStep() {
        return isSingleStep;
    }

    /**
     * Sets {@link Director#animationRate} ranged in [0, 1].
     *
     * @param rate the new animation rate
     */
    public void setAnimationRate(double rate) {
        animationRate = rate;
    }
    /**
     * Gets {@link Director#animationRate} ranged in [0, 1].
     *
     * @return the animation rate
     */
    public double getAnimationRate() {
        return animationRate;
    }

}
