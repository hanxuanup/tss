<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/IconExtension.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/jquery.edatagrid.js"></script>
 <script type="text/javascript" src="js/datagrid-detailview.js"></script>
<script type="text/javascript">
    $(function(){
        $("#dg").datagrid({
            ctrlSelect:true,//ctrl多选
            height:'100%',
            pagination:true,//分页
            url:"${pageContext.request.contextPath}/tss/query",//数据库数据
            queryParams: {
                userImport: '${param.userImport}',
                field: '${param.field}'
            },
            fitColumns:true,//自动填充满
            columns:[[
                {title:'诗名',field:'title',resizable:true,fixed:false,align:'center',width:10},
                {title:'内容',field:'content',align:'center',width:70},
                {title:'作者',field:'poet',align:'center',width:10,
                    formatter: function(value,row,index){
                       return row.poet.name;
                    }
                },
            ]],
        });
    });
</script>

</head>
<body class="easyui-layout">   
    <table id = "dg"></table>
</body> 
</html>