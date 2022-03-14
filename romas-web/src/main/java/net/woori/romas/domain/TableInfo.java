package net.woori.romas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.romas.domain.db.ReservoirOperation.OperationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {

	private String name;
	
//	private OperationType type;
//	
//	private float waterLevel;
	
	private int attentionCount;
	private int cautionCount;
	private int boundaryCount;
	private int seriousCount;
}
