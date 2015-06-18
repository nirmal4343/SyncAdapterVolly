package com.pryamid.contentprovider.model;

import android.graphics.Bitmap;

public class Status {

    private String status;

	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static final String STATUS_SUCCESS = "success";

    public static final String STATUS_FAIL = "error";

}
