package com.in28minutes.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retieveAllUsers() {
        return service.findALl();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
        // ServletUriComponentsBuilder : 현재 요청 URI를 가져와 새 URI를 생성
        // path : 생성된 URI에 경로 추가 (동적)
        // buildAndExpand : 경로에 들어갈 동적 값
        // toUri : URI 생성
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // 사용자 생성에 성공했음을 나타내는 HTTP 상태 코드 201을 반환
        // 생성된 URI를 헤더에 담아 클라이언트에게 반환
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}")
    public User retieveUsers(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id : " + id);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUsers(@PathVariable int id) {
        service.deleteById(id);
    }
}
