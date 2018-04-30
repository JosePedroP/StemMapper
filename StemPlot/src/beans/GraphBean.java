package beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import databaseAccess.DatabaseAccess;
import databaseAccess.SampleObject;
import filehandlers.ProbToEntrezHandler;
import rAccess.RAccess;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
@RequestScoped
public class GraphBean {

	//R CMD Rserve
	
	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;
	
	protected String setToUse = "set1";
	protected String[] markersToUse = { "no" };

	protected String[] useThisDataTypes;
	
	protected String[][] inputedIds = new String[][]{new String[]{"biza"}, new String[]{"bezi", "nog"}};

	protected String[] allExperimentsNames = { "My head is empty" };
	protected String[] entrezIdsList = { "My head is empty" };
	double[][] entrezIdsMatrix = new double[][] { { 0 } };
	
	protected String dendograme = "((((GSM210973.CEL.gz|(GSM210977.CEL.gz|GSM445689.CEL.gz))|(GSM172070.CEL.gz|((GSM210972.CEL.gz|(GSM1326663_Setd2.ko.1_Mouse430_2_.CEL.gz|GSM445690.CEL.gz))|((GSM1372808_1978_005_116_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|(GSM85009.CEL.gz|GSM85009Nutshel.CEL.gz))|(GSM210975.CEL.gz|GSM381319.CEL.gz)))))|(((GSM463598.CEL.gz|(GSM381321.CEL.gz|GSM381323.CEL.gz))|(GSM381320.CEL.gz|(((GSM381322.CEL.gz|(GSM1372809_1979_005_133_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM506223_ES_WTserum_free01_MG430_2_09063001.CEL.gz))|(GSM234775.CEL.gz|GSM381316.CEL.gz))|((GSM85008.CEL.gz|(GSM162863.cel.gz|GSM381310.CEL.gz))|((GSM381311.CEL.gz|(GSM210974.CEL.gz|GSM381317.CEL.gz))|(GSM506224_ES_WTserum_free02_MG430_2_09063002.CEL.gz|(GSM234774.CEL.gz|GSM381318.CEL.gz)))))))|(((((GSM315977.CEL.gz|(GSM445686.CEL.gz|GSM842158_NSC_2.CEL.gz))|(GSM1326665_Setd2.ko.3_Mouse430_2_.CEL.gz|GSM445687.CEL.gz))|(GSM381314.CEL.gz|(GSM506222_ES_TKOserum_free02_MG430_2_09063004.CEL.gz|GSM902303_ES_wildtype_2.CEL.gz)))|(((GSM1326660_Setd2.wt.1_Mouse430_2_.CEL.gz|(GSM445688.CEL.gz|GSM445691.CEL.gz))|(GSM1326662_Setd2.wt.3_Mouse430_2_.CEL.gz|GSM445685.CEL.gz))|((GSM445684.CEL.gz|(GSM234772.CEL.gz|GSM445683.CEL.gz))|((GSM797840.CEL.gz|GSM797842.CEL.gz)|(GSM172073.CEL.gz|GSM797839.CEL.gz)))))|((GSM325458.CEL.gz|GSM94860.CEL.gz)|(GSM381315.CEL.gz|(GSM234773.CEL.gz|GSM506221_ES_TKOserum_free01_MG430_2_09063003.CEL.gz))))))|((GSM381324.CEL.gz|(GSM1372807_1977_005_109_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM381325.CEL.gz))|((GSM347151.CEL.gz|GSM347155.CEL.gz)|((GSM94859.CEL.gz|(GSM238849.CEL.gz|GSM325459.CEL.gz))|(GSM522304.CEL.gz|(GSM347152.CEL.gz|GSM347156.CEL.gz))))))|((GSM795457_Ectoderm_SK7.CEL.gz|((((GSM638139.cel.gz|GSM638140.cel.gz)|((GSM638136.cel.gz|(GSM638135.cel.gz|GSM638137.cel.gz))|((GSM638148.cel.gz|(GSM638147.cel.gz|GSM638151.cel.gz))|(GSM638130.cel.gz|(GSM638131.cel.gz|GSM638150.cel.gz)))))|(GSM506226_J1G4Dex_02_MG430_2_09031902.CEL.gz|(((GSM94862.CEL.gz|GSM94863.CEL.gz)|(GSM638142.cel.gz|GSM638144.cel.gz))|(GSM638129.cel.gz|(GSM638141.cel.gz|GSM638145.cel.gz)))))|(((GSM169456.CEL.gz|GSM169460.CEL.gz)|(GSM325456.CEL.gz|GSM94858.CEL.gz))|((GSM638133.cel.gz|(GSM638146.cel.gz|(GSM347150.CEL.gz|GSM347154.CEL.gz)))|((GSM638143.cel.gz|GSM638152.cel.gz)|(GSM154465.CEL.gz|(GSM347153.CEL.gz|GSM638134.cel.gz)))))))|((((GSM685746.CEL.gz|(GSM685749.CEL.gz|((GSM973047_PSM_21.CEL.gz|GSM973054_PSM_42.CEL.gz)|(GSM973052_PSM_33.CEL.gz|(GSM685745.CEL.gz|GSM973055_PSM_43.CEL.gz)))))|(GSM787921.CEL.gz|((GSM842160_NSC_4.CEL.gz|(GSM685747.CEL.gz|GSM973045_PSM_12.CEL.gz))|(GSM1372798_1611_033_31_m01_MB_180112.CEL.gz|(GSM787920.CEL.gz|GSM842159_NSC_3.CEL.gz)))))|(((GSM902313_DP_mutant_2.CEL.gz|(GSM685748.CEL.gz|GSM902312_DP_mutant_p_1.CEL.gz))|((GSM1372804_1980_005_118_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM1372805_1981a_005_122_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz)|((GSM973053_PSM_41.CEL.gz|(GSM795459_Paraxial_mesoderm_SK7.CEL.gz|GSM973046_PSM_13.CEL.gz))|(GSM706697.CEL.gz|(GSM787922.CEL.gz|(GSM787918.CEL.gz|GSM787924.CEL.gz))))))|(((GSM706698.CEL.gz|GSM797837.CEL.gz)|(GSM973057_PSM_52.CEL.gz|(GSM973060_PSM_62.CEL.gz|(GSM973048_PSM_22.CEL.gz|GSM973051_PSM_32.CEL.gz))))|((GSM230332.CEL.gz|((GSM787925.CEL.gz|(GSM1372799_1615_033_32_m01_MB_200112.CEL.gz|GSM355342.CEL.gz))|(GSM1246268_P60_IncisorPulp_Denerved_3.CEL.gz|GSM355344.CEL.gz)))|(((GSM973058_PSM_53.CEL.gz|GSM973063_PSM_72.CEL.gz)|(GSM685750.CEL.gz|(GSM1372794_1612_033_35_m01_MB_180112.CEL.gz|(GSM1372795_1613_033_37_m01_MB_180112.CEL.gz|GSM787923.CEL.gz))))|((GSM842157_NSC_1.CEL.gz|(GSM1372806_1982_005_126_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM787919.CEL.gz))|(GSM973044_PSM_11.CEL.gz|(GSM1372797_1610_033_30_m01_MB_180112.CEL.gz|GSM355346.CEL.gz))))))))|(((((GSM230338.CEL.gz|GSM795458_Endoderm_SK7.CEL.gz)|(GSM686641_KSL_2.CEL.gz|(GSM230336.CEL.gz|(GSM1372791_1608_033_18_m01_MB_180112.CEL.gz|GSM230342.CEL.gz))))|(GSM1372793_1614_033_26_m01_MB_200112.CEL.gz|(GSM1372792_1609_033_20_m01_MB_180112.CEL.gz|GSM1372796_1616_033_39_m01_MB_200112.CEL.gz)))|(((GSM638138.cel.gz|(GSM325454.CEL.gz|GSM94866.CEL.gz))|((GSM902311_DP_wildtype_2.CEL.gz|GSM94867.CEL.gz)|(GSM325452.CEL.gz|GSM325455.CEL.gz)))|(((GSM463599.CEL.gz|GSM94869.CEL.gz)|(GSM325451.CEL.gz|GSM94861.CEL.gz))|((GSM230341.CEL.gz|(GSM902308_PE_mutant_1.CEL.gz|(GSM902309_PE_mutant_2.CEL.gz|GSM94868.CEL.gz)))|((GSM902306_PE_wildtype_1.CEL.gz|(GSM902304_ES_mutant_1.CEL.gz|GSM902310_DP_wildtype_p_1.CEL.gz))|(GSM1535475_G1ME_GATA1s_1.CEL.gz|GSM842161_NSC_5.CEL.gz))))))|(((GSM381313.CEL.gz|(GSM795456_ESC_SK7.CEL.gz|(GSM169459.CEL.gz|(GSM315976.CEL.gz|GSM506231_TS2_MG430_2_09012001.CEL.gz))))|((GSM94865.CEL.gz|(GSM325450.CEL.gz|(GSM169455.CEL.gz|(GSM325453.CEL.gz|GSM325457.CEL.gz))))|(((GSM216535.CEL.gz|GSM902302_ES_wildtype_1.CEL.gz)|(GSM506232_TS5_MG430_2_09012002.CEL.gz|GSM902305_ES_mutant_2.CEL.gz))|(GSM94864.CEL.gz|((GSM506233_XEN1_MG430_2_09012003.CEL.gz|GSM506234_XEN2_MG430_2_09012004.CEL.gz)|(GSM506225_J1G4Dex_01_MG430_2_09031901.CEL.gz|GSM902307_PE_wildtype_2.CEL.gz))))))|(((GSM1246266_P60_IncisorPulp_Denerved_1.CEL.gz|GSM1246269_P60_IncisorPulp_Denerved_4.CEL.gz)|(GSM355343.CEL.gz|(GSM1246270_P60_IncisorPulp_Denerved_5.CEL.gz|GSM1246271_P60_IncisorPulp_Control_1.CEL.gz)))|((GSM463601.CEL.gz|(GSM706700.CEL.gz|GSM706701.CEL.gz))|(GSM797838.CEL.gz|(GSM1326661_Setd2.wt.2_Mouse430_2_.CEL.gz|(GSM1326664_Setd2.ko.2_Mouse430_2_.CEL.gz|GSM381312.CEL.gz)))))))))";
	protected String dendogramelables = "GSM210973.CEL.gz|GSM210977.CEL.gz|GSM445689.CEL.gz|GSM172070.CEL.gz|GSM210972.CEL.gz|GSM1326663_Setd2.ko.1_Mouse430_2_.CEL.gz|GSM445690.CEL.gz|GSM1372808_1978_005_116_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM85009.CEL.gz|GSM85009Nutshel.CEL.gz|GSM210975.CEL.gz|GSM381319.CEL.gz|GSM463598.CEL.gz|GSM381321.CEL.gz|GSM381323.CEL.gz|GSM381320.CEL.gz|GSM381322.CEL.gz|GSM1372809_1979_005_133_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM506223_ES_WTserum_free01_MG430_2_09063001.CEL.gz|GSM234775.CEL.gz|GSM381316.CEL.gz|GSM85008.CEL.gz|GSM162863.cel.gz|GSM381310.CEL.gz|GSM381311.CEL.gz|GSM210974.CEL.gz|GSM381317.CEL.gz|GSM506224_ES_WTserum_free02_MG430_2_09063002.CEL.gz|GSM234774.CEL.gz|GSM381318.CEL.gz|GSM315977.CEL.gz|GSM445686.CEL.gz|GSM842158_NSC_2.CEL.gz|GSM1326665_Setd2.ko.3_Mouse430_2_.CEL.gz|GSM445687.CEL.gz|GSM381314.CEL.gz|GSM506222_ES_TKOserum_free02_MG430_2_09063004.CEL.gz|GSM902303_ES_wildtype_2.CEL.gz|GSM1326660_Setd2.wt.1_Mouse430_2_.CEL.gz|GSM445688.CEL.gz|GSM445691.CEL.gz|GSM1326662_Setd2.wt.3_Mouse430_2_.CEL.gz|GSM445685.CEL.gz|GSM445684.CEL.gz|GSM234772.CEL.gz|GSM445683.CEL.gz|GSM797840.CEL.gz|GSM797842.CEL.gz|GSM172073.CEL.gz|GSM797839.CEL.gz|GSM325458.CEL.gz|GSM94860.CEL.gz|GSM381315.CEL.gz|GSM234773.CEL.gz|GSM506221_ES_TKOserum_free01_MG430_2_09063003.CEL.gz|GSM381324.CEL.gz|GSM1372807_1977_005_109_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM381325.CEL.gz|GSM347151.CEL.gz|GSM347155.CEL.gz|GSM94859.CEL.gz|GSM238849.CEL.gz|GSM325459.CEL.gz|GSM522304.CEL.gz|GSM347152.CEL.gz|GSM347156.CEL.gz|GSM795457_Ectoderm_SK7.CEL.gz|GSM638139.cel.gz|GSM638140.cel.gz|GSM638136.cel.gz|GSM638135.cel.gz|GSM638137.cel.gz|GSM638148.cel.gz|GSM638147.cel.gz|GSM638151.cel.gz|GSM638130.cel.gz|GSM638131.cel.gz|GSM638150.cel.gz|GSM506226_J1G4Dex_02_MG430_2_09031902.CEL.gz|GSM94862.CEL.gz|GSM94863.CEL.gz|GSM638142.cel.gz|GSM638144.cel.gz|GSM638129.cel.gz|GSM638141.cel.gz|GSM638145.cel.gz|GSM169456.CEL.gz|GSM169460.CEL.gz|GSM325456.CEL.gz|GSM94858.CEL.gz|GSM638133.cel.gz|GSM638146.cel.gz|GSM347150.CEL.gz|GSM347154.CEL.gz|GSM638143.cel.gz|GSM638152.cel.gz|GSM154465.CEL.gz|GSM347153.CEL.gz|GSM638134.cel.gz|GSM685746.CEL.gz|GSM685749.CEL.gz|GSM973047_PSM_21.CEL.gz|GSM973054_PSM_42.CEL.gz|GSM973052_PSM_33.CEL.gz|GSM685745.CEL.gz|GSM973055_PSM_43.CEL.gz|GSM787921.CEL.gz|GSM842160_NSC_4.CEL.gz|GSM685747.CEL.gz|GSM973045_PSM_12.CEL.gz|GSM1372798_1611_033_31_m01_MB_180112.CEL.gz|GSM787920.CEL.gz|GSM842159_NSC_3.CEL.gz|GSM902313_DP_mutant_2.CEL.gz|GSM685748.CEL.gz|GSM902312_DP_mutant_p_1.CEL.gz|GSM1372804_1980_005_118_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM1372805_1981a_005_122_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM973053_PSM_41.CEL.gz|GSM795459_Paraxial_mesoderm_SK7.CEL.gz|GSM973046_PSM_13.CEL.gz|GSM706697.CEL.gz|GSM787922.CEL.gz|GSM787918.CEL.gz|GSM787924.CEL.gz|GSM706698.CEL.gz|GSM797837.CEL.gz|GSM973057_PSM_52.CEL.gz|GSM973060_PSM_62.CEL.gz|GSM973048_PSM_22.CEL.gz|GSM973051_PSM_32.CEL.gz|GSM230332.CEL.gz|GSM787925.CEL.gz|GSM1372799_1615_033_32_m01_MB_200112.CEL.gz|GSM355342.CEL.gz|GSM1246268_P60_IncisorPulp_Denerved_3.CEL.gz|GSM355344.CEL.gz|GSM973058_PSM_53.CEL.gz|GSM973063_PSM_72.CEL.gz|GSM685750.CEL.gz|GSM1372794_1612_033_35_m01_MB_180112.CEL.gz|GSM1372795_1613_033_37_m01_MB_180112.CEL.gz|GSM787923.CEL.gz|GSM842157_NSC_1.CEL.gz|GSM1372806_1982_005_126_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM787919.CEL.gz|GSM973044_PSM_11.CEL.gz|GSM1372797_1610_033_30_m01_MB_180112.CEL.gz|GSM355346.CEL.gz|GSM230338.CEL.gz|GSM795458_Endoderm_SK7.CEL.gz|GSM686641_KSL_2.CEL.gz|GSM230336.CEL.gz|GSM1372791_1608_033_18_m01_MB_180112.CEL.gz|GSM230342.CEL.gz|GSM1372793_1614_033_26_m01_MB_200112.CEL.gz|GSM1372792_1609_033_20_m01_MB_180112.CEL.gz|GSM1372796_1616_033_39_m01_MB_200112.CEL.gz|GSM638138.cel.gz|GSM325454.CEL.gz|GSM94866.CEL.gz|GSM902311_DP_wildtype_2.CEL.gz|GSM94867.CEL.gz|GSM325452.CEL.gz|GSM325455.CEL.gz|GSM463599.CEL.gz|GSM94869.CEL.gz|GSM325451.CEL.gz|GSM94861.CEL.gz|GSM230341.CEL.gz|GSM902308_PE_mutant_1.CEL.gz|GSM902309_PE_mutant_2.CEL.gz|GSM94868.CEL.gz|GSM902306_PE_wildtype_1.CEL.gz|GSM902304_ES_mutant_1.CEL.gz|GSM902310_DP_wildtype_p_1.CEL.gz|GSM1535475_G1ME_GATA1s_1.CEL.gz|GSM842161_NSC_5.CEL.gz|GSM381313.CEL.gz|GSM795456_ESC_SK7.CEL.gz|GSM169459.CEL.gz|GSM315976.CEL.gz|GSM506231_TS2_MG430_2_09012001.CEL.gz|GSM94865.CEL.gz|GSM325450.CEL.gz|GSM169455.CEL.gz|GSM325453.CEL.gz|GSM325457.CEL.gz|GSM216535.CEL.gz|GSM902302_ES_wildtype_1.CEL.gz|GSM506232_TS5_MG430_2_09012002.CEL.gz|GSM902305_ES_mutant_2.CEL.gz|GSM94864.CEL.gz|GSM506233_XEN1_MG430_2_09012003.CEL.gz|GSM506234_XEN2_MG430_2_09012004.CEL.gz|GSM506225_J1G4Dex_01_MG430_2_09031901.CEL.gz|GSM902307_PE_wildtype_2.CEL.gz|GSM1246266_P60_IncisorPulp_Denerved_1.CEL.gz|GSM1246269_P60_IncisorPulp_Denerved_4.CEL.gz|GSM355343.CEL.gz|GSM1246270_P60_IncisorPulp_Denerved_5.CEL.gz|GSM1246271_P60_IncisorPulp_Control_1.CEL.gz|GSM463601.CEL.gz|GSM706700.CEL.gz|GSM706701.CEL.gz|GSM797838.CEL.gz|GSM1326661_Setd2.wt.2_Mouse430_2_.CEL.gz|GSM1326664_Setd2.ko.2_Mouse430_2_.CEL.gz|GSM381312.CEL.gz";

