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

    public URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    public Map mapErro(String tipo, String mensagem) {
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

}
