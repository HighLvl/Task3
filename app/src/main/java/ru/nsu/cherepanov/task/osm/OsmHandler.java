package ru.nsu.cherepanov.task.osm;

import ru.nsu.cherepanov.osm.model.generated.Node;
import ru.nsu.cherepanov.osm.model.generated.Relation;
import ru.nsu.cherepanov.osm.model.generated.Way;

public interface OsmHandler {
    void handleNode(Node node);
    void handleRelation(Relation relation);
    void handleWay(Way way);
    void finish();
}