	protected String dendogrameq = "((((GSM210973.CEL.gz|(GSM210977.CEL.gz|GSM445689.CEL.gz))|(GSM172070.CEL.gz|((GSM210972.CEL.gz|(GSM1326663_Setd2.ko.1_Mouse430_2_.CEL.gz|GSM445690.CEL.gz))|((GSM1372808_1978_005_116_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|(GSM85009.CEL.gz|GSM85009Nutshel.CEL.gz))|(GSM210975.CEL.gz|GSM381319.CEL.gz)))))|(((GSM463598.CEL.gz|(GSM381321.CEL.gz|GSM381323.CEL.gz))|(GSM381320.CEL.gz|(((GSM381322.CEL.gz|(GSM1372809_1979_005_133_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM506223_ES_WTserum_free01_MG430_2_09063001.CEL.gz))|(GSM234775.CEL.gz|GSM381316.CEL.gz))|((GSM85008.CEL.gz|(GSM162863.cel.gz|GSM381310.CEL.gz))|((GSM381311.CEL.gz|(GSM210974.CEL.gz|GSM381317.CEL.gz))|(GSM506224_ES_WTserum_free02_MG430_2_09063002.CEL.gz|(GSM234774.CEL.gz|GSM381318.CEL.gz)))))))|(((((GSM315977.CEL.gz|(GSM445686.CEL.gz|GSM842158_NSC_2.CEL.gz))|(GSM1326665_Setd2.ko.3_Mouse430_2_.CEL.gz|GSM445687.CEL.gz))|(GSM381314.CEL.gz|(GSM506222_ES_TKOserum_free02_MG430_2_09063004.CEL.gz|GSM902303_ES_wildtype_2.CEL.gz)))|(((GSM1326660_Setd2.wt.1_Mouse430_2_.CEL.gz|(GSM445688.CEL.gz|GSM445691.CEL.gz))|(GSM1326662_Setd2.wt.3_Mouse430_2_.CEL.gz|GSM445685.CEL.gz))|((GSM445684.CEL.gz|(GSM234772.CEL.gz|GSM445683.CEL.gz))|((GSM797840.CEL.gz|GSM797842.CEL.gz)|(GSM172073.CEL.gz|GSM797839.CEL.gz)))))|((GSM325458.CEL.gz|GSM94860.CEL.gz)|(GSM381315.CEL.gz|(GSM234773.CEL.gz|GSM506221_ES_TKOserum_free01_MG430_2_09063003.CEL.gz))))))|((GSM381324.CEL.gz|(GSM1372807_1977_005_109_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM381325.CEL.gz))|((GSM347151.CEL.gz|GSM347155.CEL.gz)|((GSM94859.CEL.gz|(GSM238849.CEL.gz|GSM325459.CEL.gz))|(GSM522304.CEL.gz|(GSM347152.CEL.gz|GSM347156.CEL.gz))))))|((GSM795457_Ectoderm_SK7.CEL.gz|((((GSM638139.cel.gz|GSM638140.cel.gz)|((GSM638136.cel.gz|(GSM638135.cel.gz|GSM638137.cel.gz))|((GSM638148.cel.gz|(GSM638147.cel.gz|GSM638151.cel.gz))|(GSM638130.cel.gz|(GSM638131.cel.gz|GSM638150.cel.gz)))))|(GSM506226_J1G4Dex_02_MG430_2_09031902.CEL.gz|(((GSM94862.CEL.gz|GSM94863.CEL.gz)|(GSM638142.cel.gz|GSM638144.cel.gz))|(GSM638129.cel.gz|(GSM638141.cel.gz|GSM638145.cel.gz)))))|(((GSM169456.CEL.gz|GSM169460.CEL.gz)|(GSM325456.CEL.gz|GSM94858.CEL.gz))|((GSM638133.cel.gz|(GSM638146.cel.gz|(GSM347150.CEL.gz|GSM347154.CEL.gz)))|((GSM638143.cel.gz|GSM638152.cel.gz)|(GSM154465.CEL.gz|(GSM347153.CEL.gz|GSM638134.cel.gz)))))))|((((GSM685746.CEL.gz|(GSM685749.CEL.gz|((GSM973047_PSM_21.CEL.gz|GSM973054_PSM_42.CEL.gz)|(GSM973052_PSM_33.CEL.gz|(GSM685745.CEL.gz|GSM973055_PSM_43.CEL.gz)))))|(GSM787921.CEL.gz|((GSM842160_NSC_4.CEL.gz|(GSM685747.CEL.gz|GSM973045_PSM_12.CEL.gz))|(GSM1372798_1611_033_31_m01_MB_180112.CEL.gz|(GSM787920.CEL.gz|GSM842159_NSC_3.CEL.gz)))))|(((GSM902313_DP_mutant_2.CEL.gz|(GSM685748.CEL.gz|GSM902312_DP_mutant_p_1.CEL.gz))|((GSM1372804_1980_005_118_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM1372805_1981a_005_122_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz)|((GSM973053_PSM_41.CEL.gz|(GSM795459_Paraxial_mesoderm_SK7.CEL.gz|GSM973046_PSM_13.CEL.gz))|(GSM706697.CEL.gz|(GSM787922.CEL.gz|(GSM787918.CEL.gz|GSM787924.CEL.gz))))))|(((GSM706698.CEL.gz|GSM797837.CEL.gz)|(GSM973057_PSM_52.CEL.gz|(GSM973060_PSM_62.CEL.gz|(GSM973048_PSM_22.CEL.gz|GSM973051_PSM_32.CEL.gz))))|((GSM230332.CEL.gz|((GSM787925.CEL.gz|(GSM1372799_1615_033_32_m01_MB_200112.CEL.gz|GSM355342.CEL.gz))|(GSM1246268_P60_IncisorPulp_Denerved_3.CEL.gz|GSM355344.CEL.gz)))|(((GSM973058_PSM_53.CEL.gz|GSM973063_PSM_72.CEL.gz)|(GSM685750.CEL.gz|(GSM1372794_1612_033_35_m01_MB_180112.CEL.gz|(GSM1372795_1613_033_37_m01_MB_180112.CEL.gz|GSM787923.CEL.gz))))|((GSM842157_NSC_1.CEL.gz|(GSM1372806_1982_005_126_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM787919.CEL.gz))|(GSM973044_PSM_11.CEL.gz|(GSM1372797_1610_033_30_m01_MB_180112.CEL.gz|GSM355346.CEL.gz))))))))|(((((GSM230338.CEL.gz|GSM795458_Endoderm_SK7.CEL.gz)|(GSM686641_KSL_2.CEL.gz|(GSM230336.CEL.gz|(GSM1372791_1608_033_18_m01_MB_180112.CEL.gz|GSM230342.CEL.gz))))|(GSM1372793_1614_033_26_m01_MB_200112.CEL.gz|(GSM1372792_1609_033_20_m01_MB_180112.CEL.gz|GSM1372796_1616_033_39_m01_MB_200112.CEL.gz)))|(((GSM638138.cel.gz|(GSM325454.CEL.gz|GSM94866.CEL.gz))|((GSM902311_DP_wildtype_2.CEL.gz|GSM94867.CEL.gz)|(GSM325452.CEL.gz|GSM325455.CEL.gz)))|(((GSM463599.CEL.gz|GSM94869.CEL.gz)|(GSM325451.CEL.gz|GSM94861.CEL.gz))|((GSM230341.CEL.gz|(GSM902308_PE_mutant_1.CEL.gz|(GSM902309_PE_mutant_2.CEL.gz|GSM94868.CEL.gz)))|((GSM902306_PE_wildtype_1.CEL.gz|(GSM902304_ES_mutant_1.CEL.gz|GSM902310_DP_wildtype_p_1.CEL.gz))|(GSM1535475_G1ME_GATA1s_1.CEL.gz|GSM842161_NSC_5.CEL.gz))))))|(((GSM381313.CEL.gz|(GSM795456_ESC_SK7.CEL.gz|(GSM169459.CEL.gz|(GSM315976.CEL.gz|GSM506231_TS2_MG430_2_09012001.CEL.gz))))|((GSM94865.CEL.gz|(GSM325450.CEL.gz|(GSM169455.CEL.gz|(GSM325453.CEL.gz|GSM325457.CEL.gz))))|(((GSM216535.CEL.gz|GSM902302_ES_wildtype_1.CEL.gz)|(GSM506232_TS5_MG430_2_09012002.CEL.gz|GSM902305_ES_mutant_2.CEL.gz))|(GSM94864.CEL.gz|((GSM506233_XEN1_MG430_2_09012003.CEL.gz|GSM506234_XEN2_MG430_2_09012004.CEL.gz)|(GSM506225_J1G4Dex_01_MG430_2_09031901.CEL.gz|GSM902307_PE_wildtype_2.CEL.gz))))))|(((GSM1246266_P60_IncisorPulp_Denerved_1.CEL.gz|GSM1246269_P60_IncisorPulp_Denerved_4.CEL.gz)|(GSM355343.CEL.gz|(GSM1246270_P60_IncisorPulp_Denerved_5.CEL.gz|GSM1246271_P60_IncisorPulp_Control_1.CEL.gz)))|((GSM463601.CEL.gz|(GSM706700.CEL.gz|GSM706701.CEL.gz))|(GSM797838.CEL.gz|(GSM1326661_Setd2.wt.2_Mouse430_2_.CEL.gz|(GSM1326664_Setd2.ko.2_Mouse430_2_.CEL.gz|GSM381312.CEL.gz)))))))))";
	protected String dendogramelablesq = "GSM210973.CEL.gz|GSM210977.CEL.gz|GSM445689.CEL.gz|GSM172070.CEL.gz|GSM210972.CEL.gz|GSM1326663_Setd2.ko.1_Mouse430_2_.CEL.gz|GSM445690.CEL.gz|GSM1372808_1978_005_116_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM85009.CEL.gz|GSM85009Nutshel.CEL.gz|GSM210975.CEL.gz|GSM381319.CEL.gz|GSM463598.CEL.gz|GSM381321.CEL.gz|GSM381323.CEL.gz|GSM381320.CEL.gz|GSM381322.CEL.gz|GSM1372809_1979_005_133_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM506223_ES_WTserum_free01_MG430_2_09063001.CEL.gz|GSM234775.CEL.gz|GSM381316.CEL.gz|GSM85008.CEL.gz|GSM162863.cel.gz|GSM381310.CEL.gz|GSM381311.CEL.gz|GSM210974.CEL.gz|GSM381317.CEL.gz|GSM506224_ES_WTserum_free02_MG430_2_09063002.CEL.gz|GSM234774.CEL.gz|GSM381318.CEL.gz|GSM315977.CEL.gz|GSM445686.CEL.gz|GSM842158_NSC_2.CEL.gz|GSM1326665_Setd2.ko.3_Mouse430_2_.CEL.gz|GSM445687.CEL.gz|GSM381314.CEL.gz|GSM506222_ES_TKOserum_free02_MG430_2_09063004.CEL.gz|GSM902303_ES_wildtype_2.CEL.gz|GSM1326660_Setd2.wt.1_Mouse430_2_.CEL.gz|GSM445688.CEL.gz|GSM445691.CEL.gz|GSM1326662_Setd2.wt.3_Mouse430_2_.CEL.gz|GSM445685.CEL.gz|GSM445684.CEL.gz|GSM234772.CEL.gz|GSM445683.CEL.gz|GSM797840.CEL.gz|GSM797842.CEL.gz|GSM172073.CEL.gz|GSM797839.CEL.gz|GSM325458.CEL.gz|GSM94860.CEL.gz|GSM381315.CEL.gz|GSM234773.CEL.gz|GSM506221_ES_TKOserum_free01_MG430_2_09063003.CEL.gz|GSM381324.CEL.gz|GSM1372807_1977_005_109_palatal_NS_m01_AK_290114_Mouse430_2.CEL.gz|GSM381325.CEL.gz|GSM347151.CEL.gz|GSM347155.CEL.gz|GSM94859.CEL.gz|GSM238849.CEL.gz|GSM325459.CEL.gz|GSM522304.CEL.gz|GSM347152.CEL.gz|GSM347156.CEL.gz|GSM795457_Ectoderm_SK7.CEL.gz|GSM638139.cel.gz|GSM638140.cel.gz|GSM638136.cel.gz|GSM638135.cel.gz|GSM638137.cel.gz|GSM638148.cel.gz|GSM638147.cel.gz|GSM638151.cel.gz|GSM638130.cel.gz|GSM638131.cel.gz|GSM638150.cel.gz|GSM506226_J1G4Dex_02_MG430_2_09031902.CEL.gz|GSM94862.CEL.gz|GSM94863.CEL.gz|GSM638142.cel.gz|GSM638144.cel.gz|GSM638129.cel.gz|GSM638141.cel.gz|GSM638145.cel.gz|GSM169456.CEL.gz|GSM169460.CEL.gz|GSM325456.CEL.gz|GSM94858.CEL.gz|GSM638133.cel.gz|GSM638146.cel.gz|GSM347150.CEL.gz|GSM347154.CEL.gz|GSM638143.cel.gz|GSM638152.cel.gz|GSM154465.CEL.gz|GSM347153.CEL.gz|GSM638134.cel.gz|GSM685746.CEL.gz|GSM685749.CEL.gz|GSM973047_PSM_21.CEL.gz|GSM973054_PSM_42.CEL.gz|GSM973052_PSM_33.CEL.gz|GSM685745.CEL.gz|GSM973055_PSM_43.CEL.gz|GSM787921.CEL.gz|GSM842160_NSC_4.CEL.gz|GSM685747.CEL.gz|GSM973045_PSM_12.CEL.gz|GSM1372798_1611_033_31_m01_MB_180112.CEL.gz|GSM787920.CEL.gz|GSM842159_NSC_3.CEL.gz|GSM902313_DP_mutant_2.CEL.gz|GSM685748.CEL.gz|GSM902312_DP_mutant_p_1.CEL.gz|GSM1372804_1980_005_118_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM1372805_1981a_005_122_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM973053_PSM_41.CEL.gz|GSM795459_Paraxial_mesoderm_SK7.CEL.gz|GSM973046_PSM_13.CEL.gz|GSM706697.CEL.gz|GSM787922.CEL.gz|GSM787918.CEL.gz|GSM787924.CEL.gz|GSM706698.CEL.gz|GSM797837.CEL.gz|GSM973057_PSM_52.CEL.gz|GSM973060_PSM_62.CEL.gz|GSM973048_PSM_22.CEL.gz|GSM973051_PSM_32.CEL.gz|GSM230332.CEL.gz|GSM787925.CEL.gz|GSM1372799_1615_033_32_m01_MB_200112.CEL.gz|GSM355342.CEL.gz|GSM1246268_P60_IncisorPulp_Denerved_3.CEL.gz|GSM355344.CEL.gz|GSM973058_PSM_53.CEL.gz|GSM973063_PSM_72.CEL.gz|GSM685750.CEL.gz|GSM1372794_1612_033_35_m01_MB_180112.CEL.gz|GSM1372795_1613_033_37_m01_MB_180112.CEL.gz|GSM787923.CEL.gz|GSM842157_NSC_1.CEL.gz|GSM1372806_1982_005_126_rNCSC_m01_AK_290114_Mouse430_2.CEL.gz|GSM787919.CEL.gz|GSM973044_PSM_11.CEL.gz|GSM1372797_1610_033_30_m01_MB_180112.CEL.gz|GSM355346.CEL.gz|GSM230338.CEL.gz|GSM795458_Endoderm_SK7.CEL.gz|GSM686641_KSL_2.CEL.gz|GSM230336.CEL.gz|GSM1372791_1608_033_18_m01_MB_180112.CEL.gz|GSM230342.CEL.gz|GSM1372793_1614_033_26_m01_MB_200112.CEL.gz|GSM1372792_1609_033_20_m01_MB_180112.CEL.gz|GSM1372796_1616_033_39_m01_MB_200112.CEL.gz|GSM638138.cel.gz|GSM325454.CEL.gz|GSM94866.CEL.gz|GSM902311_DP_wildtype_2.CEL.gz|GSM94867.CEL.gz|GSM325452.CEL.gz|GSM325455.CEL.gz|GSM463599.CEL.gz|GSM94869.CEL.gz|GSM325451.CEL.gz|GSM94861.CEL.gz|GSM230341.CEL.gz|GSM902308_PE_mutant_1.CEL.gz|GSM902309_PE_mutant_2.CEL.gz|GSM94868.CEL.gz|GSM902306_PE_wildtype_1.CEL.gz|GSM902304_ES_mutant_1.CEL.gz|GSM902310_DP_wildtype_p_1.CEL.gz|GSM1535475_G1ME_GATA1s_1.CEL.gz|GSM842161_NSC_5.CEL.gz|GSM381313.CEL.gz|GSM795456_ESC_SK7.CEL.gz|GSM169459.CEL.gz|GSM315976.CEL.gz|GSM506231_TS2_MG430_2_09012001.CEL.gz|GSM94865.CEL.gz|GSM325450.CEL.gz|GSM169455.CEL.gz|GSM325453.CEL.gz|GSM325457.CEL.gz|GSM216535.CEL.gz|GSM902302_ES_wildtype_1.CEL.gz|GSM506232_TS5_MG430_2_09012002.CEL.gz|GSM902305_ES_mutant_2.CEL.gz|GSM94864.CEL.gz|GSM506233_XEN1_MG430_2_09012003.CEL.gz|GSM506234_XEN2_MG430_2_09012004.CEL.gz|GSM506225_J1G4Dex_01_MG430_2_09031901.CEL.gz|GSM902307_PE_wildtype_2.CEL.gz|GSM1246266_P60_IncisorPulp_Denerved_1.CEL.gz|GSM1246269_P60_IncisorPulp_Denerved_4.CEL.gz|GSM355343.CEL.gz|GSM1246270_P60_IncisorPulp_Denerved_5.CEL.gz|GSM1246271_P60_IncisorPulp_Control_1.CEL.gz|GSM463601.CEL.gz|GSM706700.CEL.gz|GSM706701.CEL.gz|GSM797838.CEL.gz|GSM1326661_Setd2.wt.2_Mouse430_2_.CEL.gz|GSM1326664_Setd2.ko.2_Mouse430_2_.CEL.gz|GSM381312.CEL.gz";
	
