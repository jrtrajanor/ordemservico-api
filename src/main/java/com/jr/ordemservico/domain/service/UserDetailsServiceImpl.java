package com.jr.ordemservico.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.ordemservico.domain.entity.Permissao;
import com.jr.ordemservico.domain.entity.Usuario;
import com.jr.ordemservico.domain.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UsuarioRepository userRepository;
    
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Usuario user = userRepository.findByUsuario( username.toUpperCase() );
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Permissao role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getDescricao()));
        }
           
        return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getSenha(), grantedAuthorities); //user.getNome()
    }
    
    
	public Boolean DuplicateUser(Usuario usuario){
		
		List<Usuario> user = userRepository.findByNomeOrderByNomeAsc(usuario.getNome());
		
		if (usuario.getId() == null && !user.isEmpty()) {
			return true;
		}else{
			for(Usuario users : user){
			    if (users.getId().longValue() != usuario.getId().longValue())
			    	return true;
			}
		}
		return false;
	}
}