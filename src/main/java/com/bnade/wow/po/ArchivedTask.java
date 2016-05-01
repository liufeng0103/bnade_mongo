package com.bnade.wow.po;

public class ArchivedTask {
	private int realmId;
	private String date;

	public ArchivedTask() {}
	
	public ArchivedTask(int realmId, String date) {
		this.realmId = realmId;
		this.date = date;
	}
	
	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
