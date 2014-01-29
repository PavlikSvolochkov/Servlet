import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client {

  private String name;
  private String surname;
  private Date dateOfBirth;
  private List<String> cards;
  private List<String> accounts;

  public Client() {
    cards = new ArrayList<String>();
    accounts = new ArrayList<String>();
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

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public List<String> getCards() {
    return cards;
  }

  public void setCards(List<String> card) {
    this.cards = card;
  }

  public List<String> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<String> accounts) {
    this.accounts = accounts;
  }

  public String toString() {
    return "\nClient:" + "\nName: " + getName() + "\nSurname: " + getSurname() + "\nDate of birth: " + getDateOfBirth()
        + "\nCards: " + getCards() + "\nAccounts: " + getAccounts();
  }
}
