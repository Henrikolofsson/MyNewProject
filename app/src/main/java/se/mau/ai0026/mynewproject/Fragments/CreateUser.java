package se.mau.ai0026.mynewproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.Entities.User;
import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUser extends Fragment {
    private Controller controller;
    private EditText etUserName;
    private EditText userPassword;
    private Button btnCreateUser;


    public CreateUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        etUserName = (EditText) view.findViewById(R.id.et_user_name);
        userPassword = (EditText) view.findViewById(R.id.pw_user_name);
        btnCreateUser = (Button) view.findViewById(R.id.btn_create_user);
    }

    private void registerListeners() {
        btnCreateUser.setOnClickListener(new CreateUserListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class CreateUserListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = etUserName.getText().toString();
            String password = userPassword.getText().toString();

            if(username.length() > 0 && password.length() > 0) {
                controller.insertUserInDatabase(username, password);
                controller.setStartFragment();
            }
        }
    }
}
