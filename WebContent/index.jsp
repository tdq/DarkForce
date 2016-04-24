<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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