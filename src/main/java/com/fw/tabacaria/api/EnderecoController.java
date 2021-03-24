package com.fw.tabacaria.api;

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
public class EnderecoController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/enderecoUsuario/{idUsuario}")
    public ResponseEntity<Iterable<Endereco>> getEnderecoUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return ResponseEntity.ok(enderecoService.getByIdUsuario(idUsuario));
    }

    @GetMapping("/endereco/{id}")
    public ResponseEntity getEnderecoById(@PathVariable("id") Integer id) {
        Optional<Endereco> product = enderecoService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/endereco")
    public ResponseEntity postEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco p = enderecoService.insert(endereco);

            URI location = tabacariaController.getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/endereco/{id}")
    public ResponseEntity putEndereco(@PathVariable("id") Integer id, @RequestBody Endereco endereco) {
        try {
            endereco.setId(id);
            Endereco u = enderecoService.update(id, endereco);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/endereco/{id}")
    public ResponseEntity deleteEndereco(@PathVariable("id") Integer id) {
        boolean ok = enderecoService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
