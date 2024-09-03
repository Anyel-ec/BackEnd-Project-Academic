package pe.edu.unamba.academic.security;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndStateTrue(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException(new StringBuilder("User ").append(username).append(" not found").toString());
        }
        return UsuarioPrincipal.build(user.get());
    }

}

