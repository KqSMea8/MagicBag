package org.github.helixcs.argtree.servlet;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**

 * Date 2018/2/10.
 */

class ArgTreeReflectGenerator {


    class CatNode<T extends CatNode> {
        private String name;
        private List<T> children;

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        public List<T> getChildren() {
            return children;
        }

        void setChildren(List<T> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "CatNode{" +
                    "name='" + name + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    @SuppressWarnings("unchecked")
    private List<CatNode> generatorChildrenToList(Class clazz) {
        List<CatNode> catNodeList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            CatNode catNode = new CatNode();
            //属性名称
            String nameString = field.getName();
            //属性类型
            String typeString = field.getType().getName();

            //catNode.setName("PN: " + nameString + "<--->" + "TS: " + typeString);
            catNode.setName(nameString);

            if (typeString.equalsIgnoreCase("java.util.List") && field.getGenericType() instanceof ParameterizedType) {
                catNode.setName("List<" + nameString + ">");

                CatNode catNode2 = new CatNode();
                ParameterizedType pt = (ParameterizedType) (field.getGenericType());
                Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                catNode2.setName("Single " + nameString);
                List<CatNode> catNodeListForGenericType = generatorChildrenToList(genericClazz);
                catNode2.setChildren(catNodeListForGenericType);

                List<CatNode> nodeListForGeneric = new ArrayList<>();
                nodeListForGeneric.add(catNode2);
                catNode.setChildren(nodeListForGeneric);
            }
            if (typeString.equals("java.util.Map") && field.getGenericType() instanceof ParameterizedType) {
                System.out.println();
            }

            String simpleTypeString = field.getType().getSimpleName();

            if (StringUtils.isBlank(type2String(simpleTypeString)) && !typeString.equalsIgnoreCase("java.util.List")) {
                catNode.setChildren(generatorChildrenToList(field.getType()));
            }
            catNodeList.add(catNode);
        }
        return catNodeList;
    }

    CatNode<CatNode> generatorToNode(Class clazz) {
        CatNode<CatNode> catNode = new CatNode<>();
        catNode.setName(clazz.getSimpleName());
        catNode.setChildren(generatorChildrenToList(clazz));
        return catNode;
    }

    /**
     * @param simpleName
     * @return
     */
    private String type2String(String simpleName) {
        switch (simpleName) {
            case "int":
                return "Int";
            case "Integer":
                return "Integer";
            case "long":
                return "long";
            case "Long":
                return "Long";
            case "byte":
                return "byte";
            case "Byte":
                return "Byte";
            case "float":
                return "float";
            case "Float":
                return "Float";
            case "Boolean":
                return "Boolean";
            case "boolean":
                return "boolean";
            case "char":
                return "char";
            case "Character":
                return "Character";
            case "String":
                return "String";
            case "Date":
                return "Date";
            case "BigInteger":
                return "BigInteger";
            case "[C":
                return "char []";
            case "[Ljava.lang.Integer;":
                return "Integer []";
            default:
                return "";
        }

    }

    private static ArgTreeReflectGenerator catReflectGenerator;

    private ArgTreeReflectGenerator() {
    }

    static ArgTreeReflectGenerator getInstance() {
        if (catReflectGenerator == null) {
            catReflectGenerator = new ArgTreeReflectGenerator();
        }
        return catReflectGenerator;
    }
}
