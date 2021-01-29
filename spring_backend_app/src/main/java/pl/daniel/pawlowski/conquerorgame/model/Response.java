package pl.daniel.pawlowski.conquerorgame.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Response {
    private int statusCode;
    private String responseMessage;
}
