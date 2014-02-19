public class DBHelper extends Thread {

  Queue queue;

  @Override
  public void run() {
    if (queue.isEmpty()) {
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else
      queue.getElement().toString();
  }
}