	protected double[][] pc1n2;
	protected String[] pointNames = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[] pointColors = { "a", "b", "c", "d", "e", "f", "g" };
	
	//alternative colors
	protected String[] pointTypes = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointTypesLegend = { };
	protected String[] pointTypesExtra = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[] pointDatabaseIds = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[] pointTypesAlt = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointTypesLegendAlt = { };
	protected String[] pointTypesAltExtra = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[] pointCondition = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointConditionLegend = { };
	protected String[] pointConditionExtra = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[] pointWildtype = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointWildtypeLegend = { };
	protected String[] pointWildtypeExtra = { "a", "b", "c", "d", "e", "f", "g" };

	protected String[] pointSuper = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointSuperLegend = { };
	protected String[] pointSuperExtra = { "a", "b", "c", "d", "e", "f", "g" };
	
	
	protected String[] pointPureStem = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointPureStemLegend = { };
	protected String[] pointPureStemExtra = { "a", "b", "c", "d", "e", "f", "g" };
	
	
	protected String[] pointClass = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointClassLegend = { };
	protected String[] pointClassExtra = { "a", "b", "c", "d", "e", "f", "g" };
	
	protected String[] pointSM = { "a", "b", "c", "d", "e", "f", "g" };
	protected String[][] pointSMLegend = { };
	protected String[] pointSMExtra = { "a", "b", "c", "d", "e", "f", "g" };
	
//	protected String[] invivoorvitro = { "a", "b", "c", "d", "e", "f", "g" };
//	protected String[] wildtype = { "a", "b", "c", "d", "e", "f", "g" };
//	protected String[] samplecondition = { "a", "b", "c", "d", "e", "f", "g" };
	
