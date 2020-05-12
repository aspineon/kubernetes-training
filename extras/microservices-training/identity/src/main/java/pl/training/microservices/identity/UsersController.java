package pl.training.microservices.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersMapper usersMapper;
    private final UsersService usersService;
    private final UriBuilder uriBuilder = new UriBuilder();

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserTo userTo) {
        User user = usersMapper.toUser(userTo);
        Long userId = usersService.addUser(user).getId();
        URI locationUri = uriBuilder.requestUriWithAppendedId(userId);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("active")
    public ResponseEntity<UserTo> getActiveUser(Principal principal) {
        User user = (User) usersService.loadUserByUsername(principal.getName());
        UserTo userTo = usersMapper.toUserTo(user);
        return ResponseEntity.ok(userTo);
    }

}
