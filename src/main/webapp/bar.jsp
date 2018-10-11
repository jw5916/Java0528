<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<!-- 引入 echarts.common.min.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.common.min.js"></script>
<!-- 引入jquery.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>

</head>
<body>

	<div id="main" style="width:800px;height:500px"></div>
	<script type="text/javascript">
		var myChart = echarts.init(document.getElementById("main"));
		myChart.setOption({
			title:{
				text:'异步数据加载柱状图示例'
			},
			tooltip:{},
			legend:{
				data:['学生成绩']
			},
			xAxis:{
				data:[]
			},
			yAxis:{},
			series:[{
				name:'学生成绩',
				type:'bar',
				data:[]
			}]
		});
		
		myChart.showLoading();
		var names = [];
		var nums = [];
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/getStudent",   /* 请求发送到getStudent处 */
			data:{},
			dataType:"json",    /* 返回数据形式为json */
			
			success:function(result) {
				/* 请求成功时执行该函数内容，result即为服务器返回的json对象 */
				if(result) {
					for (var i = 0; i < result.length; i++) {
						names.push(result[i].name);
					}
					for (var i = 0; i < result.length; i++) {
						nums.push(result[i].score);
					}
					
					myChart.hideLoading();   /* 隐藏加载动画 */
					myChart.setOption({   /* 加载数据图表 */
						xAxis:{
							data:names
						},
						series:[{   /* 根据名字对应到相应的系列 */
							name:'学生成绩',
							data:nums
						}]
						
					});
				}
			}
			
		})
	</script>
</body>
</html>