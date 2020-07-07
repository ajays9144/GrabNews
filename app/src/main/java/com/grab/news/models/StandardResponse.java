package com.grab.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardResponse<T> {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("action_status")
    @Expose
    private ActionStatus actionStatus;
    @SerializedName("articles")
    @Expose
    private T response;
    @SerializedName("message")
    @Expose
    private String errorResponse;
    @SerializedName("totalResults")
    @Expose
    private int totalResult;

    public StandardResponse() {

    }

    public String getStatus() {
        return status;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public T getResponse() {
        return response;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }
}
