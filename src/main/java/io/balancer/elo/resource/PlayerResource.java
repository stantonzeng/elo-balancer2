package io.balancer.elo.resource;

import io.balancer.elo.model.Player;
import io.balancer.elo.model.Response;
import io.balancer.elo.model.Team;
import io.balancer.elo.model.Teams;
import io.balancer.elo.service.PlayerServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//Controller

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/player")
public class PlayerResource {
    private final PlayerServiceImplementation playerServiceImplementation;

    public PlayerResource(PlayerServiceImplementation playerServiceImplementation) {
        this.playerServiceImplementation = playerServiceImplementation;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getPlayer(@PathVariable("id") Long id){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("player", playerServiceImplementation.get(id)))
                .message("Player retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/response_list")
    public ResponseEntity<Response> getList(){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("players", playerServiceImplementation.list(0, 10)))
                .message("Players retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/list/{start}/{end}")
    public List<Player> getListLimit(@PathVariable("start") int start, @PathVariable("end") int end){
        return playerServiceImplementation.list(start, end);
    }

    @GetMapping("/full_list")
    public List<Player> getFullList(){
        return playerServiceImplementation.fullList();
    }

    @GetMapping("/balanceTeams")
    public List<Team> getBalancedTeams(){
        return playerServiceImplementation.printOutTeams(-1);
    }

    @GetMapping("/balanceTeams/{amount}")
    public List<Team> getBalancedTeams(@PathVariable("amount") int amount){
        return playerServiceImplementation.printOutTeams(amount);
    }

    @PostMapping("/win/{index}/{team}")
    public ResponseEntity<Response> updateWin(@PathVariable("index") int index, @PathVariable("team") int team){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Results of game: ", playerServiceImplementation.gameResults(index, team)))
                .message("Team's elos adjusted")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
}

    @PostMapping("/add") //Post request, adds new players into the system
    public ResponseEntity<Response> addPlayer(@RequestBody @Valid Player player){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Adding", playerServiceImplementation.create(player)))
                .message("Player added")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }
}
