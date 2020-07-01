package lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static  boolean WIN;
    static {
        CARS_COUNT = 0;
        WIN = false;
    }

    CountDownLatch cdl_preparation;
    CyclicBarrier cb_ready;
    CountDownLatch cdl_start;
    CountDownLatch cdl_finish;

    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch cdl_preparation, CyclicBarrier cb_ready,
               CountDownLatch cdl_start, CountDownLatch cdl_finish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cdl_preparation = cdl_preparation;
        this.cb_ready = cb_ready;
        this.cdl_start = cdl_start;
        this.cdl_finish = cdl_finish;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cdl_preparation.countDown();
            cb_ready.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cdl_start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!WIN) {
            System.out.println(this.name + " - WIN");
            WIN = true;
        }

        cdl_finish.countDown();

    }
}