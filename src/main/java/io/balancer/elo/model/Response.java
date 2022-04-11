package io.balancer.elo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)//if something is null, just dont return it
public class Response {

    Response(LocalDateTime timeStampVal, int statusCodeVal, HttpStatus statusVal, String reasonVal, String messageVal, String developerMessageVal, Map<?,?> dataVal){
        this.timeStamp = timeStampVal;
        this.statusCode = statusCodeVal;
        this.status = statusVal;
        this.reason = reasonVal;
        this.message = messageVal;
        this.developerMessage = developerMessageVal;
        this.data = dataVal;
    }

    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?,?> data;

    

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<T>();
    }
    public static class ResponseBuilder<T> {
        ResponseBuilder(){};
        protected LocalDateTime timeStamp;
        protected int statusCode;
        protected HttpStatus status;
        protected String reason;
        protected String message;
        protected String developerMessage;
        protected Map<?,?> data;

        public ResponseBuilder timeStamp(LocalDateTime timeStampV) {
            this.timeStamp = timeStampV;
            return this;
        }
        public ResponseBuilder statusCode(int statusCodeV) {
            this.statusCode = statusCodeV;
            return this;
        }
        public ResponseBuilder status(HttpStatus statusV) {
            this.status = statusV;
            return this;
        }
        public ResponseBuilder reason(String reasonV) {
            this.reason = reasonV;
            return this;
        }
        public ResponseBuilder message(String messageV) {
            this.message = messageV;
            return this;
        }
        public ResponseBuilder developerMessage(String developerMessageV) {
            this.developerMessage = developerMessageV;
            return this;
        }
        public ResponseBuilder data(Map<?,?> dataV) {
            this.data = dataV;
            return this;
        }

        

        public Response build() {
            return new Response(timeStamp, statusCode, status, reason, message, developerMessage, data);
        }
    }
}
