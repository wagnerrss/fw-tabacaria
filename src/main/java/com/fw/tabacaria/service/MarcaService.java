package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Marca;
import com.fw.tabacaria.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    public Iterable<Marca> getMarcas() {
        return marcaRepository.findAll();
    }

    public Optional<Marca> getById(Integer id) {
        return marcaRepository.findById(id);
    }

    public Optional<Marca> getByNome(String nome) {
        return marcaRepository.findByNome(nome);
    }

    public Marca insert(Marca marca) {
        if ((marca.getId() == null) || (marca.getId() <= 0)) {
            Optional<Marca> op1 = getByNome(marca.getNome());
            if (op1.isPresent()){
                throw new RuntimeException("Marca já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return marcaRepository.save(marca);
    }

    public Marca update(Integer id, Marca marca) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Marca> optional = getById(id);

        if (!optional.get().getNome().equals(marca.getNome())) {
            Optional<Marca> optionalDescricao = getByNome(marca.getNome());
            if (optionalDescricao.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Marca já existe");
            }
        }

        if (optional.isPresent()) {
            Marca c= optional.get();
            c.setNome(marca.getNome());

            marcaRepository.save(c);

            return c;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            marcaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
