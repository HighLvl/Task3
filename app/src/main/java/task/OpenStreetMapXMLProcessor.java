package task;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import osm.model.generated.Node;
import osm.model.generated.Tag;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.util.Map;

public final class OpenStreetMapXMLProcessor {
    private final XMLStreamReader xmlReader;
    private final Map<String, Integer> userToEditNumberMap;
    private final Map<String, Integer> tagKeyToNodeNumberMap;

    private final Logger logger = LogManager.getLogger(OpenStreetMapXMLProcessor.class);
    private final OsmHandler handler;
    private int count = 0;

    public OpenStreetMapXMLProcessor(XMLStreamReader xmlReader,
                                     Map<String, Integer> userToEditNumberMap,
                                     Map<String, Integer> tagKeyToNodeNumberMap,
                                     OsmHandler handler) {
        this.xmlReader = new XsiTypeReader(xmlReader);
        this.userToEditNumberMap = userToEditNumberMap;
        this.tagKeyToNodeNumberMap = tagKeyToNodeNumberMap;
        this.handler = handler;
    }

    void process() throws XMLStreamException, JAXBException {
        var unmarshaller = JAXBContext.newInstance(Node.class).createUnmarshaller();
        while (xmlReader.hasNext()) {
            var event = xmlReader.next();
            if (event == XMLStreamConstants.START_ELEMENT && "node".equals(xmlReader.getLocalName())) {
                var node = (Node) unmarshaller.unmarshal(xmlReader);
                processNode(node);
            }
        }
    }

    private void processNode(Node node) {
        handler.handleNode(node);
        incEntryValueWithKeyFromAttr(userToEditNumberMap, node.getUser());
        node.getTag().forEach(this::processTag);
        if (++count % 100000 == 0) logger.info(count + " nodes processed");
    }

    private void processTag(Tag tag) {
        incEntryValueWithKeyFromAttr(tagKeyToNodeNumberMap, tag.getK());
    }

    private void incEntryValueWithKeyFromAttr(Map<String, Integer> map, String key) {
        var nextNumber = map.getOrDefault(key, 0) + 1;
        map.put(key, nextNumber);
    }

    private static class XsiTypeReader extends StreamReaderDelegate {

        public XsiTypeReader(XMLStreamReader reader) {
            super(reader);
        }

        @Override
        public String getNamespacePrefix(int arg0) {
            return "http://openstreetmap.org/osm/0.6";
        }

        @Override
        public String getNamespaceURI(int arg0) {
            return "http://openstreetmap.org/osm/0.6";
        }

        @Override
        public String getNamespaceURI() {
            return "http://openstreetmap.org/osm/0.6";
        }


        @Override
        public String getNamespaceURI(String prefix) {
            return "http://openstreetmap.org/osm/0.6";
        }

    }
}
