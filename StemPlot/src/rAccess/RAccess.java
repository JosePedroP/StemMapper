package rAccess;

import java.util.Arrays;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import beans.StartUpBean;
import databaseAccess.SampleObject;
import dataconverters.Dendograme;
import dataconverters.DendogrameConverter;

public class RAccess {
	
	//Be carefull with this one - use the first only in nero
	protected static String pathlibline = ".libPaths( \"/home/jose/stemPlotFiles/Rlibs\" );\n";
//	protected static String pathlibline = "";
	
	public static boolean launchRserve(String cmd) { 
		return launchRserve(cmd, "--no-save --slave","--no-save --slave",false);
	}
	
	public static boolean launchRserve(String cmd, String rargs, String rsrvargs, boolean debug) {
		
		try {
			Process p;
			boolean isWindows = false;
			String osname = System.getProperty("os.name");
			if (osname != null && osname.length() >= 7 && osname.substring(0,7).equals("Windows")) {
				isWindows = true; /* Windows startup */
				p = Runtime.getRuntime().exec("\""+cmd+"\" -e \"library(Rserve);Rserve("+(debug?"TRUE":"FALSE")+",args='"+rsrvargs+"')\" "+rargs);
			} else /* unix startup */
				p = Runtime.getRuntime().exec(new String[] {
					"/bin/sh", "-c",
					"echo 'library(Rserve);Rserve("+(debug?"TRUE":"FALSE")+",args=\""+rsrvargs+"\")'|"+cmd+" "+rargs
				});
			if (!isWindows)
				p.waitFor();
		} catch (Exception x) {
			return false;
		}
		int attempts = 5; /* try up to 5 times before giving up. We can be conservative here, because at this point the process execution itself was successful and the start up is usually asynchronous */
		while (attempts > 0) {
			try {
				RConnection c = new RConnection();
				c.close();
				return true;
			} catch (Exception e2) {
			}
			/* a safety sleep just in case the start up is delayed or asynchronous */
			try { Thread.sleep(500); } catch (InterruptedException ix) { };
			attempts--;
		}
		return false;
	}
	
	
	public static boolean checkLocalRserve(String rInstallPath) {
		if (isRserveRunning()) return true;
		String osname = System.getProperty("os.name");
		String installPath = rInstallPath;
		if (osname != null && osname.length() >= 7 && osname.substring(0,7).equals("Windows")) {
			
			if (installPath == null) {
				return false;
			}
			return launchRserve(installPath+"/bin/Rscript.exe");
		}
		else{
			return launchRserve(installPath+"/bin/Rscript");
		}
	}
	
	
	public static boolean isRserveRunning() {
		
		try {
			RConnection c = new RConnection();
			c.close();
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return false;
	}
	
	public static double[][] functionToTestDataTrasnfer() throws Exception {
		
		String data = "setwd(\"/home/jose/phb/diffIdTest/heatmapTest/ProcessedData\");";
		data += "allValues <- data.matrix(read.table(\"combinedData.csv\", sep=\"\\t\", row.names=1, header=T));";
		data += "markers <- c(\"58235\",\"52906\",\"67865\",\"74884\",\"100415901\",\"170788\",\"23834\",\"71532\",\"18023\",\"16622\",\"70120\",\"268729\",\"68644\",\"217653\",\"20317\",\"243043\",\"110606\",\"211936\",\"192119\",\"12212\",\"65961\",\"17695\",\"97890\",\"73430\",\"68611\",\"269473\",\"382089\",\"72580\",\"74195\",\"14784\",\"66464\",\"29870\",\"64406\",\"14149\",\"97810\",\"75328\",\"20191\",\"71121\",\"66790\",\"320329\",\"50917\",\"67967\",\"18475\",\"241447\",\"12562\",\"56068\",\"19296\",\"60527\",\"216549\",\"233726\",\"56705\",\"75616\",\"74452\",\"100504527\",\"56378\",\"74134\",\"74762\",\"52458\",\"228368\",\"74641\",\"225923\",\"230661\",\"74628\",\"68473\",\"99811\",\"21379\",\"12455\",\"28081\",\"70382\",\"109212\",\"234384\",\"21771\",\"381716\",\"97787\",\"68011\",\"102693\",\"226419\",\"622175\",\"69923\",\"13510\",\"21849\",\"12950\",\"98108\",\"11944\",\"100504504\",\"402737\",\"73438\",\"232947\",\"75477\",\"18125\",\"100503546\",\"106794\",\"29863\",\"72053\",\"76998\",\"97008\",\"20962\",\"66905\",\"70122\",\"243300\",\"72361\",\"76968\",\"19821\",\"140629\",\"53424\",\"67005\",\"18609\",\"101685\",\"78279\",\"109689\",\"12902\",\"76835\",\"70609\",\"69857\",\"23880\",\"13191\",\"30838\",\"77250\",\"16969\",\"67102\",\"70691\",\"97129\",\"213575\",\"19668\",\"233058\",\"70021\",\"76710\",\"76688\",\"27376\",\"94279\",\"12371\",\"70872\",\"16542\",\"56184\",\"380686\",\"13650\",\"209737\",\"14405\",\"76626\",\"71027\",\"75510\",\"67838\",\"21951\",\"22341\",\"433700\",\"16163\",\"69638\",\"23948\",\"22346\",\"77697\",\"72107\",\"102685\",\"19725\",\"72017\",\"113863\",\"67330\",\"100502619\",\"102634692\",\"74744\",\"26903\",\"14629\",\"329628\",\"66880\",\"54519\",\"234362\",\"20729\",\"100604\",\"20348\",\"664883\",\"96953\",\"22688\",\"18716\",\"71251\",\"13138\",\"224139\",\"227099\",\"102626\",\"13685\",\"67045\",\"332397\",\"13542\",\"235323\",\"217331\",\"211986\",\"71046\",\"403205\",\"552913\",\"14866\",\"100040529\",\"320211\",\"319622\",\"17962\",\"403348\",\"228061\",\"171167\",\"140780\",\"16196\",\"24059\",\"66596\",\"208890\",\"15959\",\"13690\",\"12144\",\"268534\",\"66113\",\"69234\",\"20383\",\"320244\",\"102209\",\"67199\",\"65254\",\"20496\",\"76893\",\"53356\",\"209012\",\"245865\",\"170439\",\"226594\",\"11872\",\"70381\",\"52480\",\"20355\",\"12488\",\"56222\",\"100038680\",\"75308\",\"12686\",\"70350\",\"75725\",\"215008\",\"217847\",\"83554\",\"100041694\",\"105518\",\"218581\",\"17986\",\"68054\",\"72149\",\"12449\",\"216456\",\"211007\",\"100215\",\"17451\",\"73186\",\"68002\",\"12549\",\"330427\",\"12877\",\"216169\",\"320702\",\"114606\",\"234967\",\"69677\",\"209200\",\"74499\",\"269514\",\"19128\",\"12477\",\"13110\",\"52490\",\"51953\",\"320472\",\"71777\",\"109245\",\"67578\",\"242083\",\"100039495\",\"67905\",\"14789\",\"105592\",\"69350\",\"77132\",\"246278\",\"320581\",\"73062\",\"231532\",\"228875\",\"228880\",\"66244\",\"71575\",\"235533\",\"53312\",\"226541\",\"433619\",\"327768\",\"69136\",\"26941\",\"50927\",\"18671\",\"54122\",\"234964\",\"105171\",\"59004\",\"243931\",\"244091\",\"329387\",\"20964\",\"14114\",\"18484\",\"18416\",\"15207\",\"27277\",\"19053\",\"14407\",\"320158\",\"67425\",\"18012\",\"106894\",\"235106\",\"277468\",\"20429\",\"225280\",\"380794\",\"213582\",\"14420\",\"85031\",\"226896\");";

		data += "tab <- allValues;";
		data += "tab<-subset(tab, rownames(tab) %in% markers);";
		data += "tab<-t(tab);";
		data += "log.ir <- log(tab);";
		data += "ir.pca <- prcomp(log.ir, center = TRUE, scale. = TRUE);"; 
		
		data += "scores = as.data.frame(ir.pca$x);";
		data += "mdat <- matrix(c(scores$PC1, scores$PC2), nrow = 2, ncol = length(scores$PC1));";
		data += "mdat;";
		
		RConnection c = new RConnection();
		
		REXP x = c.eval(data);

		return x.asDoubleMatrix();
		
	}
	
	public static double[][] functionToTestDataTrasnferv2() throws Exception {
		
		String data = "setwd(\"/home/jose/phb/diffIdTest/heatmapTest/ProcessedData\");";
		data += "allValues <- data.matrix(read.table(\"combinedData.csv\", sep=\"\\t\", row.names=1, header=T));";
		data += "markers <- c(\"58235\",\"52906\",\"67865\",\"74884\",\"100415901\",\"170788\",\"23834\",\"71532\",\"18023\",\"16622\",\"70120\",\"268729\",\"68644\",\"217653\",\"20317\",\"243043\",\"110606\",\"211936\",\"192119\",\"12212\",\"65961\",\"17695\",\"97890\",\"73430\",\"68611\",\"269473\",\"382089\",\"72580\",\"74195\",\"14784\",\"66464\",\"29870\",\"64406\",\"14149\",\"97810\",\"75328\",\"20191\",\"71121\",\"66790\",\"320329\",\"50917\",\"67967\",\"18475\",\"241447\",\"12562\",\"56068\",\"19296\",\"60527\",\"216549\",\"233726\",\"56705\",\"75616\",\"74452\",\"100504527\",\"56378\",\"74134\",\"74762\",\"52458\",\"228368\",\"74641\",\"225923\",\"230661\",\"74628\",\"68473\",\"99811\",\"21379\",\"12455\",\"28081\",\"70382\",\"109212\",\"234384\",\"21771\",\"381716\",\"97787\",\"68011\",\"102693\",\"226419\",\"622175\",\"69923\",\"13510\",\"21849\",\"12950\",\"98108\",\"11944\",\"100504504\",\"402737\",\"73438\",\"232947\",\"75477\",\"18125\",\"100503546\",\"106794\",\"29863\",\"72053\",\"76998\",\"97008\",\"20962\",\"66905\",\"70122\",\"243300\",\"72361\",\"76968\",\"19821\",\"140629\",\"53424\",\"67005\",\"18609\",\"101685\",\"78279\",\"109689\",\"12902\",\"76835\",\"70609\",\"69857\",\"23880\",\"13191\",\"30838\",\"77250\",\"16969\",\"67102\",\"70691\",\"97129\",\"213575\",\"19668\",\"233058\",\"70021\",\"76710\",\"76688\",\"27376\",\"94279\",\"12371\",\"70872\",\"16542\",\"56184\",\"380686\",\"13650\",\"209737\",\"14405\",\"76626\",\"71027\",\"75510\",\"67838\",\"21951\",\"22341\",\"433700\",\"16163\",\"69638\",\"23948\",\"22346\",\"77697\",\"72107\",\"102685\",\"19725\",\"72017\",\"113863\",\"67330\",\"100502619\",\"102634692\",\"74744\",\"26903\",\"14629\",\"329628\",\"66880\",\"54519\",\"234362\",\"20729\",\"100604\",\"20348\",\"664883\",\"96953\",\"22688\",\"18716\",\"71251\",\"13138\",\"224139\",\"227099\",\"102626\",\"13685\",\"67045\",\"332397\",\"13542\",\"235323\",\"217331\",\"211986\",\"71046\",\"403205\",\"552913\",\"14866\",\"100040529\",\"320211\",\"319622\",\"17962\",\"403348\",\"228061\",\"171167\",\"140780\",\"16196\",\"24059\",\"66596\",\"208890\",\"15959\",\"13690\",\"12144\",\"268534\",\"66113\",\"69234\",\"20383\",\"320244\",\"102209\",\"67199\",\"65254\",\"20496\",\"76893\",\"53356\",\"209012\",\"245865\",\"170439\",\"226594\",\"11872\",\"70381\",\"52480\",\"20355\",\"12488\",\"56222\",\"100038680\",\"75308\",\"12686\",\"70350\",\"75725\",\"215008\",\"217847\",\"83554\",\"100041694\",\"105518\",\"218581\",\"17986\",\"68054\",\"72149\",\"12449\",\"216456\",\"211007\",\"100215\",\"17451\",\"73186\",\"68002\",\"12549\",\"330427\",\"12877\",\"216169\",\"320702\",\"114606\",\"234967\",\"69677\",\"209200\",\"74499\",\"269514\",\"19128\",\"12477\",\"13110\",\"52490\",\"51953\",\"320472\",\"71777\",\"109245\",\"67578\",\"242083\",\"100039495\",\"67905\",\"14789\",\"105592\",\"69350\",\"77132\",\"246278\",\"320581\",\"73062\",\"231532\",\"228875\",\"228880\",\"66244\",\"71575\",\"235533\",\"53312\",\"226541\",\"433619\",\"327768\",\"69136\",\"26941\",\"50927\",\"18671\",\"54122\",\"234964\",\"105171\",\"59004\",\"243931\",\"244091\",\"329387\",\"20964\",\"14114\",\"18484\",\"18416\",\"15207\",\"27277\",\"19053\",\"14407\",\"320158\",\"67425\",\"18012\",\"106894\",\"235106\",\"277468\",\"20429\",\"225280\",\"380794\",\"213582\",\"14420\",\"85031\",\"226896\");";

		data += "tab <- allValues;";
		data += "tab<-subset(tab, rownames(tab) %in% markers);";
		data += "tab<-t(tab);";
		data += "log.ir <- log(tab);";
		data += "ir.pca <- prcomp(log.ir, center = TRUE, scale. = TRUE);"; 
		
		data += "scores = as.data.frame(ir.pca$x);";
		data += "mdat <- matrix(c(scores$PC1, scores$PC2), nrow = 2, ncol = length(scores$PC1));";
		data += "mdat;";
		
		RConnection c = new RConnection();
		
		REXP x = c.eval(data);

		return x.asDoubleMatrix();
		
	}
	
//	public static boolean normalizeCELFile(String file, String dir) {
//		
//		boolean res = true;
//		
//		String data = "library(\"affy\");";
//		data += "library(\"mouse4302frmavecs\");";
//		data += "library(\"frma\");";
//
//		data += "Data <- ReadAffy(\""+file+"\");";
//		data += "PData <- frma(Data);";
//		data += "tabData <- exprs(PData);";
//		data += "write.table(tabData,'"+dir+"normalized.csv',sep=\"\\t\",quote=F);";
//		
//		try {
//			RConnection c = new RConnection();
//		
//			REXP x = c.eval(data);
//		} catch(Exception e)
//		{
//			e.printStackTrace();
//			res = false;
//		}
//		return res;
//		ssh jose@193.136.227.168
//	
//	scp -r DATA.DIR/ iduarte@10.4.0.225:/home/iduarte/R/stemmapper/
//	}
	
	
	public static double[][] quantilize(String platform, String pathtocfrma, String[] entrezIdsList, double[][] entrezIdsMatrix) throws Exception{
	
	boolean res = true;
	
	RConnection c = new RConnection();
	
	StringBuffer data = new StringBuffer();
	
	if(platform.equals("mouse4302")) data.append("load(\""+pathtocfrma+"MouseQuantilizer.RData\");\n");
	else data = data.append("load(\""+pathtocfrma+"HumanQuantilizer.RData\");\n");
	
	StringBuffer exlist = new StringBuffer("exlist <- c(");
	StringBuffer list = new StringBuffer("list <- c(");
	
	for(int m=0;m<entrezIdsList.length;m++)
	{
		if(m>0)
		{
			exlist.append(",");
			list.append(",");
		}
		
		exlist.append(entrezIdsMatrix[m][0]);
		list.append("\"");
		list.append(entrezIdsList[m]);
		list.append("\"");
	}
	exlist.append(");\n");
	list.append(");\n");
	
	data.append(exlist);
	data.append(list);
	
	data.append("listx <- match(list,entrezpositions);\n");
	data.append("quantiledresults <- c();\n");

	data.append("for(i in 1:length(listx)) {\n");
	data.append("quantiledresults[i] <- quantile.expression(listx[i], exlist[i]);\n");
	data.append("}\n");
	
	data.append("quantiledresults;\n");
	REXP x = c.eval(data.toString());
	x.asDoubles();
	
	double[] pmatrix = x.asDoubles(); //expression values by probes
	
	double[][] matrix = new double[entrezIdsMatrix.length][1];
	
	for(int m=0;m<entrezIdsList.length;m++)
	{
		matrix[m][0] = pmatrix[m];
	}
	
	return matrix;
	
}
	
	
	public static Object[] normalizeCELFile(String file, String platform, String pathtocfrma) throws Exception {
		
		String excep = "";
		
		Object[] res = null;
		
//		String data = "library(\"affy\");\n";
		
		String data = pathlibline+"library(\"affy\");\n";
		if(platform.equals("mouse4302"))
		{
			data += "library(\"mouse4302frmavecs\");\n";
			data += "library(\"mouse4302.db\");\n";
		}
		else
		{
			data += "library(\"hgu133plus2frmavecs\");\n";
			data += "library(\"hgu133plus2.db\");\n";
		}
		
		data += "load(\""+pathtocfrma+"frma_frozenVectors_mouse_human.RData\");\n";
		
		data += "library(\"frma\");\n";

		data += "Data <- ReadAffy(\""+file+"\");\n";
		if(platform.equals("mouse4302")) data += "PData <- frma(Data, input.vecs=mouse.vectors);\n";
		else data += "PData <- frma(Data, input.vecs=human.vectors);\n";
		data += "tabData <- exprs(PData);\n";
//		data += "write.table(tabData,'"+dir+"normalized.csv',sep=\"\\t\",quote=F);";
		
		data += "tabData;\n";
		
		
		try {
			RConnection c = new RConnection();
			
			excep += data;
			REXP x = c.eval(data);
			double[][] matrix = x.asDoubleMatrix(); //expression values by probes
			
			data = "colnames(tabData);";
			
			excep += "\n\n"+data;
			
			x = c.eval(data);
			String[] colnames = x.asStrings(); // colnames = probes names
			
			data = "rownames(tabData);";
			
			excep += "\n\n"+data;
			x = c.eval(data);
			String[] rownames = x.asStrings(); //sample names
			
			res = new Object[]{matrix, colnames, rownames};
		} catch(Exception e)
		{
			e.printStackTrace();
			res = null;
			throw(new Exception("Normalization errorz:\n"+excep));
		}
		return res;
//		return null;
		
	}
	
	public static Object[] createPCAPlot(String[] entrezIdsList, String[] userData, String userDataName, 
		String refFlie, String[] markers, String[] samplesToUse) throws Exception {
		
		String data = (String)trimInputedData(userData, markers, entrezIdsList, userDataName);
		
		data += "allValues <- data.matrix(read.table(\""+refFlie+"\", sep=\"\\t\", row.names=1, header=T));\n";
//		data += "userValues <- data.matrix(read.table(\""+processedFile+"\", sep=\"\\t\", row.names=1, header=T));\n";
		
//		data += "markers <- c(\"58235\",\"52906\",\"67865\",\"74884\",\"100415901\",\"170788\",\"23834\",\"71532\",\"18023\",\"16622\",\"70120\",\"268729\",\"68644\",\"217653\",\"20317\",\"243043\",\"110606\",\"211936\",\"192119\",\"12212\",\"65961\",\"17695\",\"97890\",\"73430\",\"68611\",\"269473\",\"382089\",\"72580\",\"74195\",\"14784\",\"66464\",\"29870\",\"64406\",\"14149\",\"97810\",\"75328\",\"20191\",\"71121\",\"66790\",\"320329\",\"50917\",\"67967\",\"18475\",\"241447\",\"12562\",\"56068\",\"19296\",\"60527\",\"216549\",\"233726\",\"56705\",\"75616\",\"74452\",\"100504527\",\"56378\",\"74134\",\"74762\",\"52458\",\"228368\",\"74641\",\"225923\",\"230661\",\"74628\",\"68473\",\"99811\",\"21379\",\"12455\",\"28081\",\"70382\",\"109212\",\"234384\",\"21771\",\"381716\",\"97787\",\"68011\",\"102693\",\"226419\",\"622175\",\"69923\",\"13510\",\"21849\",\"12950\",\"98108\",\"11944\",\"100504504\",\"402737\",\"73438\",\"232947\",\"75477\",\"18125\",\"100503546\",\"106794\",\"29863\",\"72053\",\"76998\",\"97008\",\"20962\",\"66905\",\"70122\",\"243300\",\"72361\",\"76968\",\"19821\",\"140629\",\"53424\",\"67005\",\"18609\",\"101685\",\"78279\",\"109689\",\"12902\",\"76835\",\"70609\",\"69857\",\"23880\",\"13191\",\"30838\",\"77250\",\"16969\",\"67102\",\"70691\",\"97129\",\"213575\",\"19668\",\"233058\",\"70021\",\"76710\",\"76688\",\"27376\",\"94279\",\"12371\",\"70872\",\"16542\",\"56184\",\"380686\",\"13650\",\"209737\",\"14405\",\"76626\",\"71027\",\"75510\",\"67838\",\"21951\",\"22341\",\"433700\",\"16163\",\"69638\",\"23948\",\"22346\",\"77697\",\"72107\",\"102685\",\"19725\",\"72017\",\"113863\",\"67330\",\"100502619\",\"102634692\",\"74744\",\"26903\",\"14629\",\"329628\",\"66880\",\"54519\",\"234362\",\"20729\",\"100604\",\"20348\",\"664883\",\"96953\",\"22688\",\"18716\",\"71251\",\"13138\",\"224139\",\"227099\",\"102626\",\"13685\",\"67045\",\"332397\",\"13542\",\"235323\",\"217331\",\"211986\",\"71046\",\"403205\",\"552913\",\"14866\",\"100040529\",\"320211\",\"319622\",\"17962\",\"403348\",\"228061\",\"171167\",\"140780\",\"16196\",\"24059\",\"66596\",\"208890\",\"15959\",\"13690\",\"12144\",\"268534\",\"66113\",\"69234\",\"20383\",\"320244\",\"102209\",\"67199\",\"65254\",\"20496\",\"76893\",\"53356\",\"209012\",\"245865\",\"170439\",\"226594\",\"11872\",\"70381\",\"52480\",\"20355\",\"12488\",\"56222\",\"100038680\",\"75308\",\"12686\",\"70350\",\"75725\",\"215008\",\"217847\",\"83554\",\"100041694\",\"105518\",\"218581\",\"17986\",\"68054\",\"72149\",\"12449\",\"216456\",\"211007\",\"100215\",\"17451\",\"73186\",\"68002\",\"12549\",\"330427\",\"12877\",\"216169\",\"320702\",\"114606\",\"234967\",\"69677\",\"209200\",\"74499\",\"269514\",\"19128\",\"12477\",\"13110\",\"52490\",\"51953\",\"320472\",\"71777\",\"109245\",\"67578\",\"242083\",\"100039495\",\"67905\",\"14789\",\"105592\",\"69350\",\"77132\",\"246278\",\"320581\",\"73062\",\"231532\",\"228875\",\"228880\",\"66244\",\"71575\",\"235533\",\"53312\",\"226541\",\"433619\",\"327768\",\"69136\",\"26941\",\"50927\",\"18671\",\"54122\",\"234964\",\"105171\",\"59004\",\"243931\",\"244091\",\"329387\",\"20964\",\"14114\",\"18484\",\"18416\",\"15207\",\"27277\",\"19053\",\"14407\",\"320158\",\"67425\",\"18012\",\"106894\",\"235106\",\"277468\",\"20429\",\"225280\",\"380794\",\"213582\",\"14420\",\"85031\",\"226896\");\n";

		
		data += "markers <- c(";
		
		for(int i=0;i<markers.length;i++)
		{
			if(i==0) data += "\""+markers[i]+"\"";
			else data += ",\""+markers[i]+"\"";
		}
		
		data += ");\n";
		
		data += "tab <- allValues;\n";
		data += "tab <- subset(tab, rownames(tab) %in% markers);\n";
		data += "tab <- cbind2(tab, userValues);\n";
		data += "tab <- t(tab);\n";
		
//		data += "tab <- allValues;\n";
//		data += "colnames(userValues)[1] <- \"inserted\";";
//		data += "tab <- cbind2(allValues, userValues);\n";
//		data += "tab <- subset(tab, rownames(tab) %in% markers);\n";
//		data += "tab <- t(tab);\n";
		
		
		if(samplesToUse.length>0)
		{
			data += "samples <- c(colnames(userValues)[1]";
			
			for(int i=0;i<samplesToUse.length;i++)
			{
//				if(i==0) data += "\""+samplesToUse[i]+"\"";
//				else data += ",\""+samplesToUse[i]+"\"";
				data += ",\""+samplesToUse[i]+"\"";
			}
			
			data += ");\n";
			
			data += "tab <- subset(tab, rownames(tab) %in% samples);\n";
		}
		
		
		data += "log.ir <- log(tab);\n";
		data += "ir.pca <- prcomp(log.ir, center = TRUE, scale. = TRUE);\n"; 
		
		data += "scores = as.data.frame(ir.pca$x);\n";
		data += "mdat <- matrix(c(scores$PC1, scores$PC2), nrow = length(scores$PC1), ncol = 2);\n";
		data += "mdat <- t(mdat);";
		data += "mdat;\n";
		
		RConnection c = new RConnection();
		
		REXP x = c.eval(data);
		double[][] res1 = x.asDoubleMatrix();
		
		data = "rownames(tab);";
		
		x = c.eval(data);

		Object[] res = new Object[]{res1, x.asStrings()};
		
		return res;
	}
	
	public static void clusterTest() {
		
		boolean res = true;
		
//		String data = "a <- list();";
//		data += "a$merge <- matrix(c(-1, -2, -3, -4, 1,  2), nc=2, byrow=TRUE );";
//		data += "a$height <- c(1, 1.5, 3);";
//		data += "a$order <- 1:4;";
//		data += "a$labels <- LETTERS[1:4];";
//		data += "class(a) <- \"hclust\";";
		
		String data = "d <- dist(as.matrix(mtcars));";
		data += "a <- hclust(d);";
		
		data += "a$merge;";
		
		try {
			RConnection c = new RConnection();
		
			REXP x = c.eval(data);
			
			double[][] mat = x.asDoubleMatrix();
			
			data = "a$labels";
			
			x = c.eval(data);
			
			String[] lables = x.asStrings();
			
			Dendograme da = DendogrameConverter.convertMatrix(mat, lables);
			
			String[] ord = da.getOrder();
			
			int s = 5;
			String names = "[";
			String positions = "[";
			
			for(int a=0;a<ord.length;a++)
			{
				if(a==0)
				{
					names += "\""+ord[a]+"\"";
					positions += s;
				}
				else
				{
					names += ",\""+ord[a]+"\"";
					positions += ","+s;
				}
				
				s+=10;
			}
			
			names += "]";
			positions += "]";
			
		} catch(Exception e)
		{
			e.printStackTrace();
			res = false;
		}
		
	}
	
	public static void main(String[] args) {
		
		RAccess.clusterTest();
		
	}
	
	protected static String convertStringArray(String[] arr)
	{
		String res = "";
		
		for(int a=0;a<arr.length;a++)
		{
			if(a==0) res += arr[a];
			else res += "|"+arr[a];
		}
		
		return res;
	}
	
	protected static String convertDoubleMatrix(double[][] mat)
	{
		StringBuffer res = new StringBuffer("");
		
		for(int a=0;a<mat.length;a++)
		{
			if(a!=0) res.append("?");
			
			for(int b=0;b<mat[a].length;b++)
			{
				if(b==0) res.append(round(mat[a][b],3));
				else
				{
					res.append("|");
					res.append(round(mat[a][b],3));
				}
			}
		}
		
		return res.toString();
	}
	
	public static String[] createDendrogram(String[] entrezIdsList, String[] userData, String userDataName, String refFlie, 
		String[] markers, String[] samplesToUse) throws Exception {

		String[] res = new String[2];
		
		
		
		String data = (String)trimInputedData(userData, markers, entrezIdsList, userDataName);
		
		data += "allValues <- data.matrix(read.table(\""+refFlie+"\", sep=\"\\t\", row.names=1, header=T));\n";
//		data += "userValues <- data.matrix(read.table(\""+processedFile+"\", sep=\"\\t\", row.names=1, header=T));\n";
		
		data += "markers <- c(";
		
		for(int i=0;i<markers.length;i++)
		{
			if(i==0) data += "\""+markers[i]+"\"";
			else data += ",\""+markers[i]+"\"";
		}
		
		data += ");\n";
		
		data += "tab <- allValues;\n";
		data += "tab <- subset(tab, rownames(tab) %in% markers);\n";
		data += "tab <- cbind2(tab, userValues);\n";
		data += "tab <- t(tab);\n";
		
		
		if(samplesToUse.length>0)
		{
			data += "samples <- c(colnames(userValues)[1]";
			
			for(int i=0;i<samplesToUse.length;i++)
			{
//				if(i==0) data += "\""+samplesToUse[i]+"\"";
//				else data += ",\""+samplesToUse[i]+"\"";
				data += ",\""+samplesToUse[i]+"\"";
			}
			
			data += ");\n";
			
			data += "tab <- subset(tab, rownames(tab) %in% samples);\n";
		}
		
		data += "d <- dist(as.matrix(tab));\n";
		
		data += "a <- hclust(d);\n";
		
		data += "a$merge;";
		

		
		RConnection c = new RConnection();
		
		REXP x = c.eval(data);
		
		double[][] mat = x.asDoubleMatrix();
		
		data = "a$labels";
		
		x = c.eval(data);
		
		String[] lables = x.asStrings();
		
		Dendograme da = DendogrameConverter.convertMatrix(mat, lables);
		
		res[0] = da.toString();
		
		String[] ord = da.getOrder();
		
		int s = 5;
		String names = "";
//		String positions = "";
		
		for(int a=0;a<ord.length;a++)
		{
			if(a==0)
			{
				names += ord[a];
//				positions += s;
			}
			else
			{
				names += "|"+ord[a];
//				positions += "|"+s;
			}
			
			s+=10;
		}
		
		res[1] = names;
		
		
		return res;
	}
	
	public static String trimInputedData(String[] data, String[] markers, String[] entrezIdsList, String testName) {
		
		int[] positions = new int[markers.length];
		
		for(int i=0;i<markers.length;i++)
		{
			int z = -1;
			
			for(int a=0;a<entrezIdsList.length && z==-1;a++)
			{
//				if(entrezIdsList[a].endsWith(markers[i])) z = a;
				if(entrezIdsList[a].equals(markers[i])) z = a;
			}
			
			positions[i] = z;
		}
		
		Arrays.sort(positions);
		
		String res = "userValues <- matrix(c("+data[positions[0]];
		
		for(int i=1;i<positions.length;i++)
		{
			
			res += ", "+data[positions[i]];
		}
		
		res += "), nrow="+positions.length+", ncol=1);\n" +
			"colnames(userValues) <- \""+testName+"\";\n";
		
		
		return res;
	}
	
	//TODO: finish this one once I have the table
	
	protected static String listToString(Object[] in, boolean quotations)
	{
		String res = "";
		
		for(int a=0;a<in.length;a++)
		{
			if(quotations)
			{
				if(a==0) res += "\""+in[a].toString()+"\""; //TODO: Why is it failing?
				else res += ",\""+in[a].toString()+"\"";
				
			}
			else
			{
				if(a==0) res += in[a].toString();
				else res += ","+in[a].toString();
			}
			
		}
		
		return res;
	}
	
	public static Object[] processData(SampleObject so, Integer[] entrezs, String[] inputedids, StartUpBean sub) throws Exception {
		
		double[][] matrix = so.getMatrix(); 
		double[][] matrixq = so.getMatrixq(); 
		Integer[] rowNames = so.getRowNames();
		String[] columnNames = so.getColumnNames();
		
		StringBuffer data = new StringBuffer("tab <- matrix(c(");
		
		for(int a=0;a<matrix.length;a++)
		{
			for(int b=0;b<matrix[a].length;b++)
			{
				if(a==0 && b==0) data.append(matrix[a][b]);
				else data.append(","+matrix[a][b]);
			}
		}
		
		//Note get rid of unecessery transpositions!
		data.append("),");
		data.append("nrow="+columnNames.length+",");
		data.append("ncol="+rowNames.length+");\n");
		data.append("tab<- t(tab);\n");
		
		data.append("rownames(tab) <- c("+listToString(rowNames, false)+");\n");

		data.append("colnames(tab) <- c("+listToString(columnNames, true)+");\n");

		data.append("tab<- t(tab);\n");
		
		data.append("log.ir <- log(tab);\n");
		data.append("ir.pca <- prcomp(log.ir, center = TRUE, scale. = TRUE);\n"); 
		
		data.append("scores = as.data.frame(ir.pca$x);\n");
		data.append("mdat <- matrix(c(scores$PC1, scores$PC2), nrow = length(scores$PC1), ncol = 2);\n");
		data.append("mdat <- t(mdat);\n");
		data.append("mdat;\n");
		
		RConnection c = new RConnection();
		
		System.out.println(data.toString());
		
		REXP x = c.eval(data.toString());
		
		double[][] res1 = x.asDoubleMatrix();

		
		for(int a=0;a<res1.length;a++)
		{
			for(int b=0;b<res1[a].length;b++)
			{
				res1[a][b] = round(res1[a][b],3);
			}
		}
		
		
		Object[] res = new Object[21];
		
		//========== getting the variance here ==========

		
		data= new StringBuffer("sum <- summary(ir.pca);\n");
		data.append("za<-sum[[6]];\n");
		data.append("za;\n");

		System.out.println(data.toString());
		x = c.eval(data.toString());
		
		double[][] summat = x.asDoubleMatrix();
		
		res[11] = new double[]{round(summat[1][0], 3)*100,round(summat[1][1], 3)*100};
//		res[11]
		//==============================================
		

		System.out.println("rownames(tab);");
		x = c.eval("rownames(tab);");
		
		res[0] = res1;
		res[1] = x.asStrings();
		res[7] = sub.getSamplesColor((String[])res[1]);
		
		data= new StringBuffer("tab <- t(tab);\n");

		data.append(pathlibline+"require(Heatplus);\n");
		data.append("reg <- regHeatmap(tab, scale = c(\"none\"));\n");
		
		data.append("a <- as.hclust(reg$dendrogram$Col$dendro);\n");
		
		data.append("a$merge;\n");
		

		System.out.println(data.toString());
		x = c.eval(data.toString());
		
		double[][] mat = x.asDoubleMatrix();


		System.out.println("a$labels;");
		x = c.eval("a$labels;");
		
		String[] lables = x.asStrings();
		
		Dendograme da = DendogrameConverter.convertMatrix(mat, lables);
		
		res[2] = da.toString();
		
		String[] ord = da.getOrder();
		
		StringBuffer names = new StringBuffer("");

		String[] res6 = new String[ord.length];
		String[] res8 = new String[ord.length];
		String[] res10 = new String[ord.length];
		
		for(int a=0;a<ord.length;a++)
		{
			
			
//			res6[a] = sub.getSampleColor(ord[a]);
//			res6[a] = so.getSampleColor(ord[a]);
			String[] rest = so.getSampleColorNType(ord[a]);
			
			res6[a] = rest[0];
			res8[a] = rest[1];
			res10[a] = rest[2];
			
			if(a==0)
			{
				names.append(ord[a]);
			}
			else
			{
				names.append("|");
				names.append(ord[a]);
			}
		}
		
		res[3] = names.toString();
		
		System.out.println("reg$labels[1]$Row[[4]];");
		x = c.eval("reg$labels[1]$Row[[4]];");
		
		String[] arrs = x.asStrings();
		
		Object[] convedids = convertStringArray(arrs, entrezs, inputedids);
		
		res[4] = convedids[0];

		System.out.println("reg$data$x2;");
		x = c.eval("reg$data$x2;");
		
		res[5] = convertDoubleMatrix(x.asDoubleMatrix());
		
		res[6] = res6;
		res[8] = res8;
		res[10] = res10;
		
		res[9] = convedids[1];
		
		//==========================================new Matrix
		data = new StringBuffer("tab <- matrix(c(");
		
		for(int a=0;a<matrixq.length;a++)
		{
			for(int b=0;b<matrixq[a].length;b++)
			{
				if(a==0 && b==0) data.append(matrixq[a][b]);
				else data.append(","+matrixq[a][b]);
			}
		}
		
		//Note get rid of unecessery transpositions!
		data.append("),");
		data.append("nrow="+columnNames.length+",");
		data.append("ncol="+rowNames.length+");\n");
		data.append("tab<- t(tab);\n");
		
		data.append("rownames(tab) <- c("+listToString(rowNames, false)+");\n");

		data.append("colnames(tab) <- c("+listToString(columnNames, true)+");\n");

//		data.append("tab<- t(tab);\n");
		
		data.append("reg <- regHeatmap(tab, scale = c(\"none\"));\n");
		
		data.append("a <- as.hclust(reg$dendrogram$Col$dendro);\n");
		
		data.append("a$merge;\n");
		
		System.out.println(data.toString());
		x = c.eval(data.toString());
		
		double[][] matq = x.asDoubleMatrix();


		System.out.println("a$labels;");
		x = c.eval("a$labels;");
		
		String[] lablesq = x.asStrings();
		
		Dendograme daq = DendogrameConverter.convertMatrix(matq, lablesq);
		
		res[13] = daq.toString(); //new starts at 12
		
		String[] ordq = daq.getOrder();
		
		StringBuffer namesq = new StringBuffer("");

		String[] res14 = new String[ordq.length];
		String[] res15 = new String[ordq.length];
		String[] res16 = new String[ordq.length];
		
		for(int a=0;a<ordq.length;a++)
		{
			
			
//			res6[a] = sub.getSampleColor(ord[a]);
//			res6[a] = so.getSampleColor(ord[a]);
			String[] rest = so.getSampleColorNType(ordq[a]);
			
			res14[a] = rest[0];
			res15[a] = rest[1];
			res16[a] = rest[2];
			
			if(a==0)
			{
				namesq.append(ordq[a]);
			}
			else
			{
				namesq.append("|");
				namesq.append(ordq[a]);
			}
		}
		
		res[17] = namesq.toString();
		
		System.out.println("reg$labels[1]$Row[[4]];");
		x = c.eval("reg$labels[1]$Row[[4]];");
		
		String[] arrsq = x.asStrings();
		
		Object[] convedidsq = convertStringArray(arrsq, entrezs, inputedids);
		
		res[18] = convedidsq[0];

		System.out.println("reg$data$x2;");
		x = c.eval("reg$data$x2;");
		
		res[19] = convertDoubleMatrix(x.asDoubleMatrix());
		
		res[14] = res14;
		res[15] = res15;
		res[16] = res16;
		
		res[20] = convedidsq[1];
		
		
		return res;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	
	protected static Object[] convertStringArray(String[] arr, Integer[] entrezs, String[] inputedids)
	{
		StringBuffer res = new StringBuffer("");
		String[][] res2 = new String[arr.length][2];
		
		for(int a=0;a<arr.length;a++)
		{
//			System.out.println("arr.length "+arr.length+" arr[a] "+arr[a]);
			StringBuffer tsb = convertId(arr[a],entrezs,inputedids);
			
			if(a==0) res.append(tsb);
			else
			{
				res.append("|");
				res.append(tsb);
			}
//			System.out.println("tsb "+tsb);
			
			res2[(arr.length-1)-a][0] = tsb.toString();
			res2[(arr.length-1)-a][1] = arr[a];
		}
		
		return new Object[]{res.toString(),res2};
	}
	
	protected static StringBuffer convertId(String arr, Integer[] entrezs, String[] inputedids)
	{
		StringBuffer res = null;
		
		for(int i=0;res==null && i<inputedids.length;i++)
		{
			if(entrezs[i].toString().equals(arr))
			{
				res = new StringBuffer();
				res.append(inputedids[i]);
				res.append(" (");
				res.append(arr);
				res.append(")");
			}
			
		}
		return res;
	}
	
}