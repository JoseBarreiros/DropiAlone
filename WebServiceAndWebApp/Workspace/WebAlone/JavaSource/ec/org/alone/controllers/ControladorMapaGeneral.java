package ec.org.alone.controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.LatLng;

import ec.org.alone.entities.Photo;
import ec.org.alone.services.PhotoService;

@ManagedBean
@ViewScoped
public class ControladorMapaGeneral {
	@EJB
	PhotoService photoService;
	private String saludo;
	private MapModel advancedModel;
	private Marker marker;
	private String zonaSeleccionada;
	private Date fechaInicio;
	private Date fechaFin;
	private String filtroNombre;
	private List<Photo> fotosFiltradas;

	public ControladorMapaGeneral() {
		fechaFin = new Date();
		fechaInicio = new Date();
		fotosFiltradas = new ArrayList<Photo>();
	}

	@PostConstruct
	public void init() {
		saludo = "Hola";
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		buscarTodos();
	}

	public void buscarTodos() {
		advancedModel = new DefaultMapModel();
		List<LatLng> latLongs = new ArrayList<LatLng>();
		fotosFiltradas = photoService.buscarTodos();
		for (Photo photo : fotosFiltradas) {
			Calendar calendar = Calendar.getInstance();
			System.out.println(photo.getPhotoName());
			// String[] parts = photo.getPhotoName().split(".");
			// System.out.println(parts[0]);
			StringTokenizer tk = new StringTokenizer(photo.getPhotoName(), ".");
			calendar.setTimeInMillis(Long.parseLong(tk.nextToken()));
			DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
			LatLng coord = new LatLng(photo.getPhotoLat(), photo.getPhotoLon());
			advancedModel.addOverlay(new Marker(coord,
					"<strong>INFORMACION: </strong>"
							+ "<br><strong>Planta: </strong>"
							+ photo.getPhotoPlantName()
							+ "<br><strong>Zona: </strong>"
							+ photo.getPhotoZone()
							+ "<br><strong>Porcentaje total de da�o: </strong>"
							+ photo.getPhotoTotalLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o alto: </strong>"
							+ photo.getPhotoHighLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o bajo: </strong>"
							+ photo.getPhotoLowLeafDamage() + "%"
							+ "<br><strong>Fecha de captura: </strong>"
							+ df4.format(calendar.getTime()), photo
							.getPhotoName(),
					"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
		}
	}

	public void buscarPorFechas() {
		advancedModel = new DefaultMapModel();
		List<LatLng> latLongs = new ArrayList<LatLng>();
		fotosFiltradas = photoService.buscarTodos();
		for (Photo photo : fotosFiltradas) {
			Calendar calendar = Calendar.getInstance();
			System.out.println(photo.getPhotoName());
			// String[] parts = photo.getPhotoName().split(".");
			// System.out.println(parts[0]);
			StringTokenizer tk = new StringTokenizer(photo.getPhotoName(), ".");
			calendar.setTimeInMillis(Long.parseLong(tk.nextToken()));
			DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
			if (calendar.after(fechaInicio) && calendar.before(fechaFin)) {
				LatLng coord = new LatLng(photo.getPhotoLat(),
						photo.getPhotoLon());
				advancedModel
						.addOverlay(new Marker(
								coord,
								"<strong>INFORMACION: </strong>"
										+ "<br><strong>Planta: </strong>"
										+ photo.getPhotoPlantName()
										+ "<br><strong>Zona: </strong>"
										+ photo.getPhotoZone()
										+ "<br><strong>Porcentaje total de da�o: </strong>"
										+ photo.getPhotoTotalLeafDamage()
										+ "%"
										+ "<br><strong>Porcentaje de da�o alto: </strong>"
										+ photo.getPhotoHighLeafDamage()
										+ "%"
										+ "<br><strong>Porcentaje de da�o bajo: </strong>"
										+ photo.getPhotoLowLeafDamage()
										+ "%"
										+ "<br><strong>Fecha de captura: </strong>"
										+ df4.format(calendar.getTime()), photo
										.getPhotoName(),
								"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
			}
		}
	}

	public void buscarPorZona() {
		advancedModel = new DefaultMapModel();
		List<LatLng> latLongs = new ArrayList<LatLng>();
		fotosFiltradas = photoService.buscarPorZona(zonaSeleccionada);
		for (Photo photo : fotosFiltradas) {
			Calendar calendar = Calendar.getInstance();
			System.out.println(photo.getPhotoName());
			// String[] parts = photo.getPhotoName().split(".");
			// System.out.println(parts[0]);
			StringTokenizer tk = new StringTokenizer(photo.getPhotoName(), ".");
			calendar.setTimeInMillis(Long.parseLong(tk.nextToken()));
			DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
			LatLng coord = new LatLng(photo.getPhotoLat(), photo.getPhotoLon());
			advancedModel.addOverlay(new Marker(coord,
					"<strong>INFORMACION: </strong>"
							+ "<br><strong>Planta: </strong>"
							+ photo.getPhotoPlantName()
							+ "<br><strong>Zona: </strong>"
							+ photo.getPhotoZone()
							+ "<br><strong>Porcentaje total de da�o: </strong>"
							+ photo.getPhotoTotalLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o alto: </strong>"
							+ photo.getPhotoHighLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o bajo: </strong>"
							+ photo.getPhotoLowLeafDamage() + "%"
							+ "<br><strong>Fecha de captura: </strong>"
							+ df4.format(calendar.getTime()), photo
							.getPhotoName(),
					"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
		}
	}

	public void buscarPorNombre() {
		advancedModel = new DefaultMapModel();
		List<LatLng> latLongs = new ArrayList<LatLng>();
		fotosFiltradas = photoService.buscarPorNombre(filtroNombre);
		for (Photo photo : fotosFiltradas) {
			Calendar calendar = Calendar.getInstance();
			System.out.println(photo.getPhotoName());
			// String[] parts = photo.getPhotoName().split(".");
			// System.out.println(parts[0]);
			StringTokenizer tk = new StringTokenizer(photo.getPhotoName(), ".");
			calendar.setTimeInMillis(Long.parseLong(tk.nextToken()));
			DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
			LatLng coord = new LatLng(photo.getPhotoLat(), photo.getPhotoLon());
			advancedModel.addOverlay(new Marker(coord,
					"<strong>INFORMACION: </strong>"
							+ "<br><strong>Planta: </strong>"
							+ photo.getPhotoPlantName()
							+ "<br><strong>Zona: </strong>"
							+ photo.getPhotoZone()
							+ "<br><strong>Porcentaje total de da�o: </strong>"
							+ photo.getPhotoTotalLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o alto: </strong>"
							+ photo.getPhotoHighLeafDamage() + "%"
							+ "<br><strong>Porcentaje de da�o bajo: </strong>"
							+ photo.getPhotoLowLeafDamage() + "%"
							+ "<br><strong>Fecha de captura: </strong>"
							+ df4.format(calendar.getTime()), photo
							.getPhotoName(),
					"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
		}
	}

	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
	}

	public Marker getMarker() {
		return marker;
	}

	public String getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(String zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFiltroNombre() {
		return filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}

	public List<Photo> getFotosFiltradas() {
		return fotosFiltradas;
	}

	public void setFotosFiltradas(List<Photo> fotosFiltradas) {
		this.fotosFiltradas = fotosFiltradas;
	}
}
