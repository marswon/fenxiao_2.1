package com.kedang.fenxiao.util;

import java.math.BigDecimal;

import com.kedang.fenxiao.util.po.OverdueRate;

public class BigDecimalUtils {
	private final static int SCALE=2;
	private final static int SCALE2=0;
	private final static int ROUNDINGMODE=BigDecimal.ROUND_HALF_UP;
	public final static BigDecimal FACTOR = new BigDecimal(100);
	public final static BigDecimal zero=new BigDecimal(0);
	/**
	 * 加法
	 * b1与b2相加,例:15.321+20.125=35.45 (进行四舍五入并保留小数点后2位)
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal add(BigDecimal b1,BigDecimal b2){
		if(b1==null){
			b1= new BigDecimal(0);
		}
		if(b2==null){
			b2= new BigDecimal(0);
		}
		return b1.add(b2).setScale(SCALE, ROUNDINGMODE);
	}
	/**
	 * 减法
	 * b1减去b2,例:
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal b1,BigDecimal b2){
		if(b1==null){
			b1= new BigDecimal(0);
		}
		if(b2==null){
			b2= new BigDecimal(0);
		}
		return b1.subtract(b2).setScale(SCALE, ROUNDINGMODE);
	}
	
	/**
	 * 乘法
	 * b1乘以b2,例:
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal b1,BigDecimal b2){
		if(b1==null){
			b1= new BigDecimal(0);
		}
		if(b2==null){
			b2= new BigDecimal(0);
		}
		return b1.multiply(b2).setScale(SCALE, ROUNDINGMODE);
	}
	
	/**
	 * 乘法
	 * b1乘以b2,例:
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal mul2(BigDecimal b1,BigDecimal b2){
		if(b1==null){
			b1= new BigDecimal(0);
		}
		if(b2==null){
			b2= new BigDecimal(0);
		}
		return b1.multiply(b2).setScale(SCALE2, ROUNDINGMODE);
	}
	/**
	 * 除法
	 * b1除以b2,例:
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal div(BigDecimal b1,BigDecimal b2,int scale){
		if(b1==null){
			b1= new BigDecimal(0);
		}
		if(b2==null){
			return new BigDecimal(0);
		}
		return b1.divide(b2,scale, ROUNDINGMODE);
	}
	/**
	 * 计算费率
	 * @param total
	 * @param day
	 * @param overdueRate
	 * @return
	 */
	public static BigDecimal overdueAmount(BigDecimal total,int day,OverdueRate overdueRate){
	 		BigDecimal withinDayRate=overdueRate.getWithinDayRate();
	 		BigDecimal increaseDayRate=overdueRate.getIncreaseDayRate();
	 		int withinDay=overdueRate.getWithinDay();
	 		int increaseDay=overdueRate.getIncreaseDay();
	 		if(day<=withinDay){
	 			if(day%withinDay==1||withinDay==1){
	 				return mul(total, div(withinDayRate, new BigDecimal(100),6));
	 			}
	 		}else{
	 			if((day-withinDay)%increaseDay==1||increaseDay==1){
	 				int multiple=increaseDay==1?(day-withinDay)/increaseDay:(day-withinDay)/increaseDay+1;
	 				return mul(total, div(add(withinDayRate, mul(increaseDayRate, new BigDecimal(multiple))), new BigDecimal(100),6));
	 			}
	 		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(String.valueOf(mul2(new BigDecimal(1.34), new BigDecimal(100))));
//	}
}
