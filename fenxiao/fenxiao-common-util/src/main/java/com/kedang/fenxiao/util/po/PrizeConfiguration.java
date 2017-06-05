package com.kedang.fenxiao.util.po;

/**
 * 游戏配置
 */
public class PrizeConfiguration {
	
	private String name;				//奖品名称
	private int type;					//奖品类型
	private int number;					//奖品个数
	private int everydayNum;			//每日掉落个数
	private double probability;			//掉落概率
	private int prizeId;					//奖品id
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getEverydayNum() {
		return everydayNum;
	}
	public void setEverydayNum(int everydayNum) {
		this.everydayNum = everydayNum;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public int getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}
	
}
