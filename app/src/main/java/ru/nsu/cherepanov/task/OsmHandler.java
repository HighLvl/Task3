package ru.nsu.cherepanov.task;

import ru.nsu.cherepanov.osm.model.generated.Node;

public interface OsmHandler {
    void handleNode(Node node);
}
