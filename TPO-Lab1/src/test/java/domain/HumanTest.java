package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
;

public class HumanTest {
    private Human ford;
    private Action action;

    @BeforeEach
    void setUp() {
        ford = new Human("Ford", 2);
        action = new Action("action", 0.5);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3, 5, 100, 1000})
    void allTaskCompleted(int numOfTasks){
        Human.setIntervalBetweenTries(1);
        assertAll(
                () -> assertDoesNotThrow(() -> {
                    for (int i = 0; i < numOfTasks; i++) ford.setTask(Action.copyOf(action));
                    int completedTasks = ford.workLifeCallable();

                    assertEquals(numOfTasks, completedTasks);
                }),
                () -> assertEquals(0, ford.getBacklog().size())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000})
    void allTaskFinishedButSomeTaskGaveUp(int numOfTasks){
        action = new Action("action", 0.1);
        Human.setIntervalBetweenTries(10);
        assertAll(
                () -> assertDoesNotThrow(() -> {
                    ArrayList<Action> actions = new ArrayList<>();
                    for (int i = 0; i < numOfTasks; i++) actions.add(new Action("action " + i, 0.001));
                    for (Action action : actions) ford.setTask(action);
                    Future<Integer> completedTasks = ford.startWorkLife();
                    Thread.sleep(100);
                    for(int i = 0; i < numOfTasks; i++) ford.giveUp(actions.get(i));
                    assertNotEquals(numOfTasks, completedTasks.get());
                }),
                () -> assertEquals(0, ford.getActionsInWork().size())
        );
    }


    @Test
    void setTaskWhenInterruptedShouldThrowInterruptedException(){
        assertThrows(InterruptedException.class, () -> {
            Thread.currentThread().interrupt();
            ford.setTask(action);
        });
    }

    @Test
    void setTaskNotInterruptedShouldNotThrowInterruptedException(){
        assertDoesNotThrow(() -> {
            ford.setTask(action);
        });
        assertEquals(1, ford.getBacklog().size());
    }

    @Test
    void interruptedWhileDoingAction() throws InterruptedException {
        action = new Action("action", 0);
        ford.setTask(action);
        Future<Integer> future = ford.startWorkLife();
        Thread.sleep(1000);
        future.cancel(true);
        assertTrue(future.isCancelled());
    }

    @Test
    void setIllegalIntervalBetweenTriesShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> Human.setIntervalBetweenTries(-1), "Interval must be greater than 0");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2.0, -0.1, 1.1, 2.0})
    void setIllegalIntervalProbabilityShouldThrowIllegalArgumentException(double probability){
        assertThrows(IllegalArgumentException.class, () -> new Action("action", probability), "Probability must be in range [0, 1]");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.1, 0.5, 1.0})
    void setLegalIntervalProbabilityShouldNotThrowIllegalArgumentException(double probability){
        assertDoesNotThrow(() -> new Action("action", probability));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "  "})
    void putIllegalActionNameThrowIllegalArgumentException(String name){
        assertThrows(IllegalArgumentException.class, () -> new Action(name, 0.1), "Name must be not null and not empty");
    }

    @Test
    void interruptWhileDoingActionShouldThrowInterruptedException() {
        action = new Action("action", 0);
        try {
            ford.setTask(action);
            Thread thread = getThread(ford);
            thread.start();
            TimeUnit.SECONDS.sleep(1);
            thread.interrupt();
        } catch (IllegalAccessException e) {
            fail("Can't access field");
        }
        catch (InterruptedException e){
            fail("Can't acquire semaphore");
        }
        catch (NoSuchFieldError | NoSuchMethodError | NoSuchMethodException e){
            fail(e.getMessage());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Thread getThread(Human ford) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        Method method = Human.class.getDeclaredMethod("doAction", Action.class);
        method.setAccessible(true);
        Field mulLevelField = Human.class.getDeclaredField("multitaskingLevel");
        mulLevelField.setAccessible(true);
        Field backLogField = Human.class.getDeclaredField("backlog");
        backLogField.setAccessible(true);
        Semaphore multitaskingLevel = (Semaphore) mulLevelField.get(ford);
        multitaskingLevel.acquire();
        var backlog = (BlockingQueue<Action>) backLogField.get(ford);
        Action action = backlog.take();
        Thread thread = new Thread(() -> {
            try {
                method.invoke(ford, action);
            } catch (Exception e) {
                assertTrue(e.getCause() instanceof InterruptedException);
            }
        });
        return thread;
    }

    @Test
    void testAppDoesntThrowExceptions() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
