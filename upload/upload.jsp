<%@ page import="java.util.*" %>
<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Upload Image</title>
		<link href="style.css" rel="stylesheet" type="text/css" />
	</head>

		<body>
			<h3>Upload Images</h3>
			Choose your image: <br />
			<form action="UploadServlet" method="post"enctype="multipart/form-data">
			<input type="file" name="file" size="50"/>
			

			



				<input type="submit" value="Upload File" />


</br></br>
<div class=desc>
				<div class=letter><b>Give Details</b></div></font><textarea name="details" id="details" class="input"></textarea>
			</div> <br>


			</form>
<center><h4>Images:</h4>
</center>
<div class=out>

<%
            String[] reply = (String [])request.getAttribute("styles");
             out.println("<table>");
             for(int i = 0; i < reply.length; i++) {
               if(i % 3 == 0 && i != 0 ) {
                   out.println("<tr>");
               }
               out.println(" <td><img id=\"cells\" src=\"./image/" + reply[i] + "\" width=\"230\" height=\"200\"><br>");


               out.println("<div class=button> <center><a href=\"displayimage.jsp?img=" + reply[i] + "\">Edit</a></center> </div></td>");
              
        
             }
             out.println("</table>");
                     
            %>




</div>

		</body>

</html>
