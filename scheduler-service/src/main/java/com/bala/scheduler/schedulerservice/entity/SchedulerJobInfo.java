package com.bala.scheduler.schedulerservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "SCHEDULER_JOB_DETAILS")
public class SchedulerJobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String jobClass;
    private String cronExpression;
    private String description;    
    private String interfaceName;
    private Long repeatTime;
    private Boolean cronJob;
	
    public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Long getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(Long repeatTime) {
		this.repeatTime = repeatTime;
	}
	public Boolean getCronJob() {
		return cronJob;
	}
	public void setCronJob(Boolean cronJob) {
		this.cronJob = cronJob;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    
}