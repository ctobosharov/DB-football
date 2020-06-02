package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.PlayerDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final static String JSON_FILE_PATH = "C:\\Users\\Home\\Desktop\\Football-Info_Skeleton\\src\\main\\resources\\files\\json\\players.json";

    private final PlayerRepository playerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PictureRepository pictureRepository;

    @Autowired
    public PlayerServiceImpl(PictureRepository pictureRepository, PlayerRepository playerRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil, PictureRepository pictureRepository1) {
        this.playerRepository = playerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.pictureRepository = pictureRepository1;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();

        PlayerDto[] playerDtos = this.gson.fromJson(readPlayersJsonFile(), PlayerDto[].class);

        for (PlayerDto playerDto : playerDtos) {
            Player player = this.modelMapper.map(playerDto, Player.class);
            Picture picture = this.pictureRepository.findByUrl(playerDto.getPicture().getUrl());

            if (!this.validatorUtil.isValid(player) || player == null){
                sb.append("Invalid Player").append(System.lineSeparator());
                continue;
            }

            player.setPicture(picture);
            this.playerRepository.saveAndFlush(player);

            sb.append(String.format("Successfully imported Player - %s", player.getFirstName() + " " + player.getLastName()))
                    .append(System.lineSeparator());
        }


        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() != 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(JSON_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        List<Player> players = this.playerRepository.findPlayersWithSalaryBigger();

        for (Player player : players) {
            sb.append(String.format("Full Name: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator())
                    .append(String.format("Number: %d", player.getNumber())).append(System.lineSeparator())
                    .append(String.format("Salary: %s", player.getSalary())).append(System.lineSeparator())
                    .append(String.format("Team: %s", player.getTeam())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        return null;
    }
}
