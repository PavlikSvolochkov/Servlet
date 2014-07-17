package exchanger;

import java.util.concurrent.Exchanger;

// Поток Thread, формирующий строку
public class MakeString implements Runnable {

  String str;
  Exchanger<String> ex;

  public MakeString(Exchanger<String> c) {
    this.ex = c;
    this.str = new String();
    new Thread(this).start();
  }  
  
//  ResultSet rs = ps.executeQuery(sql);
//  boolean hasNext = rs.next();
//  while (hasNext) {
//   // init local data ie:
//   // long id = rs.getLong("id");
//   hasNext = rs.next();
//   if (hasNext) {
//     // processing for rows that aren't last
//   } else {
//     // processing for last row
//   }
//  }    

  @Override
  public void run() {

    char ch = 'A';
    for (int i = 0; i < 3; i++) {
      // Заполнение буфера
      for (int j = 0; j < 5; j++) {
        str += (char) ch++;
      }
      try {
        // Заполненый буфер становится пустым
        str = ex.exchange(str);
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }
  }
}
