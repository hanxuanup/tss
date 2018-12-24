<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<html>
<head>
</head>
<body>
    <div style="text-align:center;padding:200px">
        <h1>你要搜点啥</h1>
    <form method="post" action = "${pageContext.request.contextPath}/showAll.jsp">
        全选<input type="radio" name = "field" value = "all" checked/>
        作者<input type = "radio" name = "field" value = "author"/>
        诗名<input type = "radio" name = "field" value = "title"/>
        内容<input type = "radio" name = "field" value = "content"/>
        <input  type = "test" name = "userImport" />
        <input type = "submit" value=" 搜索">
    </form>
    </div>
</body>
</html>
