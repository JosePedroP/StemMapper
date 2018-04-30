var uid = 0;
var savedcheckedboxesvalues;
var toggleA = false;

function resettoggle()
{
	toggleA = false;
}

function toggleAccordion()
{
	
	var sel = document.getElementById("sc:tab:platselect").selectedIndex;
	
	var num = 0;
	
	if(sel==0) num=14;
	else num=7;
	
	for(var a=0;a<num;a++)
	{
		if(!toggleA) accordionWidget.select(a);
		else accordionWidget.unselect(a);
	}
	
	toggleA = !toggleA;
}




function recolortest()
{
	var update = {text: pointNames, 'marker':{color: pointColors}};
	Plotly.restyle(plotdiv, update);
}

function corrstart(mainentrez, mainname)
{
	document.getElementById('sc:corrMainGeneId').value = mainentrez;
	document.getElementById('sc:corrMainGeneName').value = mainname;
	document.getElementById('sc:corrDatabaseIds').value = document.getElementById('sc:pointDatabaseIds').value;
	
//	alert(document.getElementById('sc:corrMainGeneId').value);
//	alert(document.getElementById('sc:corrDatabaseIds').value);
	
	document.getElementById('sc:corrcalc').click();
}

function dealwithradios(dat)
{
	document.getElementById('sc:tab:searchText').value = dat;
	disalbeIfEmpty();
}

function toggletext(arrids, primus)
{
	
	var istoad = $("#"+primus).is(':checked');
	
	var currentvals = document.getElementById('sc:tab:searchText').value.split(" ");
	
	if(istoad)
	{
		var newdata = [];
		
		for(var a=0;a<arrids.length;a++)
		{
			var doit = true;
			for(var b=0;b<currentvals.length && doit;b++)
			{
				if(arrids[a]==currentvals[b])
				{
					doit = false;
					
				}
			}
			if(doit)
			{
				newdata.push(arrids[a]);
			}
			
		}
		
		if(newdata.length>0)
		{
			var dat = "";
		
			for(var a=0;a<newdata.length;a++)
			{
				if(a>0) dat+=" ";
				dat += newdata[a];
			}
			
			currentvals = document.getElementById('sc:tab:searchText').value;
			
			if(currentvals!="")
			{
				document.getElementById('sc:tab:searchText').value = currentvals+" "+dat;
			}
			else
			{
				document.getElementById('sc:tab:searchText').value = dat;
			}
			
		}
		
	}
	else
	{
		var dat = "";
		var first = true;
		
		for(var a=0;a<currentvals.length;a++)
		{
			var doit = true;
			for(var b=0;b<arrids.length && doit;b++)
			{
				if(currentvals[a]==arrids[b])
				{
					doit = false;
					
				}
			}
			if(doit)
			{
				if(!first)
				{
					dat +=" ";
					dat +=currentvals[a];
				}
				else
				{
					first = false;
					dat =currentvals[a];
				}
			}
			
		}
		
		document.getElementById('sc:tab:searchText').value = dat;

	}
	disalbeIfEmpty();
}

