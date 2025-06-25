package com.usuario.service.controlador;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (Objects.isNull(usuario))
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(usuario);
    }

    @PostMapping()
    public ResponseEntity<Usuario> guardarUsuario(@Validated @RequestBody Usuario usuario) {
        Usuario usuarioSave = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioSave);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (Objects.isNull(usuario))
            return ResponseEntity.notFound().build();
        else {
            List<Carro> carros = usuarioService.getCarros(usuario.getId());
            return ResponseEntity.ok(carros);
        }
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (Objects.isNull(usuario))
            return ResponseEntity.notFound().build();
        else {
            List<Moto> motos = usuarioService.getMotos(usuario.getId());
            return ResponseEntity.ok(motos);
        }
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") Integer id, @Validated @RequestBody Carro carro) {
        return ResponseEntity.ok(usuarioService.saveCarro(id, carro));
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") Integer id, @Validated @RequestBody Moto moto) {
        return ResponseEntity.ok(usuarioService.saveMoto(id, moto));
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") Integer id) {
        return ResponseEntity.ok(usuarioService.getUsuarioAndVehiculo(id));
    }

    private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") Integer id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " tiene los carros en el taller.", HttpStatus.OK);
    }

    private ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") Integer id, @Validated @RequestBody Carro carro, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " no tiene dinero para los carros.", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") Integer id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " tiene las motos en el taller.", HttpStatus.OK);
    }

    private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") Integer id, @Validated @RequestBody Moto moto, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " no tiene dinero para las motos.", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") Integer id, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + id + " tiene los vehiculos en el taller.", HttpStatus.OK);
    }
}
