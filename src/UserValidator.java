import java.sql.*;

public class UserValidator extends DatabaseConnection {

    Connection conn;

    public UserData getUserData(String username, String password) throws IllegalArgumentException {
        conn = openConnection();
        if (userExists(username) && passwordIsValid(password)) {
            ResultSet userResultSet = getUserResultSet(username);
            UserData userData = extractUserData(userResultSet);
            closeConnection(conn);
            return userData;
        } else {
            closeConnection(conn);
            throw new IllegalArgumentException("Username or password are incorrect");
        }
    }

    public boolean userExists(String username) {
        ResultSet userResultSet = getUserResultSet(username);
        try {
            if (userResultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private UserData extractUserData(ResultSet rs) {
        UserData userData = new UserData();

        try {
            rs.next();
            int userIDIndex = rs.findColumn("UserID");
            int firstNameIndex = rs.findColumn("FirstName");
            int lastNameIndex = rs.findColumn("LastName");
            int usernameIndex = rs.findColumn("UserName");

            userData.userID = rs.getInt(userIDIndex);
            userData.firstName = rs.getString(firstNameIndex);
            userData.lastName = rs.getString(lastNameIndex);
            userData.username = rs.getString(usernameIndex);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userData;
    }

    private ResultSet getUserResultSet(String username) {
        try {
            DatabaseQueryBuilder queryBuilder = new DatabaseQueryBuilder();
            String[] select = {"*"};
            String from = "Users";
            String[] where = {"UserName"};
            String[] condition = {username};
            String query = queryBuilder.selectFromWhere(select, from, where, condition);

            Statement statement = conn.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    private boolean passwordIsValid(String password) {
        //handle password validation with encryption
        return true;
    }

}