function checkradios()
{
	
	var wasse=-1;
	
	if(document.getElementById("sc:tab:selct:0").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Ly6d CD10 Cd43';
		wasse=0;
	}
	else if(document.getElementById("sc:tab:selct:1").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Ebf1 Gata3 Gfi1 Ikzf1 Myb';
		wasse=1;
	}
	else if(document.getElementById("sc:tab:selct:2").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Cd13 Cd33 Cd38 Icam1 Itgam';
		wasse=2;
	}
	else if(document.getElementById("sc:tab:selct:3").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'CD71 Cd38 Kit Ptprc';
		wasse=3;
	}
	else if(document.getElementById("sc:tab:selct:4").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'CD133 Gata2 Runx1 Tal1';
		wasse=4;
	}
	else if(document.getElementById("sc:tab:selct:5").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Cebpa Gata1 Nfe2';
		wasse=5;
	}
	else if(document.getElementById("sc:tab:selct:6").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Dbx1 Dbx2 Irx3';
		wasse=6;
	}
	else if(document.getElementById("sc:tab:selct:7").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Emx1 Emx2 Foxg1';
		wasse=7;
	}
	else if(document.getElementById("sc:tab:selct:8").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Ncam Gli1 Gfap';
		wasse=8;
	}
	else if(document.getElementById("sc:tab:selct:9").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'NES1 Sox1 Sox3 Sox9';
		wasse=9;
	}
	else if(document.getElementById("sc:tab:selct:10").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Afp Gpc3 H19';
		wasse=10;
	}
	else if(document.getElementById("sc:tab:selct:11").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Cd200 Krt14 Krt15';
		wasse=11;
	}
	else if(document.getElementById("sc:tab:selct:12").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Pax6 Sox1 Sox2';
		wasse=12;
	}
	else if(document.getElementById("sc:tab:selct:13").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Afp Foxa2 Foxa3 Smad2 Sox17';
		wasse=13;
	}
	else if(document.getElementById("sc:tab:selct:14").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Mesp1 Nodal T Tdgf1';
		wasse=14;
	}
	else if(document.getElementById("sc:tab:selct:15").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Eng Nt5e Thy1';
		wasse=15;
	}
	else if(document.getElementById("sc:tab:selct:16").checked)
	{
//		document.getElementById('sc:tab:searchText').value = 'Gata4 Flk1 Mesp1 Mesp2 Nkx2-5';
		wasse=16;
	}
	
	if(wasse>-1)
	{
		var lab = 'label[for="sc:tab:selct:'+wasse+'"]';
		
		var tex = $(lab).text();
		
		if(tex==" B cell progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'Ly6d CD10 Cd43';
		}
		else if(tex==" Common lymphoid progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'Ebf1 Gata3 Gfi1 Ikzf1 Myb';
		}
		else if(tex==" Granulocyte/macrophage progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'Cd13 Cd33 Cd38 Icam1 Itgam';
		}
		else if(tex==" Megakaryocyte/erythroid progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'CD71 Cd38 Kit Ptprc';
		}
		else if(tex==" Haematopoietic stem cells")
		{
			document.getElementById('sc:tab:searchText').value = 'CD133 Gata2 Runx1 Tal1';
		}
		else if(tex==" Common myeloid progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'Cebpa Gata1 Nfe2';
		}
		else if(tex==" Neural progenitor")
		{
			document.getElementById('sc:tab:searchText').value = 'Dbx1 Dbx2 Irx3';
		}
		else if(tex==" Brain dorsal subventricular")
		{
			document.getElementById('sc:tab:searchText').value = 'Emx1 Emx2 Foxg1';
		}
		else if(tex==" Brain lateral subventricular")
		{
			document.getElementById('sc:tab:searchText').value = 'Ncam Gli1 Gfap';
		}
		else if(tex==" Neural stem cells")
		{
			document.getElementById('sc:tab:searchText').value = 'NES1 Sox1 Sox3 Sox9';
		}
		else if(tex==" Hepatic stem cells")
		{
			document.getElementById('sc:tab:searchText').value = 'Afp Gpc3 H19';
		}
		else if(tex==" Hair Follicle SC")
		{
			document.getElementById('sc:tab:searchText').value = 'Cd200 Krt14 Krt15';
		}
		else if(tex==" Ectoderm")
		{
			document.getElementById('sc:tab:searchText').value = 'Pax6 Sox1 Sox2';
		}
		else if(tex==" Endoderm")
		{
			document.getElementById('sc:tab:searchText').value = 'Afp Foxa2 Foxa3 Smad2 Sox17';
		}
		else if(tex==" Mesoderm")
		{
			document.getElementById('sc:tab:searchText').value = 'Mesp1 Nodal T Tdgf1';
		}
		else if(tex==" Mesenchymal stem cells")
		{
			document.getElementById('sc:tab:searchText').value = 'Eng Nt5e Thy1';
		}
		else if(tex==" Cardiac progenitors")
		{
			document.getElementById('sc:tab:searchText').value = 'Gata4 Flk1 Mesp1 Mesp2 Nkx2-5';
		}
		else
		{
			document.getElementById('sc:tab:searchText').value = '';
		}
		
	}
	
}

function insertNewMarker()
{
	var data = document.getElementById("sc:tab:newEntry_input").value;
	
	document.getElementById('sc:tab:searchText').value += " "+data;
}

function checkWhichOnesAreCheckedMaster(primus, secundos)
{
	$("."+secundos).attr('checked', $("#"+primus).is(':checked'));
	checkWhichOnesAreChecked();
}

function unCheckPrime(primus)
{
	$("#"+primus).attr('checked', false);
}

function checkWhichOnesAreChecked()
{
	document.getElementById("sc:pc1").value = "";
	
	var val="";
	$(".setType").each(function()
	{
		if($(this).is(':checked'))
		{
			if(val=="") val = $(this).val().split("!")[0];
			else val += "|"+$(this).val().split("!")[0];
		}
	});
	
//	alert("vala "+val);
	
	document.getElementById("sc:useThisDataTypes").value=val;
}

