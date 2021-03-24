package com.fw.tabacaria.api;

import com.fw.tabacaria.domain.Categoria;
import com.fw.tabacaria.domain.Endereco;
import com.fw.tabacaria.domain.Usuario;
import com.fw.tabacaria.service.CategoriaService;
import com.fw.tabacaria.service.EnderecoService;
import com.fw.tabacaria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tabacaria")
public class CategoriaController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria")
    public ResponseEntity<Iterable<Categoria>> getCategoria() {
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity getCategoriaById(@PathVariable("id") Integer id) {
        Optional<Categoria> product = categoriaService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/categoria")
    public ResponseEntity postCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria p = categoriaService.insert(categoria);

            URI location = tabacariaController.getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity putCategoria(@PathVariable("id") Integer id, @RequestBody Categoria categoria) {
        try {
            categoria.setId(id);
            Categoria u = categoriaService.update(id, categoria);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Integer id) {
        boolean ok = categoriaService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
