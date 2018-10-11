<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.common.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
</head>
<body>

	<!-- <div id="main" style="width:500px;height:300px"></div>
	<script type="text/javascript">
		var myCharts = echarts.init(document.getElementById("main"));
		myCharts.setOption({
			title:{
				text:'学生成绩',
				subtext:'成绩比',
				x:'center'
			},
			tooltip:{
				trigger:'item',
				formatter:"{a} <br/>{b} : {c} ({d}%) "
			},
			legend:{
				orient:'vertical',
				x:'left',
				data:[]
			},
			calculable:true,
			series:[{
				name:'学生成绩',
				type:'pie',
				radius:'55%',
				center:['50%','60%'],
				data:[]
			}]
		});
		myCharts.showLoading();
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/getStudent",
			data:[],
			dataType:"json",
			success:function(result) {
				myCharts.hideLoading();
				myCharts.setOption({
					title : {
						text:'学生成绩',
						subtext:'成绩比',
						x:'center'
					},
					tooltip:{
						trigger:'item',
						formatter:"{a} <br/>{b} : {c} ({d}%)"
					},
					legend:{
						orient:'vertical',
						x:'left',
						data:[]
					},
					calculable:true,
					series:[{
						name:'学生成绩',
						type:'pie',
						radius:'55%',
						center:['50%','60%'],
						data:result
					}]
				});
			}
		})
	
	
	</script> -->
	
	
		<div id="main" style="width:600px;height:400px;"></div>
		<script type="text/javascript">
		var mychart=echarts.init(document.getElementById('main'));
		
		var option={
		    title:{
		       text:'Echarts饼状图ajax演示'
		    },   
		    series:[{
		      name:'成绩',
		      type:'pie',
		      data:[]
		    }]
		};
		//把相关设置属性配置到图表对象
		mychart.setOption(option);
		//先把加载数据提示显示
		mychart.showLoading();
		//定义2个数组来存储从服务器端获取到的学生姓名、成绩
		var names=[];
		var scores=[];
		//编写ajax程序异步加载数据到Echart图表
		$.ajax({
		   type:"post",
		   url:"${pageContext.request.contextPath}/getStudent",
		   data:{},
		   dataType:"json",
		   success:function(result){
		     if(result){
		       //隐藏加载动画
		       mychart.hideLoading();
		       //把从服务器端获取到的数据，设置给echarts图表
		       mychart.setOption({
		        
		          series:[{
		            name:'成绩',
		            data:result
		          }]         
		       
		       });
		     }
		   },
		   error:function(msg){
		     alert("图表数据请求失败！");
		     mychart.hideLoading();
		   }
		})
		
		</script>
</body>
</html>