function setMarkersAndUpload()
{
	bunkerData();
	checkWhichOnesAreChecked();
	document.getElementById('sc:upload_input').click();
}

function setMarkersAndProcess()
{
	bunkerData();
	checkWhichOnesAreChecked();
	document.getElementById('sc:inputless').click();
}

function bunkerData()
{
	document.getElementById("sc:pc1").value= "";
	
	savedcheckedboxes = [];
	savedcheckedboxesvalues = [];
	
	$(".checktosave").each(function()
	{
		savedcheckedboxes.push($(this).attr('id'));
		savedcheckedboxesvalues.push($(this).is(':checked'));
		
	});
}

function restorFromBunker()
{
	for(var i=0;i<savedcheckedboxes.length;i++)
	{
		$("#"+savedcheckedboxes[i]).attr('checked', savedcheckedboxesvalues[i]);
	}
	
}

// Dendogram handeling

function dendoGo()
{
//	//alert("1 Going to get it");
	
	var heighMultiplier = 5;

	var names = document.getElementById("sc:dendogramelables").value.split("|");
	var input = document.getElementById("sc:dendograme").value;
//	//alert("Didn't get it");
	
	var positions = new Array(names.length);
	
	for(var i=0;i<names.length;i++)
	{
		positions[i] = 5 + 10*i;
	}
//	//alert("Uuu");
	
	var layoutDIY = {
		autosize: false, 
		bargap: 0, 
		//  height: 1000, 
		hovermode: "closest", 
		margin: {
			r: 20, 
			t: 50, 
			autoexpand: true, 
			l: 20
		}, 
		showlegend: false, 
		//  title: "Dendrogram Heatmap", 
		width: 1400, 
		xaxis: {
			autorange: true, 
			domain: [0.15, 0.9], 
			mirror: "allticks", 
			range: [0, 960], 
			rangemode: "tozero", 
			showgrid: false, 
			showline: false, 
			showticklabels: true, 
			tickmode: "array", 
			ticks: "", 
			ticktext: names, 
			tickvals: positions, 
			title: "", 
			type: "linear", 
			zeroline: false
		}
	};
//	//alert("got here easy 2");
	
	var dataDIY = findTheTracers(input, names, positions, heighMultiplier, 5);
//	//alert("got here easy 3");
	
	plotdiv = document.getElementById("dendodiv");
//	//alert("got here easy 4");
	
	Plotly.newPlot(plotdiv, dataDIY, layoutDIY);
//	//alert("done easy");
}

function prepaertodownladlheat(heattouse)
{
	if(heattouse==1)
	{
		document.getElementById("sc:inputval").value = 
			document.getElementById("sc:heatmaprows").value
			+"*"+document.getElementById("sc:heatmaprowsnames").value+"*"+
			document.getElementById("sc:dendogramelables").value;
	}
	else
	{
		document.getElementById("sc:inputval").value = 
			document.getElementById("sc:heatmaprowsq").value
			+"*"+document.getElementById("sc:heatmaprowsnamesq").value+"*"+
			document.getElementById("sc:dendogramelablesq").value;
	}
}

