package com.moto.service.controlador;

import com.moto.service.entidades.Moto;
import com.moto.service.servicio.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMotos() {
        List<Moto> motos = motoService.getAll();
        if (motos.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") Integer id) {
        Moto moto = motoService.getMotoById(id);
        if (Objects.isNull(moto))
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarMoto(@Validated @RequestBody Moto moto) {
        Moto motoSave = motoService.save(moto);
        return ResponseEntity.ok(motoSave);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") Integer id) {
        List<Moto> motos = motoService.findByUsuarioId(id);
        if (motos.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(motos);
    }
}