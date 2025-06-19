package com.usuario.service.feignClients;

import com.usuario.service.modelos.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "carro-service", url = "http://localhost:8002/carro")
public interface CarroFeignClient {

    @PostMapping()
    public Carro guardarMoto(@RequestBody Carro carro);

    @GetMapping("/usuario/{usuarioId}")
    public List<Carro> getCarros(@PathVariable("usuarioId") Integer usuarioId);
}