	protected String heatmaprows;
	protected String heatmaprowsnames;
	protected String[] heatlabelcolors = { "" };
	protected String[] heatlabeltypes = { "" };
	protected String[] heatsupertypes = { "" };
	
	protected String heatmaprowsq;
	protected String heatmaprowsnamesq;
	protected String[] heatlabelcolorsq = { "" };
	protected String[] heatlabeltypesq = { "" };
	protected String[] heatsupertypesq = { "" };
	
	protected String[][] buttonsforcorr = new String[][]{};
	
	protected boolean showResults = false;
	protected boolean showNotEnogthGenes = false;
	protected boolean showNoGeneMatches = false;
	protected boolean showNormalizationError = false;
	
	protected String searchData = "";
	protected String[][] searchResults = new String[][]{};
	protected String[][] searchDetails = new String[][]{};
	protected String searchResultsExtra = "Galaxan years";
	
	protected String userdefinedid = "";
	
	protected String platform = "mouse4302";
	protected String platform2 = "mouse4302";
	
	protected String unfound = "";
	
	protected double pvPC1 = 0;
	protected double pvPC2 = 0;
	
	protected boolean truncatethedata = false;

	protected String[][] sources;
	
	protected String justthis;
	
	protected int activeIndex=0;
	
	
	
	
	
	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public boolean isShowNormalizationError() {
		return showNormalizationError;
	}


