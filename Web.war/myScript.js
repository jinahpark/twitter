//XMLHttpRequest ��ü ����
var xmlhttp;                                   

if(window.XMLHttpRequest){    
	xmlhttp = new XMLHttpRequest(); //IE7�̻�, crome, firefox ����
}
else{    
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //�� IE����
} 

//JSON ��ü �����(JSON string to JSON Object )
function toJsonObject(jsonText) 
{    
	return eval('(' + jsonText + ')'); 
}

//���� loading���� event handler
function onLoad()
{
	//�����۸�ũ�� ���� �ڷḦ ���� get
	//api/list: XML HTTP�� ��û�� ���� ��� �������� URL
	//true: �񵿱� send() �޼ҵ� ����� ������ ��û �� ������ ������ ��ٸ��� �ʰ� �ٷ� ���� �ڹ� ��ũ��Ʈ ������ ����
	//false: ���� send() �޼ҵ� ����� ������ ��û �� ������ ������ �ö����� ��ٸ���
	xmlhttp.open("GET", "api/list.json", false);
	xmlhttp.send();

	//200: �������� ó�� 404: ���� ����X 403: forbidden(���ٰź�) 500: internal server error(���������߻�)
	if(xmlhttp.status != 200) {
		alert("loading failed.");
	}
	var items = toJsonObject(xmlhttp.responseText);    //JSON array(string)
	//for(������ in ��ü����/�迭����)
	for (var index in items) {        
		createNode(items[index].id, items[index].tweet, false);
	}
}

//Tweet ������ UI �����function
function createNode(id, tweet, isEditMode)
{
	var newDiv = document.createElement("li");
	newDiv.innerHTML = "<textarea id = 'textbox' rows = '10' cols = '50'></textarea><br>"
	                 + "<input type = 'button' id = 'button' value = 'Delete' onclick='onDelete(this)'></input>"
	                 + "<input type = 'button' id = 'button' value = '" + (isEditMode ? "Ok" : "Edit") + "' onclick='onEdit(this)'></input>";

	newDiv.id = id;  
	newDiv.firstChild.value = tweet;  
	newDiv.firstChild.readOnly = !isEditMode;  //ó�� ������ false

	document.getElementById("boardList").appendChild(newDiv);
}

//create ��ư ������ ���� event handler
//html ������ ���� UI�� �����ϸ� ��
//Id: UI������ �Ҵ����� �ʰ� ������ api/create���� �����Ǿ� �������� �� ������ onEdit()���� ���� ���� 
//tweet: ����� �Է� ���̹Ƿ� null
function onBtnCreate()
{
	createNode(null, null, true);
}

//delete ��ư ������ ���� event handler
function onDelete(item)
{
	var id = item.parentNode.getAttribute("id");
	xmlhttp.open("GET", "api/delete?id="+id, false);
	xmlhttp.send();
	
	if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == ""
 	   || toJsonObject(xmlhttp.responseText).result != "succeeded") {
 		alert("delete failed.");
	}
	document.getElementById("boardList").removeChild(item.parentNode);
}

//edit ��ư ������ ���� event handler
function onEdit(buttonNode)
{
	var Node = buttonNode.parentNode;  //list
	var textBox = Node.firstChild; //text box
	var tweet = textBox.value;

	if(buttonNode.value == "Ok"){
		if(Node.id == null || Node.id == ""){ //li�� ID�� ���� ��� Create
			xmlhttp.open("GET", "api/create?tweet=" + tweet, false);
			xmlhttp.send();
				
			if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
				alert("save failed.");
			}
			Node.id = toJsonObject(xmlhttp.responseText).id;
		}
		
		else{ //li�� ID�� ���� ��� Update 
			xmlhttp.open("GET", "api/update?id=" + Node.id + "&" + "tweet=" + tweet, false);
	    	xmlhttp.send();
	    	
	    	if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == ""
	    	   || toJsonObject(xmlhttp.responseText).result != "succeeded") {
	    		alert("update failed.");
	    	}
		}
		textBox.readOnly = true;
		buttonNode.value = "Edit";
	}
	//buttonNode.value == "Edit"
	else{
			buttonNode.value = "Ok";
			textBox.readOnly = false;
	}
}
