public class Main {
    public static void main(String[] args) {
        final int maxDistance = 50;
        AnimalThread rabbit = new AnimalThread("Кролик", Thread.NORM_PRIORITY + 2, maxDistance);
        AnimalThread turtle = new AnimalThread("Черепаха", Thread.NORM_PRIORITY - 2, maxDistance);
        rabbit.start();
        turtle.start();

        while (rabbit.isAlive() && turtle.isAlive()) {
            if (turtle.getDistance() < rabbit.getDistance()) {
                turtle.setPriority(Math.min(Thread.MAX_PRIORITY, turtle.getPriority() + 1));
                rabbit.setPriority(Math.max(Thread.MIN_PRIORITY, rabbit.getPriority() - 1));
            } else if (rabbit.getDistance() < turtle.getDistance()) {
                rabbit.setPriority(Math.min(Thread.MAX_PRIORITY, rabbit.getPriority() + 1));
                turtle.setPriority(Math.max(Thread.MIN_PRIORITY, turtle.getPriority() - 1));
            }

            System.out.println("Состояние: Кролик = " + rabbit.getDistance() +
                    " м, Черепаха = " + turtle.getDistance() +
                    " м | Приоритеты: Кролик = " + rabbit.getPriority() +
                    ", Черепаха = " + turtle.getPriority());

            try {
                Thread.sleep(500); // пауза между проверками
            } catch (InterruptedException e) {
                System.out.println("Главный поток прерван");
            }
        }
        System.out.println("Гонка завершена!");
    }
}

class AnimalThread extends Thread {
    private final String name;
    private int distance = 0;
    private final int maxDistance;

    public AnimalThread(String name, int priority, int maxDistance) {
        this.name = name;
        this.maxDistance = maxDistance;
        setPriority(priority);
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public void run() {
        while (distance < maxDistance) {
            distance += (int) (Math.random() * 5) + 1;
            System.out.println(name + " пробежал " + distance + " м, приоритет: " + getPriority());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(name + " прерван");
                return;
            }
        }
        System.out.println(name + " финишировал!");
    }
}