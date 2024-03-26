package domain;

import java.util.concurrent.atomic.AtomicBoolean;

public class Action {
    private final String name;
    private final double probability;
    private final AtomicBoolean isActive = new AtomicBoolean(true);

    public String getName() {
        return name;
    }
    public double getProbability() {
        return probability;
    }

    public static Action copyOf(Action action){
        return new Action(action.getName(), action.getProbability());
    }

    public AtomicBoolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean value) {
        isActive.compareAndSet(!value, value);
    }

    public Action(String name, double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name must be not null and not empty");
        }
        this.name = name;
        this.probability = probability;
    }

    public boolean takeATry() {
        return Math.random() < probability;
    }

}
