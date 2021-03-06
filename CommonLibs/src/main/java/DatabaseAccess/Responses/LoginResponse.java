package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;

/**
 * Request: asks to login a user
 * Response: a string specifies the user permission {student, teacher, dean}
 *
 * status dictionary:
 *  0 - success
 *  1 - unauthorized access - user isn't logged in
 *  2 - username wasn't found
 *  3 - user is currently logged in from a different terminal"
 *  4 - wrong password
 */
public class LoginResponse extends DatabaseResponse {

    private final String permission;
    private final String name;

    // successful request
    public LoginResponse(int status, String permission, String name, DatabaseRequest request) {
        super(status, request);
        this.permission = permission;
        this.name = name;
    }

    // unsuccessful request
    public LoginResponse(int status, DatabaseRequest request) {
        super(status, request);
        this.permission = null;
        this.name = null;
    }
    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }
}