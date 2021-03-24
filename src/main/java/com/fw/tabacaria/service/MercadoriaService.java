package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Categoria;
import com.fw.tabacaria.domain.Marca;
import com.fw.tabacaria.domain.Mercadoria;
import com.fw.tabacaria.domain.MercadoriaCompleta;
import com.fw.tabacaria.repository.MercadoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoriaService {
    @Autowired
    private MercadoriaRepository mercadoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MarcaService marcaService;

    public Iterable<Mercadoria> getMercadorias() {
        return mercadoriaRepository.findAll();
    }

    public Optional<Mercadoria> getById(Integer id) {
        return mercadoriaRepository.findById(id);
    }

    public Iterable<Mercadoria> getByCategoria(Categoria categoria){
        return mercadoriaRepository.findByCategoria(categoria);
    }

    public Iterable<Mercadoria> getByMarca(Marca marca){
        return mercadoriaRepository.findByMarca(marca);
    }

    public Optional<Mercadoria> getByNome(String nome) {
        return mercadoriaRepository.findByNome(nome);
    }

    public Optional<Mercadoria> getByDescricao(String descricao) {
        return mercadoriaRepository.findByDescricao(descricao);
    }

    public Optional<Mercadoria> getByEan(String ean) {
        return mercadoriaRepository.findByEan(ean);
    }

    public Iterable<Mercadoria> getByNomeDescricao(String nome, String descricao){
        return mercadoriaRepository.findByNomeDescricao(nome, descricao);
    }

    public Mercadoria insert(Mercadoria mercadoria) {
        if ((mercadoria.getId() == null) || (mercadoria.getId() <= 0)) {
            Optional<Mercadoria> op1 = getByNome(mercadoria.getNome());
            if (op1.isPresent()){
                throw new RuntimeException("Mercadoria já existe");
            }

            op1 = getByEan(mercadoria.getEan());
            if (op1.isPresent()){
                throw new RuntimeException("Mercadoria já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return mercadoriaRepository.save(mercadoria);
    }

    public Mercadoria update(Integer id, Mercadoria mercadoria) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Mercadoria> optional = getById(id);

        if (!optional.get().getNome().equals(mercadoria.getNome())) {
            Optional<Mercadoria> optional1 = getByNome(mercadoria.getNome());
            if (optional1.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Mercadoria já existe");
            }

            optional1 = getByEan(mercadoria.getEan());
            if (optional1.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Mercadoria já existe");
            }
        }

        if (optional.isPresent()) {
            Mercadoria m = optional.get();
            m.setCategoria(categoriaService.getById(mercadoria.getCategoria().getId()).get());
            m.setMarca(marcaService.getById(mercadoria.getMarca().getId()).get());
            m.setFoto1(mercadoria.getFoto1());
            m.setFoto2(mercadoria.getFoto2());
            m.setFoto3(mercadoria.getFoto3());
            m.setNome(mercadoria.getNome());
            m.setDescricao(mercadoria.getDescricao());
            m.setPrecoSugerido(mercadoria.getPrecoSugerido());
            m.setEan(mercadoria.getEan());
            m.setTipo(mercadoria.getTipo());

            mercadoriaRepository.save(m);

            return m;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            mercadoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
