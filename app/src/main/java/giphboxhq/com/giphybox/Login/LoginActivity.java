package giphboxhq.com.giphybox.Login;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.R;

public class LoginActivity extends AppCompatActivity implements LoginView{
    private static final String TAG = "LoginActivity";

    @BindView(R.id.activity_login_username)
    EditText username;
    @BindView(R.id.activity_login_button)
    FloatingActionButton loginButton;
    @BindView(R.id.activity_login_form)
    View form;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this))
                .userComponent(((GiphyBoxApplication) getApplication()).getUserComponent())
                .build()
                .inject(this);
        presenter.onCreate();
    }

    @OnClick(R.id.activity_login_button)
    public void login(){
        if(username.getText().toString().isEmpty()){
            ((TextInputLayout) username.getParent().getParent()).setError("Please enter a username");
        }else{
            presenter.onLoginSelected(username.getText().toString());
        }
    }

    @Override
    public void showForm() {
        form.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToMainActivity() {
        setResult(RESULT_OK);
//        overridePendingTransition();
        finish();
    }
}