	public void setShowNormalizationError(boolean showNormalizationError) {
	}
	
	public String getJustthis() {
		return justthis;
	}


	public String[][] getSources() {
		return sources;
	}
	
	
//	public GraphBean() {
//		super();
//	}
	
	public String getPvPC1() {
		return String.valueOf((RAccess.round(pvPC1,1)));
	}

	public boolean isTruncatethedata() {
		return truncatethedata;
	}

	public void setTruncatethedata(boolean truncatethedata) {
	}

	public void setPvPC1(String pvPC1) {
	}

	public String getPvPC2() {
		return String.valueOf((RAccess.round(pvPC2,1)));
	}

	public void setPvPC2(String pvPC2) {
	}
	


	public String getUnfound() {
		return unfound;
	}

	public void setUnfound(String unfound) {
	}
	
	public String getPlatform2() {
		return platform2;
	}

	public void setPlatform2(String platform2) {
		this.platform2 = platform2;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String[][] getButtonsforcorr() {
		return buttonsforcorr;
	}

	public void setButtonsforcorr(String[][] buttonsforcorr) {
	}

	public String getUserdefinedid() {
		return userdefinedid;
	}

	public void setUserdefinedid(String userdefinedid) {
	}

	public String[][] getSampleNIds() {
		return this.startUpBean.getSampleNIds();
	}

	public void setSampleNIds(String[][] sampleNIds) {
	}

	public String[][] getSearchDetails() {
		return searchDetails;
	}

	public void setSearchDetails(String[][] searchDetails) {
		this.searchDetails = searchDetails;
	}

	public String[][] getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(String[][] searchResults) {
	}

	public String getSearchData() {
		return searchData;
	}
	
	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}
	
	protected String[] breakQuerry(String querry)
	{
		if(querry==null || querry.length()==0)
		{
			return new String[]{};
		}
		
		StringTokenizer st = new StringTokenizer(querry, " \t\n\r\f,");
		
		String[] res;
		
		if(st.countTokens()>100)
		{
			this.truncatethedata=true;
			res = new String[100];
		}
		else
		{
			this.truncatethedata=false;
			res = new String[st.countTokens()];
		}
		
		int u = 0;
		while(st.hasMoreTokens() && u<100)
		{
			res[u] = st.nextToken();
			u++;
		}
		
		return res;
	}
	
	protected Object[] breakQuerryEntrez(String querry)
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		if(querry==null || querry.length()==0)
		{
			return new String[]{};
		}
		
		StringTokenizer st = new StringTokenizer(querry, " \t\n\r\f,");
		String[] res = new String[st.countTokens()];
		
		int u = 0;
		while(st.hasMoreTokens())
		{
			res[u] = st.nextToken();
			
			if(StringUtils.isNumeric(res[u]))
				temp.add(new Integer(res[u]));
			
			
			u++;
		}
		