function heatmapGo(heattouse)
{
	var heighMultiplier = 5;


	var names;
	var input;
	
	if(heattouse==1)
	{
		names = document.getElementById("sc:dendogramelables").value.split("|");
		input = document.getElementById("sc:dendograme").value;
	}
	else
	{
		names = document.getElementById("sc:dendogramelablesq").value.split("|");
		input = document.getElementById("sc:dendogrameq").value;
	}
		
	
	var positions = new Array(names.length);
	
	for(var i=0;i<names.length;i++)
	{
		positions[i] = 5 + 10*i;
	}
	
	var rnames;
	
	if(heattouse==1) rnames = document.getElementById("sc:heatmaprowsnames").value.split("|");
	else rnames = document.getElementById("sc:heatmaprowsnamesq").value.split("|");
	
	var rpositions = new Array(rnames.length);
	
	for(var i=0;i<rnames.length;i++)
	{
		rpositions[i] = 5 + 10*i;
	}
	
	var rangey = 0.5;
	var heighty = 500;
	
	if(rnames.length>10 && rnames.length<20)
	{
		rangey = 0.6;
		heighty = 700;
	}
	else if(rnames.length>=20)
	{
		rangey = 0.78;
		heighty = 1000;
	}
	
	var layoutDIY = {
		autosize: false, 
		bargap: 0.0001, 
//		height: 1000, 
		height: heighty, 
		hovermode: "closest", 
		margin: {
			r: 20, 
			t: 50, 
			autoexpand: true, 
			l: 20
		}, 
		showlegend: false, 
//		title: "Dendrogram Heatmap", 
//		width: 1400, 
		width: 1400, 
		xaxis: {
			autorange: true, 
			domain: [0.15, 0.9], 
			mirror: "allticks", 
			range: [0, 960], 
			rangemode: "tozero", 
			showgrid: false, 
			showline: false, 
			tickmode: "array", 
			ticks: "", 
			showticklabels: false, 
			ticktext: names, 
			tickvals: positions, 
			title: "Samples", 
			type: "linear", 
			zeroline: false
		},
		yaxis: {
			autorange: false, 
//			domain: [0, 0.78], 
			domain: [0, rangey], 
			mirror: "allticks", 
//			range: [0, 457.894736842], 
			range: [0, 10*rnames.length], 
			rangemode: "tozero", 
			showgrid: false, 
			showline: false, 
			showticklabels: true, 
			tickmode: "array", 
			ticks: "", 
			ticktext: rnames, //tick names
			tickvals: rpositions,
			title: "Gene names (Entrez id)", 
			type: "linear", 
			zeroline: false
		}, 
		yaxis2: {
			autorange: true,
//			domain: [0.78, 1], 
			domain: [rangey, 1], 
			overlaying: false, 
			range: [-23014.5471172, 437276.395226], 
			showgrid: false, 
			showline: false, 
			tickmode: "array", 
			ticks: "", 
			title: "", 
			type: "linear", 
			showticklabels: false, 
			zeroline: false
		}
	};
	
	
	var dataDIY = findTheTracers(input, names, positions, heighMultiplier, 0);
	
	var heattemp;
	if(heattouse==1) heattemp = document.getElementById("sc:heatmaprows").value.split("?");
	else heattemp = document.getElementById("sc:heatmaprowsq").value.split("?");
	
	var heatvalues = new Array(heattemp.length);
	
	for(var i=0;i<heattemp.length;i++)
	{
		var theat = heattemp[i].split("|");
		heatvalues[i] = new Array(theat.length);
		
		for(var c=0;c<theat.length;c++)
		{
			heatvalues[i][c] = parseFloat(theat[c]);
		}
	}
	
	//this is the heatmap
	
	
	var traceHeatmap = {
		x: positions, 
		y: rpositions, 
		z: heatvalues,
		 autocolorscale: false, 
		 colorbar: {
			x: 0.96, 
			y: 0.9, 
			bgcolor: "rgba(0, 0, 0, 0)", 
			bordercolor: "#444", 
		    borderwidth: 0, 
		    exponentformat: "B", 
		    len: 1, 
		    lenmode: "fraction", 
		    nticks: 0, 
		    outlinecolor: "#444", 
		    outlinewidth: 1, 
		    showexponent: "all", 
		    showticklabels: true, 
		    thickness: 20, 
		    thicknessmode: "pixels", 
		    tickangle: "auto", 
		    tickfont: {
		    	color: "#444", 
		    	family: "\"Open sans\", verdana, arial, sans-serif", 
		    	size: 12
		    }, 
		    ticks: "", 
		    title: "Expression", 
		    titlefont: {
		      color: "#444", 
		      family: "\"Open sans\", verdana, arial, sans-serif", 
		      size: 12
		    }, 
		    titleside: "top", 
		    xanchor: "right", 
		    xpad: 0, 
		    yanchor: "top"
		 },
		 colorscale: [
		    ['0.0', 'rgb(49,54,149)'],
		    ['0.111111111111', 'rgb(69,117,180)'],
		    ['0.222222222222', 'rgb(116,173,209)'],
		    ['0.333333333333', 'rgb(171,217,233)'],
		    ['0.444444444444', 'rgb(224,243,248)'],
		    ['0.555555555556', 'rgb(254,224,144)'],
		    ['0.666666666667', 'rgb(253,174,97)'],
		    ['0.777777777778', 'rgb(244,109,67)'],
		    ['0.888888888889', 'rgb(215,48,39)'],
		    ['1.0', 'rgb(165,0,38)']
		 ],
		 name: "trace138_y", 
		 opacity: 1, 
		 reversescale: false, 
		 showscale: true, 
		 type: "heatmap", 
		 uid: "3f3555", 
		 zauto: true, 
		 zmax: 155160.122245, 
		 zmin: 0, 
		 zsmooth: false
	};
	
	dataDIY = dataDIY.concat([traceHeatmap]);
	
	var heatlabelcolors;
	if(heattouse==1) heatlabelcolors = document.getElementById("sc:heatlabelcolors").value.split("|");
	else heatlabelcolors = document.getElementById("sc:heatlabelcolorsq").value.split("|");
	
	var heatmarkersx = new Array(heatlabelcolors.length);
	var heatmarkersy = new Array(heatlabelcolors.length);
	
	var stax = 5;
	
//	//alert("heatlabelcolors.length: "+heatlabelcolors.length);
	
	var linkx = [];
	var linky = [];
	var linkname = [];
	var linkextra = [];
	
	
	for(var i=0;i<heatlabelcolors.length;i++)
	{
		heatmarkersx[i] = stax;
		heatmarkersy[i] = -5;
		stax = stax + 10;
//		//alert(heatlabelcolors[i]+" : "+heatmarkersx[i]+","+heatmarkersy[i]);
		
		linkx[i] = 5+10*i;
		linky[i] = 0;
		linkname[i] = names[i];
		linkextra[i] = names[i];
	}
	
	var dataBar =[
		{
			x: heatmarkersx,
			y: heatmarkersy,
			type: "bar",
			yaxis: "y2",
			marker: {
				color: heatlabelcolors
			},
			hoverinfo: "none"
		}
	];
	
	dataDIY = dataDIY.concat(dataBar);
//	dataDIY = dataBar;
	
	var rtypes;
	if(heattouse==1) rtypes = document.getElementById("sc:heatlabeltypes").value.split("|");
	else rtypes = document.getElementById("sc:heatlabeltypesq").value.split("|");
	
	var newlables = new Array(names.length);
	
	for(var i=0;i<names.length;i++)
	{
		newlables[i] = names[i]+" "+rtypes[i];
	}
	
	var linkpoints =[
		{
			x: linkx,
			y: linky,
			type: "scatter", 
			mode: "markers",
			yaxis: "y2",
			ticktext: linkname, 
			text: newlables,
			marker: {
				size: 1,
				color: heatlabelcolors
			},
			extra: linkextra,
			hoverinfo: "text"
		}
	];
	
	dataDIY = dataDIY.concat(linkpoints);
	
	var plotdiv;
	if(heattouse==1) plotdiv = document.getElementById('heatmapdiv');
	else plotdiv = document.getElementById('heatmapdivq');
	
	Plotly.newPlot(plotdiv, dataDIY, layoutDIY, {modeBarButtonsToRemove: ['sendDataToCloud']});
	
	
	var userdid = document.getElementById("sc:userdefinedid").value;
	
	plotdiv.on('plotly_click', function(data){
		
		var pn =  data.points[0].pointNumber;
		
		if(data.points[0].data.yaxis == "y2" && data.points[0].data.mode == "markers")
		{
			if(userdid!=data.points[0].data.extra[pn])
				window.open ("sampledata.jsf?p="+data.points[0].data.extra[pn], "_blank");
//				window.open ("sampledata.jsf?p="+data.points[0].data.extra[pn], "_blank","location=1,status=1,scrollbars=1");
		}
		else if(data.points[0].data.type == "heatmap" )
		{
			jumptopopup(rnames[pn[0]])
		}
	});
}

