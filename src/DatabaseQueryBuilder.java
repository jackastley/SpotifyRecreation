public class DatabaseQueryBuilder {
    public String selectFromWhere(String[] select, String from, String[] where, String[] condition) {
        String query = "SELECT ";

        int count = 0;
        for (String s : select) {
            if (count == select.length - 1) {
                query += s;
            } else
                query += (s + ", ");
        }

        query += ("FROM " + from + " WHERE ");

        for (int i = 0; i < where.length; i++) {
            if (i == 0) {
                query += where[i] +
                        "=" +
                        "\"" +
                        condition[i] +
                        "\"";
            } else {
                query += " AND " +
                        where[i] +
                        "=" +
                        "\"" +
                        condition[i] +
                        "\"";
            }
        }
        return query;
    }

    public String insert(String table, String[] columns, String[] values) {
        String query = "INSERT INTO " + table + " (";

        int count = 0;
        for (String c : columns) {
            if (count == columns.length - 1) {
                query += c;
            } else
                query += (c + ", ");
            count++;
        }

        query += ") VALUES (";

        count = 0;
        for (String v : values) {
            if (count == values.length - 1) {
                query += "\"" + v + "\"";
            } else
                query += ("\"" + v + "\", ");
            count++;
        }
        return query + ")";
    }
}
