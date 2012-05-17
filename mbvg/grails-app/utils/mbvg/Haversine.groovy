package mbvg
class Haversine {
	static double R = 6371
	
		static double compute(Point p1, Point p2) {
			def dLat = Math.toRadians(p2.lat-p1.lat);
			def dLon = Math.toRadians(p2.lon-p1.lon);
			def a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					Math.cos( Math.toRadians(p1.lat) ) *
					Math.cos( Math.toRadians(p2.lat) ) * Math.sin(dLon/2) * Math.sin(dLon/2);
			def c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			def d = R * c;
			return 1000*d;
		}
}

/**
* Compute distance in Google Earth KML path file from a path
* @author Marc DEXET ( marcdexet [at] gmail [dot] org )
*/
class Point {
   def lat
   def lon
   public Point(){}
   public Point(lat, lon){
	   this.lat = lat;
	   this.lon = lon;
   }

   public Point(String gps) {
	   def xyz = gps.tokenize(',');
	   lat = Double.parseDouble( xyz[1] )
	   lon = Double.parseDouble( xyz[0] )
   }
   public String toString() {
	   return "LAT: ${lat} LON: ${lon}"
   }

   public static double distance(Point p0, Point p1) {
	   return Haversine.compute(p0, p1)
   }
}