function findTheTracers(input, names, positions, heighMultiplier, heighAddition)
{
	
	var res = null;
	var tar = splitPair(input);
	if(isLeaf(input))
	{
	}
	else if(isItASimpleBranch(input))
	{
		
		var x1 = nameToXValues(tar[0], names, positions);
		var x2 = nameToXValues(tar[1], names, positions);
		uid++;
		
		var traceDIY = {
			x: [x1, x1, x2, x2], 
			y: [0+heighAddition, heighMultiplier, heighMultiplier, 0+heighAddition], 
			line: {width: 1}, 
			marker: {color: "rgb(255,133,27)"}, 
//			mode: "traceDIY"+uid, 
			mode: "lines",
			hoverinfo: "none",
			name: "traceDIY"+uid, 
			type: "scatter", 
			uid: "traceDIY"+uid, 
			yaxis: "y2"
		};
		
		res = [traceDIY];
	}
	else
	{
		var left = findTheTracers(tar[0], names, positions, heighMultiplier, heighAddition);
		var right = findTheTracers(tar[1], names, positions, heighMultiplier, heighAddition);
		
		var newHeight = heighMultiplier;
		var x1 = 5;
		var x2 = 15;
		var y1 = 0+heighAddition;
		var y2 = 0+heighAddition;
		
		if(left!=null && right!=null)
		{
			var childl = left[0];
			var childr = right[0];
			
			if(childl.y[1]>childr.y[1]) newHeight+=childl.y[1];
			else newHeight+=childr.y[1];
			
			x1 = (childl.x[1]+childl.x[2])/2;
			x2 = (childr.x[1]+childr.x[2])/2;
			
			y1 = childl.y[1];
			y2 = childr.y[1];
		}
		else if(left==null)
		{
			var childr = right[0];
			
			newHeight+=childr.y[1];
			
			x1 = nameToXValues(tar[0], names, positions);
			x2 = (childr.x[1]+childr.x[2])/2;
			
			y1 = 0+heighAddition;
			y2 = childr.y[1];
		}
		else if(right==null)
		{
			var childl = left[0];
			
			newHeight+=childl.y[1];
			
			x1 = (childl.x[1]+childl.x[2])/2;
			x2 = nameToXValues(tar[1], names, positions);
			
			y1 = childl.y[1];
			y2 = 0+heighAddition;
		}
		uid++;
		var traceDIY = {
			x: [x1, x1, x2, x2], 
			y: [y1+heighAddition, newHeight, newHeight, y2], 
			line: {width: 1}, 
			marker: {color: "rgb(255,133,27)"}, 
//			mode: "traceDIY"+uid, 
			mode: "lines", 
			hoverinfo: "none",
			name: "traceDIY"+uid, 
			type: "scatter", 
			uid: "traceDIY"+uid, 
			yaxis: "y2"
		};
		
		res = [traceDIY];
		
		if(left!=null) res = res.concat(left);
		if(right!=null) res = res.concat(right);
		
		
	}
	
	return res;
}

