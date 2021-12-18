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
	console.log(222);
	var mapObj = mapRoot.getMap();
	
	var testData = [];
	document.getElementById('map1').setData(testData);
	
	$.ajax({
		url: contextPath + "/home/reservoir",
		type: "GET",
		success: function(response) {
			var mapData = [];
			
			$.each(response, function(i, item) {
				var data = {"code": 0, "address": "", "facCode": item.facCode, "facilityName": item.facilityName, "lat": item.latitude, "lng": item.longitude, "sales":100};
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

var layoutStr = '\
<?xml version="1.0" encoding="utf-8"?>\
<rMateMapChart>\
	<MapChart id="mainMap1" showDataTips="true" dataTipType="Type4" dataTipFill="#fff0f0" dataTipBorderColor="#fff0f0" dataTipColor="#ffffff" dataTipAlpha="1">\
		<series>\
			<MapSeries id="mapseries" interactive="true" selectionMarking="line" color="#777777" labelPosition="none" displayName="Map" useGis="true" dataTipFill="#ff007e" dataTipBorderColor="#ff007e" dataTipColor="#ffffff" dataTipAlpha="1" hideOverSizeLabel="false">\
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
			<MapPlotSeries id="plot1" areaCodeField="code" labelField="address" horizontalCenterGapField="h" verticalCenterGapField="v" adjustedRadius="5" fill="#ff007e" color="#888888" fontWeight="bold" labelPosition="bottom" displayName="지점" rangeLegendDataField="value" useGis="true" itemRenderer="CircleItemRenderer">\
				<stroke>\
					<Stroke color="#ff007e" weight="0" alpha="1"/>\
				</stroke>\
			</MapPlotSeries>\
		</series>\
	</MapChart>\
</rMateMapChart>\
';
