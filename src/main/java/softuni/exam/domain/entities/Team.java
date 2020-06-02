package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture picture;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
