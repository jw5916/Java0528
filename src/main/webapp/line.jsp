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
		var myChart = echarts.init(document.getElementById('main'));
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: '异步数据加载折线图示例'
        },
        tooltip: {},
        legend: {
            data: ['成绩']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '成绩',
            type: 'line',
            data: []
        }]
    });

    myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var names = [];    //姓名数组（实际用来盛放X轴坐标值）
    var nums = [];    //成绩数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",      
        url: "${pageContext.request.contextPath}/getStudent",    //请求发送到getStudent处
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    names.push(result[i].name);    //挨个取出类别并填入姓名数组
                }
                for (var i = 0; i < result.length; i++) {
                    nums.push(result[i].score);    //挨个取出成绩并填入成绩数组
                }
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    xAxis: {
                        data: names
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '成绩',
                        data: nums
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
        }
    })
		
	</script>
</body>
</html>