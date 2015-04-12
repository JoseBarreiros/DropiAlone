package ec.org.alone.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.org.alone.entities.Photo;

@Stateless
public class PhotoService extends Generic<Photo> {

	public PhotoService() {
		super(Photo.class, PhotoService.class);
		// TODO Auto-generated constructor stub
	}

	public List<Photo> findAllOrderedByDateOriginal() {
		System.out.println("sigue entrando");
		Query q = em
				.createQuery("SELECT a FROM Photo a ORDER BY a.photoUploadDate DESC");
		return q.getResultList();
	}

}
