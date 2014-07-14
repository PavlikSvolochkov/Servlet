package semaphore;

import java.util.concurrent.Semaphore;

public class Q {

  int n;

  // Начинаем с недоступного семафора потребителя
  static Semaphore semCon = new Semaphore(0);
  static Semaphore semProd = new Semaphore(1);

  void get() {
    try {
      semCon.acquire();
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    System.out.println("Получено: " + n);
    semProd.release();
  }

  void put(int n) {
    try {
      semProd.acquire();
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    this.n = n;
    System.out.println("Отпарвлено: " + n);
    semCon.release();
  }
}
