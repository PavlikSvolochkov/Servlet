package semaphore;

import java.util.concurrent.Semaphore;

public class SemDemo {

  public static void main(String[] args) {
    Semaphore sem = new Semaphore(1);
    new IncThread(sem, "A");
    new DecThread(sem, "B");
    
    Q q = new Q();
    new Consumer(q);
    new Producer(q);
  }
}
