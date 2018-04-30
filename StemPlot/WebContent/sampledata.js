function startup()
{
	var params = document.URL.split("#");
	
	
	params = params[0].split("?");
	
	params = params[1];
	
	var p = params.split("=");
	
	var lis;
	
	if(p.length==2)
	{
		lis = p[1];
	}
	else
	{
		lis = p[1].split("&")[0];
		document.getElementById("sc:hiddenExtra").value = p[2];
		document.getElementById("sc:inputvalextra").value = p[2];
	}
	
	document.getElementById("sc:inputval").value = lis;
	
	document.getElementById("sc:hiddenPos").value = lis;

	document.getElementById('sc:update1').click();
}