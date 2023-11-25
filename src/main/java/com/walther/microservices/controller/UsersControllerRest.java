package com.walther.microservices.controller;


import com.walther.microservices.common.ApiResponse;
import com.walther.microservices.model.UserDTO;
import com.walther.microservices.model.UserSummaryDTO;
import com.walther.microservices.service.UserSaveService;
import com.walther.microservices.service.UserDeleteService;
import com.walther.microservices.service.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersControllerRest {
    private static final String USER_DELETED_MESSAGE = "User deleted";
    @Autowired
    private UserDeleteService userDeleteService;
    @Autowired
    private UserSaveService userSaveService;
    @Autowired
    private UserViewService userViewService;

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> getUserById(@PathVariable("id") Integer id) {
        Optional<UserSummaryDTO> optUserSummaryDTO = userViewService.getUserById(id);
        try {
            UserSummaryDTO userSummaryDTO = optUserSummaryDTO.orElseThrow(NoSuchElementException::new);
            Link whitSelfRel = linkTo(methodOn(UsersControllerRest.class)
                    .getUserById(userSummaryDTO.getId()))
                    .withSelfRel();
            userSummaryDTO.add(whitSelfRel);
            return ResponseEntity.ok(userSummaryDTO);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserSummaryDTO>> listAllUsersSummary(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "lastname") String lastName,
            @RequestParam(required = false, name = "edad") Integer edad,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Link selfLink = linkTo(methodOn(UsersControllerRest.class)
                .listAllUsersSummary(name, lastName, edad, pageable))
                .withSelfRel();

        Page<UserSummaryDTO> pageUserSummaryDTOs = userViewService.getAllUsers(name, lastName, edad, pageable);
        List<UserSummaryDTO> userSummaryDTOList = pageUserSummaryDTOs.getContent();

        CollectionModel<UserSummaryDTO> result = CollectionModel.of(userSummaryDTOList, selfLink);
        result.getContent().forEach(this::addLinksToUserSummaryDTO);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    private void addLinksToUserSummaryDTO(UserSummaryDTO userSummaryDTO) {
        Link selfLink = linkTo(methodOn(UsersControllerRest.class).getUserById(userSummaryDTO.getId()))
                .withSelfRel();
        userSummaryDTO.add(selfLink);
    }

    @PostMapping
    public ResponseEntity<UserSummaryDTO> createUser(@RequestBody UserDTO userDTO) {

        UserSummaryDTO savedUserSummaryDTO = userSaveService.saveUser(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserSummaryDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUserSummaryDTO);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<UserSummaryDTO>> createListUsers(@RequestBody List<UserDTO> userDTOList) {

        List<UserSummaryDTO> savedAllUsersSummaryDTO = userSaveService.saveAllUsers(userDTOList);
        List<String> locations = savedAllUsersSummaryDTO.stream()
                .map(userSummaryDTO -> ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/users/{id}")
                        .buildAndExpand(userSummaryDTO.getId())
                        .toUri()
                        .toString())
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", locations);
        return new ResponseEntity<>(savedAllUsersSummaryDTO, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserSummaryDTO> updateUser(@RequestBody UserDTO userDTO) {
        UserSummaryDTO savedUserSummaryDTO = userSaveService.saveUser(userDTO);
        return ResponseEntity.ok(savedUserSummaryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        Optional<UserSummaryDTO> optUserSummaryDTO = userViewService.getUserById(id);
        if (optUserSummaryDTO.isPresent()) {
            userDeleteService.deleteUserById(id);
            return ResponseEntity.ok(new ApiResponse("The user "+ optUserSummaryDTO.get().getName() +" was deleted", true));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAllUsers() {
        userDeleteService.deleteAllUsers();
        return ResponseEntity.ok(new ApiResponse("All users was deleted successfully", true));
    }

}
