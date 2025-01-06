package pe.edu.unamba.academic.security.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.UserInfo;
import pe.edu.unamba.academic.repositories.UserRepository;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos al usuario con state = true
        UserInfo usuario = usuarioRepository.findByUsernameAndStateTrue(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username)
                );

        // Construimos el objeto PrimaryUser que implementa UserDetails
        return PrimaryUser.build(usuario);
    }
}
