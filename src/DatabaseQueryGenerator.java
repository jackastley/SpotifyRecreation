public class DatabaseQueryGenerator {
    public static String selectFromWhere(String[] select, String from, String[] where, String[] condition) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT ");

        int count = 0;
        for (String s : select) {
            if (count == select.length - 1) {
                stringBuilder.append(s);
            } else
                stringBuilder.append(s + ", ");
        }

        stringBuilder.append("FROM " + from + " WHERE ");

        for (int i = 0; i < where.length; i++) {
            if (i == 0) {
                stringBuilder.append(where[i] +
                        "=" +
                        "\"" +
                        condition[i] +
                        "\"");
            } else {
                stringBuilder.append(" AND " +
                        where[i] +
                        "=" +
                        "\"" +
                        condition[i] +
                        "\"");
            }
        }
        return stringBuilder.toString();
    }

    public static String insert(String table, String[] columns, String[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + table + " (");

        int count = 0;
        for (String c : columns) {
            if (count == columns.length - 1) {
                stringBuilder.append(c);
            } else
                stringBuilder.append(c + ", ");
            count++;
        }

        stringBuilder.append(") VALUES (");

        count = 0;
        for (String v : values) {
            if (count == values.length - 1) {
                stringBuilder.append(("\"" + v + "\""));
            } else
                stringBuilder.append("\"" + v + "\", ");
            count++;
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
