package com.example.qlsv.ui.Self;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlsv.R;
import com.example.qlsv.User;
import com.example.qlsv.databinding.FragmentSelfBinding;
import com.example.qlsv.ui.Test.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

public class SelfFragment extends Fragment {
    TextView birthdayTv;
    TextView majorTv;
    TextView addressTv;
    TextView classNameTv;
    TextView nameTv;
    TextView emailTv;
    private @NonNull FragmentSelfBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SelfViewModel studentSelfViewModel =
                new ViewModelProvider(this).get(SelfViewModel.class);

        binding = FragmentSelfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTv = root.findViewById(R.id.user_name);
        emailTv = root.findViewById(R.id.user_email);
        birthdayTv = root.findViewById(R.id.user_birthday);
        majorTv = root.findViewById(R.id.user_major);
        addressTv = root.findViewById(R.id.user_address);
        classNameTv = root.findViewById(R.id.user_class);
        getUser();
        return root;
    }

    private void getUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = MessageFormat.format("https://qlsv-api.onrender.com/api/user/detailuser/{0}", User.getId());

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject responseObj = response.getJSONObject(i);
                        String name = responseObj.getString("name");
                        String email = responseObj.getString("email");
                        String birthday = responseObj.getString("birthday");
                        String address = responseObj.getString("address");
                        String major = responseObj.getString("majorName");
                        String className = responseObj.getString("class");

                        nameTv.setText(name);
                        emailTv.setText(email);
                        birthdayTv.setText(birthday);
                        addressTv.setText(address);
                        majorTv.setText(major);
                        classNameTv.setText(className);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors
                Log.d("Test", error.toString());
            }
        });
        requestQueue.add(request);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}