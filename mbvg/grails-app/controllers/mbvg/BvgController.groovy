package mbvg

import de.schildbach.pte.BvgProvider;
import de.schildbach.pte.dto.Departure
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.NearbyStationsResult
import de.schildbach.pte.dto.QueryDeparturesResult
import de.schildbach.pte.dto.StationDepartures
import grails.converters.XML
import groovyx.gpars.*
import java.util.concurrent.Future



class BvgController {

	def index = {
		def posLat = new Integer(params.lat);
		def posLon = new Integer(params.lon);
		//def stops = BvgStop.findAllByLatBetweenAndLonBetween(posLat-5000, posLat+5000,posLon-5000,posLon+5000);
		def stops = BvgStop.findAllByLatBetweenAndLonBetween(posLat-3500, posLat+3500,posLon-3500,posLon+3500);
		stops.each {stop ->
			Point currentPos = new Point(posLat/1000000, posLon/1000000);
			Point stopPos = new Point(stop.lat/1000000, stop.lon/1000000)
			stop.distance = Math.ceil(Haversine.compute(currentPos, stopPos));
		}
		stops = stops.sort {it.distance};
		def stopDestMap = [:];
		def uniqueStops = [];
		stops.each {stop ->
			def lCount = stopDestMap.get(stop.lines);
			if(lCount==null){
				uniqueStops.add(stop);
				stopDestMap.put(stop.lines,1);
			}else if(lCount==1){
				uniqueStops.add(stop);
				stopDestMap.put(stop.lines,2);
			}

		}

		stops = uniqueStops;
		
		BvgProvider bvgProvider = new BvgProvider();
		def responseFu = new ArrayList();
		//QueryDeparturesResult queryDeparturesResult = bvgProvider.queryDepartures(stops[idx].id, 5, true)
		GParsPool.withPool {
			stops.each {responseFu.add({stId -> bvgProvider.queryDepartures(stId, 3, true)}.callAsync(it.id))}
		}


		//
		def hResponse = [];
		stopDestMap = [:];
		def i=0;
		
		responseFu.each {
			QueryDeparturesResult qdrResult = it.get();
		
			qdrResult.stationDepartures.each {
				def depList = [];
				if(it.departures.size()>0){
					for(Departure dep in it.departures) {
					//println dep;	
						
						def key = dep.line.label + dep.destination.name;
				
						def lCount = stopDestMap.get(key);

						if(lCount < 2){
							def predictedString = BvgControlerUtility.buildTimeString(dep.predictedTime);
							def plannedString = BvgControlerUtility.buildTimeString(dep.plannedTime);
							
							def color = '#'+Integer.toHexString(dep.line.style.backgroundColor).substring(2);
	
							depList.add([line:dep.line.label.substring(1), destination:BvgControlerUtility.trimName(dep.destination.name), predictedTime:predictedString, plannedTime:plannedString, position:dep.position,
							color:color])
						
							if(lCount==null){
								stopDestMap.put(key,1);
							}else if(lCount==1){				
								stopDestMap.put(key,2);
							}
						}
					}
					def stop = stops.find {s -> s.id==it.location.id}
					
					def sDep = [place:it.location.place, name:BvgControlerUtility.trimName(it.location.name), distance:stop.distance, departures:depList];
					//def sDep = [place:it.location.place, name:it.location.name, departures:depList];
					hResponse.add(sDep);
				}
			}
			
		}

		render(contentType:"text/json") {
			[qdr:hResponse]
		}
	}
}