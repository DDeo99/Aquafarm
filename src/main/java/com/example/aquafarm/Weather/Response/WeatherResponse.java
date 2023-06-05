package com.example.aquafarm.Weather.Response;

public class WeatherResponse {
    private Response response;

    public WeatherResponse() {
        this.response = new Response();
        this.response.setBody(new Body());
        this.response.getBody().setItem(new com.example.aquafarm.Weather.Response.Item());
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private Body body;

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    public static class Body {
        private com.example.aquafarm.Weather.Response.Item item;

        public com.example.aquafarm.Weather.Response.Item getItem() {
            return item;
        }

        public void setItem(com.example.aquafarm.Weather.Response.Item item) {
            this.item = item;
        }
    }

    // 추가된 getStatus() 메서드
    public String getStatus() {
        return response.getBody().getItem().getStatus();
    }
}
