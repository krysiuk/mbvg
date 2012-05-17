package mbvg

import javax.persistence.Transient;

class BvgStop {

	static mapping = {
		table 'bvg_stop'
		version false
	}
	
    static constraints = {
    }
	
	static transients = [ "distance" ]
	
	
	Integer id;
	String place;
	String name;
	Double lat;
	Double lon;
	String lines;

	Integer distance;
}
