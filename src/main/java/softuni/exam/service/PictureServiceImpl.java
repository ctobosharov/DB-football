package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.PictureDto;
import softuni.exam.domain.entities.PictureRootDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private final static String XML_FILE_PATH = "C:\\Users\\Home\\Desktop\\Football-Info_Skeleton\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PictureRootDto pictureRootDto = this.xmlParser.importXMl(PictureRootDto.class, XML_FILE_PATH);

        for (PictureDto pictureDto : pictureRootDto.getPictures()) {
            Picture picture = this.modelMapper.map(pictureDto, Picture.class);

            if (!this.validatorUtil.isValid(picture) || picture == null){
                sb.append("Invalid Picture").append(System.lineSeparator());
                continue;
            }

            this.pictureRepository.saveAndFlush(picture);
            sb.append(String.format("Successfully imported Picture - %s", picture.getUrl()))
                    .append(System.lineSeparator());

        }

        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() != 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return this.fileUtil.readFile(XML_FILE_PATH);
    }

}
