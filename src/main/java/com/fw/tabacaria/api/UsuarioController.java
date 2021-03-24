package com.fw.tabacaria.api;

import com.fw.tabacaria.domain.Endereco;
import com.fw.tabacaria.domain.Usuario;
import com.fw.tabacaria.service.EnderecoService;
import com.fw.tabacaria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tabacaria")
public class UsuarioController {

    @Autowired
    private TabacariaController tabacariaController;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public ResponseEntity<Iterable<Usuario>> getUser() {
        return ResponseEntity.ok(usuarioService.getUsers());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Integer id) {
        Optional<Usuario> product = usuarioService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/usuario")
    public ResponseEntity postUser(@RequestBody Usuario usuario) {
        try {
            Usuario p = usuarioService.insert(usuario);

            URI location = tabacariaController.getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity putUser(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Usuario u = usuarioService.update(id, usuario);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(tabacariaController.mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        boolean ok = usuarioService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(tabacariaController.mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

    //*****************************************
//    Login
//*****************************************
    @GetMapping("/login")
    public ResponseEntity getLogin(@RequestBody Map login) {
        try {
            Usuario u = usuarioService.findUser(login);

            if ((u == null) || (u.getId() <= 0)) {

                return new ResponseEntity<>(tabacariaController.mapErro("GET", ""), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(u);
        } catch (Exception ex) {
            return new ResponseEntity<>(tabacariaController.mapErro("GET", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
