package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.TeamDto;
import softuni.exam.domain.entities.TeamRootDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    private final static String XML_FILE_PATH = "C:\\Users\\Home\\Desktop\\Football-Info_Skeleton\\src\\main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, Gson gson, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importTeams() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        TeamRootDto teamRootDto = this.xmlParser.importXMl(TeamRootDto.class, XML_FILE_PATH);

        for (TeamDto teamDto : teamRootDto.getTeams()) {
            Team team = this.modelMapper.map(teamDto, Team.class);
            Picture picture = this.pictureRepository.findByUrl(teamDto.getUrl());

            if (!this.validatorUtil.isValid(team) || team == null){
                sb.append("Invalid Team").append(System.lineSeparator());
                continue;
            }

            team.setPicture(picture);
            this.teamRepository.saveAndFlush(team);

            sb.append(String.format("Successfully imported Team - %s", team.getName()))
                    .append(System.lineSeparator());
        }


        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() != 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(XML_FILE_PATH);
    }
}
