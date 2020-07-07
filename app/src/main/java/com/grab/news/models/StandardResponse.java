package com.grab.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardResponse<T> {
    public final static StandardResponse EMPTY_VIEW = new StandardResponse("404", ActionStatus.ERROR, null, "Something Went Wrong");
    public final static StandardResponse UNAUTH_USER = new StandardResponse("401", ActionStatus.ERROR, null, "User Not Authorized");
    public final static StandardResponse FORBIDDEN_USER = new StandardResponse("403", ActionStatus.ERROR, null, "Request Forbidden");
    public final static StandardResponse INTERNAL_SERVER = new StandardResponse("500", ActionStatus.ERROR, null, "Internal Server Error");
    public final static StandardResponse BAD_REQUEST = new StandardResponse("400", ActionStatus.ERROR, null, "Bad Request");
    public final static StandardResponse NO_INTERNET_CONNECTION = new StandardResponse("0", ActionStatus.ERROR, null, "No Internet Connection");

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

    public StandardResponse(String status, ActionStatus actionStatus, T response, String errorResponse) {
        this.status = status;
        this.actionStatus = actionStatus;
        this.response = response;
        this.errorResponse = errorResponse;
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
