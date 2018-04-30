var subntypes;
var submedian;
var subpem;
var subnmins;
var subnmaxs;

var supntypes;
var supmedian;
var suppem;
var supnmins;
var supnmaxs;

var name;

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
//				window.open ("popup.jsf","_blank","location=1,status=1,scrollbars=1");
				window.open ("popup.jsf","_blank");
			}
			
		}
	}
}

function startuppop() {
	var tit = document.getElementById("ti");
	name = sessionStorage.stemmapername;
	tit.innerHTML = sessionStorage.stemmapername+" expression";
	
	var supertypes = sessionStorage.stemmapersupertypes.split("|");
	var types = sessionStorage.stemmaperlabeltypes.split("|");
	var expre = sessionStorage.stemmaperexpress.split("|");
	
	subntypes = [];
	submedian = [];
	subpem = [];
	subnmins = [];
	subnmaxs = [];
	
	getdata(types, expre, subntypes, submedian, subpem, subnmins, subnmaxs);
	
	supntypes = [];
	supmedian = [];
	suppem = [];
	supnmins = [];
	supnmaxs = [];
	
	getdata(supertypes, expre, supntypes, supmedian, suppem, supnmins, supnmaxs);
	
	loadsubdata(true);
	
	sorttable.makeSortable(document.getElementById("gdata"));
}

function getdata(types, expre, stypes, smedian, spem, smins, smaxs)
{
	var ntypes = [];
	var avexpr = [];
	var nitera = [];
	var nmaxs = [];
	var nmins = [];
	
	var agregator = 0;
	
	var len = types.length;
	
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
	
	var lem = ntypes.length;
	
	for (var i=0;i<lem;i++) 
	{
		var num = avexpr[i]/nitera[i];
		
		var values = [];
		
		for (var a=0;a<len;a++) 
		{
			if(ntypes[i]==types[a])
			{
				values.push(parseFloat(expre[a]));
			}
		}
		
		stypes.push(ntypes[i]);
		smedian.push(median(values).toFixed(3));
		spem.push((num-agregator).toFixed(3));
		smins.push(nmins[i].toFixed(3));
		smaxs.push(nmaxs[i].toFixed(3));
	}
	
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

function loadsubdata(typ)
{
	if(typ)
	{
		document.getElementById("sc:filename").value = name+" lineage expression";
		document.getElementById("sc:inputval").value = "Lineage\tMedian Expression\tPEM\tMin\tMax\n";
		document.getElementById("gdata").rows[0].cells[0].innerHTML = "Lineage";
		loadsubdataaux(subntypes, submedian, subpem, subnmins, subnmaxs);
	}
		
	else
	{
		document.getElementById("sc:filename").value = name+" tissue expression";
		document.getElementById("sc:inputval").value = "Tissue\tMedian Expression\tPEM\tMin\tMax\n";
		document.getElementById("gdata").rows[0].cells[0].innerHTML = "Tissue";
		loadsubdataaux(supntypes, supmedian, suppem, supnmins, supnmaxs);
	}

}

function loadsubdataaux(sntypes, smedian, spem, smins, smaxs)
{
	var lem = sntypes.length;
	
	for (var i=0;i<lem;i++) 
	{
		var table = document.getElementById("gdata").getElementsByTagName('tbody')[0];
		
		var row = table.insertRow(i);
		var cell1 = row.insertCell(0);
		cell1.innerHTML = sntypes[i];
		var cell2 = row.insertCell(1);
		cell2.innerHTML = smedian[i];
		var cell3 = row.insertCell(2);
		cell3.innerHTML = spem[i];
		var cell4 = row.insertCell(3);
		cell4.innerHTML = smins[i];
		var cell5 = row.insertCell(4);
		cell5.innerHTML = smaxs[i];
		
		document.getElementById("sc:inputval").value += sntypes[i]+"\t"+smedian[i]+"\t"+spem[i]+"\t"+smins[i]+"\t"+smaxs[i]+"\n"
	}
}