package it.fede.eserciziofcg.controllers;

import it.fede.eserciziofcg.models.dto.UserDto;
import it.fede.eserciziofcg.models.entities.UserEntity;
import it.fede.eserciziofcg.models.mappers.UserEntityDtoMapper;
import it.fede.eserciziofcg.repositories.UserRepository;
import it.fede.eserciziofcg.repositories.UserSpecifications;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserRepository userRepository;
    private final UserEntityDtoMapper userEntityDtoMapper;

    public UserController(UserRepository userRepository, UserEntityDtoMapper userEntityDtoMapper) {
        this.userRepository = userRepository;
        this.userEntityDtoMapper = userEntityDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);

        return ResponseEntity.ok(userEntityDtoMapper.userEntityToDto(user.get()));

    }

    @GetMapping("")
    public ResponseEntity findAll() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> responseList = users.stream().map(userEntity -> userEntityDtoMapper.userEntityToDto(userEntity))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);

    }

    @PostMapping("")
    public ResponseEntity add(@Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = userEntityDtoMapper.userDtoToEntity(userDto);
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntityDtoMapper.userEntityToDto(userEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok(userEntityDtoMapper.userEntityToDto(user.get()));

        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
        Optional<UserEntity> user = userRepository.findById(userDto.getId());
        if( !user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        UserEntity userEntity = userEntityDtoMapper.userDtoToEntity(userDto);
        userEntity.setId(id);
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntityDtoMapper.userEntityToDto(userEntity));
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(defaultValue = "") String firstname,
                                 @RequestParam(defaultValue = "") String lastname) {

        Specification<UserEntity> build = UserSpecifications.build(firstname, lastname);
        List<UserEntity> reponseList = userRepository.findAll(build);

        return ResponseEntity.ok(userEntityDtoMapper.userDtoListToEntities(reponseList));

    }



}
