package nl.firepy.viglo.component;

import nl.firepy.viglo.compiler.VigloParser;
import nl.firepy.viglo.component.expr.EmptyComponent;
import nl.firepy.viglo.type.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class ParamList extends EmptyComponent {

    private ArrayList<ParamListItem> params = new ArrayList<>();

    public ParamList(List<VigloParser.ParamItemContext> paramItemContexts) {
        for(VigloParser.ParamItemContext item : paramItemContexts) {
            params.add(new ParamListItem(item.NAME().getText(), item.type().getText()));
        }
    }

    public String asJasminList() {
        StringBuilder sb = new StringBuilder();
        for(ParamListItem item : params) {
            sb.append(TypeConverter.rawToJasmin(item.type));
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public ArrayList<ParamListItem> items() {
        return params;
    }

    public class ParamListItem {
        private String name;
        private String type;

        public ParamListItem(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
