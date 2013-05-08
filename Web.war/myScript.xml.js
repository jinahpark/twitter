//XMLHttpRequest 객체 생성
var xmlhttp;                                   

if(window.XMLHttpRequest){    
	xmlhttp = new XMLHttpRequest(); //IE7이상, crome, firefox 지원
}
else{    
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //구 IE버전
} 

//최초 loading시의 event handler
function onLoad()
{
	//하이퍼링크를 통해 자료를 전송 get
	//api/list: XML HTTP가 요청을 보낼 대상 페이지의 URL
	//true: 비동기 send() 메소드 실행시 서버에 요청 후 서버의 응답을 기다리지 않고 바로 다음 자바 스크립트 문장이 실행
	//false: 동기 send() 메소드 실행시 서버에 요청 후 서버의 응답이 올때까지 기다린다
	xmlhttp.open("GET", "api/list.xml", false);
	xmlhttp.send();

	//200: 응답정상 처리 404: 파일 존재X 403: forbidden(접근거부) 500: internal server error(서버오류발생)
	if(xmlhttp.status != 200) {
		alert("loading failed.");
	}

	//현재 엘리먼트 자식 중 명시된 태그명을 가지고 있는 것들의 배열을 반환 
	//Object Nodelist
	var list = xmlhttp.responseXML.getElementsByTagName("item");

	for(var index = 0; index < list.length; ++index) {
		var id = list[index].getElementsByTagName("id")[0].firstChild.nodeValue;
		var tweet = list[index].getElementsByTagName("tweet")[0].firstChild.nodeValue;
		createNode(id, tweet, false);
	} 
}

//Tweet 아이템 UI 만들기function
function createNode(id, tweet, isEditMode)
{
	var newDiv = document.createElement("li");
	newDiv.innerHTML = "<textarea id = 'textbox' rows = '10' cols = '50'></textarea><br>"
	                 + "<input type = 'button' id = 'button' value = 'Delete' onclick='onDelete(this)'></input>"
	                 + "<input type = 'button' id = 'button' value = '" + (isEditMode ? "Ok" : "Edit") + "' onclick='onEdit(this)'></input>";

	newDiv.id = id;  
	newDiv.firstChild.value = tweet;  
	newDiv.firstChild.readOnly = !isEditMode;  //처음 생성시 false

	document.getElementById("boardList").appendChild(newDiv);
}

//create 버튼 눌렸을 때의 event handler
//html 페이지 상의 UI만 구성하면 됨
//Id: UI구성시 할당하지 않고 서버의 api/create에서 생성되어 응답으로 온 것으로 onEdit()에서 추후 셋팅 
//tweet: 사용자 입력 전이므로 null
function onBtnCreate()
{
	createNode(null, null, true);
}

//delete 버튼 눌렸을 때의 event handler
function onDelete(item)
{
	var id = item.parentNode.getAttribute("id");
	xmlhttp.open("GET", "api/delete.xml?id="+id, false);
	xmlhttp.send();
	
	if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
 		alert("delete failed.");
	}
	document.getElementById("boardList").removeChild(item.parentNode);
}

//edit 버튼 눌렸을 때의 event handler
function onEdit(buttonNode)
{
	var Node = buttonNode.parentNode;  //list
	var textBox = Node.firstChild; //text box
	var tweet = textBox.value;

	if(buttonNode.value == "Ok"){
		if(Node.id == null || Node.id == ""){ //li의 ID가 없을 경우 Create
			xmlhttp.open("GET", "api/create.xml?tweet=" + tweet , false);
			xmlhttp.send();
				
			if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
				alert("save failed.");
			}

			var id = xmlhttp.responseXML.getElementsByTagName("id")[0].firstChild.nodeValue;
			Node.id = id;
		}    
		
		else{ //li의 ID가 있을 경우 Update 
			xmlhttp.open("GET", "api/update.xml?id=" + Node.id + "&" + "tweet=" + tweet, false);
	    	xmlhttp.send();
	    	
	    	if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
	    		alert("update failed.");
	    	}
		}
		textBox.readOnly = true;
		buttonNode.value = "Edit";
	}
	else{
			buttonNode.value = "Ok";
			textBox.readOnly = false;
		}
}
