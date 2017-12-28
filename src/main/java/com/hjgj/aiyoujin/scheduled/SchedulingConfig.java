//package com.hjgj.aiyoujin.scheduled;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling
//public class SchedulingConfig {
//	
//	protected final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);
//	
//
//	
//	
//    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
//    public void insertEquityReport() {
//    	statsReportService.insertEquityReport();
//        logger.info("执行【权益报表统计】定时任务！");
//    }
//    
//    
//    @Scheduled(cron = "0 59 23 * * ?") // 每天23点59执行
//    public void insertreguserReport() {
//    	statsReportService.insertReguserReport();
//    	logger.info("执行【有效用户报表统计】定时任务！");
//    }
//    
//    
//    @Scheduled(cron = "0 59 23 * * ?") //  每天23点59执行
//    public void insertwholeReport() {
//    	statsReportService.insertWholeReport();
//    	logger.info("执行【整体报表统计】定时任务！");
//    }
//
//}
