package softuni.exam.domain.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureRootDto {

    @XmlElement(name = "picture")
    private List<PictureDto> pictures;

    public PictureRootDto() {
    }

    public List<PictureDto> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureDto> pictures) {
        this.pictures = pictures;
    }
}
