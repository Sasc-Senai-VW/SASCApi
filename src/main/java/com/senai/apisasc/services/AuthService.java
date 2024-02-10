package com.senai.apisasc.services;

import com.senai.apisasc.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var funcionario = funcionarioRepository.findByEmail(username);
        if (funcionario == null) {
            throw new UsernameNotFoundException("Funcionario não encontrado");
        }

        return funcionario;
    }
}
