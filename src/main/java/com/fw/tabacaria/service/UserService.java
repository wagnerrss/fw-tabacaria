package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.User;
import com.fw.tabacaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByUserName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User insert(User user) {
        if ((user.getId() == null) || (user.getId() <= 0)) {
            Optional<User> optional = getByUserName(user.getName());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return userRepository.save(user);
    }

    public User update(Integer id, User user) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<User> optional = getById(id);

        if (!optional.get().getEmail().equals(user.getEmail())){
            Optional<User> optionalEmail = getByEmail(user.getEmail());
            if (optionalEmail.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Email já existe");
            }
        }

        if (optional.isPresent()) {
            User u = optional.get();
            u.setName(user.getName());
            u.setPassword(user.getPassword());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setProfile(user.getProfile());
            u.setDocument(user.getDocument());
            u.setPicture(user.getPicture());

            userRepository.save(u);

            return u;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public User findUser(Map login){
        User u = userRepository.findByEmailPassword(login.get("email").toString(), login.get("password").toString());

        return u;
    }

}
