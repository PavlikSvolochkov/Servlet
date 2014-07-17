package exchanger;

import java.util.concurrent.Exchanger;

// Thread, использующий строку
public class UseString implements Runnable {

  String str;
  Exchanger<String> ex;

  public UseString(Exchanger<String> c) {
    ex = c;
    new Thread(this).start();
  }

  @Override
  public void run() {
    for (int i = 0; i < 3; i++) {
      try {
        // Пустой буфер становится заполненным.
        str = ex.exchange(new String());
        System.out.println("Получено: " + str);
      } catch (InterruptedException ex) {
        System.out.println(ex);
      }
    }
  }
}
