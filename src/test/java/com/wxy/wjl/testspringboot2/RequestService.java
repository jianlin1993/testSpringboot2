package com.wxy.wjl.testspringboot2;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import com.wxy.wjl.testng.common.StringUtil;
import com.wxy.wjl.testspringboot2.service.Son;
import com.wxy.wjl.testspringboot2.utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.formula.functions.T;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RequestService {


//	@ECPReference
//	private AcmHoldService cmHoldService;


	@Test
	public void test(){
		System.out.println(Math.max(TimeUnit.SECONDS.toMinutes(130), 1L));

	}

	/**
	 * String.split函数 以|作为分隔符  需要转义
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception{
		String acTyp="100||800|808";
		String [] acTypList=acTyp.split("\\|");
		for(int i=0;i<acTypList.length;i++){
			System.out.println(acTypList[i]);
		}

	}

	/**
	 * AviatorEvaluator求值表达式
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception{
		String formual="'50.00'<'100'";
		boolean result=(Boolean) AviatorEvaluator.execute(formual);
		System.out.println(result);
		StringBuilder formula2 = new StringBuilder();
		//纯字符串判断需要使用"'"  上面是字符串判断   数字判断需要把单引号去掉50.00<100
		formula2.append("50.00").append("<").append("100");
		System.out.println(formula2.toString());
		System.out.println( AviatorEvaluator.execute(formula2.toString()));
	}
	@Test
	public void test4() throws Exception{
//		String regex = "^[a-zA-Z]+$";
//		String warn="11";
//		boolean result=warn.matches(regex);
//		System.out.println(result);
//		String patrn = "^[0-9]+(.)?[0-9]+$";
//		String warn="50.00";
//		boolean result=warn.matches(patrn);
//		System.out.println(result);
		String tableName="T_BUI_OPER";
		String uniqueIndexPattern = "^UI\\d+_"+tableName;
		String normalIndexPattern = "^NI\\d+_"+tableName;
		String indexName="UI11_T_BUI_OPER_";
		boolean matchResult=false;
		matchResult=indexName.matches(uniqueIndexPattern);
		System.out.println(matchResult);


	}
	@Test
	public void test5() throws Exception{
		//System.out.println(getDate("yyyyMMdd"));
		System.out.println("URM00000".substring(3));
		System.out.println("20191231".substring(0,4));

		String beginDate="20191031";
		String firstPayDate="";
		int begin=Integer.parseInt(beginDate.substring(4));
		if(begin < 321){
			firstPayDate=beginDate.substring(0,4)+"0321";
		}else if(begin >= 321 && begin < 621){
			firstPayDate=beginDate.substring(0,4)+"0621";
		}else if(begin >= 621 && begin < 921){
			firstPayDate=beginDate.substring(0,4)+"0921";
		}else if(begin >= 921 && begin < 1221){
			firstPayDate=beginDate.substring(0,4)+"1221";
		}else{
			int temp=Integer.parseInt(beginDate.substring(0,4))+1;
			firstPayDate=temp+"0321";
		}
		System.out.println(firstPayDate);
	}


	/**
	 * java日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String ret = "";
		if (date == null) {
			return ret;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ret = formatter.format(date);
		return ret;
	}
	public static String getDate(String format) {
		Calendar calendar = Calendar.getInstance();
		return dateToString(calendar.getTime(), format);
	}

/*	*//**
	 * 测试泛型上下界
	 * @throws Exception
	 *//*
	@Test
	public void test6() throws Exception{
		List<? extends Human> list1=new ArrayList<>();
		Human h=list1.get(0); //可以取  取出来都是Human
		//list1.add(new Human()); 编译器报错
		//list1.add(new Father());编译器报错
		List<? super Father> list2=new ArrayList<>();
		list2.add(new Father()); //可以存Father
		//list2.add(new SonOfFather());//可以存Father的子类
		Object o=list2.get(0);
	}*/

/*	*//**
	 * 测试泛型上下界
	 * @throws Exception
	 *//*
	@Test
	public void test7() throws Exception{
		Father father=new Father();
		father.setSum(1);
		Human human=new Human();
		human.setFather(father);
		System.out.println(human.getFather().getSum());
	}*/


	/**
	 * 测试数组转列表
	 * @throws Exception
	 */
	@Test
	public void test8() throws Exception{
		Object[] array=new String[4];
		array[0]="a";
		array[1]="b";
		array[3]="d";
		List<Object> b = new ArrayList<>(Arrays.asList(array));
		for(Object s:b){
			System.out.println(s.toString());
		}
	}

/*	*//**
	 * 测试列表排序
	 * @throws Exception
	 *//*
	@Test
	public void test9() throws Exception{
		List<Father> list=new ArrayList<>();
		Father father1=new Father();
		Father father2=new Father();
		Father father3=new Father();
		Father father4=new Father();
		Father father5=new Father();
		father1.setToolId("3");
		father2.setToolId("5");
		father3.setToolId("6");
		father4.setToolId("2");
		father5.setToolId("0");
		list.add(father1);
		list.add(father2);
		list.add(father3);
		list.add(father4);
		list.add(father5);
		List<String> list2=new ArrayList<>();
		list2.add("7");
		list2.add("0");
		list2.add("1");
		list2.add("2");
		list2.add("3");
		list2.add("5");

		System.out.println("排序前数据");
		list.forEach(t-> System.out.println(t));
		setListOrder(list2,list);
		System.out.println("排序后数据");
		list.forEach(t-> System.out.println(t));


	}

	public static void setListOrder(List<String> orderRegulation, List<Father> targetList) {
		//按照Posts的Id来排序
		Collections.sort(targetList, ((o1, o2) -> {
			int io1 = orderRegulation.indexOf(o1.getToolId());
			int io2 = orderRegulation.indexOf(o2.getToolId());
			return io1 - io2;
		}));
	}
*/

	/**
	 * 测试BigDecimal比较大小
	 * @throws Exception
	 */
	@Test
	public void test10() throws Exception{
		BigDecimal A=new BigDecimal(2);
		BigDecimal B=new BigDecimal(3);
		System.out.println(A.compareTo(B));
		BigDecimal c=null;
		System.out.println(c);
	}

	/**
	 * 测试  json转数组
	 * @throws Exception
	 */
/*	@Test
	public void test11() throws Exception{
		String json="[{\"payTool\":\"1021\",\"toolId\":\"CGW0020191104000000000000014324\",\"ordSeq\":\"0\"},{\"payTool\":\"1011\",\"toolId\":\"0\",\"ordSeq\":\"1\"}]";
		List<McaUrmPayLstBO> payWayOrdList= JSONArray.parseArray(json,McaUrmPayLstBO.class);
		payWayOrdList.forEach(t-> System.out.println(t.getPayTool()+" "+t.getToolId()+" "+t.getOrdSeq()));
	}*/


	/**
	 * 测试arrayList自动扩容
	 *
	 */
	@Test
	public void test13() throws Exception{
		String tableNamePattern = "^T_[A-Z]{3}_\\w+";
		System.out.println("T_ABC_OPER_2019".matches(tableNamePattern));
	}

	/**
	 * 测试集合 空 和null
	 *
	 */
	@Test
	public void test14() throws Exception{
		List list1=null;
		List list2=new ArrayList();
		List list3=new ArrayList();
		list3.add(1);

		System.out.println(CollectionUtils.isEmpty(list1));
		System.out.println(CollectionUtils.isEmpty(list2));
		System.out.println(CollectionUtils.isEmpty(list3));

	}
	/**
	 * 测试String equals Long
	 */
	@Test
	public void test15() throws Exception{
		String str="110";
		Long l=110L;
		System.out.println(str.equals(l.toString()));
		System.out.println(l.equals(110L));
	}

	/**
	 * druid解密数据库密码
	 */
	@Test
	public void test16() throws Exception{
		String pwd = "d1VeV9B7tWfVB1EFAGQKFkD8gNyVAgQqvUvy+N8c7eG1wMUBqmwypJAtceU1tMkhIgi4xCNv3WP2Ptz6Fd/Vjg==";
		String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJEOiy0Vf996paS13nuLz4scR/1dbZgg0fEYgzb+o94fEjpP+9Ipp1IStxFnvFOmZcAUYXEOq1s/JEO4dgtfF3kCAwEAAQ==";
		try {
			String depwd = ConfigTools.decrypt(pub, pwd);
			System.out.println(depwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 金额格式化
	 * DecimalFormat 类主要靠 # 和 0 两种占位符号来指定数字长度。
	 * 0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。
	 */
	@Test
	public void test17() throws Exception{
//		BigDecimal amt1=new BigDecimal("0");
//		BigDecimal amt2=new BigDecimal("200.000");
//		BigDecimal amt3=new BigDecimal("300.0");
//		BigDecimal amt4=new BigDecimal("40");
//		System.out.println(new DecimalFormat("0.00").format(amt1));
//		System.out.println(new DecimalFormat("0.00").format(amt2));
//		System.out.println(new DecimalFormat("0.00").format(amt3));
//		System.out.println(new DecimalFormat("0.00").format(amt4));
		BigDecimal amt5=new BigDecimal("1,111,222".replace(",",""));
		BigDecimal amt6=new BigDecimal("10.1");
		BigDecimal amt7=new BigDecimal("100.17");
		BigDecimal amt8=new BigDecimal("1001.118");
		BigDecimal amt9=new BigDecimal("111123211");
		System.out.println(new DecimalFormat("#,##0.00").format(amt5));
		System.out.println(new DecimalFormat("#,##0.00").format(amt6));
		System.out.println(new DecimalFormat("#,##0.00").format(amt7));
		System.out.println(new DecimalFormat("#,##0.00").format(amt8));
		System.out.println(new DecimalFormat("#,##0.00").format(amt9));

	}

	/**
	 * 日期格式化
	 */
	@Test
	public void test18() throws Exception{
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
		Date data=new Date(1576857600000L);
		String str1 = sdf1.format(data);
		System.out.println("当前时间通过 yyyy-MM-dd 格式化后的输出: "+str1);
	}


	/**
	 * 测试http请求
	 */
	@Test
	public void test20() throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("hospitalName","积水潭医院");
		String para= JSON.toJSONString(jsonObject);
		String rsp= HttpUtils.doPost2(para,"https://hmly.tebon.com.cn:10999/api/datacenter/hospitalAssetsList/list");
		System.out.println(rsp);
	}

	/**
	 * 测试http请求
	 */
	@Test
	public void test21() throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("hospitalName","积水潭医院");
		String para= JSON.toJSONString(jsonObject);
		String url2="http://10.163.118.117:8380/mrbui/circularPurchase";
		String rsp=HttpUtils.doPost2(para,url2);
		System.out.println(rsp);
	}



	/**
	 * 蓝血联盟   机具入库
	 */
	@Test
	public void test22() throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("inviteCode","Q100011");
		jsonObject.put("snNoStar","00000000000001");
		jsonObject.put("snNoEnd","00000000000005");
		jsonObject.put("macTyp","200");
		jsonObject.put("orgCd","1004");
		jsonObject.put("ordNo","2020011521111000001352");
		jsonObject.put("regTm","");
		jsonObject.put("applyTyp","");
		jsonObject.put("groupId","22");
		String para= JSON.toJSONString(jsonObject);
		String url2="http://106.14.223.103/mca/mrpay/MachinesStorage.htm";
		String rsp=HttpUtils.doPost2(para,url2);
		System.out.println(rsp);
	}
	/**
	 * 蓝血联盟   月初重置
	 */
	@Test
	public void test23() throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("inviteCode","Q100011");
		jsonObject.put("snNoStar","00000000000001");
		jsonObject.put("snNoEnd","00000000000005");
		jsonObject.put("macTyp","200");
		jsonObject.put("orgCd","1004");
		jsonObject.put("ordNo","2020011521111000001352");
		jsonObject.put("regTm","");
		jsonObject.put("applyTyp","");
		jsonObject.put("groupId","22");
		String para= JSON.toJSONString(jsonObject);
		String url2="http://106.14.223.103/bap/pertimbpc1/resetOther";
		String rsp=HttpUtils.doPost2(para,url2);
		System.out.println(rsp);
	}


    /**
     * java获取软连接路径
     */
    @Test
    public void test27() throws Exception{
        Path path=Paths.get("war包真实部署路径");
        String str=path.toRealPath().toString();

    }

	/**
	 * 测试字节数
	 */
	@Test
	public void test28() throws Exception{
		String a="體";
		byte [] gbk=a.getBytes("GBK");
		byte [] gb18030=a.getBytes("gb18030");
		byte [] utf8=a.getBytes("UTF-8");
		byte [] utf16=a.getBytes("UTF-16");
		byte [] asc=a.getBytes("ASCII");
		byte [] unicode=a.getBytes("UNICODE");
		System.out.println("'好'字GBK编码占用字节数："+gbk.length);
		System.out.println("'好'字gb18030编码占用字节数："+gb18030.length);
		System.out.println("'好'字utf8编码占用字节数："+utf8.length);
		System.out.println("'好'字utf16编码占用字节数："+utf16.length);
		System.out.println("'好'字asc编码占用字节数："+asc.length);
		System.out.println("'好'字unicode编码占用字节数："+unicode.length);

		String b="a";
		byte [] gbk_b=b.getBytes("GBK");
		byte [] gb18030_b=b.getBytes("gb18030");
		byte [] utf8_b=b.getBytes("UTF-8");
		byte [] utf16_b=b.getBytes("UTF-16");
		byte [] asc_b=b.getBytes("ASCII");
		byte [] unicode_b=b.getBytes("UNICODE");
		System.out.println("'a'字母GBK编码占用字节数："+gbk_b.length);
		System.out.println("'a'字母gb18030编码占用字节数："+gb18030_b.length);
		System.out.println("'a'字母utf8编码占用字节数："+utf8_b.length);
		System.out.println("'a'字母utf16编码占用字节数："+utf16_b.length);
		System.out.println("'a'字母asc编码占用字节数："+asc_b.length);
		System.out.println("'a'字母unicode编码占用字节数："+unicode_b.length);

	}


	/**
	 * json字符串转换
	 */
	@Test
	public void test29() throws Exception{


		JSONObject jsonObject=new JSONObject();
		jsonObject.put("ClientUserName","bankOutbound");
		jsonObject.put("ClientPassword","ckdoLikt12vqRBWdItcoPbV5CRjpIOlU4v7ZrQPBqQWfIyvxE5lbwB7SiLTDb7UAA1sKgMapN9aq3/UU1tNoHOQVAJqIhq0G3CSZL8xbnPknvhFXm1MH2/6PZBxbzR4XjZ0STk7+l+GcDx/sBFSFwHimzUcg6Tsv7lxZdA93dSS8KfgyDddrecPJqqaI4vJYUckjf4JcLXOf9aDc8t51bSp4Gz1J1CA9woMpT+XZLJsvZInh8Lah/wvAVsSleAw6guKpLIUHNElBlXcsbd8j1JKuOk2pdDYtvocrwjkIGWjkEqiTy4uX2GrorKEk8rOA1oiiU/AnoWG6XVoVHpduJg==");
		jsonObject.put("RequestRefID","PRE_AG_${seqno}${random}");
		jsonObject.put("ReceiptNo","${timestamp}${seqno}${random}");
		jsonObject.put("TimeStamp","${timestamp}");
		jsonObject.put("MobileNumber","254${msisdnno}");
		jsonObject.put("TransactionTime","${timestamp}");
		jsonObject.put("ServiceName","OD_UTILIZATION_ADVICE");
		jsonObject.put("MPESASubscriberID","${VmtReferenceNumberCounter}");
		jsonObject.put("AccessFee","1");
		jsonObject.put("ODAmount","500");
		jsonObject.put("AvailableLimit","20000");
		jsonObject.put("ODDueDate","20201231");
		jsonObject.put("ODTransactionType","01");
		jsonObject.put("FloatAcctRunningBal","22");
		String para= JSON.toJSONString(jsonObject);
		System.out.println(para);
	}


	/**
	 * 测试hashcode与equals重写
	 */
	@Test
	public void test30() throws Exception{
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
		Map<Son,String> map=new HashMap<>();
		Son son1=new Son();
		son1.setName("张三");
		System.out.println(son1.hashCode());
		Son son2=new Son();
		son2.setName("张三");
		map.put(son1,"son1");
		System.out.println(son2.hashCode());


		System.out.println(map.get(son2));


		String str1="1";
		String str2="1";
		System.out.println(str1 == str2);  //返回true

		String str3=new String("1");
		String str4=new String("1");
		System.out.println(str3 == str4);  //返回false


	}

	/**
	 * 文件路径检测  安全问题修复
	 */
	@Test
	public void test31() throws Exception{
		String fileName="/bbb/aaa/";
		fileName = FilenameUtils.normalize(fileName);
//获取文件名称
		//fileName = FilenameUtils.getName(fileName);
		System.out.println(fileName);
	}

	@Test
	public void test32() throws Exception{
		String formatSql="query.topTwenty";
		System.out.println(formatSql.split("\\.")[0].trim());
		System.out.println(new BigDecimal("120.010").toString());

		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
		System.out.println( NumberUtils.toInt(DateFormatUtils.format(new Date(), "HH")));
	}

	@Test
	public void test33() throws Exception{
		System.out.println(StringUtils.leftPad("99999999", 9, '0'));

	}

	@Test
	public void test34() throws Exception{

		String data="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ic4=\"http://VMTComponentModel/InterfaceSpecification/Interfaces/C4/IC4FSP2VMT\" xmlns:c4=\"http://VMTComponentModel/InterfaceSpecification/Messages/C4/\">\n" +
				"\t<soapenv:Header/>\n" +
				"\t<soapenv:Body>\n" +
				"\t\t<ic4:FSPInitiatedActivateNotification>\n" +
				"\t\t\t<ic4:request>\n" +
				"\t\t\t\t<c4:AdditionalInformation></c4:AdditionalInformation>\n" +
				"\t\t\t\t<c4:BankShortCode>262626</c4:BankShortCode>\n" +
				"\t\t\t\t<c4:CustomerId>\n" +
				"\t\t\t\t\t<c4:MSISDN>254711121023</c4:MSISDN>\n" +
				"\t\t\t\t\t<c4:VmtReferenceNumber>254711121023</c4:VmtReferenceNumber>\n" +
				"\t\t\t\t</c4:CustomerId>\n" +
				"\t\t\t\t<c4:MessageId>\n" +
				"\t\t\t\t\t<c4:Id>2ca5de95-06c1-4153-ac13-0789772710bf</c4:Id>\n" +
				"\t\t\t\t\t<c4:TimeStamp>2020-11-12T17:37:15.444+03:00</c4:TimeStamp>\n" +
				"\t\t\t\t</c4:MessageId>\n" +
				"\t\t\t\t<c4:TransactionId>20201112223228</c4:TransactionId>\n" +
				"\t\t\t\t<c4:TransactionReceiptNumber>20201112223228</c4:TransactionReceiptNumber>\n" +
				"\t\t\t\t<c4:TransactionTypeName>ActivateAccount</c4:TransactionTypeName>\n" +
				"\t\t\t\t<c4:AccountCreationTimeStamp>2020-11-12T17:37:15.444+03:00</c4:AccountCreationTimeStamp>\n" +
				"\t\t\t\t<c4:BankResponseCode>S0</c4:BankResponseCode>\n" +
				"\t\t\t\t<c4:SavingsAccountNumber>1060030601435892</c4:SavingsAccountNumber>\n" +
				"\t\t\t\t<c4:SimAppTransId>20201112223228</c4:SimAppTransId>\n" +
				"\t\t\t</ic4:request>\n" +
				"\t\t</ic4:FSPInitiatedActivateNotification>\n" +
				"\t</soapenv:Body>\n" +
				"</soapenv:Envelope>";

		System.out.println(data.length());






	}
	private final AtomicLong now=new AtomicLong(System.currentTimeMillis());;

	@Test
	public void test35() throws Exception{
		String requestUrl="https://172.16.0.14/";
		int idx1 = requestUrl.indexOf("/", 8);
		if (idx1 == -1) {

		}

		int idx3 = requestUrl.indexOf("?");
		String gwWebRoot = "";
		if (idx3 != -1) {
			gwWebRoot = requestUrl.substring(idx1, idx3);
		} else {
			gwWebRoot = requestUrl.substring(idx1);
		}
		System.out.println(gwWebRoot);
	}


	protected static long getDatacenterId(long maxDatacenterId) {
		long id = 0L;

		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			System.out.println(network.getHardwareAddress().toString());
			if (network == null) {
				id = 1L;
			} else {
				byte[] mac = network.getHardwareAddress();
				if (null != mac) {
					id = (255L & (long)mac[mac.length - 1] | 65280L & (long)mac[mac.length - 2] << 8) >> 6;
					id %= maxDatacenterId + 1L;
				}
			}
		} catch (Exception var7) {
			//logger.warn(" getDatacenterId: " + var7.getMessage());
		}

		return id;
	}

	public static void main(String[] args) {
		System.out.println(getDatacenterId(31L));
	}
}
