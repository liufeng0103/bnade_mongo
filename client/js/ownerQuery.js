$(document).ready(function() {   
	$('#queryBtn').click(function() {
		query();
	});
	function query() {
		$("#ownerItemsContainer").html("正在查询,请稍等...");
		var realm = $('#realm').val();
		var owner = $('#owner').val();
		if (realm === "" || owner === "") {
			$("#ownerItemsContainer").html("服务器或玩家名不能为空");
		} else {
    		$.get("wow/auction/realm/" + realm + "/owner/" + owner, function(data) {
	       		if (data.code === 201) {
	       			alert("获取数据失败:" + data.errorMessage);
	       		} else {
	       			if (data.length === 0) {
	       				$("#ownerItemsContainer").html("找不到玩家拍卖的物品");
	       			} else {
		       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>物品</th><th>竞价</th><th>一口价</th><th>数量</th><th>剩余时间</th></tr></thead><tbody>";
		       			for (var i in data) {
		       				var ownerItem = data[i];
		       				var bid = Bnade.getGold(ownerItem.bid);
		       				var buyout = Bnade.getGold(ownerItem.buyout);
		       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+ownerItem.item+"</td><td>"+ownerItem.name+"</td><td>"+bid+"</td><td>"+buyout+"</td><td>"+ownerItem.quantity+"</td><td>"+leftTimeMap[ownerItem.timeLeft]+"</td></tr>";
		       			}
		       			tblHtml += "</tbody></table>";
		       			$("#ownerItemsContainer").html(tblHtml);
	       			}
	       		}
			}).fail(function() {
				$("#ownerItemsContainer").html("数据加载出错");
		    });
		}
	}
	function queryByUrl() {
		var realm = getUrlParam('realm');
		var owner = getUrlParam('owner');			
		if (owner !== null && owner !== "") {
			$('#realm').val(realm);
			$('#owner').val(owner);
			$("#queryBtn").click();
		}		
	}
	!function() {
		queryByUrl();
	}();
});