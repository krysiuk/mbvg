package mbvg

import java.util.Date;

class BvgControlerUtility {
	
	static TimeZone cetTime = TimeZone.getTimeZone("CET");
	
	static trimName(String name){
		return name.size()<22?name:name.substring(0,19)+"...";
	}
	
	static String buildTimeString(Date timeIn){
		if(timeIn==null)
			return null;
			
		def result = Math.round((timeIn.getTime() - System.currentTimeMillis())/60000);
		//println result;
		return ""+result;
		//println timeIn;
		/*
		GregorianCalendar time = null;
		def timeString = null;
		if(timeIn!=null){
			time = new GregorianCalendar();
			time.setTimeZone(cetTime);
			time.time = timeIn;
			timeString = String.format('%tR', time);
		}
		return timeString;
		*/
	}
}
