package com.example.Task.Api.dto;

public class SyncRequestItem {

	public enum Op{
		
		CREATE ,UPDATE,DELETE
	}
	
	private Op op;
	private TaskDto task;
	public Op getOp() {
		return op;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	public TaskDto getTask() {
		return task;
	}
	public void setTask(TaskDto task) {
		this.task = task;
	}
	public SyncRequestItem(Op op, TaskDto task) {
		super();
		this.op = op;
		this.task = task;
	}
	public SyncRequestItem() {
		super();
		
	}
	
	
}
