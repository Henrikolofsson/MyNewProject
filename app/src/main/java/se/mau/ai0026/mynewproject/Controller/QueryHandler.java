package se.mau.ai0026.mynewproject.Controller;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.Entities.User;

public class QueryHandler extends AsyncTask<String, String, Boolean> {
    private Controller controller;
    private DBAccess databaseAccess;
    private String userToAdd;
    private String password;
    public QueryState state;

    private static String fakeEntry1 = "2019-11-03";
    private static String fakeEntry2 = "2019-11-04";
    private static String fakeEntry3 = "2019-11-05";
    private static String fakeEntry4 = "2019-11-06";

    public QueryHandler(Controller controller, DBAccess dbAccess) {
        this.controller = controller;
        this.databaseAccess = dbAccess;
    }

    public QueryHandler(Controller controller, DBAccess dbAccess, String userToAdd, String password) {
        this.controller = controller;
        this.databaseAccess = dbAccess;
        this.userToAdd = userToAdd;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        switch(state) {
//---------------------------------------------------------------------------------
            case ADD_IF_NOT_EXIST:
                boolean userExist;
                String username = databaseAccess.getName(userToAdd);

                if(username == null) {
                    userExist = false;
                } else {
                    userExist = true;
                }

                if(!userExist) {
                    User user = new User(userToAdd, password);
                    databaseAccess.addUser(user);
                }
                return userExist;
//-----------------------------------------------------------------------------------
            case GET_USERS:
                List<User> users = databaseAccess.getUsers();
                for(int i = 0; i < users.size(); i++) {
                    Log.d("ASYNCTASK", users.get(i).getName());
                }
                break;
//-----------------------------------------------------------------------------------
            case USER_LOGON_ACCEPTED:
                int userId = databaseAccess.getUserId(userToAdd, password);
                boolean userCredentialsAccepted;
                if(userId == 0) {
                    userCredentialsAccepted = false;
                } else {
                    userCredentialsAccepted = true;
                    Log.d("USERID", String.valueOf(userId));
                    controller.setUserId(userId);
                }
                return userCredentialsAccepted;
//-----------------------------------------------------------------------------------
            case GET_USER_ENTRIES:
                List<DataStepModel> userEntryList = databaseAccess.getStepEntries(controller.getUserId());
                controller.setDataStepModelList(userEntryList);
                return true;
//-----------------------------------------------------------------------------------
            case ADD_USER_ENTRY:
                DataStepModel dataStepModel = new DataStepModel(controller.getUserId(), controller.getTodaysDate(), controller.getSteps());
                databaseAccess.addStepEntry(dataStepModel);
                controller.setDataStepModelList(databaseAccess.getStepEntries(controller.getUserId()));
                return true;
//-----------------------------------------------------------------------------------
            case UPDATE_USER_STEPS:
                databaseAccess.incrementSteps(controller.getUserId(), controller.getTodaysDate());
                return true;
//-----------------------------------------------------------------------------------
            case GET_USER_STEPS:
                controller.setTotalSteps(databaseAccess.getSteps(controller.getUserId(), controller.getTodaysDate()));
                return true;
//-----------------------------------------------------------------------------------
            case ADD_FAKE_ENTRIES:
                DataStepModel dataStepModel2 = new DataStepModel(1, fakeEntry1, 9000);
                databaseAccess.addStepEntry(dataStepModel2);
                return true;

            case ADD_FAKE_ENTRIES2:
                DataStepModel dataStepModel3 = new DataStepModel(1, fakeEntry2, 12000);
                databaseAccess.addStepEntry(dataStepModel3);
                return true;

            case ADD_FAKE_ENTRIES3:
                DataStepModel dataStepModel4 = new DataStepModel(1, fakeEntry3, 8000);
                databaseAccess.addStepEntry(dataStepModel4);
                return true;

            case ADD_FAKE_ENTRIES4:
                DataStepModel dataStepModel5 = new DataStepModel(1, fakeEntry4, 7000);
                databaseAccess.addStepEntry(dataStepModel5);
                return true;


        }

       return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean userExist) {
        switch(state) {
            case ADD_IF_NOT_EXIST:
                if(!userExist) {
                    controller.userInsertedInDatabase(true);
                } else {
                    controller.userInsertedInDatabase(false);
                }
                break;

            case USER_LOGON_ACCEPTED:
                if(userExist) {
                    controller.logInSuccess(userToAdd);
                } else {
                    controller.logInFailure();
                }
                break;

            case GET_USER_ENTRIES:
                break;

            case ADD_USER_ENTRY:

                break;

        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

}

enum QueryState {
    ADD_IF_NOT_EXIST, GET_USERS, USER_LOGON_ACCEPTED, GET_USER_ENTRIES, ADD_USER_ENTRY, UPDATE_USER_STEPS, GET_USER_STEPS, ADD_FAKE_ENTRIES,
    ADD_FAKE_ENTRIES2, ADD_FAKE_ENTRIES3, ADD_FAKE_ENTRIES4,
    ADDSTEPS, RESETSTEPS, GETSTEPS, ADDUSER, GETUSERID;
}

