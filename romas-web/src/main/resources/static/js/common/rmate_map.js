// -----------------------맵차트 설정 시작-----------------------
// rMate 맵차트 생성 준비가 완료된 상태 시 호출할 함수를 지정합니다.
var mapVars = "rMateOnLoadCallFunction=mapReadyHandler";

// 맵차트의 속성인 rMateOnLoadCallFunction 으로 설정된 함수.
// rMate 맵차트 준비가 완료된 경우 이 함수가 호출됩니다.
// 이 함수를 통해 맵차트에 레이아웃과 데이터를 삽입합니다.
// 파라메터 : id - rMateMapChartH5.create() 사용 시 사용자가 지정한 id 입니다.
// 맵차트 콜백함수 7개 존재합니다.
// 1. setLayout - 스트링으로 작성된 레이아웃 XML을 삽입합니다.
// 2. setData - 배열로 작성된 데이터를 삽입합니다.
// 3. setMapDataBase - 스트링으로 작성된 MapData XML을 삽입합니다.
// 4. setLayoutURLEx - 레이아웃 XML 경로를 지시합니다.
// 5. setDataURLEx - 데이터 XML 경로를 지시합니다.
// 6. setMapDataBaseURLEx - MapData XML 경로를 지시합니다.
// 7. setSourceURLEx - Map Source 경로를 지시합니다.
var mapApp, mapRoot;

function mapcomp() {
	var mapObj = mapRoot.getMap();
	
//	var testData = [{"codeA": 80305, "facCode": "1", "facilityName": "test"}];
//	document.getElementById('map1').setData(testData);
	
//	$.ajax({
//		url: contextPath + "/home/reservoir",
//		type: "GET",
//		success: function(response) {
//			var mapData = [];
//			
//			$.each(response, function(i, item) {
//				const rand_h = Math.floor(Math.random() * 20);
//				const rand_v = Math.floor(Math.random() * 20);
//				
//				let data = {"facCode": item.facCode, "facilityName": item.facilityName, "h": rand_h, "v": rand_v};
//				
//				if (item.areaSiGun != null && item.areaSiGun != 0) {
//					if (item.level == 0) 
//						data["codeA"] = item.areaSiGun;
//					else if (item.level == 1) 
//						data["codeB"] = item.areaSiGun;
//					else if (item.level == 2) 
//						data["codeC"] = item.areaSiGun;
//					else if (item.level == 3) 
//						data["codeD"] = item.areaSiGun;
//					mapData.push(data);
//				}
//				
//				if (item.areaDong != null && item.areaDong != 0) {
//					if (item.level == 0) 
//						data["codeA"] = item.areaDong;
//					else if (item.level == 1) 
//						data["codeB"] = item.areaDong;
//					else if (item.level == 2) 
//						data["codeC"] = item.areaDong;
//					else if (item.level == 3) 
//						data["codeD"] = item.areaDong;
//					mapData.push(data);
//				}
//			});
//			
//			document.getElementById('map1').setData(mapData);
//		}
//	});
	
	var mapData = [];
	
	$.ajax({
		url: contextPath + "/home/reservoir/level",
		type: "GET",
		success: function(response) {
			mapData.push({"code":100, "level":100});
			mapData.push({"code":101, "level":200});
			mapData.push({"code":102, "level":300});
			mapData.push({"code":103, "level":400});
			mapData.push({"code":104, "level":500});
			
			$.each(response, function(i, item) {
				let data = {"code": item.code};
				
				if (item.level == 0) 
					data["level"] = 200;
				if (item.level == 1) 
					data["level"] = 300;
				else if (item.level == 2) 
					data["level"] = 400;
				else if (item.level == 3) 
					data["level"] = 500;
				mapData.push(data);
			});
			
			document.getElementById('map1').setData(mapData);
		}
	});
}

function mapReadyHandler(id) {
	document.getElementById(id).setLayout(layoutStr);
	document.getElementById(id).setMapDataBaseURLEx(mapDataBaseURL);
	document.getElementById(id).setSourceURLEx(sourceURL);

	mapApp = document.getElementById(id);
	mapRoot = mapApp.getRoot();
	mapRoot.addEventListener("creationComplete", mapcomp);
}

// Map Data 경로 정의
// setMapDataBase함수로 mapDataBase를 문자열로 넣어줄 경우 주석처리나 삭제하십시오.
var mapDataBaseURL = contextPath + "/rMateMapChartH5/MapDataBaseXml/SouthKoreaDrillDownUMD_GIS.xml";

// MapChart Source 선택
// MapSource 디렉토리의 지도 이미지중 택일가능하며, 이외에 사용자가 작성한 별도의 Svg이미지를 지정할 수 있습니다.(매뉴얼 참조)
var sourceURL = contextPath + "/rMateMapChartH5/MapSource/SouthKoreaDrillDownUMD_GIS.svg";

// rMateMapChart 를 생성합니다.
// 파라메터 (순서대로) 
//  1. 맵차트의 id ( 임의로 지정하십시오. ) 
//  2. 맵차트가 위치할 div 의 id (즉, 차트의 부모 div 의 id 입니다.)
//  3. 맵차트 생성 시 필요한 환경 변수들의 묶음인 chartVars
//  4. 맵차트의 가로 사이즈 (생략 가능, 생략 시 100%)
//  5. 맵차트의 세로 사이즈 (생략 가능, 생략 시 100%)
rMateMapChartH5.create("map1", "mapHolder", mapVars, "100%", "100%");

