package org.iesteis.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

    @JsonProperty("info")
    private Info info;

    @JsonProperty("results")
    private List<Character> results;

    public void setInfo(Info info){
        this.info = info;
    }

    public Info getInfo(){
        return info;
    }

    public void setResults(List<Character> results){
        this.results = results;
    }

    public List<Character> getResults(){
        return results;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "info = '" + info + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}