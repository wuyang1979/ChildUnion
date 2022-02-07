package com.qinzi123;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @title: LocalTest
 * @projectName: trunk
 * @description: 亲子企服
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class LocalTest {

	private String convertFee(String fee){
		BigDecimal bigDecimal = new BigDecimal(fee);
		BigDecimal result = bigDecimal.multiply(new BigDecimal(100)).setScale(0);
		return String.valueOf(result.toString());
	}

	@Test
	@Ignore
	public void testA(){
		System.out.println(convertFee("0.15"));
	}

	@Test
	public void testRatio(){
		System.out.println(  (int) Math.ceil( 5 * 10 * 1.0 / 100));
	}
}
