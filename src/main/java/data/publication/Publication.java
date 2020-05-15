package data.publication;

import java.util.UUID;

import data.Model;
import offlineads.Utilities;

/**
 * @author richard
 *
 */
public class Publication {
	
	private static final String UNSPECIFIED = "Unspecified";
	private String id = UNSPECIFIED; // UUID4 randomly-generated String
	private String fileName = UNSPECIFIED;
	private String name = UNSPECIFIED;
	private String publisher = UNSPECIFIED;
	private String language = UNSPECIFIED;
	private Location location = new Location(UNSPECIFIED, UNSPECIFIED, UNSPECIFIED);
	private int pages = 0;
	private String format = UNSPECIFIED;
	private String isAlternativeFormatAvailable = UNSPECIFIED;
	private String publishedOn = UNSPECIFIED;
	
	/**
	 * Create a new Publication object from an existing ready-to-process publication
	 * @param publicationFile
	 */
	public Publication(Model model, int fileNum) {
		this.id = UUID.randomUUID().toString();
		this.fileName = model.getFilesNewPubs()[fileNum].getName();
		this.pages = model.getFilesNewPubsImages()[fileNum].listFiles().length;
		this.publishedOn = Utilities.getTimeNowUTC();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIsAlternativeFormatAvailable() {
		return isAlternativeFormatAvailable;
	}

	public void setIsAlternativeFormatAvailable(String isAlternativeFormatAvailable) {
		this.isAlternativeFormatAvailable = isAlternativeFormatAvailable;
	}

	public String getId() {
		return id;
	}

	public String getPublishedOn() {
		return publishedOn;
	}

	public class Location {
		String country = "Country";
		String city = "city";
		String url = "www.example.com";
		
		public Location(String country, String city, String url) {
			this.country = country;
			this.city = city;
			this.url = url;
		}
		
		public String getCountry() {
			return country;
		}
		
		public void setCountry(String country) {
			this.country = country;
		}
		
		public String getCity() {
			return city;
		}
		
		public void setCity(String city) {
			this.city = city;
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
	
	}
	
}
