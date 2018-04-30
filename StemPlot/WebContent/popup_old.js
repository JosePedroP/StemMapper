function jumptopopup(topop) {
//	var topop = "Ly6d (17068)";
	
	var rnames = document.getElementById("sc:heatmaprowsnames").value.split("|");
	
	var found = false;
	
	for (var i = 0, len = rnames.length; i < len && !found; i++) 
	{
		if(topop==rnames[i])
		{
			found = true;
			var express = document.getElementById("sc:heatmaprows").value.split("?")[i];
			
			if(typeof(Storage) !== "undefined") {
				sessionStorage.stemmapername = topop;
				sessionStorage.stemmaperexpress = express;
				sessionStorage.stemmaperlabeltypes = document.getElementById("sc:heatlabeltypes").value;
				sessionStorage.stemmapersupertypes = document.getElementById("sc:heatsupertypes").value;
				window.open ("popup.jsf","_blank","location=1,status=1,scrollbars=1");
			}
			
		}
	}
}

function startuppop() {
	var types = sessionStorage.stemmaperlabeltypes.split("|");
	var expre = sessionStorage.stemmaperexpress.split("|");
	
	var ntypes = [];
	var avexpr = [];
	var nitera = [];
	var nmaxs = [];
	var nmins = [];
	
	var agregator = 0;
	
	var len = types.length;
	
	var tit = document.getElementById("ti");
	tit.innerHTML = sessionStorage.stemmapername+" expression";
	
	
	for (var i = 0; i < len; i++) 
	{
		var found = false;
		var val = parseFloat(expre[i]);
		agregator += val;
		for (var a = 0, lem = ntypes.length; a < lem && !found; a++) 
		{
			if(ntypes[a]==types[i])
			{
				found = true;
				avexpr[a] += val;
				nitera[a] += 1;
				if(val>nmaxs[a])
					nmaxs[a] = val;
				if(val<nmins[a])
					nmins[a] = val;
			}
		}
		if(!found)
		{
			ntypes.push(types[i]);
			avexpr.push(val);
			nitera.push(1);
			nmaxs.push(val);
			nmins.push(val);
		}
	}
	
	agregator = agregator/len;
	
	lem = ntypes.length;
	
	for (var i=0;i<lem;i++) 
	{
		var table = document.getElementById("gdata").getElementsByTagName('tbody')[0];
		
		var num = avexpr[i]/nitera[i];
		
		var values = [];
		
		for (var a=0;a<len;a++) 
		{
			if(ntypes[i]==types[a])
			{
				values.push(parseFloat(expre[a]));
			}
		}
		
		var row = table.insertRow(i);
		var cell1 = row.insertCell(0);
		cell1.innerHTML = ntypes[i];
		var cell2 = row.insertCell(1);
		cell2.innerHTML = median(values);
		var cell3 = row.insertCell(2);
		cell3.innerHTML = num-agregator;
		var cell4 = row.insertCell(3);
		cell4.innerHTML = nmins[i];
		var cell5 = row.insertCell(4);
		cell5.innerHTML = nmaxs[i]+" DDD";
	}
	
	sorttable.makeSortable(document.getElementById("gdata"));
}

function median(values) {
	values.sort( function(a,b) {return a - b;} );
	var half = Math.floor(values.length/2);
	if(values.length % 2)
		return values[half];
	else
		return (values[half-1] + values[half]) / 2.0;
}

function emptyexplist() {
	$("#bod").empty();
}