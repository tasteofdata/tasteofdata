<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/base.jsp"%>
<title>500错误页</title>
<link rel="stylesheet" href="<%=contextPath%>/css/errorpage.css" />
<script src="<%=contextPath%>/js/require.js" data-main="<%=contextPath%>/js/site/search_result.js"></script>
</head>
	<body>
		<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/search_header_mini.jsp"%>
		<div class="error">
			<dl class="content">
				<dt class="fiveerror"></dt>
				<dd>
					<h1>很抱歉，服务器繁忙！</h1>
					<p class="youcan f_14">您还可以：</p>
					<p><a ka="500-home" href="<%=contextPath%>/" class="btn">返回首页</a></p>
					<p class="f_14 mt25 mb10">您还可以查看：</p>
					<p><a ka="500-button1" href="<%=contextPath%>/msh/lsp1/" class="btn">热门公司面试</a><a ka="500-button2" href="<%=contextPath%>/top-company/salaries/" class="btn">行业工资排行榜</a><a ka="500-button3" href="<%=contextPath%>/best-company.html" class="btn">最适合工作的50家公司</a></p>
				</dd>
			</dl>
		</div>
		<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>
	</body>
</html>