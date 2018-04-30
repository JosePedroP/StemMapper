function toggledisplay(id)
{
	var sta = document.getElementById(id).style.display;
	
	if(sta=="none") sta = 'block';
	else sta = 'none';
	
	document.getElementById(id).style.display = sta;
}