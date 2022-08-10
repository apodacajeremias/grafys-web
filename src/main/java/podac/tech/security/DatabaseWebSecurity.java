package podac.tech.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import podac.tech.audit.AuditorAwareImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, estado from Usuario where username=?")
				.authoritiesByUsernameQuery("select u.username, p.nombre from UsuarioPerfil up "
						+ "inner join Usuario u on u.id = up.idUsuario " + "inner join Perfil p on p.id = up.idPerfil "
						+ "where u.username = ?");
	}

	/**
	 * Personalizamos el Acceso a las URLs de la aplicación
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

				// Los recursos estáticos no requieren autenticación
				.antMatchers("/adminlte/**", "/autonumeric/**", "/bootstrap/**", "/js/**").permitAll()

				// Las vistas públicas no requieren autenticación
				.antMatchers("/fragments/**", "/login", "/signup", "/search", "/bcrypt/**", "/about",
						"/presupuesto/exportar/**")
				.permitAll()

				// Asignar permisos a URLs por ROLES
				.antMatchers("/configuracion/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR", "ROOT")
				.antMatchers("/cotizacion/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR", "ROOT")
				.antMatchers("/empresa/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR", "ROOT")
				.antMatchers("/moneda/**").hasAnyAuthority("ADMINISTRADOR", "ROOT").antMatchers("/pago/**")
				.hasAnyAuthority("USUARIO", "SUPERVISOR", "ADMINISTRADOR", "ROOT").antMatchers("/perfil/**")
				.hasAnyAuthority("ADMINISTRADOR", "ROOT").antMatchers("/persona/**")
				.hasAnyAuthority("USUARIO", "SUPERVISOR", "ADMINISTRADOR", "ROOT").antMatchers("/presupuesto/**")
				.hasAnyAuthority("USUARIO", "SUPERVISOR", "ADMINISTRADOR", "ROOT").antMatchers("/producto/**")
				.hasAnyAuthority("USUARIO", "SUPERVISOR", "ADMINISTRADOR", "ROOT").antMatchers("/usuario/**")
				.hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR", "ROOT")
				// Todas las demás URLs de la Aplicación requieren autenticación
				.anyRequest().authenticated()
				// El formulario de Login no requiere autenticacion
				.and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
	}

	/**
	 * Implementación de Spring Security que encripta passwords con el algoritmo
	 * Bcrypt
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Implementación de Auditor que permite visualizar creador y modificador de
	 * registro, asignando nombres y fechas
	 * 
	 * @return
	 */
	@Bean
	public AuditorAwareImpl auditorAware() {
		return new AuditorAwareImpl();
	}

}