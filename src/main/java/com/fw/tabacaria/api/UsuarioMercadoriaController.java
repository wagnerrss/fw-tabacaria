package com.fw.tabacaria.api;

import com.fw.tabacaria.domain.UsuarioMercadoria;
import com.fw.tabacaria.service.UsuarioMercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tabacaria")
public class UsuarioMercadoriaController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private UsuarioMercadoriaService usuarioMercadoriaService;

    @GetMapping("/usuariomercadoria")
    public ResponseEntity<Iterable<UsuarioMercadoria>> getUsuarioMercadoria() {
        return ResponseEntity.ok(usuarioMercadoriaService.getUsuarioMercadoria());
    }

    @GetMapping("/usuariomercadoria/{id}")
    public ResponseEntity getUsuarioMercadoriaById(@PathVariable("id") Integer id) {
        Optional<UsuarioMercadoria> mercadoria = usuarioMercadoriaService.getById(id);

        return mercadoria.isPresent() ?
                ResponseEntity.ok(mercadoria.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/usuariomercadoria")
    public ResponseEntity postUsuarioMercadoria(@RequestBody UsuarioMercadoria mercadoria) {
        try {
            UsuarioMercadoria m = usuarioMercadoriaService.insert(mercadoria);

            URI location = tabacariaController.getUri(m.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/usuariomercadoria/{id}")
    public ResponseEntity putUsuarioMercadoria(@PathVariable("id") Integer id, @RequestBody UsuarioMercadoria mercadoria) {
        try {
            mercadoria.setId(id);
            UsuarioMercadoria m = usuarioMercadoriaService.update(id, mercadoria);

            return m != null ?
                    ResponseEntity.ok(m) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/usuariomercadoria/{id}")
    public ResponseEntity deleteUsuarioMercadoria(@PathVariable("id") Integer id) {
        boolean ok = usuarioMercadoriaService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
