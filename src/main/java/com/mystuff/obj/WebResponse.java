package com.mystuff.obj;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystuff.obj.dto.ModelDtoObject;

@JsonInclude(JsonInclude .Include.NON_NULL)
public class WebResponse {
	private String message;
	private boolean succesfullOpt;
	private String additionalInfo;
	private ModelDtoObject modelDtoObject;
	private List<? extends  ModelDtoObject> modelDtoList;

	public WebResponse() {
	}

	public WebResponse(String message, boolean succesfullOpt, String additionalInfo) {
		super();
		this.message = message;
		this.succesfullOpt = succesfullOpt;
		this.additionalInfo = additionalInfo;
	}

	public WebResponse(String message, boolean succesfullOpt, ModelDtoObject modelDtoObject) {
		super();
		this.message = message;
		this.succesfullOpt = succesfullOpt;
		this.modelDtoObject = modelDtoObject;
		this.additionalInfo = message;
	}
	
	public WebResponse(String message, boolean succesfullOpt, List<? extends  ModelDtoObject> modelDtoList) {
		super();
		this.message = message;
		this.succesfullOpt = succesfullOpt;
		this.modelDtoList = modelDtoList;
		this.additionalInfo = message;
	}

	public WebResponse(String message, boolean succesfullOpt) {
		super();
		this.message = message;
		this.succesfullOpt = succesfullOpt;
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

	public List<? extends ModelDtoObject> getModelDtoList() {
		return modelDtoList;
	}

	public void setModelDtoList(List<? extends ModelDtoObject> modelDtoList) {
		this.modelDtoList = modelDtoList;
	}
	
	

}