		return new Object[]{res, temp.toArray(new Integer[]{})};
	}
	
	public String searchDatabase() throws Exception 
	{
		Object[] dat = this.breakQuerryEntrez(this.searchData);
		
		dat = DatabaseAccess.searchForGene(this.startUpBean.getSessionFactory(), (String[])dat[0], (Integer[])dat[1]);
		
		if(dat!=null)
		{
			if(((String[][])dat[1]).length>0)
			{
				this.searchResults = (String[][])dat[0];
				this.searchDetails = (String[][])dat[1];
			}
			else
			{
				this.searchResults = new String[][]{new String[]{"No match found"}};
			}
		}
		else
		{
			this.searchResults = new String[][]{new String[]{"No match found"}};
		}
		
		return null;
	}
	
	public String getHeatlabelcolors() {

		String res = "";

		if (heatlabelcolors != null && heatlabelcolors.length > 0) {
			res = "" + heatlabelcolors[0];

			for (int i = 1; i < heatlabelcolors.length; i++) {
				res += "|" + heatlabelcolors[i];
			}
		}

		return res;
	}

	public void setHeatlabelcolors(String heatlabelcolors) {
		this.heatlabelcolors = heatlabelcolors.split("\\|");
	}
	
	public String getHeatlabelcolorsq() {

		String res = "";

		if (heatlabelcolorsq != null && heatlabelcolorsq.length > 0) {
			res = "" + heatlabelcolorsq[0];

			for (int i = 1; i < heatlabelcolorsq.length; i++) {
				res += "|" + heatlabelcolorsq[i];
			}
		}

		return res;
	}

	public void setHeatlabelcolorsq(String heatlabelcolors) {
		this.heatlabelcolorsq = heatlabelcolors.split("\\|");
	}
	
	public String getHeatlabeltypes() {

		String res = "";

		if (heatlabeltypes != null && heatlabeltypes.length > 0) {
			res = "" + heatlabeltypes[0];

			for (int i = 1; i < heatlabeltypes.length; i++) {
				res += "|" + heatlabeltypes[i];
			}
		}

		return res;
	}

	public void setHeatlabeltypes(String heatlabeltypes) {
	}
	
	public String getHeatsupertypes() {

		String res = "";

		if (heatsupertypes != null && heatsupertypes.length > 0) {
			res = "" + heatsupertypes[0];

			for (int i = 1; i < heatsupertypes.length; i++) {
				res += "|" + heatsupertypes[i];
			}
		}

		return res;
	}

	public void setHeatsupertypes(String heatsupertypes) {
	}
	
	public String getHeatlabeltypesq() {

		String res = "";

		if (heatlabeltypesq != null && heatlabeltypesq.length > 0) {
			res = "" + heatlabeltypesq[0];

			for (int i = 1; i < heatlabeltypesq.length; i++) {
				res += "|" + heatlabeltypesq[i];
			}
		}

		return res;
	}

	public void setHeatlabeltypesq(String heatlabeltypes) {
	}
	
	public String getHeatsupertypesq() {

		String res = "";

		if (heatsupertypesq != null && heatsupertypesq.length > 0) {
			res = "" + heatsupertypesq[0];

			for (int i = 1; i < heatsupertypesq.length; i++) {
				res += "|" + heatsupertypesq[i];
			}
		}

		return res;
	}

	public void setHeatsupertypesq(String heatsupertypes) {
	}
	
	public boolean isShowNotEnogthGenes() {
		return showNotEnogthGenes;
	}

	public void setShowNotEnogthGenes(boolean showNotEnogthGenes) {
	}

	public boolean isShowNoGeneMatches() {
		return showNoGeneMatches;
	}

	public void setShowNoGeneMatches(boolean showNoGeneMatches) {
	}

	public boolean isShowResults() {
		return showResults;
	}

	public void setShowResults(boolean showResults) {
		this.showResults = showResults;
	}

	public String getHeatmaprowsnames() {
		return heatmaprowsnames;
	}

	public void setHeatmaprowsnames(String heatmaprowsnames) {
		this.heatmaprowsnames = heatmaprowsnames;
	}

	public String getHeatmaprowsnamesq() {
		return heatmaprowsnamesq;
	}

	public void setHeatmaprowsnamesq(String heatmaprowsnamesq) {
		this.heatmaprowsnamesq = heatmaprowsnames;
	}
	
	public String getHeatmaprows() {
		return heatmaprows;
	}

	public void setHeatmaprows(String heatmaprows) {
		this.heatmaprows = heatmaprows;
	}
	
	public String getHeatmaprowsq() {
		return heatmaprowsq;
	}

	public void setHeatmaprowsq(String heatmaprows) {
		this.heatmaprowsq = heatmaprows;
	}
	
	public String getPc1() {
		return this.getPc(0);
	}

	public void setPc1(String pc1) {
		setPc(0, pc1);
	}

	public String getPc2() {
		return this.getPc(1);
	}

	public void setPc2(String pc2) {
		setPc(1, pc2);
	}

	protected String arrayToString(String[] data) {

		StringBuffer res = new StringBuffer("");

		if (data != null && data.length > 0) {
			res.append(data[0]);

			for (int i = 1; i < data.length; i++) {
				res.append("|");
				res.append(data[i]);
			}
		}
		
		return res.toString();
	}
	
	protected String arrayToString(String[][] data) {

		StringBuffer res = new StringBuffer("");

		if (data != null && data.length > 0) {
			res.append(data[0][0]);
			res.append("*");
			res.append(data[0][1]);
			for (int i = 1; i < data.length; i++) {
				res.append("|");
				res.append(data[i][0]);
				res.append("*");
				res.append(data[i][1]);
			}
		}
		
		return res.toString();
	}
	
	public String getPointColors() {
		return this.arrayToString(pointColors);
	}

	public void setPointColors(String pointColors) {
		this.pointColors = pointColors.split("\\|");
	}
	
	public String getPointTypes() {
		return this.arrayToString(pointTypes);
	}
	
	public void setPointTypes(String arr) {
	}
	
	
	
	
	
	public String getPointTypesAlt() {
		return this.arrayToString(pointTypesAlt);
	}

	public void setPointTypesAlt(String pointTypesAlt) {
	}

	public String getPointTypesLegendAlt() {
		return this.arrayToString(pointTypesLegendAlt);
	}

	public void setPointTypesLegendAlt(String pointTypesLegendAlt) {
	}
	
	
	
	
	
	

	public String getPointNames() {
		return this.arrayToString(pointNames);
	}

	public void setPointNames(String pointNames) {
		
		this.pointNames = pointNames.split("\\|");
	}

	public String getPointCondition() {
		return this.arrayToString(pointCondition);
	}

	public void setPointCondition(String notused) {
	}

	public String getPointConditionLegend() {
		return this.arrayToString(pointConditionLegend);
	}

	public void setPointConditionLegend(String pointConditionLegend) {
	}

	public String getPointWildtype() {
		return this.arrayToString(pointWildtype);
	}

	public void setPointWildtype(String pointWildtype) {
	}

	public String getPointWildtypeLegend() {
		return this.arrayToString(pointWildtypeLegend);
	}

	public void setPointWildtypeLegend(String pointWildtypeLegend) {
	}
	
	public String getPointTypesLegend() {
		return this.arrayToString(pointTypesLegend);
	}

	public void setPointTypesLegend(String pointTypesLegend) {
	}
	
	
	public String getPointTypesExtra() {
		return this.arrayToString(pointTypesExtra);
	}

	public void setPointTypesExtra(String pointTypesExtra) {
	}
	
	public String getPointDatabaseIds() {
		return this.arrayToString(pointDatabaseIds);
	}

	public void setPointDatabaseIds(String pointDatabaseIds) {
	}

	public String getPointTypesAltExtra() {
		return this.arrayToString(pointTypesAltExtra);
	}

	public void setPointTypesAltExtra(String pointTypesAltExtra) {
	}

	public String getPointConditionExtra() {
		return this.arrayToString(pointConditionExtra);
	}

	public void setPointConditionExtra(String pointConditionExtra) {
	}

	public String getPointWildtypeExtra() {
		return this.arrayToString(pointWildtypeExtra);
	}

	public void setPointWildtypeExtra(String pointWildtypeExtra) {
	}
	
	public String getPointPureStemLegend() {
		return this.arrayToString(pointPureStemLegend);
	}

	public void setPointPureStemLegend(String pointPureStemLegend) {
	}

	
	
	
	public String getPointSM() {
		return this.arrayToString(pointSM);
	}

	public void setPointSM(String pointClass) {
	}

	public String getPointSMLegend() {
		return this.arrayToString(pointSMLegend);
	}

	public void setPointSMLegend(String pointClassLegend) {
	}

	public String getPointSMExtra() {
		return this.arrayToString(pointSMExtra);
	}

	public void setPointSMExtra(String pointClassExtra) {
	}
	
	public String getPointClass() {
		return this.arrayToString(pointClass);
	}

	public void setPointClass(String pointClass) {
	}

	public String getPointClassLegend() {
		return this.arrayToString(pointClassLegend);
	}

	public void setPointClassLegend(String pointClassLegend) {
	}

	public String getPointClassExtra() {
		return this.arrayToString(pointClassExtra);
	}

	public void setPointClassExtra(String pointClassExtra) {
	}

	
	
	public String getPointPureStemExtra() {
		return this.arrayToString(pointPureStemExtra);
	}

	public void setPointPureStemExtra(String pointPureStemExtra) {
	}
	
	public String getPointPureStem() {
		return this.arrayToString(pointPureStem);
	}

	public void setPointPureStem(String pointPureStem) {
	}

	protected String getPc(int a) {

		String res = "";
		
		if (pc1n2 != null && pc1n2.length > 0) {
			res = "" + pc1n2[a][0];

			for (int i = 1; i < pc1n2[a].length; i++) {
				res += "|" + pc1n2[a][i];
			}

		}

		return res;
	}

	protected void setPc(int a, String pc) {
		if(pc!=null && pc.length()>0)
		{
			if (pc1n2 == null) pc1n2 = new double[2][];
			
			String[] temp = pc.split("\\|");
			
			pc1n2[a] = new double[temp.length];
			
			for(int i=0;i<temp.length;i++)
			{
				pc1n2[a][i] = new Double(temp[i]).doubleValue();
			}
		}
	}
	
	public String getDendograme() {
		return dendograme;
	}

	public void setDendograme(String dendograme) {
		this.dendograme = dendograme;
	}

	public String getDendogramelables() {
		return dendogramelables;
	}

	public void setDendogramelables(String dendogramelables) {
		this.dendogramelables = dendogramelables;
	}
	
	public String getDendogrameq() {
		return dendogrameq;
	}

	public void setDendogrameq(String dendograme) {
		this.dendogrameq = dendograme;
	}

	public String getDendogramelablesq() {
		return dendogramelablesq;
	}

	public void setDendogramelablesq(String dendogramelables) {
		this.dendogramelablesq = dendogramelables;
	}
	
	public String getInputedIds() {
		
		String res = "";
		
		for(int a=0;a<this.inputedIds.length;a++)
		{
			if(a>0) res += "|";
			
			for(int b=0;b<this.inputedIds[a].length;b++)
			{
				if(b==0) res += this.inputedIds[a][b];
				else res += "/"+this.inputedIds[a][b];
			}
		}
		
		return res;
	}

	public void setInputedIds(String inputedIds) {
		
		String[] temp = inputedIds.split("\\|");
		
		this.inputedIds = new String[temp.length][];
		
		for(int i=0;i<this.inputedIds.length;i++)
		{
			this.inputedIds[i] = temp[i].split("/");
		}
//		this.inputedIds = inputedIds;
	}


	public StartUpBean getStartUpBean() {
		return startUpBean;
	}

	public void setStartUpBean(StartUpBean startUpBean) {
		this.startUpBean = startUpBean;
	}
	
	public String[][][][] getDataTypes() {
		int i=-1;
		
		if(this.platform.equals("mouse4302")) i = 0;
		else if(this.platform.equals("hgu133plus2")) i = 1;
		
		return this.startUpBean.getSampleTypes(i);
	}

	public void setDataTypes(String[] dataTypes) {
	}
	
	public String[][][][] getDataTypes2() { //TODO: change here
		int i=-1;
		
		if(this.platform2.equals("mouse4302")) i = 0;
		else if(this.platform2.equals("hgu133plus2")) i = 1;
		
		return this.startUpBean.getSampleTypes(i);
	}

	public void setDataTypes2(String[] dataTypes) {
	}
	
	public String getSetToUse() {
		return setToUse;
	}

	public void setSetToUse(String setToUse) {
		this.setToUse = setToUse;
	}
	
	public String getMarkersToUse() {

		String res = "";

		if (markersToUse != null && markersToUse.length > 0) {
			res = "" + markersToUse[0];

			for (int i = 1; i < markersToUse.length; i++) {
				res += "|" + markersToUse[i];
			}
		}
		
		return res;
	}

	public void setMarkersToUse(String markersToUse) {
		
		if (markersToUse != null) {
			this.markersToUse = markersToUse.split("\\|");
		} else
			this.markersToUse = new String[] {};
	}
	
	public List<String> completeText(String query) {
		FacesContext context = FacesContext.getCurrentInstance();
		String setsToUse = context.getExternalContext()
				.getRequestParameterMap().get("sc:tab:newEntry_input");
		this.setMarkersToUse(setsToUse);

		List<String> results = new ArrayList<String>();

		String[] refusethis = this.startUpBean.listOfAllIds(markersToUse);

		String[] entlist = this.startUpBean.getMasterIdList();

		if (query.equals("")) {

			int z = 0;

			for (int i = 0; i < entlist.length && z < 10; i++) {
				if (!this.completeText_aux(entlist[i], refusethis)) {
					results.add(entlist[i]);
					z++;
				}
			}
		} else {
			int z = 0;

			for (int i = 0; i < entlist.length && z < 10; i++) {

				if (entlist[i].matches("(?i).*" + query + ".*")
						&& !this.completeText_aux(entlist[i], refusethis)) {
					results.add(entlist[i]);
					z++;
				}

			}
		}

		return results;
	}

	protected boolean completeText_aux(String id, String[] nolist) {

		boolean res = false;

		for (int i = 0; !res && i < nolist.length; i++) {

			res = nolist[i].equals(id);

		}

		return res;
	}

	public String getUseThisDataTypes() {
		String res = "";

		if (useThisDataTypes != null && useThisDataTypes.length > 0) {
			res = "" + useThisDataTypes[0];

			for (int i = 1; i < useThisDataTypes.length; i++) {
				res += "|" + useThisDataTypes[i];
			}
		}

		return res;
	}

	public void setUseThisDataTypes(String useThisDataTypes) {
		if (useThisDataTypes != null) {
			this.useThisDataTypes = useThisDataTypes.split("\\|");
		} else
			this.useThisDataTypes = new String[] {};
	}

	public String getEntrezIdsMatrix() {

		String res = this.allExperimentsNames[0];

		for (int i = 1; i < this.allExperimentsNames.length; i++)
			res += "\\|" + this.allExperimentsNames[i];

		res += "/";

		for (int a = 0; a < this.entrezIdsMatrix.length; a++) {
			if (a > 0)
				res += "*" + entrezIdsMatrix[a][0];
			else
				res += entrezIdsMatrix[a][0];
			for (int b = 1; b < this.entrezIdsMatrix[a].length; b++) {
				res += "|" + entrezIdsMatrix[a][b];
			}
		}

		return res;
	}

	public void setEntrezIdsMatrix(String entrezIdsMatrix) {

		String[] ptemp = entrezIdsMatrix.split("/");

		this.allExperimentsNames = ptemp[0].split("\\|");
		
		String[] temp = ptemp[1].split("\\*");

		this.entrezIdsMatrix = new double[temp.length][];

		for (int a = 0; a < this.entrezIdsMatrix.length; a++) {
			String[] temp2 = temp[a].split("\\|");

			this.entrezIdsMatrix[a] = new double[temp2.length];

			for (int b = 0; b < temp2.length; b++) {
				this.entrezIdsMatrix[a][b] = new Double(temp2[b]).doubleValue();
			}

		}
	}
	
	protected Object[] getSampleObject()
	{
		//0-entrez 1-inputed
		
		this.markersToUse = this.breakQuerry(this.searchData);
		
		if(this.markersToUse.length<2)
		{
			this.showNoGeneMatches = false;
			this.showNotEnogthGenes = true;
			this.pc1n2 = null;
			this.activeIndex = 1;
			return null;
		}
		
		int aim = 1;
		
		if(platform.equals("mouse4302")) aim = 1;
		else aim = 2;
		
		Object[] tinputedIds = DatabaseAccess.convertToEntrez(this.startUpBean.getSessionFactory(), this.markersToUse, aim);
		
		this.unfound = ((String[])tinputedIds[2])[0];
		
		Integer[] entrezs = (Integer[])tinputedIds[0];
		
		if(entrezs.length<2)
		{
			this.showNoGeneMatches = true;
			this.showNotEnogthGenes = false;
			this.pc1n2 = null;
			this.activeIndex = 1;
			return null;
		}
		
		
		SampleObject ras = DatabaseAccess.extractExpressionTable(this.startUpBean.getSessionFactory(),
			new String[]{this.platform}, useThisDataTypes, entrezs);
		
		
		StringBuffer temp = new StringBuffer();
		
		for(int i=0;i<ras.getTypesids().length;i++)
		{
			if(i>0) temp.append(';');
			temp.append(ras.getTypesids()[i]);
		}
		
		this.justthis = temp.toString();
		
		this.sources = ras.getSources();
		
//		SampleObject ras = DatabaseAccess.extractExpressionTable(this.startUpBean.getSessionFactory(),
//			new String[]{"hgu133plus2"}, useThisDataTypes, entrezs);
		
		return new Object[]{ras,entrezs,(String[])tinputedIds[1]};
	}
	
	protected void takeCareOfSampleObject(SampleObject ras, Integer[] entrezs, String[] inputedids) throws Exception
	{
		System.out.println("1");
		
			this.pointTypes = ras.getTypeColors();
			this.pointTypesLegend = ras.getTypeColorsLegend();
			this.pointTypesExtra = ras.getSampleType();	

		System.out.println("2");
		//I'm taking an unancessery step here I could process the name of the heatmap's rows earlier
			Object[] dat = RAccess.processData(ras, entrezs, inputedids, this.startUpBean);
		System.out.println("3");
			this.pc1n2 = (double[][])dat[0];

			this.pointNames = (String[])dat[1];

			this.pointColors = (String[])dat[7];
			
//			this.pointTypes = ras.getTypeColors();
//			this.pointTypesLegend = ras.getTypeColorsLegend();
//			this.pointTypesExtra = ras.getSampleType();	
		System.out.println("4");
			this.pointDatabaseIds = ras.getSampleDBid();
			
			this.pointTypesAlt = ras.getTypeAltColors2();
			this.pointTypesLegendAlt = ras.getTypeColorsLegend();
			this.pointTypesAltExtra = ras.getSampleType();
				
			this.pointCondition = ras.getConditionColors();
			this.pointConditionLegend = ras.getConditionColorsLegend();
			this.pointConditionExtra = ras.getSampleCondition();
			
			this.pointWildtype = ras.getSampleWildOrNot();
			this.pointWildtypeLegend = ras.getsampleWildOrNotLegend();
			this.pointWildtypeExtra = ras.getSampleWildOrNot2();
			
			this.pointSuper = ras.getSampleSuperTypeColors();
			this.pointSuperLegend = ras.getSampleSuperTypeColorLegend();
			this.pointSuperExtra = ras.getSampleSuperType();
			
			
			this.pointPureStem = ras.getPureStem();
			this.pointPureStemLegend = ras.getPureStemLegend();
			this.pointPureStemExtra = ras.getPureStemExtra();
			
			
			
			this.pointClass = ras.getSampleClassColors();
			this.pointClassLegend = ras.getClassLegend();
			this.pointClassExtra = ras.getSamplecclass();
			
			
			this.pointSM = ras.getSurfacemarkersColors();
			this.pointSMLegend = ras.getSurfacemarkersLegend();
			this.pointSMExtra = ras.getSurfacemarkers();

			
			
			
			
			
			this.dendograme = (String)dat[2];
			this.dendogramelables = (String)dat[3];
			this.heatmaprows = (String)dat[5];
			this.heatmaprowsnames = (String)dat[4];

			this.heatlabelcolors = (String[])dat[6];
			this.heatlabeltypes = (String[])dat[8];
			this.heatsupertypes = (String[])dat[10];
			
			this.dendogrameq = (String)dat[13];
			this.dendogramelablesq = (String)dat[17];
			this.heatmaprowsq = (String)dat[19];
			this.heatmaprowsnamesq = (String)dat[18];

			this.heatlabelcolorsq = (String[])dat[14];
			this.heatlabeltypesq = (String[])dat[15];
			this.heatsupertypesq = (String[])dat[16];
			
			
//			this.heatlabelcolors = this.startUpBean.getSamplesColor(this.dendogramelables.split("\\|"));
			
			this.buttonsforcorr = (String[][])dat[9];
			
			double[] pvpcs = (double[])dat[11];
			
			this.pvPC1 = pvpcs[0];
			this.pvPC2 = pvpcs[1];
			
			this.showResults = true;
		System.out.println("done");
	}
	
	
	
	public String getPointSuper() {
		return this.arrayToString(pointSuper);
	}
	
	public void setPointSuper(String pointSuper) {
	}
	
	public String getPointSuperLegend() {
		return this.arrayToString(pointSuperLegend);
	}
	
	public void setPointSuperLegend(String pointSuperLegend) {
	}

	public String getPointSuperExtra() {
		return this.arrayToString(pointSuperExtra);
	}

	public void setPointSuperExtra(String pointSuperExtra) {
	}



	public String doFullDataProcessingGeneOnly() 
	{
		
		try{
			Object[] tobj = this.getSampleObject();
			
			if(tobj==null) return null;
			SampleObject ras = (SampleObject)tobj[0];
			Integer[] entrezs = (Integer[])tobj[1];
			String[] inputedids = (String[])tobj[2];
			
			takeCareOfSampleObject(ras,entrezs,inputedids);
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		
		try {
			UploadedFile file = event.getFile();
			
			if (file != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String setsToUse = context.getExternalContext().getRequestParameterMap().get("sc:markersToUse");
			
				this.setMarkersToUse(setsToUse);
				this.setUseThisDataTypes(context.getExternalContext()
					.getRequestParameterMap().get("sc:useThisDataTypes"));
				String setsToUseNew = context.getExternalContext().getRequestParameterMap().get("sc:tab:searchText");
				
				this.platform = context.getExternalContext().getRequestParameterMap().get("sc:tab:platselect");
				
				this.searchData = setsToUseNew;
								
				Object[] tobj = this.getSampleObject();
				
				if(tobj!=null)
				{
				
					SampleObject ras = (SampleObject)tobj[0];
					Integer[] entrezs = (Integer[])tobj[1];
					String[] inputedids = (String[])tobj[2];
				
					//-------------------------------- Getting the user's data
				
					StringBuffer basePath = new StringBuffer(this.startUpBean.getBasepath());
				
					int num = this.startUpBean.getNextIteration();
				
					basePath.append("temp/");
					basePath.append(num);
					basePath.append("/");
				
					String tempdir = basePath.toString();
				
					File theDir = new File(tempdir);

					theDir.mkdir();
				
					basePath.append(file.getFileName());
				
					String fname = basePath.toString();

					FileUtils.writeByteArrayToFile(new File(fname), file.getContents());
				
					
					
					System.out.println("GOT TO NORMALIZATION!");
					
					Object[] tnorm = null;
					
					try {
						tnorm = RAccess.normalizeCELFile(fname,this.platform,this.startUpBean.getBasepath());
					} catch(Exception e)
					{e.printStackTrace();
					this.showNormalizationError=true;
					this.activeIndex=1;}
					
					
				
					System.out.println("Finished NORMALIZATION! "+this.activeIndex);
					
					FileUtils.deleteDirectory(theDir);
					
					System.out.println(tnorm!=null);
					if(tnorm!=null)
					{
					//this will change a lot!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//						if(tnorm==null) throw(new Exception("Normalization failed"));

						double[][] values = (double[][])tnorm[0];
						String[] allExperimentsNames = (String[])tnorm[1];
						String[] probs = (String[])tnorm[2];
				
//					Object[] entz = DatabaseAccess.getProbToEntrezTable(this.startUpBean.getSessionFactory(), "mouse4302");
						

						System.out.println("PROBSMAPPING 1");
						
						Object[] entz = DatabaseAccess.getProbToEntrezTable(this.startUpBean.getSessionFactory(), this.platform);  //I just need to update the probes table
					
					
						String[] entrezIdsList = (String[])entz[1];
						
						System.out.println("PROBSMAPPING 2");
				
						double[][] entrezIdsMatrix = ProbToEntrezHandler.getProcessedMatrix(
							(String[][])entz[0], 
							values, allExperimentsNames, probs, 
							entrezIdsList);
					//this will change a lot!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

						
						System.out.println("PROBSMAPPING DONE");
					
					
					
						this.userdefinedid = allExperimentsNames[0];
				
						
//						System.out.println("-----------------------------------------------------------------------------");
//						
//						for(int c=0;c<allExperimentsNames.length;c++)
//						{
//							System.out.println(allExperimentsNames[c]);
//							
//						}
//						for(int m=0;m<entrezIdsList.length;m++)
//						{
//							System.out.println(entrezIdsList[m]+":"+entrezIdsMatrix[m][0]);
//						}
//						
//						System.out.println("-----------------------------------------------------------------------------");
						

						System.out.println("Quantile");
						
						double[][] entrezIdsMatrixq = RAccess.quantilize(platform, this.startUpBean.getBasepath(), entrezIdsList, entrezIdsMatrix);
						
						System.out.println("Quantile done");
						
						ras.combineMatrixes(stringArrayToIntegerArray(entrezIdsList), allExperimentsNames, entrezIdsMatrix, entrezIdsMatrixq); //TODO:will have to change
						
						System.out.println("Combine matrixs");
					
						takeCareOfSampleObject(ras,entrezs,inputedids);
					}
				}
			}
		} catch (Exception e)
		{e.printStackTrace();}
		
	}
	
	protected Integer[] stringArrayToIntegerArray(String[] in)
	{
		Integer[] out = new Integer[in.length];
		
		for(int i=0;i<in.length;i++)
		{
			out[i] = new Integer(in[i]);
		}
		
		return out;
	}
	
	public String[][] getTypeToColor() {
		return this.pointTypesLegend;
//		return this.startUpBean.getTypeToColor();
	}

	public void setTypeToColor(String[][] typeToColor) {
	}
	
	public String getSearchResultsExtra() {
		return searchResultsExtra;
	}
	
	public void setSearchResultsExtra(String searchResultsExtra) {
	}
	
	public String searchDatabaseForExperiments() throws Exception 
	{
		String[][][] res = DatabaseAccess.searchForExperiments(this.startUpBean.getSessionFactory(), this.useThisDataTypes);
		
		this.searchResults = res[0];
		this.searchResultsExtra = res[1][0][0];
		
		return null;
	}
	
}
