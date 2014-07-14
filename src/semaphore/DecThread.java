package semaphore;

import java.util.concurrent.Semaphore;

// Поток выполнения, уменьшающий значение счетчика на единицу
class DecThread implements Runnable {

  String name;
  Semaphore sem;

  public DecThread(Semaphore s, String n) {
    sem = s;
    name = n;
    new Thread(this).start();
  }

  @Override
  public void run() {
    System.out.println("Запуск " + name);
    try {
      // Сначала получаем разрешение
      System.out.println(name + " ожидает разрешения.");
      sem.acquire();
      System.out.println(name + " получает разрешение.");

      // Теперь обращаемся к общему ресурсу
      for (int i = 0; i < 5; i++) {
        Shared.count--;
        System.out.println(name + ": " + Shared.count);

        // Если это возможно, разрешаем контекстное переключение
        Thread.sleep(10);
      }
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    // Освобождаем разрешение
    System.out.println(name + " освобождает разрешение.");
    sem.release();
  }
}
