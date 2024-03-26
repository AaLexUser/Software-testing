package domain;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Human {
    private final String name;
    public final Semaphore multitaskingLevel;
    private final BlockingQueue<Action> backlog = new LinkedBlockingQueue<>();
    private final ArrayList<Action> actionsInWork = new ArrayList<>();
    private ExecutorService executorService;
    private static int INTERVAL_BETWEEN_TRIES = 1000;

    private static final Logger logger = Logger.getLogger(Human.class.getName());
    public static void setIntervalBetweenTries(int interval){
        if(interval < 0){
            throw new IllegalArgumentException("Interval must be greater than 0");
        }
        INTERVAL_BETWEEN_TRIES = interval;
    }

    public Human(String name, int multitaskingLevel) {
        this.name = name;
        this.multitaskingLevel = new Semaphore(multitaskingLevel);
        executorService = Executors.newFixedThreadPool(multitaskingLevel);
        System.out.println(name + ": I am alive!");
    }

    public BlockingQueue<Action> getBacklog() {
        return backlog;
    }

    synchronized
    public ArrayList<Action> getActionsInWork() {
        return actionsInWork;
    }

    public Future<Integer> startWorkLife(){
        FutureTask<Integer> future = new FutureTask<>(this::workLifeCallable);
        new Thread(future).start();
        return future;
    }
    public int workLifeCallable() {
        boolean allDone = false;
        AtomicInteger taskCompleted = new AtomicInteger(0);
        try {
            while (!backlog.isEmpty()) {
                if (multitaskingLevel.tryAcquire()) {
                    Action action = backlog.take();
                    CompletableFuture.supplyAsync(() -> doAction(action), executorService)
                            .thenApply(isCompleted -> {
                                if (isCompleted) {
                                    taskCompleted.incrementAndGet();
                                }
                                return isCompleted;
                            });
                }
            }
            executorService.shutdown();
            allDone = executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        } finally { if (!allDone) stopWorkLife(Thread.currentThread()); }
        return taskCompleted.get();
    }

    private void cancelTask(Action action){
        synchronized (action.getIsActive()) {
            if (action.getIsActive().get()) {
                action.setActive(false);
            }
        }
        synchronized (backlog) {
            boolean ignored = backlog.remove(action);
        }
    }

    public void giveUp(Action action){
        cancelTask(action);
        System.out.println(name + ": I am giving up " + action.getName());
    }

    public void stopWorkLife(Thread thread){
        synchronized (actionsInWork) {
            for (Action action : actionsInWork) {
                giveUp(action);
            }
        }
        thread.interrupt();
        System.out.println(name + ": That's all for now!");
    }

    public void setTask(Action action) throws InterruptedException {
        backlog.put(action);
    }

    public boolean doAction(Action action){
        boolean isCompleted = false;
        try {
            synchronized (actionsInWork) {
                actionsInWork.add(action);
            }

            System.out.println(name + ": I am starting " + action.getName());
            while (true) {
                TimeUnit.MILLISECONDS.sleep(INTERVAL_BETWEEN_TRIES);
                synchronized (action.getIsActive()) {
                    if(!action.getIsActive().get()){
                        break;
                    }
                    if (action.takeATry()) {
                        action.setActive(false);
                        System.out.println(name + ": I am done " + action.getName());
                        isCompleted = true;
                        break;
                    } else {
                        System.out.println(name + ": I am trying " + action.getName());
                    }
                }
            }

            synchronized (actionsInWork) {
                actionsInWork.remove(action);
            }
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, name + ": I was interrupted while doing " + action.getName());
            Thread.currentThread().interrupt();
        } finally { multitaskingLevel.release();}
        return isCompleted;
    }

}
