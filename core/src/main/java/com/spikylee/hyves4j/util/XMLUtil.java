/*
 * Copyright 2008 Arthur Bogaart <spikylee at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spikylee.hyves4j.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author Arthur Bogaart
 */
public class XMLUtil {

    private XMLUtil() {
    }

    public static Collection<Node> getChildElements(Node node) {
        NodeList nodes = node.getChildNodes();
        if (nodes.getLength() == 0) {
            return null;
        } else {
            List<Node> elements = new ArrayList<Node>(nodes.getLength());
            for (int i = 0; i < nodes.getLength(); i++) {
                Node childNode = nodes.item(i);
                if (childNode instanceof Element) {
                    elements.add(childNode);
                }
            }
            return elements;
        }
    }

    /**
     * Get the text value for the specified element.  If the element is null or empty return null
     *
     * @param element The Element
     * @return The value String or null
     */
    public static String getValue(Element element) {
        if (element != null) {
            Node dataNode = element.getFirstChild();
            if (dataNode != null) {
                return ((Text) dataNode).getData();
            }
        }
        return null;
    }

    public static String getValue(Node node) {
        if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
            return node.getFirstChild().getNodeValue();
        }
        return null;
    }

    /**
     * Get the first child element with the given name.
     *
     * @param element The parent element
     * @param name The child element name
     * @return The child element or null
     */
    public static Element getChild(Element element, String name) {
        return (Element) element.getElementsByTagName(name).item(0);
    }

    public static Element getChild(Collection<Node> nodes, String name) {
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.getTagName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    public static Element getChild(NodeList nodes, String name) {
        for(int i=0; i<nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            if (element.getTagName().equals(name)) {
                return element;
            }
        }
        return null;
    }


    
    /**
     * Get the value of the fist child element with the given name.
     *
     * @param element The parent element
     * @param name The child element name
     * @return The child element value or null
     */
    public static String getChildValue(Element element, String name) {
        return getValue(getChild(element, name));
    }

    public static int getAttributeAsInt(Element el, String name) {
        String s = el.getAttribute(name);
        if (s != null && s.length() > 0) {
            return Integer.parseInt(s);
        }
        return 0;
    }

    public static boolean getAttributeAsBoolean(Element el, String name) {
        String s = el.getAttribute(name);
        if (s == null || "0".equals(s)) {
            return false;
        }
        if ("1".equals(s)) {
            return true;
        }
        return Boolean.valueOf(s);
    }
    
    public static String prettyPrintXML(Node node) {
    	StringBuffer buf = new StringBuffer();
    	prettyPrintXML(node, buf, 0);
    	return buf.toString();
    }
    
    public static void prettyPrintXML(Node node, StringBuffer sb, int depth) {
        String prefix = "";
        for(int i=0; i<depth;i++)
            prefix += "  ";
        sb.append(prefix);
        sb.append("<");
        sb.append(node.getNodeName());
        sb.append(">");
        if(XMLUtil.getValue(node) != null) {
            sb.append("\n");
            sb.append(prefix);
            sb.append("  ");
            sb.append(XMLUtil.getValue(node));
        }
        sb.append("\n");
        if(node.hasChildNodes()) {
            Collection<Node> childNodes = XMLUtil.getChildElements(node);
            for(Node childNode : childNodes) {
                prettyPrintXML(childNode, sb, depth+1);
            }
        }
        sb.append(prefix);
        sb.append("</");
        sb.append(node.getNodeName());
        sb.append(">\n");
    }

	public static int getChildValueAsInt(Element node, String string) {
		try {
			Integer newInt = new Integer(getChildValue(node, string));
			return newInt.intValue();
		} catch (NumberFormatException e) {
			return -1;
		}
	}

}