function splitPair(pair)
{
//	//alert("SPLIT IT: "+pair);
	var first = "";
	var second = "";
	var parentesisCounter = 0;
	var inSecond = false;
	
	for (var i = 0, len = pair.length; i < len; i++) 
	{
		if(!inSecond)
		{
			if(pair[i]=='|' && parentesisCounter==0)
			{
				inSecond = true;
			}
			else
			{
				if(pair[i]=='(') parentesisCounter++;
				else if(pair[i]==')') parentesisCounter--;
				
				first += pair[i];
			}
		}
		else second += pair[i];
	}
	
	var ret;
	
	if(second=="") ret = [first];
	else ret = [trimSplit(first), trimSplit(second)];
	
	return ret;
}

function trimSplit(data)
{
	var ret = "";
	
	if(data[0]=='(' && data[data.length-1]==')')
	{
		for (var i = 1, len = (data.length-1); i < len; i++) 
		{
			ret += data[i];
		}
	}
	else ret = data;
	
	return ret;
}

function isLeaf(data)
{
	var ret = false;
	
	if(data.indexOf('|') === -1)
	{
		ret = true;
	}
	
	return ret;
}

function isItASimpleBranch(data)
{
	var ret = true;
	var c = 0;
	
	for (var i = 0, len = data.length; i < len && ret; i++) 
	{
		if(data[i]=='|' && c>0) ret = false;
		else if(data[i]=='|') c++;
	}
	
	return ret;
}

function nameToXValues(name, names, positions)
{
//	//alert(name+" "+names.length+" "+positions.length);
	var stop = false;
	var ret = 0;
	
	for (var i = 0, len = names.length; i < len && !stop; i++) 
	{
		if(names[i] == name)
		{
			ret = positions[i];
			stop = true;
		}
	}
	
	return ret;
}

function drawPlot()
{
	plotdiv = document.getElementById('pcaplotdiv');
	
	var pc1 = document.getElementById("sc:pc1").value.split("|");
	var pc2 = document.getElementById("sc:pc2").value.split("|");
	var pointNames = document.getElementById("sc:pointNames").value.split("|");
	var extra = document.getElementById("sc:pointTypesExtra").value.split("|");
	var dbids = document.getElementById("sc:pointDatabaseIds").value.split("|");
	
	for(var i=0;i < pointNames.length;i++) pointNames[i] = pointNames[i]+" "+extra[i];
	
	var pointColors = document.getElementById("sc:pointTypes").value.split("|");
	
//	//alert(pointColors);
	
	var trace1 = {
	  x: pc1,
	  y: pc2,
	  text: pointNames,
	  mode: 'markers',
	  type: 'scatter',
	  marker: {
		  color: pointColors  
	  },
	  extra: dbids
	};

	var data = [trace1];
	
	var layout = {
//		title: 'PCA',
		hovermode: 'closest',
		xaxis: {
			title: "PC1 ("+document.getElementById("sc:pvpc1").value+"%)"
		},
		yaxis: {
			title: "PC2 ("+document.getElementById("sc:pvpc2").value+"%)"
		}
	};
	
	Plotly.newPlot(plotdiv, data, layout, {modeBarButtonsToRemove: ['sendDataToCloud']});
	//, {modeBarButtonsToRemove: ['sendDataToCloud']}
	
	plotdiv.on('plotly_click', function(data){
		var pn='',
		tn='',
		colors=[];
		for(var i=0; i < data.points.length; i++){
			pn = data.points[i].pointNumber;
		};
		window.open ("sampledata.jsf?p="+data.points[0].data.extra[pn], "_blank","location=1,status=1,scrollbars=1");
//		Plotly.restyle('pcaplotdiv', update, [tn]);
	});
}


