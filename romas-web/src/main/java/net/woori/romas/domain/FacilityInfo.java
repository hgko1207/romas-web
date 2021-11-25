package net.woori.romas.domain;

import lombok.Data;
import net.woori.romas.domain.db.Reservoir;
import net.woori.romas.domain.db.ReservoirMgmt;

@Data
public class FacilityInfo {

	private Reservoir reservoir;
	
	private ReservoirMgmt reservoirMgmt;
	
	/** 저수율(%) */
	private float rate;
}
