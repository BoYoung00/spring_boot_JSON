package com.in28minutes.rest.webservices.restfulwebservices.jpa.users;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource2 {
    private UserRepository repository;

    public UserResource2(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/jpa/users")
    public List<User> retieveAllUsers() {
        return repository.findAll();
    }

    // 사용자 생성하는 메서드
    // ResponseEntity : HTTP 응답 표현
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = repository.save(user);
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

    @GetMapping("/jpa/users/{id}")
    public EntityModel<Optional<User>> retieveUsers(@PathVariable int id) {
        // 해당 ID를 가진 사용자를 데이터베이스에서 검색
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        // HATEOAS를 이용해 리소스와 관련된 링크 생성
        EntityModel<Optional<User>> entityModel = EntityModel.of(user);
        // 현재 메서드(retieveAllUsers)에 대한 링크 생성
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retieveAllUsers());
        // 생성된 링크를 'all-users'라는 관계(rel)로 추가하여 리턴
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUsers(@PathVariable int id) {
        repository.deleteById(id);
    }
}
