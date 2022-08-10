package podac.tech.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podac.tech.model.Perfil;

public interface IPerfilService {
	void guardar(Perfil objeto);

	void eliminar(Long id);

	List<Perfil> buscarTodas();

	Perfil buscarPorId(Long id);

	Page<Perfil> buscarTodas(Pageable page);

	boolean existePerfil(String perfil);

	long contarRegistros();

	Perfil buscarPorNombre(String nombre);

	Perfil ultimoRegistro();

	boolean estaVacio();

	boolean existe(Long id);
}
