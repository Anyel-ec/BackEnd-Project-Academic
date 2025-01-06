package pe.edu.unamba.academic.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.unamba.academic.models.UserInfo;

import java.util.Collection;
import java.util.Collections;

public class PrimaryUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled; // mapearemos state a esta propiedad
    private final Collection<? extends GrantedAuthority> authorities;

    public PrimaryUser(Long id, String username, String password, boolean enabled,
                       Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    // Factory method para construir un PrimaryUser a partir de UserInfo
    public static PrimaryUser build(UserInfo usuario) {
        /*
         * El usuario tiene un solo rol (ManyToOne). Por ello, creamos un único GrantedAuthority
         * en lugar de un stream de roles.
         */
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getName());

        /*
         * Si 'state' es true, el usuario está habilitado; de lo contrario, está deshabilitado.
         */
        boolean enabled = usuario.getState() != null && usuario.getState();

        return new PrimaryUser(
                usuario.getIdUser(),
                usuario.getUsername(),
                usuario.getPassword(),
                enabled,
                Collections.singletonList(authority)
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Asumimos que la cuenta no expira (ajusta según tu lógica)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Asumimos que la cuenta no está bloqueada (ajusta según tu lógica)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Asumimos que las credenciales no expiran (ajusta según tu lógica)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Habilitado según el campo 'state'
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
