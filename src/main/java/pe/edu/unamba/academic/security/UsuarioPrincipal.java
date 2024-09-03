package pe.edu.unamba.academic.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import pe.edu.unamba.academic.models.User;


public class UsuarioPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Long id, String username, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(User usuario) {
        // List<GrantedAuthority> authorities = usuario.getRoles().stream()
        //     .map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getIdUser(), usuario.getUsername(), usuario.getPassword(),
                null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}