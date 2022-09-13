package RequestBuilder;

import java.util.List;

public class RequestBuilder {
    public RequestBuilder(final String TableName){
        this.TableName = TableName;
    }

    public String buildSelect(List<String> columnNames){
        var reqselect = new StringBuilder("SELECT ");
        if (columnNames.size() == 0){
            reqselect.append("* ");
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
        var res = new String(reqselect);
        return res;
    }

    public String buildInsert(String columnName, List<String> toInsert){
        var reqinsert = new StringBuilder("INSERT INTO ");
        reqinsert.append(this.getTableName());
        reqinsert.append(" (");
        reqinsert.append(columnName);
        reqinsert.append(") ");
        reqinsert.append("VALUES");
        reqinsert.append(" (");
        reqinsert.append(toInsert.get(0));
        reqinsert.append(")");
        for (int i = 1;i < toInsert.size();i++){
            reqinsert.append(",");
            reqinsert.append(" (");
            reqinsert.append(toInsert.get(i));
            reqinsert.append(")");
        }
        reqinsert.append(";");
        var res = new String(reqinsert);
        return res;
    }

    public String buildUpdate(String columnName, String newValue,
                              List<String> conditions){
        var requpdate = new StringBuilder("UPDATE ");
        requpdate.append(this.getTableName());
        requpdate.append(" SET");
        requpdate.append("<column>=");
        requpdate.append(newValue);
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
        var res = new String(requpdate);
        return res;
    }

    public String getTableName(){
        return this.TableName;
    }

    public void setTableName(String TableName){
        this.TableName = TableName;
    }

    private String TableName;
}
