package interview.wbox.atm.model;

import javax.persistence.*;

/**
 * Created by ioana on 11/28/2020.
 */
@Entity
@Table(name = "banknote", schema="public")
public class Banknote {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="value", unique = true)
    private Integer value;
    @Column(name="initial_amount")
    private Integer initial_amount;
    @Column(name="left_amount")
    private Integer left_amount;

    public Banknote(){};
    public Banknote(int value, int initial_amount, int left_amount) {
        this.value = value;
        this.initial_amount = initial_amount;
        this.left_amount= left_amount;
    }
    public Long getId() {
        return id;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public Integer getInitial_amount() {
        return initial_amount;
    }
    public void setInitial_amount(Integer initial_amount) {
        this.initial_amount = initial_amount;
    }
    public Integer getLeft_amount() {
        return left_amount;
    }
    public void setLeft_amount(Integer left_amount) {
        this.left_amount = left_amount;
    }

}
