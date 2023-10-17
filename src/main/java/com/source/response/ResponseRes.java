package com.source.response;

public class ResponseRes<T> {

 	private Integer respCode;
    private String respStatus;
    private String respDescription;
    private T payload;

    public ResponseRes() {
	}


	public ResponseRes(Integer respCode) {
		super();
		this.respCode = respCode;
	}

	public ResponseRes(Integer respCode, String respStatus) {
		super();
		this.respCode = respCode;
		this.respStatus = respStatus;
	}


	public ResponseRes(Integer respCode, String respStatus, String respDescription) {
		super();
		this.respCode = respCode;
		this.respStatus = respStatus;
		this.respDescription = respDescription;
	}

	public ResponseRes(Integer respCode, String respStatus, String respDescription, T payload) {
		super();
		this.respCode = respCode;
		this.respStatus = respStatus;
		this.respDescription = respDescription;
		this.payload = payload;
	}


	public Integer getRespCode() {
		return respCode;
	}


	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}


	public String getRespStatus() {
		return respStatus;
	}


	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}


	public String getRespDescription() {
		return respDescription;
	}


	public void setRespDescription(String respDescription) {
		this.respDescription = respDescription;
	}


	public T getPayload() {
		return payload;
	}


	public void setPayload(T payload) {
		this.payload = payload;
	}




}
