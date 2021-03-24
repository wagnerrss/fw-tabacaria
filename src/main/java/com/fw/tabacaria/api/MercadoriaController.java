package com.fw.tabacaria.api;

import com.fw.tabacaria.domain.Mercadoria;
import com.fw.tabacaria.domain.MercadoriaCompleta;
import com.fw.tabacaria.service.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tabacaria")
public class MercadoriaController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private MercadoriaService mercadoriaService;

    @GetMapping("/mercadoria")
    public ResponseEntity<Iterable<MercadoriaCompleta>> getMercadoria() {
        return ResponseEntity.ok(mercadoriaService.getMercadorias());
    }

    @GetMapping("/mercadoria/{id}")
    public ResponseEntity getMercadoriaById(@PathVariable("id") Integer id) {
        Optional<Mercadoria> mercadoria = mercadoriaService.getById(id);

        return mercadoria.isPresent() ?
                ResponseEntity.ok(mercadoria.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/mercadoria")
    public ResponseEntity postMercadoria(@RequestBody Mercadoria mercadoria) {
        try {
            Mercadoria m = mercadoriaService.insert(mercadoria);

            URI location = tabacariaController.getUri(m.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/mercadoria/{id}")
    public ResponseEntity putMercadoria(@PathVariable("id") Integer id, @RequestBody Mercadoria mercadoria) {
        try {
            mercadoria.setId(id);
            Mercadoria m = mercadoriaService.update(id, mercadoria);

            return m != null ?
                    ResponseEntity.ok(m) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/mercadoria/{id}")
    public ResponseEntity deleteMercadoria(@PathVariable("id") Integer id) {
        boolean ok = mercadoriaService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
