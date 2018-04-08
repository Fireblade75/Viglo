package nl.saxion.viglo.component;

import nl.saxion.viglo.VigloParser;
import nl.saxion.viglo.component.expr.EmptyComponent;
import nl.saxion.viglo.type.TypeConverter;

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
        private String label;
        private String type;

        public ParamListItem(String label, String type) {
            this.label = label;
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}