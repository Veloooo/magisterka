package pl.daniel.pawlowski.conquerorgame.model.battle.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultReceiveDamageStrategy.class, name = "Default")
}
)
public interface UnitReceiveDamageStrategy {

    void receiveDamage(Unit unit, double damageToReceive);
}
