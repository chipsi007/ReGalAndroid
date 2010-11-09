package net.dahanne.gallery3.client.business;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dahanne.gallery.commons.model.Album;
import net.dahanne.gallery.commons.model.G2Picture;
import net.dahanne.gallery.commons.remote.GalleryConnectionException;
import net.dahanne.gallery.commons.remote.GalleryOperationNotYetSupportedException;
import net.dahanne.gallery.commons.remote.ImpossibleToLoginException;
import net.dahanne.gallery.commons.remote.RemoteGallery;
import net.dahanne.gallery3.client.business.exceptions.G3GalleryException;
import net.dahanne.gallery3.client.model.Item;
import net.dahanne.gallery3.client.utils.G3ConvertUtils;
import net.dahanne.gallery3.client.utils.ItemUtils;

import org.apache.http.cookie.Cookie;

public class G3Connection implements RemoteGallery {

	private G3Client client;

	public G3Connection(String galleryUrl) {
		client = new G3Client(galleryUrl);

	}

	public int createNewAlbum(String arg0, int arg1, String arg2, String arg3,
			String arg4) throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public HashMap<String, String> fetchAlbums(String arg0)
			throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public Album findAlbumFromAlbumName(Album arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Album> getAllAlbums(String arg0)
			throws GalleryConnectionException {
		
		
		try {
			Item item = client.getItem(1, false);
			for (String member : item.getMembers()) {
				Integer itemIdFromUrl = ItemUtils.getItemIdFromUrl(member);
				Item item2 = client.getItem(itemIdFromUrl, false);
			}
		} catch (G3GalleryException e) {
			throw new GalleryConnectionException(e);
		}

		return null;
	}

	public InputStream getInputStreamFromUrl(String arg0)
			throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public Collection<G2Picture> getPicturesFromAlbum(String arg0, int arg1)
			throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public List<Cookie> getSessionCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEmbeddedGallery(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void loginToGallery(String galleryUrl, String username,
			String password) throws ImpossibleToLoginException {
		try {
			client.getApiKey(username, password);
		} catch (G3GalleryException e) {
			throw new ImpossibleToLoginException(e);
		}

	}

	public Album retrieveRootAlbumAndItsHierarchy(String arg0)
			throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public int sendImageToGallery(String arg0, int arg1, File arg2,
			String arg3, String arg4, String arg5)
			throws GalleryConnectionException {
		throw new GalleryOperationNotYetSupportedException(
				"Not available in G3 yet");
	}

	public Album getAlbumAndSubAlbums(String galleryUrl, int parentAlbumId)
			throws GalleryConnectionException {
		Album parentAlbum=null;
		try {
			List<Item> albumAndSubAlbums = client.getAlbumAndSubAlbums(parentAlbumId);
			parentAlbum = G3ConvertUtils.itemToAlbum(albumAndSubAlbums.get(0));
			for (Item item : albumAndSubAlbums) {
				if(item==albumAndSubAlbums.get(0)){
					//no need to add the first one, it is the parent
					continue;
				}
				Album itemToAlbum = G3ConvertUtils.itemToAlbum(item);
				itemToAlbum.setParentName(parentAlbum.getName());
				parentAlbum.getChildren().add(itemToAlbum);
			}
			
		} catch (G3GalleryException e) {
			throw new GalleryConnectionException(e);
		}
		return parentAlbum;
	}

}
