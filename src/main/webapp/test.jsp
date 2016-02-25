<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<title>welcome</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<head>

    <style>
        html, body { padding: 0; margin: 0; }
        html, .ui-mobile, .ui-mobile body {
            height: 635px;
        }
        .ui-mobile, .ui-mobile .ui-page {
            min-height: 635px;
        }
        .ui-content{
            padding:10px 15px 0px 15px;
        }
        .ui-content .ui-listview {
            margin: -15px -15px 0px -15px;
        }

    </style>
</head>

<body>
Hi hunter, welcome here.
</body>
</html>