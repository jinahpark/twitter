//XMLHttpRequest ��ü ����
var xmlhttp;                                   

if(window.XMLHttpRequest){    
	xmlhttp = new XMLHttpRequest(); //IE7�̻�, crome, firefox ����
}
else{    
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //�� IE����
} 

//���� loading���� event handler
function onLoad()
{
	//�����۸�ũ�� ���� �ڷḦ ���� get
	//api/list: XML HTTP�� ��û�� ���� ��� �������� URL
	//true: �񵿱� send() �޼ҵ� ����� ������ ��û �� ������ ������ ��ٸ��� �ʰ� �ٷ� ���� �ڹ� ��ũ��Ʈ ������ ����
	//false: ���� send() �޼ҵ� ����� ������ ��û �� ������ ������ �ö����� ��ٸ���
	xmlhttp.open("GET", "api/list.xml", false);
	xmlhttp.send();

	//200: �������� ó�� 404: ���� ����X 403: forbidden(���ٰź�) 500: internal server error(���������߻�)
	if(xmlhttp.status != 200) {
		alert("loading failed.");
	}

	//���� ������Ʈ �ڽ� �� ��õ� �±׸��� ������ �ִ� �͵��� �迭�� ��ȯ 
	//Object Nodelist
	var list = xmlhttp.responseXML.getElementsByTagName("item");

	for(var index = 0; index < list.length; ++index) {
		var id = list[index].getElementsByTagName("id")[0].firstChild.nodeValue;
		var tweet = list[index].getElementsByTagName("tweet")[0].firstChild.nodeValue;
		createNode(id, tweet, false);
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
	xmlhttp.open("GET", "api/delete.xml?id="+id, false);
	xmlhttp.send();
	
	if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
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
			xmlhttp.open("GET", "api/create.xml?tweet=" + tweet , false);
			xmlhttp.send();
				
			if(xmlhttp.status != 200 || xmlhttp.responseText == null || xmlhttp.responseText == "") {
				alert("save failed.");
			}

			var id = xmlhttp.responseXML.getElementsByTagName("id")[0].firstChild.nodeValue;
			Node.id = id;
		}    
		
		else{ //li�� ID�� ���� ��� Update 
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
