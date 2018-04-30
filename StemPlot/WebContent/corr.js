function predownload()
{
	document.getElementById("sc:filename").value = document.getElementById("sc:title").value+" correlations";
	
	var x = document.getElementById("gdata").rows.length;
	
	for (var i=0;i<x;i++) 
	{
		var row1;
		if(i==0)
		{
			row1 = document.getElementById("gdata").rows[i].cells[0].innerHTML;
		}
		else
		{
			var tex = document.getElementById("gdata").rows[i].cells[0].innerHTML;
			row1 = tex.split(" ")[0];
			row1 += " ("+tex.split("/gene/")[1].split("\"")[0]+")";
		}
		var row2 = document.getElementById("gdata").rows[i].cells[1].innerHTML;
		var row3 = document.getElementById("gdata").rows[i].cells[2].innerHTML;
		
		if(i==0) document.getElementById("sc:inputval").value = row1+"\t"+row2+"\t"+row3;
		else document.getElementById("sc:inputval").value += "\n"+row1+"\t"+row2+"\t"+row3;
	}
	
//	alert(document.getElementById("sc:inputval").value);
//	alert(document.getElementById("sc:title").value);
//	alert(document.getElementById("sc:filename").value);
}