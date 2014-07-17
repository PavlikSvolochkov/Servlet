package exchanger;

import java.util.concurrent.Exchanger;

public class ExgrDemo {

  public static void main(String[] args) {
    Exchanger<String> exgr = new Exchanger<String>();
    new UseString(exgr);
    new MakeString(exgr);
  }
}
