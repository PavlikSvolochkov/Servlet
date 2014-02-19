public class Consumer implements Runnable {
  
  Queue queue;

  public Consumer(Queue q) {
    this.queue = q;
    new Thread(this, "Consumer").start();
  }

  @Override
  public void run() {
    while(true) {
      queue.get();
    }
  }  
}
