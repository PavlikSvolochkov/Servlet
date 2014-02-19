

public class Main {

  public static void main(String[] args) throws Exception {
    
    Queue queue = new Queue();

    ClientSaxParser saxParser = new ClientSaxParser("d:\\temp\\data\\clients.xml");
    saxParser.setQueue(queue);
    saxParser.parseDocument();
    
    new Consumer(queue);
    
  }
}
