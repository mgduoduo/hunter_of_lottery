<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
	var URL = '${ctx}';
</script>
</head>

<body>
	<div class="container errorMsg">
		<div class="hero-unit">
		  <h1>出错了</h1>
		  <p>${errorMsg }</p>
		</div>
	</div>
		
</body>

</html>
				