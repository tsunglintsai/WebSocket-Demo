/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pyrogusto.websocket;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Dani
 */
public class Figure {

    private JsonObject json;
    private float x;
    private float y;

    public Figure(JsonObject json) {
        this.json = json;
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(json);
        return writer.toString();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }
}
