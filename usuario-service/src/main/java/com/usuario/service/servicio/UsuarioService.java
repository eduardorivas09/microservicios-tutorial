package com.usuario.service.servicio;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignClients.CarroFeignClient;
import com.usuario.service.feignClients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Carro> getCarros(Integer usuarioId) {
        List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMotos(Integer usuarioId) {
        List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
        return motos;
    }

    public Carro saveCarro(Integer usuarioId, Carro carro) {
        carro.setUsuarioId(usuarioId);
        return carroFeignClient.guardarMoto(carro);
    }

    public Moto saveMoto(Integer usuarioId, Moto moto) {
        moto.setUsuarioId(usuarioId);
        return motoFeignClient.guardarMoto(moto);
    }

    public Map<String, Object> getUsuarioAndVehiculo(Integer usuarioId) {
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            resultado.put("Mensaje", "Usuario no encontrado.");
            return resultado;
        }

        resultado.put("Usuario", usuario);

        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        resultado.put("Carros", Objects.requireNonNullElse(carros, "El usuario no tiene carros."));

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        resultado.put("Motos", Objects.requireNonNullElse(motos, "El usuario no tiene motos."));
        return resultado;
    }
}
