$(document).ready(function() {
	$("#queryBtn").click(function() {
		query();
	});
	function query() {
		clear();
		var realm = $("#realm").val();
		var petName = $("#petName").val();
		if (petName === "") {
			$('#msg').html("宠物名不能为空");
		} else {	
			petQuery(petName);
		}
	}
	function petQuery(name) {
		$.get('wow/pet/name/' + name, function(data) {
			if (data.code === 201) {
				$('#msg').html("查询宠物失败:" + data.errorMessage);
			} else if (data.petSpeciesId === 0) {		
				$('#msg').html("找不到宠物:" + name);
			} else {
				BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.pet.key, name);
				var id = data.petSpeciesId;
				var tblHtml = "<p>该宠物有" + data.petStats.length + "种成长类型,请选择一种类型查找:</p>";				
				tblHtml += "<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>宠物名</th><th>成长类型</th><th>生命值</th><th>攻击</th><th>速度</th></tr></thead><tbody>";
				for (var i in data.petStats) {
					var stats = data.petStats[i];
					tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+id+"</td><td><a href='javascript:void(0)' id='petQuery"+i+"' petId='"+id+"' breed='"+stats.breedId+"'>"+name+"</a></td><td>"+stats.breedId+"</td><td>"+stats.health+"</td><td>"+stats.power+"</td><td>"+stats.speed+"</td></tr>";
				}
				tblHtml += "</tbody></table>";
				$('#petResult').html(tblHtml);
				for (var i in data.petStats) {
					$('#petQuery' + i).click(function() {						
						accurateQuery($(this).attr('petId'), $(this).attr('breed'), name);
					});
				}
				var url = window.location.protocol + "//" + window.location.host + window.location.pathname + "?name=" + encodeURIComponent(name);		
				$("#queryByUrl").html("快速查询URL: <a href='" + url + "'>" + url + "</a>");
			}			
		}).fail(function() {
			$('#msg').html("查询出错");
	    });
	}
	function accurateQuery(id, breed, name) {
		$('#msg').html("正在查询，请稍等...");
		isShowAll = true;
		$("#showAllTbl").hide();
		$("#showAllA").html("所有服务器+");	
		getItemByAllRealms(id, breed, name);
	}
	function getItemByAllRealms(id, breed, name) {	
		$('#allRealmMsg').show();
		$('#allRealmMsg').html("查询所有服务器数据,请稍等...");
		$.get('wow/auction/pet/id/' + id + '/breed/' + breed, function(data) {
			if (data.code === 404) {
				$('#allRealmMsg').html("查询所有服务器失败:" + data.errorMessage);
				$('#allRealmCtlDiv').hide();
			} else {						
				var isShowAll = true;
				$("#showAllA").click(function() {
					if(isShowAll){
						isShowAll = false;
						$("#showAllTbl").show();
						$("#showAllA").html("所有服务器-");			
						$("#showAllBody").empty();
						for (var i in data) {
							var item = data[i];
							var buyout = Bnade.getGold(item.minBuyout);
							var realm = Bnade.getConnectedRealms(item.realm);								
							$("#showAllBody").append("<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td>"+buyout+"</td><td>"+item.minBuyoutOwner+"</td><td>"+item.petLevel+"</td><td>"+item.totalQuantity+"</td><td>"+new Date(item.lastModified).format("MM-dd hh:mm:ss")+"</td></tr>");
						}
					} else {
						isShowAll = true;
						$("#showAllTbl").hide();
						$("#showAllA").html("所有服务器+");
					}		
				});
				$('#allRealmMsg').html("");
				$('#allRealmCtlDiv').show();				
				var showCount=20;//显示条数
				var avgBuyoutOfRealms=0;
				var chart1Labels=[];
				var chart1BuyoutData=[];
				var chart1QuantityData=[];
				var chart2Labels=[];
				var chart2BuyoutData=[];
				var chart2QuantityData=[];
				var avgQuantity=0;
				data.sort(function(a,b) {
					return a.minBuyout - b.minBuyout;
				});
				var avgBuyoutCount = 0;
				for (var i in data) {
					var item = data[i];
					var realm = item.realm;
					var minBuyout = toDecimal(item.minBuyout/10000);
					if (i < showCount) {
						chart1Labels[i] = realm;
						chart1BuyoutData[i] = minBuyout;
						if (minBuyout >= 10) {
							chart1BuyoutData[i] = parseInt(minBuyout);
						}
						chart1QuantityData[i] = item.totalQuantity;
					}					
					if (i >= (data.length*0.1) && i <= (data.length*0.9)) {
						avgBuyoutOfRealms += minBuyout;
						avgBuyoutCount++;
					}	
					avgQuantity += item.totalQuantity;
				}	
				avgQuantity = parseInt(avgQuantity/data.length);
				avgBuyoutOfRealms = toDecimal(avgBuyoutOfRealms/avgBuyoutCount);
				if (avgBuyoutOfRealms >= 10)avgBuyoutOfRealms = parseInt(avgBuyoutOfRealms);				
				var tmpHight20Count = 0;
				var blackCount = 0;
				var black = [];
				for(var i=data.length-1;i>0;i--){
					var item=data[i];
					var minBuyout=toDecimal(item.minBuyout/10000);
					if(minBuyout<avgBuyoutOfRealms*3&&tmpHight20Count<showCount){
						chart2Labels[tmpHight20Count]=item.realm;
						chart2BuyoutData[tmpHight20Count]=minBuyout;
						if(minBuyout>=10){
							chart2BuyoutData[tmpHight20Count]=parseInt(minBuyout);
						} 
						chart2QuantityData[tmpHight20Count]=item.totalQuantity;
						tmpHight20Count++;
					}
					if(minBuyout>avgBuyoutOfRealms*3){
						black[blackCount++]=item;
					}
				}
				$('#allRealmMsg').html("所有服务器平均价格:"+avgBuyoutOfRealms);			
				$('#allMinBuyout').html(chart1BuyoutData[0]);
				$('#allMaxBuyout').html(chart2BuyoutData[0]);
				$('#allAvgBuyout').html(avgBuyoutOfRealms);
				$('#allAvgQuantity').html(avgQuantity);					
				$("#showAllA").click();
			}
			$('#msg').html("");
		});
	}

	$("#fuzzyQueryBtn").click(function() {
		var realm = $("#realm").val();
		var petName = $("#petName").val();
		if(petName === ""){
			$('#msg').html("宠物名不能为空");
		} else {	
			$('#msg').html("正在查询，请稍等...");
			$('#pastWeekMsg').html("");	
			$('#allRealmMsg').html("");
			fuzzyQueryItems(realm, petName);			
		}
	});
	function fuzzyQueryItems(realm, petName) {
		$.get('wow/pet/fuzzy/name/' + encodeURIComponent(petName), function(data) {
			if (data.code === 201) {
				$('#msg').html("模糊查询失败:" + data.errorMessage);								
			} else { 
				if (data.length === 0) {
					$('#msg').html("找不到物品:" + $("#petName").val());
				} else {
					$("#fuzzyItemsList").show();
					$("#fuzzyItemsList").html("<li class='active'><a href='javascript:void(0)'>物品名</a></li>");					
					for (var i in data) {
						var id = "fuzzyItem" + i;						
						$("#fuzzyItemsList").append("<li><a href='javascript:void(0)' id='" + id + "'>" + data[i] + "</a></li>");
						$("#" + id).click(function() {
							$("#petName").val($(this).html());
							$("#queryBtn").click();
						});
					}	
					$('#msg').html("");
				}
			}
		}).fail(function() {
			$('#msg').html("查询出错");
	    });
	}	
	function clear(){
		$('#past24CtlDiv').hide();
		$('#past24Msg').hide();
		$('#pastWeekCtlDiv').hide();
		$('#pastWeekMsg').hide();
		$('#allRealmCtlDiv').hide();
		$('#allRealmMsg').hide();
		$("#fuzzyItemsTbl").hide();
	}
	function queryByUrl(){
		var name = getUrlParam("name");
		if (name !== null && name !== "") {
			$('#petName').val(name);
			$("#queryBtn").click();
		}		
	}		
	!function(){
		$('#past24CtlDiv').hide();
		$('#pastWeekCtlDiv').hide();
		$('#allRealmCtlDiv').hide();
		$("#fuzzyItemsTbl").hide();
		queryByUrl();
	}();	
});