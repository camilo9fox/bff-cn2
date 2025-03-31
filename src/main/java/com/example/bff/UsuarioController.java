package com.example.bff;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final String baseUrl = "https://crudusercn2.azurewebsites.net/api/usuarios";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<String> getUsuarios() {
        try {
            return restTemplate.getForEntity(baseUrl, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/byId")
    public ResponseEntity<String> getUsuarioById(@RequestParam String id) {
        try {
            return restTemplate.getForEntity(baseUrl + "?id=" + id, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody String usuario) {
        try {
            return restTemplate.postForEntity(baseUrl, usuario, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateUsuario(@RequestBody String usuario) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(usuario, headers);
            return restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

  @DeleteMapping
  public ResponseEntity<String> deleteUsuario(@RequestParam String id) {
      try {
          restTemplate.delete(baseUrl + "?id=" + id);
          return ResponseEntity.ok("Usuario eliminado");
      } catch (HttpClientErrorException e) {
          return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
  }
}