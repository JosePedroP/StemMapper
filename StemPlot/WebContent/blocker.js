function showload()
{
//	$("#sc:getExpression").attr("disabled", true);
	//$("#sc:giveMeATXT").attr("disabled", true);
//	$("#sc:getExpression").button({ disabled: true });
	document.getElementById("sc:getExpression").disabled = true;
	$(".shutdownit").attr("disabled", true);
	$("#loading-image").show();
}

function hideload()
{
//	$("#sc:getExpression").removeAttr('disabled');
//	$("#sc:giveMeATXT").attr("disabled", true);
	$(".shutdownit").removeAttr('disabled');
	$("#loading-image").hide();
}