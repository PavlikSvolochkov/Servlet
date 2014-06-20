
import java.util.ArrayList;
import java.util.List;

public class Client {

  private int clientId;
  private String name;
  private String surname;
  private String dateOfBirth;
  private List<String> cards;
  private List<String> accounts;

  public Client() {
    cards = new ArrayList<>();
    accounts = new ArrayList<>();
  }

  public int getId() {
    return clientId;
  }

  public void setId(int client_id) {
    this.clientId = client_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String birth) {
    this.dateOfBirth = birth;
  }
  
  public void setCard(String card) {
    this.cards.add(card);
  }

  public List<String> getCards() {
    return cards;
  }

  public void setCards(List<String> card) {
    this.cards = card;
  }

  public void setAcc(String acc) {
    this.accounts.add(acc);
  }
  
  public List<String> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<String> accounts) {
    this.accounts = accounts;
  }

  @Override
  public String toString() {
    return "\nId: " + getId() + "\nName: " + getName() + "\nSurname: " + getSurname() + "\nDate of birth: " + getDateOfBirth()
            + "\nCards: " + getCards().toString() + "\nAccounts: " + getAccounts().toString();
  }
}
