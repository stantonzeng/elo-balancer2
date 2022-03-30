package io.balancer.elo.resource;


import io.balancer.elo.model.Player;
import io.balancer.elo.model.Response;
import io.balancer.elo.model.User;
import io.balancer.elo.service.UserServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserResource {
    private final UserServiceImplementation userServiceImplementation;

    public UserResource(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @GetMapping("/get/{userName}")
    public User getUser(@PathVariable("userName") String userName){
        log.info("Get Api call: get/{}", userName);
        User u = userServiceImplementation.get(userName);
//        if(u == null) return new User((long) -1, "null", new ArrayList<>());
        return u;

//        return ResponseEntity.ok(Response.builder()
//                .timeStamp(now())
//                .data(Map.of("User", userServiceImplementation.get(userName)))
//                .message("User retrieved")
//                .status(OK)
//                .statusCode(OK.value())
//                .build());
    }

    @GetMapping("/check/{userName}")
    public Boolean checkUser(@PathVariable("userName") String userName){
        log.info("Get Api call: check/{}", userName);
        User u = userServiceImplementation.get(userName);
        return u != null;
    }

    @GetMapping("/full_list/{user}")
    public List<Player> getFullListUser(@PathVariable String user){
        log.info("Get Api call: full_list");
        return userServiceImplementation.fullListUser(user);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addUser(@RequestBody @Valid User user) {
        log.info("Post Api call: add User");

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Adding", userServiceImplementation.create(user)))
                .message("User added")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @PostMapping("/addString")
    public ResponseEntity<Response> addUserString(@RequestBody @Valid String user) {
        log.info("Post Api call: adding string {}", user);

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Adding", userServiceImplementation.create(new User(null, user, ""))))
                .message("User added")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    //TODO: add the player correctly
    @PostMapping("/addPlayer/{p}")
    public ResponseEntity<Response> addUser(@RequestBody @Valid String user, @PathVariable String p) {
        log.info("Post Api call: add player {} to user list", p);

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Adding", userServiceImplementation.get(user).addToList(p)))
                .message("Player added")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }
}
