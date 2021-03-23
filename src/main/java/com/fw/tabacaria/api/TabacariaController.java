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
public class TabacariaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public String get() {
        return "Get api FW para Tabacaria";
    }

    @PostMapping
    public String post() {
        return "Post api FW para Tabacaria";
    }

    @PutMapping
    public String put() {
        return "Put api FW para Tabacaria";
    }

    @DeleteMapping
    public String delete() {
        return "Delete api FW para Tabacaria";
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    private Map mapErro(String tipo, String mensagem) {
        switch (tipo) {
            case "GET":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 404);
                        put("mensagem", mensagem.trim().equals("") ? "Registro não encontrado!" : mensagem);
                    }
                };
            case "POST":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível inserir o registro!" : mensagem);
                    }
                };
            case "PUT":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível atualizar o registro!" : mensagem);
                    }
                };
            case "DELETE":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", mensagem.trim().equals("") ? "Não foi possível excluir o registro!" : mensagem);
                    }
                };
        }

        return new LinkedHashMap();
    }

//*****************************************
//    Usuário
//*****************************************

    @GetMapping("/usuario")
    public ResponseEntity<Iterable<Usuario>> getUser() {
        return ResponseEntity.ok(usuarioService.getUsers());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Integer id) {
        Optional<Usuario> product = usuarioService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/usuario")
    public ResponseEntity postUser(@RequestBody Usuario usuario) {
        try {
            Usuario p = usuarioService.insert(usuario);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity putUser(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Usuario u = usuarioService.update(id, usuario);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        boolean ok = usuarioService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//    Login
//*****************************************
    @GetMapping("/login")
    public ResponseEntity getLogin(@RequestBody Map login) {
        try {
            Usuario u = usuarioService.findUser(login);

            if ((u == null) || (u.getId() <= 0)) {

                return new ResponseEntity<>(mapErro("GET", ""), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(u);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("GET", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

//*****************************************
//    Categoria
//*****************************************

    @GetMapping("/categoria")
    public ResponseEntity<Iterable<Categoria>> getCategoria() {
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity getCategoriaById(@PathVariable("id") Integer id) {
        Optional<Categoria> product = categoriaService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/categoria")
    public ResponseEntity postCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria p = categoriaService.insert(categoria);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity putCategoria(@PathVariable("id") Integer id, @RequestBody Categoria categoria) {
        try {
            categoria.setId(id);
            Categoria u = categoriaService.update(id, categoria);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Integer id) {
        boolean ok = categoriaService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

//*****************************************
//    Endereço
//*****************************************

    @GetMapping("/enderecoUsuario/{idUsuario}")
    public ResponseEntity<Iterable<Endereco>> getEnderecoUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return ResponseEntity.ok(enderecoService.getByIdUsuario(idUsuario));
    }

    @GetMapping("/endereco/{id}")
    public ResponseEntity getEnderecoById(@PathVariable("id") Integer id) {
        Optional<Endereco> product = enderecoService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(mapErro("GET", ""), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/endereco")
    public ResponseEntity postEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco p = enderecoService.insert(endereco);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/endereco/{id}")
    public ResponseEntity putEndereco(@PathVariable("id") Integer id, @RequestBody Endereco endereco) {
        try {
            endereco.setId(id);
            Endereco u = enderecoService.update(id, endereco);

            return u != null ?
                    ResponseEntity.ok(u) :
                    new ResponseEntity<>(mapErro("PUT", ""), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("PUT", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/endereco/{id}")
    public ResponseEntity deleteEndereco(@PathVariable("id") Integer id) {
        boolean ok = enderecoService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE", ""), HttpStatus.BAD_REQUEST);
    }

}
