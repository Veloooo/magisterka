package pl.daniel.pawlowski.conquerorgame.model.battle.attack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.daniel.pawlowski.conquerorgame.model.battle.Unit;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultAttackStrategy.class, name = "Default"),
        @JsonSubTypes.Type(value = RangedAttackStrategy.class, name = "Ranged") }
)
public interface UnitAttackStrategy {
    Attack attackUnit(Unit unit);

    default Unit getAttackedUnit(List<Unit> units){
        List<Unit> defaultUnits = units.stream().filter(unit -> unit.getUnitAttackStrategy() instanceof DefaultAttackStrategy).collect(Collectors.toList());
        if(defaultUnits.size() > 0)
            return defaultUnits.get((int)(Math.random() * defaultUnits.size()));
        else
            return units.get((int)(Math.random() * units.size()));
    }
}
