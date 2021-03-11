package com.example.myapplication;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//This is Saftey Kit page
public class MainFragment extends  Fragment{
    private  onFragmentBtnSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public  void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected)
        {
            listener = (onFragmentBtnSelected)context;
        }else{
            throw new ClassCastException(context.toString());
        }
    }
    public  interface  onFragmentBtnSelected{
        public  void  onButtonSelected();
    }
}
