package com.zooms.dean.push.model;


public class Notification {
	private String title;
	private String body;
	private String deviceIds;
	private String deviceId;
	private String accounts;
	private String account;
	private String aliases;
	private String alias;
	private String tag;
	private String tagExpression;
	private String extParameters;

	public String getExtParameters() {
		return extParameters;
	}

	public void setExtParameters(String extParameters) {
		this.extParameters = extParameters;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagExpression() {
		return tagExpression;
	}

	public void setTagExpression(String tagExpression) {
		this.tagExpression = tagExpression;
	}

    @Override
    public String toString() {
        return "Notification [title=" + title + ", body=" + body + ", deviceIds=" + deviceIds + ", deviceId="
            + deviceId + ", accounts=" + accounts + ", account=" + account + ", aliases=" + aliases + ", alias="
            + alias + ", tag=" + tag + ", tagExpression=" + tagExpression + ", extParameters=" + extParameters + "]";
    }

}