function switchcolor(palet)
{
	var plotdiv = document.getElementById('pcaplotdiv');
	var pointColors;
	var pointColorsLe;
	var extra;
	$("#letab tbody tr").remove();
    var table = document.getElementById("letab");
	var dbids = document.getElementById("sc:pointDatabaseIds").value.split("|");

	if(palet==1)
	{
		pointColors = document.getElementById("sc:pointColors").value.split("|");
	}
	else if(palet==2)
	{
		pointColors = document.getElementById("sc:pointTypes").value.split("|");
		pointColorsLe = document.getElementById("sc:pointTypesLegend").value.split("|");
		extra = document.getElementById("sc:pointTypesExtra").value.split("|");
	}
	else if(palet==3)
	{
		pointColors = document.getElementById("sc:pointCondition").value.split("|");
		pointColorsLe = document.getElementById("sc:pointConditionLegend").value.split("|");
		extra = document.getElementById("sc:pointConditionExtra").value.split("|");
	}
	else if(palet==4)
	{
		pointColors = document.getElementById("sc:pointWildtype").value.split("|");
		pointColorsLe = document.getElementById("sc:pointWildtypeLegend").value.split("|");
		extra = document.getElementById("sc:pointWildtypeExtra").value.split("|");
	}
	else if(palet==5)
	{
		pointColors = document.getElementById("sc:pointTypesAlt").value.split("|");
		pointColorsLe = document.getElementById("sc:pointTypesLegendAlt").value.split("|");
		extra = document.getElementById("sc:pointTypesAltExtra").value.split("|");
	}
	else if(palet==6)
	{
		pointColors = document.getElementById("sc:pointSuper").value.split("|");
		pointColorsLe = document.getElementById("sc:pointSuperLegend").value.split("|");
		extra = document.getElementById("sc:pointSuperExtra").value.split("|");
	}
	else if(palet==7)
	{
		pointColors = document.getElementById("sc:pointPureStem").value.split("|");
		pointColorsLe = document.getElementById("sc:pointPureStemLegend").value.split("|");
		extra = document.getElementById("sc:pointPureStemExtra").value.split("|");
	}
	else if(palet==8)
	{
		pointColors = document.getElementById("sc:pointClass").value.split("|");
		pointColorsLe = document.getElementById("sc:pointClassLegend").value.split("|");
		extra = document.getElementById("sc:pointClassExtra").value.split("|");
	}
	else if(palet==9)
	{
		pointColors = document.getElementById("sc:pointSM").value.split("|");
		pointColorsLe = document.getElementById("sc:pointSMLegend").value.split("|");
		extra = document.getElementById("sc:pointSMExtra").value.split("|");
	}
	
	if(palet>2 && palet!=5)
	{
		for(var i=0; i < pointColorsLe.length; i++){
			var row = table.insertRow(i);
			
			var pc = pointColorsLe[i].split("*");

		    var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
		    cell1.innerHTML = pc[0];
		    cell2.innerHTML = "1";
		    cell2.style.color = pc[1];
		    cell2.style.backgroundColor = pc[1];
		}
	}
	else if(palet==2 || palet==5)
	{
		for(var i=0; i < pointColorsLe.length; i++){
			var row = table.insertRow(i);
			
			var pc = pointColorsLe[i].split("*");

		    var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			
			if(pc[1]=="nill")
			{
			    cell1.innerHTML = "<b>"+pc[0]+"</b>";
			    cell2.innerHTML = "";
				
			}
			else
			{
			    cell1.innerHTML = "&nbsp;&nbsp;&nbsp;"+pc[0];
			    cell2.innerHTML = "1";
			    cell2.style.color = pc[1];
			    cell2.style.backgroundColor = pc[1];
			}
			
		    
		}
	}
	
	var pointNames = document.getElementById("sc:pointNames").value.split("|"); 
	
	
	var pc1 = document.getElementById("sc:pc1").value.split("|");
	var pc2 = document.getElementById("sc:pc2").value.split("|");
	var pointNames = document.getElementById("sc:pointNames").value.split("|");
	
	for(var i=0;i < pointNames.length;i++) pointNames[i] = pointNames[i]+" "+extra[i];
	
	var trace1 = {
	  x: pc1,
	  y: pc2,
	  text: pointNames,
	  mode: 'markers',
	  type: 'scatter',
	  marker: {
		  color: pointColors  
	  },
	  extra: dbids
	};

	var data = [trace1];

	
	var layout = {
//		title: 'PCA',
		hovermode: 'closest',
		xaxis: {
			title: "PC1 ("+document.getElementById("sc:pvpc1").value+"%)"
		},
		yaxis: {
			title: "PC2 ("+document.getElementById("sc:pvpc2").value+"%)"
		}
	};
	
	Plotly.newPlot(plotdiv, data, layout, {modeBarButtonsToRemove: ['sendDataToCloud']});
	
	
	plotdiv.on('plotly_click', function(data){
		
		var pn='',
		tn='',
		colors=[];
		for(var i=0; i < data.points.length; i++){
			pn = data.points[i].pointNumber;
		};
		
		if(data.points[0].data.extra[pn]!=null) window.open ("sampledata.jsf?p="+data.points[0].data.extra[pn], "_blank","location=1,status=1,scrollbars=1");
//		Plotly.restyle('pcaplotdiv', update, [tn]);
	});
	
	
}

