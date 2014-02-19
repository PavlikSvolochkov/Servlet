
class Q {

  int n;
  boolean valueSet = false;

  synchronized int get() {
    while (!valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("InterruptedException");
      }
    }
    System.out.println("Получено: " + n);
    valueSet = false;
    notify();
    return n;
  }

  synchronized void put(int n) {
    while (valueSet) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println("Отправлено: " + n);
      }
    }
    this.n = n;
    valueSet = true;
    System.out.println("Отправлено: " + n);
    notify();

  }
}

class Producer implements Runnable {

  Q q;

  public Producer(Q q) {
    this.q = q;
    new Thread(this, "Поставщик").start();
  }

  public void run() {
    int i = 0;

    while (true) {
      q.put(i++);
    }
  }
}

class Consumer1 implements Runnable {

  Q q;

  public Consumer1(Q q) {
    this.q = q;
    new Thread(this, "Потребитель").start();
  }

  public void run() {
    while (true) {
      q.get();
    }
  }
}

class Consumer2 implements Runnable {
  Queue q;

  public Consumer2(Queue q) {
    this.q = q;
    new Thread(this, "COnsumer2").start();
  }
  
  public void run() {
    while(true) {
      q.get();
    }
  }
}

class TESTS {

  public static void main(String[] args) {
    Q q = new Q();
    Queue queue = new Queue();
    ClientSaxParser saxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    saxParser.setQueue(queue);
    saxParser.parseDocument();
    saxParser.printData();
    new Consumer2(queue);
//    new Producer(q);
//    new Consumer1(q);
  }
}
