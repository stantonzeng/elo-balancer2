package io.balancer.elo.resource;

import io.balancer.elo.model.GameResult;
import io.balancer.elo.model.Player;
import io.balancer.elo.model.Response;
import io.balancer.elo.model.Team;
import io.balancer.elo.service.PlayerServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//Controller

@CrossOrigin(origins = "https://www.teambalancer.com")
@RestController
@RequestMapping("/api/player")
@Slf4j
public class PlayerResource {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PlayerResource.class);
    private final PlayerServiceImplementation playerServiceImplementation;

    public PlayerResource(PlayerServiceImplementation playerServiceImplementation) {
        this.playerServiceImplementation = playerServiceImplementation;
    }

    @GetMapping("/")
    public String hello() {
        return "test test test";
      }

    //public ResponseEntity<Response> getPlayer(@PathVariable("id") Long id){
    @GetMapping("/get/{id}")
    public Player getPlayer(@PathVariable("id") Long id){
        log.info("Get Api call: get/{id}");

        return playerServiceImplementation.get(id);
        // return ResponseEntity.ok(Response.builder()
        //         .timeStamp(now())
        //         .data(Map.of("player", playerServiceImplementation.get(id)))
        //         .message("Player retrieved")
        //         .status(OK)
        //         .statusCode(OK.value())
        //         .build());
    }

    // @GetMapping("/response_list")
    // public ResponseEntity<Response> getList(){
    //     log.info("Get Api call: response_list");
    //     return ResponseEntity.ok(Response.builder()
    //             .timeStamp(now())
    //             .data(Map.of("players", playerServiceImplementation.list(0, 10)))
    //             .message("Players retrieved")
    //             .status(OK)
    //             .statusCode(OK.value())
    //             .build());
    // }

    @GetMapping("/list/{start}/{end}")
    public List<Player> getListLimit(@PathVariable("start") int start, @PathVariable("end") int end){
        log.info("Get Api call: list/{start}/{end}");
        return playerServiceImplementation.list(start, end);
    }

    @GetMapping("/full_list")
    public List<Player> getFullList(){
        log.info("Get Api call: full_list");
        return playerServiceImplementation.fullList();
    }

    @GetMapping("/full_list/{user}")
    public List<Player> getFullListUser(@PathVariable String user){
        log.info("Get Api call: full_list");
        return playerServiceImplementation.fullListUser(user);
    }


    @GetMapping("/balanceTeams")
    public List<Team> getBalancedTeams(){
        log.info("Get Api call: balanceTeams");
        return playerServiceImplementation.printOutTeams(5);
    }

    @GetMapping("/balanceTeams/{amount}")
    public List<Team> getBalancedTeams(@PathVariable("amount") int amount){
        log.info("Get Api call: balanceTeams/{amount}");
        return playerServiceImplementation.printOutTeams(amount);
    }

    //public ResponseEntity<Response> updateWin(@RequestBody @Valid GameResult gameResult, @PathVariable String index) throws Exception {
    @PostMapping("/win/{index}")
    public String updateWin(@RequestBody @Valid GameResult gameResult, @PathVariable String index) throws Exception {
        log.info("Post Api call: win");
        log.info(index);

        return (("Results of game: " + playerServiceImplementation.gameResults(Integer.parseInt(index), gameResult.getTeamNumb())));
        // return ResponseEntity.ok(Response.builder()
        //         .timeStamp(now())
        //         .data(Map.of("Results of game: ", playerServiceImplementation.gameResults(Integer.parseInt(index), gameResult.getTeamNumb()))) //Remember to change index of 0 to gameResult.getIndex()
        //         .message("Team's elos adjusted")
        //         .status(CREATED)
        //         .statusCode(CREATED.value())
        //         .build());
}

    //public ResponseEntity<Response> addPlayer(@RequestBody @Valid Player player){
    @PostMapping("/add") //Post request, adds new players into the system
    public String addPlayer(@RequestBody @Valid Player player){
        log.info("Post Api call: add Player");

        return String.valueOf(playerServiceImplementation.create(player).getId());
        // return ResponseEntity.ok(Response.builder()
        //         .timeStamp(now())
        //         .data(Map.of("Adding", playerServiceImplementation.create(player)))
        //         .message("Player added")
        //         .status(CREATED)
        //         .statusCode(CREATED.value())
        //         .build());
    }

    //public ResponseEntity<Response> selectingPlayers(@RequestBody @Valid List<Player> gamers){
    @PostMapping("/selectedPlayers")
    public String selectingPlayers(@RequestBody @Valid List<Player> gamers){
        log.info("Post Api call: selectedPlayers");

        return ("Adding " + playerServiceImplementation.setPostedPlayers(gamers));
        // return ResponseEntity.ok(Response.builder()
        //         .timeStamp(now())
        //         .data(Map.of("Adding", playerServiceImplementation.setPostedPlayers(gamers)))
        //         .message("Gamers Added")
        //         .status(CREATED)
        //         .statusCode(CREATED.value())
        //         .build());
    }
}
