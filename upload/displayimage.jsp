<%@ page import="javax.xml.parsers.*,org.w3c.dom.*,javax.xml.transform.*,java.io.*,javax.xml.transform.dom.DOMSource,javax.xml.transform.stream.StreamResult,
javax.xml.parsers.ParserConfigurationException" %>


<html>
<link href="one.css" rel="stylesheet" type="text/css" />
<head>
<title>Edit your picture</title>

</head>

<body>
<h>Edit your picture </h>
<%
String img = request.getParameter("img");
%>

<%
try{
String new_img = img.substring(0,img.length()-4);

String XmlPath = "./WEB-INF/"+new_img+".xml";

String path = "/usr/share/tomcat6/webapps/upload/WEB-INF/"+ new_img + ".xml";
%>


<%
String new_details = getXMLValue("details",path);
String new_height = getXMLValue("height",path);
String new_width = getXMLValue("width",path);
String new_rotation = getXMLValue("rotation",path);

if(request.getParameter	("details")!=null){ 
	new_details = request.getParameter("details");}
if(request.getParameter	("width")!=null){ 
	new_width = request.getParameter("width");}
if(request.getParameter	("height")!=null){ 
	new_height= request.getParameter("height");}
if(request.getParameter("rotation")!=null){
	new_rotation = request.getParameter("rotation");}

%>



<%!
Document doc;

String getXMLValue(String name, String path) {
		
try{	File fXmlFile = new File(path);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	doc = dBuilder.parse(fXmlFile);
	doc.getDocumentElement().normalize();
	NodeList nlist=doc.getElementsByTagName(name);
	String value = nlist.item(0).getFirstChild().getNodeValue();
	return value;
	}catch(Exception e){ e.printStackTrace();}
	return null;
}

void setXMLValue(String s,String name, String path){

	try{	File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nlist=doc.getElementsByTagName(name);
		nlist.item(0).getFirstChild().setNodeValue(s);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(path);
		transformer.transform(source, result);
		return;
	}catch(Exception e){ e.printStackTrace();}
}
%>

<%
String appPath = application.getRealPath("/");
DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
DocumentBuilder db=dbf.newDocumentBuilder();
doc=db.parse(appPath + XmlPath);
%>
</br>
</br>
<h5>
<% out.println(new_details);%>
</h5>
</br>
</br>

<div id="image">

<img src='./image/<%= img %>' height="<%=new_height%>px" width="<%=new_width%>px" style="transform: rotate(<%=new_rotation%>deg);
   -moz-transform: rotate(<%=new_rotation%>deg);
   -webkit-transform: rotate(<%=new_rotation%>deg);" />
</div>

<br/><br/><br/><br/>

<form action="displayimage.jsp" method="GET">

<div id="a1">
	<div>New Height:</div><br/>
</div>
 <div id="b1">
	<input name="height" type="text" value="<%=new_height%>" size="4"/>
	</div>	
<br/>

<div id="a1">
	<div>New Width:</div><br/>
</div>
<div id="b1">
<input name="width" type="text" value="<%=new_width%>" size="4"/>
</div>
<br/>
<div id="b1">
<input name="img" type="hidden" value="<%=img%>"/>
</div>
<div id="a1">
	<div>New Rotation:</div><br/>
</div>
<div id="b1">
<input name="rotation" type="text" value="<%=new_rotation%>"/>
</div>
<br/>
<div id="a1">
	<div>New Details:</div><br/>
</div>
<div id="b1"><textarea name="details" id="details" class="input"></textarea>
</div>
<br/>
<div id="b1">
<input type="submit" value="Apply"/>
</div>
<br/>
<br/>
<div id="back">
<% out.println("<li><a href=\"./start\">Back</a></li>"); %>
</dov>
</form>


<%
setXMLValue(new_details,"details",path);
setXMLValue(new_rotation,"rotation",path);
setXMLValue(new_height,"height",path);
setXMLValue(new_width,"width",path);
%>

<%
}catch(Exception e){ }
%>

 
 
</body>

</html>
