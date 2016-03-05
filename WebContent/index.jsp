<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/darkforce.css">
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="${pageContext.request.contextPath}/js/webcomponents-lite.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/darkforce.js"></script>

<link rel="import" href="${pageContext.request.contextPath}/components/darkforce-components.html">

<script type="text/javascript">
$(document).ready(function() {
	DarkForce.init("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/server");
});
</script>
</head>
<body>

</body>
</html>