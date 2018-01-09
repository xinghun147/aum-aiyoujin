package com.hjgj.aiyoujin.core.constants;


public class Constants {
	public enum DelFlag{
		NO,//否
		YES//是
	}
	public enum RecordStauts{
		success ,//成功
		failure ,//失败
	}
	/**
	 * 产品状态
	 */
	public enum ProdStatus{
	  draft
			{
			@Override
			public String getDesc() {
				return "草稿未上架";
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return null;
			}
		},		
	putaway
			{
			@Override
			public String getDesc() {
				return "上架未开售";
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return null;
			}
		},		
	saling{
			@Override
			public String getDesc() {
				return "正在销售中";
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return null;
			}
		},
	soldout {
			@Override
			public String getDesc() {
				return "售磬未下架";
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return null;
			}
		},
	withdraw {
				@Override
				public String getDesc() {
					return "下架";
				}

				@Override
				public String getCode() {
					// TODO Auto-generated method stub
					return null;
			}
		};		
		public abstract String getDesc();
		public abstract String getCode();
	}
	
		 public static ProdStatus getProdStatus(Integer  code) {   
		        for  (ProdStatus stauts : ProdStatus.values()) {   
		            if  (stauts.ordinal() == code) {
		                return  stauts;   
		            }
		        }   
		        return null ;   
		  }
		 
		public enum prodPicType{
			 thumb,//缩略图
			 large,//大图
			 middle//中图
		}
	
}
