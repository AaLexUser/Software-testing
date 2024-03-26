package domain;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Human ford = new Human("Ford", 2);
        Human arthur = new Human("Arthur", 1);
        Action action1 = new Action("fall asleep", 0.09);
        Action action2 = new Action("write a paper on vogons", 0.1);
        arthur.setTask(Action.copyOf(action1));
        arthur.setTask(Action.copyOf(action2));
        ford.setTask(action1);
        ford.setTask(action2);
        Future<Integer> fordsLife = ford.startWorkLife();
        Future<Integer>  arthursLife = arthur.startWorkLife();
        TimeUnit.SECONDS.sleep(5);
        ford.giveUp(action1);
        Action action3 = new Action( "put on a robe ", 0.5);
        ford.setTask(action3);
        ford.giveUp(action1);
        TimeUnit.SECONDS.sleep(5);
    }

}
