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
var mapData = [];

function mapReadyHandler(id) {
	$.ajax({
		url: contextPath + "/home/reservoir/level",
		type: "GET",
		success: function(response) {
			var mapData = [];
			
			$.each(response, function(i, item) {
				let data = {"code": item.country, "level": item.level, "longitude": item.longitude, "latitude": item.latitude};
				mapData.push(data);
			});
			
			document.getElementById(id).setData(mapData);
			document.getElementById(id).setLayout(layoutStr);
			document.getElementById(id).setMapDataBaseURLEx(mapDataBaseURL);
			document.getElementById(id).setSourceURLEx(sourceURL);
			
			mapApp = document.getElementById(id);
			mapRoot = mapApp.getRoot();
		}
	});
}

// Map Data 경로 정의
// setMapDataBase함수로 mapDataBase를 문자열로 넣어줄 경우 주석처리나 삭제하십시오.
var mapDataBaseURL = contextPath + "/rMateMapChartH5/MapDataBaseXml/SouthKoreaDrillDown_opacity.xml";

// MapChart Source 선택
// MapSource 디렉토리의 지도 이미지중 택일가능하며, 이외에 사용자가 작성한 별도의 Svg이미지를 지정할 수 있습니다.(매뉴얼 참조)
var sourceURL = contextPath + "/rMateMapChartH5/MapSource/SouthKoreaDrillDown.svg";

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

/// level에 따른 컬러 지정
function colorFunction(code, label, data) {
	let color = "#EBF0F4";
	if (data){
		if (data.level == 0) 
			color = "#3266FE";
		else if (data.level == 1) 
			color = "#F3F42E";
		else if (data.level == 2) 
			color = "#FF6700";
		else if (data.level == 3)
			color = "#FE0000";
	}
	return color;
}


function clickFunction(code, label, data) {
	const str = label.substr(-1);
	if (str == '구' || str == '시' || str == '군') {
		initWorldMap(data.longitude, data.latitude);
		
		$('#mapHolder').addClass('display-none');
		$('#vMap').removeClass('display-none');
	}
}

var layoutStr = '\
<?xml version="1.0" encoding="utf-8"?>\
<rMateMapChart>\
	<MapChart id="mainMap" drillDownEnabled="true" showDataTips="true" backImageY="25" dataTipJsFunction="dataTipFunction" mapChangeJsFunction="clickFunction"\
				dataTipType="Type3" dataTipFill="#2e7dca" dataTipBorderColor="#fff0f0" dataTipColor="#ffffff" dataTipAlpha="1">\
		<series>\
			<MapSeries id="mapseries" interactive="true" selectionMarking="line" color="#353535" labelPosition="inside" displayName="Map Series"\
						rangeLegendDataField="level" localFillJsFunction="colorFunction"\
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
