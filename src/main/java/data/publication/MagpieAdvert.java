package data.publication;

/**
* Data class laid out to match format of the Offline Ads API data Json object
*/
public class MagpieAdvert {

    private String id;
    private String image_url;
    private String headline_text;
    private String city;
    private int country;
    private PublicationDate publication_date;
    private String page_number;
    private String keywords;
    private Sector sector;
    private CreatedAt created_at;
    private Advertiser advertiser;
    private Publication publication;
    private String observations;
    private boolean is_changed;	

    /**
     * Default constructor for Data objects
     */
	public MagpieAdvert() {

	}
	
	/**
	 * Create a new Data object to be converted into Json
	 */
	public MagpieAdvert(String id, String image_url, String headline_text, String city, int country, PublicationDate publication_date, String page_number, String keywords, Sector sector,
			CreatedAt created_at, Advertiser advertiser, Publication publication, String observations, boolean is_changed) {
		this.id = id;
		this.image_url = image_url;
		this.headline_text = headline_text;
		this.city = city;
		this.country = country;
		this.publication_date = publication_date;
		this.page_number = page_number;
		this.keywords = keywords;
		this.sector = sector;
		this.created_at = created_at;
		this.advertiser = advertiser;
		this.publication = publication;
		this.observations = observations;
		this.is_changed = is_changed;
	}
	
	/**
	 * toString method for a Data object
	 * @return String representation of a Data object
	 */
	public String toString () {
		StringBuffer adStringBuffer = new StringBuffer();
			adStringBuffer.append("id: " + id + "\n");
			adStringBuffer.append("image_url: " + image_url + "\n");
			adStringBuffer.append("headline_text: " + headline_text + "\n");
			adStringBuffer.append("city: " + city + "\n");
			adStringBuffer.append("country: " + country + "\n");
			adStringBuffer.append("publication_date_date: " + publication_date.date + "\n");
			adStringBuffer.append("publication_date_timezone_type: " + publication_date.timezone_type + "\n");
			adStringBuffer.append("publication_date_timezone: " + publication_date.timezone + "\n");
			adStringBuffer.append("page_number: " + page_number + "\n");
			adStringBuffer.append("keywords: " + keywords + "\n");
			adStringBuffer.append("sector_id: " + sector.id + "\n");
			adStringBuffer.append("sector_name: " + sector.name + "\n");
			adStringBuffer.append("created_at_date: " + created_at.date + "\n");
			adStringBuffer.append("created_at_timezone_type: " + created_at.timezone_type + "\n");
			adStringBuffer.append("created_at_timezone: " + created_at.timezone + "\n");
			adStringBuffer.append("advertiser_id: " + advertiser.id + "\n");
			adStringBuffer.append("advertiser_name: " + advertiser.name + "\n");
			adStringBuffer.append("publication_id: " + publication.id + "\n");
			adStringBuffer.append("publication_name: " + publication.name + "\n");
			adStringBuffer.append("publication_publication_type_id: " + publication.publication_type.id + "\n");
			adStringBuffer.append("publication_publication_type_name: " + publication.publication_type.name + "\n");
			adStringBuffer.append("observations: " + observations + "\n");
			adStringBuffer.append("is_changed: " + is_changed + "\n");
		return adStringBuffer.toString();
	}
    
    class PublicationDate {
    	String date;
    	int timezone_type;
    	String timezone;
    	
    	public PublicationDate(String date, int timezone_type, String timezone) {
    		this.date = date;
    		this.timezone_type = timezone_type;
    		this.timezone = timezone;
    	}
    	
    	public String getDate() {
    		return this.date;
    	}

    	public int getTimezone_type() {
    		return this.timezone_type;
    	}

    	public String getTimezone() {
    		return this.timezone;
    	}
    }
    
    class Sector {
    	int id;
    	String name;
    	
    	public Sector(int id, String name) {
    		this.id = id;
    		this.name = name;
    	}
    	
    	public int getId() {
    		return this.id;
    	}
    	
    	public String getName() {
    		return this.name;
    	}
    }
    
    class CreatedAt {
    	String date;
    	int timezone_type;
    	String timezone;
    	
    	public CreatedAt(String date, int timezone_type, String timezone) {
    		this.date = date;
    		this.timezone_type = timezone_type;
    		this.timezone = timezone;
    	}
    	
    	public String getDate() {
    		return this.date;
    	}
    	
    	public int getTimezone_type() {
    		return this.timezone_type;
    	}
    	
    	public String getTimezone() {
    		return this.timezone;
    	}
    }
    
    class Advertiser {
    	String id;
    	String name;
    	
    	public Advertiser(String id, String name) {
    		this.id = id;
    		this.name = name;
    	}
    	
    	public String getId() {
    		return this.id;
    	}
    	
    	public String getName() {
    		return this.name;
    	}	
    }
    
   class Publication {
    	String id;
    	String name;
    	PublicationType publication_type;
    	
    	public Publication(String id, String name, PublicationType publication_type) {
    		this.id = id;
    		this.name = name;
    		this.publication_type = publication_type;
    	}
    	
    	public String getId() {
    		return this.id;
    	}
    	
    	public String getName() {
    		return this.name;
    	}
    	
    	public PublicationType getPublication_type() {
    		return this.publication_type;
    	}
    	
    	
    	class PublicationType {
    		int id;
    		String name;
    		
    		public PublicationType(int id, String name) {
    			this.id = id;
    			this.name = name;
    		}
    		
    		public int getId() {
    			return this.id;
    		}
    		
    		public String getName() {
    			return this.name;
    		}
    	}
    }

	public String getId() {
		return id;
	}

	public String getImage_url() {
		return image_url;
	}

	public String getHeadline_text() {
		return headline_text;
	}

	public String getCity() {
		return city;
	}

	public int getCountry() {
		return country;
	}

	public PublicationDate getPublication_date() {
		return publication_date;
	}

	public String getPage_number() {
		return page_number;
	}

	public String getKeywords() {
		return keywords;
	}

	public Sector getSector() {
		return sector;
	}

	public CreatedAt getCreated_at() {
		return created_at;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public Publication getPublication() {
		return publication;
	}

	public String getObservations() {
		return observations;
	}

	public Boolean getIs_changed() {
		return is_changed;
	}
    
}