function dataTipFunction(seriesId, code, label, data) {
  if (seriesId == "plot1" || seriesId == "plot2" || seriesId == "plot3" || seriesId == "plot4") {
       return "시설 : " + data.facilityName;
  } else
      return label;
}

function itemClickFunction(seriesId, code, label, data) {
    location.href = contextPath + "/facility/" + data.facCode;
}

function clickFunction(code, label, data) {
	if (label == '경기도') {
	} else if (label == '강원도') {
		initWorldMap(128.34652853368348, 37.750174547038476);
	} else if (label == '충청북도') {
		initWorldMap(127.63308137873065, 36.67464493679185);
	} else if (label == '충청남도') {
		initWorldMap(126.90366459133672, 36.55025699120413);
	} else if (label == '경상북도') {
		initWorldMap(128.74121345720187, 36.587247368002615);
	} else if (label == '경상남도') {
		initWorldMap(128.35972451038734, 35.40262829035685);
	} else if (label == '전라북도') {
		initWorldMap(127.18130434578022, 35.77963772942307);
	} else if (label == '전라남도') {
		initWorldMap(127.02799724321814, 34.93837178858539);
	} else if (label == '제주도') {
		initWorldMap(126.5661352113117, 33.38662515740234);
	}
	
	if (label.length != 2) {
		$('#mapHolder').addClass('display-none');
		$('#vMap').removeClass('display-none');
	}
}

var layoutStr = '\
<?xml version="1.0" encoding="utf-8"?>\
<rMateMapChart>\
	<MapChart id="mainMap" drillDownEnabled="false" showDataTips="true" dataTipJsFunction="dataTipFunction" itemClickJsFunction="itemClickFunction" mapChangeJsFunction="clickFunction"\
				dataTipType="Type3" dataTipFill="#2e7dca" dataTipBorderColor="#fff0f0" dataTipColor="#ffffff" dataTipAlpha="1">\
		<series>\
			<MapSeries id="mapseries" interactive="true" selectionMarking="line" color="#353535" labelPosition="inside" displayName="Map Series"\
						localFillByRange="[#EBF0F4,#3266FE,#F3F42E,#FF6700,#FE0000]" rangeLegendDataField="level"\
						useGis="true" dataTipFill="#ff007e" dataTipBorderColor="#ff007e" dataTipColor="#ffffff" dataTipAlpha="1" hideOverSizeLabel="false">\
				<stroke>\
					<Stroke color="#CAD7E0" weight="0.5" alpha="1"/>\
				</stroke>\
				<rollOverStroke>\
					<Stroke color="#CAD7E0" weight="0.5" alpha="1"/>\
				</rollOverStroke>\
				<localFill>\
					<SolidColor color="#EBF0F4"/>\
				</localFill>\
			</MapSeries>\
			<MapPlotSeries id="plot1" areaCodeField="codeA" labelField="facilityName" adjustedRadius="6" fill="#3266FE" color="#888888"\
							fontWeight="bold" labelPosition="bottom" displayName="관심"\
							horizontalCenterGapField="h" verticalCenterGapField="v">\
		    	<stroke>\
			     	<Stroke color="#3266FE" weight="0" alpha="1"/>\
			 	</stroke>\
			</MapPlotSeries>\
			<MapPlotSeries id="plot2" areaCodeField="codeB" labelField="facilityName" adjustedRadius="6" fill="#F3F42E" color="#888888"\
							fontWeight="bold" labelPosition="bottom" displayName="주의"\
							horizontalCenterGapField="h" verticalCenterGapField="v">\
		    	<stroke>\
			     	<Stroke color="#F3F42E" weight="0" alpha="1"/>\
			 	</stroke>\
			</MapPlotSeries>\
			<MapPlotSeries id="plot3" areaCodeField="codeC" labelField="facilityName" adjustedRadius="6" fill="#FF6700" color="#888888"\
							fontWeight="bold" labelPosition="bottom" displayName="경계"\
							horizontalCenterGapField="h" verticalCenterGapField="v">\
			    <stroke>\
			     	<Stroke color="#FF6700" weight="0" alpha="1"/>\
			 	</stroke>\
			</MapPlotSeries>\
			<MapPlotSeries id="plot4" areaCodeField="codeD" labelField="facilityName" adjustedRadius="6" fill="#FE0000" color="#888888"\
							fontWeight="bold" labelPosition="bottom" displayName="심각"\
							horizontalCenterGapField="h" verticalCenterGapField="v">\
		    	<stroke>\
		     		<Stroke color="#FE0000" weight="0" alpha="1"/>\
		 		</stroke>\
			</MapPlotSeries>\
		</series>\
	</MapChart>\
	<Legend dataProvider="{mainMap}" height="30" useVisibleCheck="false" horizontalGap="3" direction="horizontal" borderStyle="solid" defaultMouseOverAction="true"/>\
</rMateMapChart>\
';
