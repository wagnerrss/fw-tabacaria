package com.fw.tabacaria.api;

import com.fw.tabacaria.domain.Marca;
import com.fw.tabacaria.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tabacaria")
public class MarcaController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/marca")
    public ResponseEntity<Iterable<Marca>> getMarca() {
        return ResponseEntity.ok(marcaService.getMarcas());
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity getMarcaById(@PathVariable("id") Integer id) {
        Optional<Marca> product = marcaService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/marca")
    public ResponseEntity postMarca(@RequestBody Marca marca) {
        try {
            Marca p = marcaService.insert(marca);

            URI location = tabacariaController.getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/marca/{id}")
    public ResponseEntity putMarca(@PathVariable("id") Integer id, @RequestBody Marca marca) {
        try {
            marca.setId(id);
            Marca u = marcaService.update(id, marca);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity deleteMarca(@PathVariable("id") Integer id) {
        boolean ok = marcaService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
