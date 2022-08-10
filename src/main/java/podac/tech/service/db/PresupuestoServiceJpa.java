package podac.tech.service.db;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import podac.tech.model.Empresa;
import podac.tech.model.Presupuesto;
import podac.tech.repository.EmpresaRepository;
import podac.tech.repository.PresupuestoRepository;
import podac.tech.service.IPresupuestoService;

@Service
public class PresupuestoServiceJpa implements IPresupuestoService {

	@Value("${podac.tech.ruta.imagenes}")
	private String RUTA;

	@Autowired
	private PresupuestoRepository repo;

	@Autowired
	private EmpresaRepository empresaRepo;

	@Override
	public Presupuesto guardar(Presupuesto objeto) {
		return this.repo.save(objeto);
	}

	@Override
	public void eliminar(Long id) {
		this.repo.deleteById(id);
	}

	@Override
	public List<Presupuesto> buscarTodas() {
		return this.repo.findAll();
	}

	@Override
	public Presupuesto buscarPorId(Long id) {
		return this.repo.findById(id).isPresent() ? this.repo.findById(id).get() : null;
	}

	@Override
	public Page<Presupuesto> buscarTodas(Pageable page) {
		return this.repo.findAll(page);
	}

	@Override
	public Presupuesto ultimoRegistro() {
		Optional<Presupuesto> ultimo = this.repo.findTopByOrderByIdDesc();
		return ultimo.isPresent() ? ultimo.get() : null;
	}

	@Override
	public boolean estaVacio() {
		return this.repo.findTopByOrderByIdDesc().isEmpty();
	}

	@Override
	public boolean existe(Long id) {
		return id == null ? false : this.repo.existsById(id);
	}

	@Override
	public Page<Presupuesto> buscarUltimosPresupuestosPorCliente(Long id, Pageable page) {
		return this.repo.findTop15ByClienteIdOrderByIdDesc(id, page);
	}

	@Override
	public Page<Presupuesto> buscarPresupuestosPorCliente(Long id, Pageable page) {
		return this.repo.findByClienteIdOrderById(id, page);
	}

	@Override
	public List<Presupuesto> buscarPresupuestosPorCliente(Long id) {
		return this.repo.findByClienteIdOrderById(id);
	}

	@Override
	public void aprobar(Long id) {
		this.repo.approve(id);
	}

	@Override
	public void rechazar(Long id) {
		this.repo.reject(id);
	}

	@Override
	public Page<Presupuesto> buscarUltimosPresupuestosPorUsuario(String username, Pageable page) {
		return this.repo.findTop15ByRegistradoPorOrderByIdDesc(username, page);
	}

	@Override
	public Page<Presupuesto> buscarPresupuestosPorUsuario(String username, Pageable page) {
		return this.repo.findByRegistradoPorOrderById(username, page);
	}

	@Override
	public List<Presupuesto> buscarPresupuestosPorUsuario(String username) {
		return this.repo.findByRegistradoPorOrderById(username);
	}

	@Override
	public void actualizarDescuento(Long idPresupuesto, Double descuento) {
		this.repo.updateDescuento(idPresupuesto, descuento);
	}

	@Override
	public List<Presupuesto> buscarConEjemplo(Example<Presupuesto> example) {
		return this.repo.findAll(example);
	}

	@Override
	public Page<Presupuesto> buscarConEjemplo(Example<Presupuesto> example, Pageable page) {
		return this.repo.findAll(example, page);
	}

	@Override
	public List<Presupuesto> buscarPorClienteEntreRangoFechas(Long idCliente, LocalDate fechaInicial,
			LocalDate fechaFinal) {
		return this.repo.findByClienteIdAndFechaRegistroBetween(idCliente, fechaInicial, fechaFinal);
	}

	@Override
	public Page<Presupuesto> buscarPorClienteEntreRangoFechas(Long idCliente, LocalDate fechaInicial,
			LocalDate fechaFinal, Pageable page) {
		return this.repo.findByClienteIdAndFechaRegistroBetween(idCliente, fechaInicial, fechaFinal, page);
	}

	@Override
	public List<Presupuesto> buscarEntreRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
		return this.repo.findByFechaRegistroBetween(fechaInicial, fechaFinal);
	}

	@Override
	public Page<Presupuesto> buscarEntreRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal, Pageable page) {
		return this.repo.findByFechaRegistroBetween(fechaInicial, fechaFinal, page);
	}

	@Override
	public ResponseEntity<Resource> exportarPresupuesto(long idCliente, long idPresupuesto) {
		Optional<Presupuesto> opt = this.repo.findByClienteIdAndId(idCliente, idPresupuesto);
		if (opt.isPresent()) {
			try {
				final Presupuesto presupuesto = opt.get();
				final Empresa empresa = empresaRepo.findTopByOrderByIdDesc().get();
				final File file = ResourceUtils.getFile("classpath:jasper/Presupuesto01.jasper");
				final File logotipoInicial = ResourceUtils.getFile("classpath:image/LogotipoInicial.png");
				final File logotipoEmpresa = ResourceUtils.getFile("file:" + RUTA + empresa.getLogotipo());
				final JasperReport report = (JasperReport) JRLoader.loadObject(file);

				final HashMap<String, Object> parameters = new HashMap<>();
				parameters.put("presupuesto", presupuesto);
				parameters.put("infoEmpresa", empresa);

				if (logotipoEmpresa.exists()) {
					parameters.put("logo", new FileInputStream(logotipoEmpresa));
				} else {
					parameters.put("logo", new FileInputStream(logotipoInicial));
				}

				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
				byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
				String sdf = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				StringBuilder stringBuilder = new StringBuilder().append("PresupuestoPDF:");
				ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
						.filename(stringBuilder.append(presupuesto.getId()).append("ownedBy:")
								.append(presupuesto.getCliente().getNombre().replace("\s", "").toLowerCase())
								.append("generateDate:").append(sdf).append(".pdf").toString())
						.build();

				HttpHeaders headers = new HttpHeaders();
				headers.setContentDisposition(contentDisposition);
				return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
						.headers(headers).body(new ByteArrayResource(reporte));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ResponseEntity.noContent().build(); // No se encuentra presupuesto
		}
		return null;
	}
}
