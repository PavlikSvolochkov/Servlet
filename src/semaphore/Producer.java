package semaphore;

public class Producer implements Runnable {

  Q q;

  public Producer(Q q) {
    this.q = q;
    new Thread(this, "Producer").start();
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      q.put(i);
    }
  }
}
