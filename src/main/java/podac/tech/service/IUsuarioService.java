package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Usuario;

public interface IUsuarioService {
	void guardar(Usuario usuario);

	void eliminar(Long idUsuario);

	List<Usuario> buscarTodas();

	Page<Usuario> buscarTodas(Pageable page);

	List<Usuario> buscarRegistrados();

	Usuario buscarPorId(Long idUsuario);

	Usuario buscarPorUsername(String username);

	Long bloquear(Long idUsuario);

	Long activar(Long idUsuario);

	Long contarRegistros();

	Usuario ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);
}
