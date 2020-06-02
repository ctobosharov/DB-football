package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(name = "url", nullable = false)
    @NotNull
    private String url;

    @OneToMany(targetEntity = Team.class, mappedBy = "picture")
    private List<Team> teams;

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
