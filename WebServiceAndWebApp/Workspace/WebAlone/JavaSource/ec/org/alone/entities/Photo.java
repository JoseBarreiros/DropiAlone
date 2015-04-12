package ec.org.alone.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the photo database table.
 * 
 */
@Entity
@NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idphoto;

	@Column(name = "photo_damage_percent")
	private double photoDamagePercent;

	@Column(name = "photo_lat")
	private double photoLat;

	@Column(name = "photo_lon")
	private double photoLon;

	@Column(name = "photo_name")
	private String photoName;

	@Column(name = "photo_ndvi")
	private double photoNdvi;

	@Column(name = "photo_ozone_percent")
	private int photoOzonePercent;

	@Column(name = "photo_plant_name")
	private String photoPlantName;

	@Column(name = "photo_drop")
	private String photoDrop;

	@Column(name = "photo_zone")
	private String photoZone;

	@Column(name = "photo_high_leaf_damage")
	private int photoHighLeafDamage;

	@Column(name = "photo_low_leaf_damage")
	private int photoLowLeafDamage;

	@Column(name = "photo_total_leaf_damage")
	private int photoTotalLeafDamage;

	@Temporal(TemporalType.DATE)
	@Column(name = "photo_upload_date")
	private Date photoUploadDate;

	public Photo() {
	}

	public int getIdphoto() {
		return this.idphoto;
	}

	public void setIdphoto(int idphoto) {
		this.idphoto = idphoto;
	}

	public double getPhotoDamagePercent() {
		return this.photoDamagePercent;
	}

	public void setPhotoDamagePercent(double photoDamagePercent) {
		this.photoDamagePercent = photoDamagePercent;
	}

	public double getPhotoLat() {
		return this.photoLat;
	}

	public void setPhotoLat(double photoLat) {
		this.photoLat = photoLat;
	}

	public double getPhotoLon() {
		return this.photoLon;
	}

	public void setPhotoLon(double photoLon) {
		this.photoLon = photoLon;
	}

	public String getPhotoName() {
		return this.photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public double getPhotoNdvi() {
		return this.photoNdvi;
	}

	public void setPhotoNdvi(double photoNdvi) {
		this.photoNdvi = photoNdvi;
	}

	public String getPhotoPlantName() {
		return this.photoPlantName;
	}

	public void setPhotoPlantName(String photoPlantName) {
		this.photoPlantName = photoPlantName;
	}

	public String getPhotoZone() {
		return this.photoZone;
	}

	public void setPhotoZone(String photoZone) {
		this.photoZone = photoZone;
	}

	public Date getPhotoUploadDate() {
		return photoUploadDate;
	}

	public void setPhotoUploadDate(Date photoUploadDate) {
		this.photoUploadDate = photoUploadDate;
	}

	public int getPhotoHighLeafDamage() {
		return photoHighLeafDamage;
	}

	public void setPhotoHighLeafDamage(int photoHighLeafDamage) {
		this.photoHighLeafDamage = photoHighLeafDamage;
	}

	public int getPhotoLowLeafDamage() {
		return photoLowLeafDamage;
	}

	public void setPhotoLowLeafDamage(int photoLowLeafDamage) {
		this.photoLowLeafDamage = photoLowLeafDamage;
	}

	public int getPhotoTotalLeafDamage() {
		return photoTotalLeafDamage;
	}

	public void setPhotoTotalLeafDamage(int photoTotalLeafDamage) {
		this.photoTotalLeafDamage = photoTotalLeafDamage;
	}

	public String getPhotoDrop() {
		return photoDrop;
	}

	public void setPhotoDrop(String photoDrop) {
		this.photoDrop = photoDrop;
	}

	public int getPhotoOzonePercent() {
		return photoOzonePercent;
	}

	public void setPhotoOzonePercent(int photoOzonePercent) {
		this.photoOzonePercent = photoOzonePercent;
	}

}