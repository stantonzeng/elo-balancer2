package io.balancer.elo.resource;

import io.balancer.elo.model.Response;
import io.balancer.elo.model.User;
import io.balancer.elo.service.UserServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
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
        return userServiceImplementation.get(userName);
    }

    @GetMapping("/check/{userName}")
    public Boolean checkUser(@PathVariable("userName") String userName){
        log.info("Get Api call: check/{}", userName);
        User u = userServiceImplementation.get(userName);
        return u != null;
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

    @PostMapping("/addPlayer/{p}")
    public ResponseEntity<Response> addUser(@RequestBody @Valid String user, @PathVariable String p) {
        log.info("Post Api call: add player {} to user list", p);
        User u = userServiceImplementation.get(user);
        u.addToList(p);
        userServiceImplementation.update(u);

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("Adding", p))
                .message("Player added")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }
}
