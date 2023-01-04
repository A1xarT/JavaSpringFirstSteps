package mockito.basics.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "money")
    private int money;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonBackReference
    private Person owner;

    public Wallet(int money, Person owner) {
        this.money = money;
        this.owner = owner;
    }

    public Wallet() {
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", money=" + money +
                ", owner=" + owner +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
