package softuni.exam.domain.entities;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PlayerDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer number;

    @Expose
    private BigDecimal salary;

    @Expose
    private String position;

    @Expose
    private Picture picture;

    public PlayerDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
