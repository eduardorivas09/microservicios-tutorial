package com.carro.service.controlador;

import com.carro.service.entidades.Carro;
import com.carro.service.servicio.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros() {
        List<Carro> carros = carroService.getAll();
        if (carros.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") Integer id) {
        Carro carro = carroService.getCarroById(id);
        if (Objects.isNull(carro))
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(carro);
    }

    @PostMapping
    public ResponseEntity<Carro> guardarCarro(@Validated @RequestBody Carro carro) {
        Carro carroSave = carroService.save(carro);
        return ResponseEntity.ok(carroSave);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") Integer id) {
        List<Carro> carros = carroService.findByUsuarioId(id);
        if (carros.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(carros);
    }
}
