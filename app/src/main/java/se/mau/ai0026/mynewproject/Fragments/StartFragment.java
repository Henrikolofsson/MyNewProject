package se.mau.ai0026.mynewproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Controller controller;
    private EditText et_username_login;
    private EditText et_password_login;
    private Button btn_login;
    private Button btn_create_user_login;
    private Button btn_generate;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void initializeComponents(View view) {
        et_username_login = (EditText) view.findViewById(R.id.et_username_login);
        et_password_login = (EditText) view.findViewById(R.id.et_password_login);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_create_user_login = (Button) view.findViewById(R.id.btn_create_user_login);

        btn_generate = (Button) view.findViewById(R.id.btn_generate);
    }

    private void registerListeners() {
        btn_login.setOnClickListener(new ButtonLogInListener());
        btn_create_user_login.setOnClickListener(new ButtonCreateUserListener());

        btn_generate.setOnClickListener(new ButtonGenerateListener());
    }

    private class ButtonLogInListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String username = et_username_login.getText().toString();
            String password = et_password_login.getText().toString();
            controller.logIn(username, password);

        }
    }

    private class ButtonCreateUserListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            controller.setCreateUserFragment();
        }
    }

    private class ButtonGenerateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.generateFakeEntries();
        }
    }

}
