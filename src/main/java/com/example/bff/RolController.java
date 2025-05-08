package com.example.bff;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/roles")
public class RolController {
    private static final String GET_ROLES_URL = "https://functionrolecn2.azurewebsites.net/api/GetRoles";
    private static final String CREATE_ROLE_URL = "https://functionrolecn2.azurewebsites.net/api/CreateRole";
    private static final String UPDATE_ROLE_URL = "https://functionrolecn2.azurewebsites.net/api/UpdateRole";
    private static final String DELETE_ROLE_URL = "https://functionrolecn2.azurewebsites.net/api/DeleteRole";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();

    public RolController() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping
    public ResponseEntity<String> getAllRoles() {
        try {
            return restTemplate.getForEntity(GET_ROLES_URL, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/byId")
    public ResponseEntity<String> getRolById(@RequestParam String id) {
        try {
            return restTemplate.getForEntity(GET_ROLES_URL + "?id=" + id, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> createRol(@RequestBody String rol) {
        try {
            HttpEntity<String> request = new HttpEntity<>(rol, headers);
            return restTemplate.postForEntity(CREATE_ROLE_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRol(@RequestBody String rol) {
        try {
            HttpEntity<String> request = new HttpEntity<>(rol, headers);
            return restTemplate.postForEntity(UPDATE_ROLE_URL, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        }

    @DeleteMapping("/DeleteRole")
    public ResponseEntity<String> deleteRol(@RequestParam String id) {
        String url = DELETE_ROLE_URL + "?id=" + id;
        try {
            restTemplate.delete(url);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}