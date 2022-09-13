package data;

import java.util.List;

public class RequestBuilder {
    public RequestBuilder(final String TableName){
        this.TableName = TableName;
    }

    public static boolean ismatched(String s){
        return s.matches("[a-zA-Z0-9 -]*");
    }

    public String buildSelect(List<String> columnNames){
        for (var col : columnNames){
            if (!ismatched(col)) {
                return null;
            }
        }
        var reqselect = new StringBuilder("SELECT ");
        if (columnNames.size() == 0){
            reqselect.append("*");
        }
        else{
            reqselect.append(columnNames.get(0));
            for (int i = 1;i < columnNames.size();i++){
                reqselect.append(", ");
                reqselect.append(columnNames.get(i));
            }
        }
        reqselect.append(" FROM ");
        reqselect.append(this.getTableName());
        reqselect.append(";");
        return new String(reqselect);
    }

    public String buildInsert(String columnName, List<String> toInsert){
        if (!ismatched(columnName)) {
            return null;
        }
        for (var elt : toInsert){
            if (!ismatched(elt)) {
                return null;
            }
        }
        var reqinsert = new StringBuilder("INSERT INTO ");
        reqinsert.append(this.getTableName());
        reqinsert.append(" (");
        reqinsert.append(columnName);
        reqinsert.append(") ");
        reqinsert.append("VALUES");
        if (toInsert.size() != 0) {
            reqinsert.append(" ('");
            reqinsert.append(toInsert.get(0));
            reqinsert.append("')");
            for (int i = 1; i < toInsert.size(); i++) {
                reqinsert.append(",");
                reqinsert.append(" ('");
                reqinsert.append(toInsert.get(i));
                reqinsert.append("')");
            }
        }
        else {
            return null;
        }
        reqinsert.append(";");
        return new String(reqinsert);
    }

    public String buildUpdate(String columnName, String newValue,
                              List<String> conditions){
        if (!ismatched(columnName)) {
            return null;
        }
        if (!ismatched(newValue)) {
            return null;
        }
        for (var elt : conditions){
            if (!elt.matches("([a-zA-Z0-9-]*)='([a-zA-Z0-9 -]*)'")) {
                return null;
            }
        }
        var requpdate = new StringBuilder("UPDATE ");
        requpdate.append(this.getTableName());
        requpdate.append(" SET ");
        requpdate.append(columnName);
        requpdate.append("='");
        requpdate.append(newValue);
        requpdate.append("'");
        int len = conditions.size();
        if (len != 0){
            requpdate.append(" WHERE ");
            requpdate.append(conditions.get(0));
            for (int i = 1;i < len;i++){
                requpdate.append(" AND ");
                requpdate.append(conditions.get(i));
            }
        }
        requpdate.append(";");
        return new String(requpdate);
    }

    public String getTableName(){
        return this.TableName;
    }

    public void setTableName(String TableName){
        this.TableName = TableName;
    }

    private String TableName;
}