function goingtosortit()
{
	sorttable.makeSortable(document.getElementById("resultsearch"));
}

function aftermath()
{
	hideload('');
	
	if(document.getElementById("sc:pc1").value!="")
	{
		drawPlot();
		heatmapGo(1);
		heatmapGo(2);
		
		tabPanelWidget.select(2);		
	}
	
	enableInputs();
	
	restorFromBunker();
}

function disalbeIfEmpty()
{
	var checks = $(".checktosave:checked");

//	alert("Kemor");
//	var labelValues = $('.countit:checked').map(function() {
//	    return [ this.value ];
//	}).get();
//	alert("Vulkar "+labelValues);
	
	if(document.getElementById('sc:tab:searchText').value=="" || checks.length<1) $(".shutdownit").attr("disabled", true);
	else
	{

//		var checks3 = $(".countit:checked");
//		
//		var tot = checks3.length;
		
		var labelValues = $('.countit:checked').map(function() {
		    return [ this.value ];
		}).get();
		
		
		var tot = 0;
		
		for(var a=0;a<labelValues.length;a++)
		{
			var tx = labelValues[a].split("!")[1];
			tot += parseInt(tx); 
		}
		
//		alert("tot "+tot);
//		
//		var texts = checks.next().text().split("(");
//		var texts = checks.next().text();
//		
//		alert("Tata "+checks);
//		
		
//		for(var a=0;a<texts.length;a++)
//		{
//			alert("Tata "+texts[a]);
//		}
		
		
//		var tot = 0;
//		
//		for(var a=1;a<texts.length;a++)
//		{
//			var tx = texts[a].split(")")[0];
//			tot += parseInt(tx); 
//		}
////		alert("tot "+tot);
//		
//		
		if(tot>1) $("input").removeAttr('disabled');
		else $(".shutdownit").attr("disabled", true);
		
	}
}

function showload(ex)
{
//	$("input").attr('disabled','disabled');
	$(".shutdownit").attr("disabled", true);
	$("#loading-image"+ex).show();
}

function hideload(ex)
{
	$("input").removeAttr('disabled');
	$("#loading-image"+ex).hide();
}

function disableInputs()
{
	$("input").attr('disabled',true);
}

function enableInputs()
{
	$("input").removeAttr('disabled');
}


function checkWhichOnesAreCheckedSuperType()
{
	var val="";
	$(".supertype").each(function()
	{
		$(".esub"+$(this).val()).each(function()
		{
			if($(this).is(':checked'))
			{
				if(val=="") val = $(this).val();
				else val += "|"+$(this).val();
			}
		});
		
	});
	
//	alert("vla: "+val);
	
	document.getElementById("sc:useThisDataTypes").value=val;
}

function example()
{
	document.getElementById('sc:tab:searchText').value = 'Nanog Sox2 Pou5f1';
 	$(":checkbox").attr('checked', false);
 	$(":radio").attr('checked', false);
 	$("#master6").attr('checked', true);
 	$(".sub6").attr('checked', true);
 	$("input").removeAttr('disabled');
}