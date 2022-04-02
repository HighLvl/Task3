package task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Map;

public final class OpenStreetMapXMLProcessor {
    private final XMLStreamReader xmlReader;
    private final Map<String, Integer> userToEditNumberMap;
    private final Map<String, Integer> tagKeyToNodeNumberMap;

    private final Logger logger = LogManager.getLogger(OpenStreetMapXMLProcessor.class);
    private int count = 0;

    public OpenStreetMapXMLProcessor(XMLStreamReader xmlReader,
                                     Map<String, Integer> userToEditNumberMap,
                                     Map<String, Integer> tagKeyToNodeNumberMap) {
        this.xmlReader = xmlReader;
        this.userToEditNumberMap = userToEditNumberMap;
        this.tagKeyToNodeNumberMap = tagKeyToNodeNumberMap;
    }

    private boolean isInnerTag = false;

    void process() throws XMLStreamException {
        while (xmlReader.hasNext()) {
            var event = xmlReader.next();
            if (isInnerTag) {
                processNodeChildTagEvent(event);
            } else {
                processNodeEvent(event);
            }
        }
    }

    private void processNodeChildTagEvent(int event) {
        switch (event) {
            case XMLStreamConstants.START_ELEMENT -> processTag();
            case XMLStreamConstants.END_ELEMENT -> {
                if ("node".equals(xmlReader.getLocalName())) {
                    isInnerTag = false;
                    if (++count % 100000 == 0) logger.info(count + " nodes processed");
                }
            }
        }
    }

    private void processTag() {
        incEntryValueWithKeyFromAttr(tagKeyToNodeNumberMap, "k");
    }


    private void processNodeEvent(int event) {
        if (event == XMLStreamConstants.START_ELEMENT && "node".equals(xmlReader.getLocalName())) {
            processNode();
            isInnerTag = true;
        }
    }

    private void processNode() {
        incEntryValueWithKeyFromAttr(userToEditNumberMap, "user");
    }

    private void incEntryValueWithKeyFromAttr(Map<String, Integer> map, String attrKey) {
        var key = xmlReader.getAttributeValue(null, attrKey);
        var nextNumber = map.getOrDefault(key, 0) + 1;
        map.put(key, nextNumber);
    }
}
