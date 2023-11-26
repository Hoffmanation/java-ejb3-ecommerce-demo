package com.mystuff.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystuff.obj.dto.ModelDtoObject;

@JsonInclude(JsonInclude .Include.NON_NULL)
public class WebResponse {
	private String message;
	private boolean succesfullOpt;
	private String additionalInfo;
	private ModelDtoObject modelDtoObject;

	public WebResponse() {
	}

	public WebResponse(String message, boolean flag, String additionalInfo) {
		super();
		this.message = message;
		this.succesfullOpt = flag;
		this.additionalInfo = additionalInfo;
	}

	public WebResponse(String message, boolean flag, ModelDtoObject modelDtoObject) {
		super();
		this.message = message;
		this.succesfullOpt = flag;
		this.modelDtoObject = modelDtoObject;
		this.additionalInfo = message;
	}

	public WebResponse(String message, boolean flag) {
		super();
		this.message = message;
		this.succesfullOpt = flag;
		this.additionalInfo = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccesfullOpt() {
		return succesfullOpt;
	}

	public void setSuccesfullOpt(boolean succesfullOpt) {
		this.succesfullOpt = succesfullOpt;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public ModelDtoObject getModelDtoObject() {
		return modelDtoObject;
	}

	public void setModelDtoObject(ModelDtoObject modelDtoObject) {
		this.modelDtoObject = modelDtoObject;
	}

}