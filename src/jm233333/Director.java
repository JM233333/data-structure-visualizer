package jm233333;

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jm233333.visualized.VDSInstantiation;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
public class Director {

    private static Director instance = new Director();

    private Stage primaryStage;

    private ArrayList<String> menuItemsList;
    private HashMap<String, VDSInstantiation> vdsInstantiationMap;
    private HashMap<String, ArrayList<String>> vdsCodeMap;
    private IntegerProperty loadingProgressProperty;
    private int maxLoadingProgress;

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
     * Creates the unique instance of {@code Director}.
     */
    private Director() {
        primaryStage = null;

        menuItemsList = new ArrayList<>();
        vdsInstantiationMap = new HashMap<>();
        vdsCodeMap = new HashMap<>();
        loadingProgressProperty().setValue(0);
        maxLoadingProgress = 1;

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

    public void addMenuItem(final String item) {
        menuItemsList.add(item);
    }
    public final ArrayList<String> getMenuItems() {
        return menuItemsList;
    }
    public final HashMap<String, VDSInstantiation> getVDSInstantiationMap() {
        return vdsInstantiationMap;
    }
    public final HashMap<String, ArrayList<String>> getVdsCodeMap() {
        return vdsCodeMap;
    }

    public final String getRootPath() {
        String pathJar = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//        return pathJar.substring(0, pathJar.lastIndexOf('/') + 1);
        return "F:/Java/DataStructureVisualizer/";
    }

    /**
     * a {@code BooleanProperty} that represents the animation status.
     * means PLAYING when the value is {@code true}, or PAUSED while {@code false}.
     */
    public final IntegerProperty loadingProgressProperty() {
        if (loadingProgressProperty == null) {
            loadingProgressProperty = new IntegerPropertyBase(0) {
                @Override
                public Object getBean() {
                    return this;
                }
                @Override
                public String getName() {
                    return "loadingProgress";
                }
            };
        }
        return loadingProgressProperty;
    }

    public void setMaxLoadingProgress(int maxLoadingProgress) {
        System.out.println("ADDED MAX " + maxLoadingProgress);
        this.maxLoadingProgress = maxLoadingProgress;
    }
    public int getMaxLoadingProgress() {
        return maxLoadingProgress;
    }
    public boolean isAllLoaded() {
        return (loadingProgressProperty().getValue() == maxLoadingProgress);
    }

    public double getLoadingProgress() {
        if (maxLoadingProgress == 0) {
            return 0.0;
        }
        return ((double)loadingProgressProperty().getValue() / maxLoadingProgress);
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
    public void createDelayInvocation(double delay, final EventHandler<ActionEvent> eventHandler) {
        addEmptyTimeline();
        Timeline timeline = Director.getInstance().getLastTimeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), eventHandler);
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
     * Clear all cached animation info in force.
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
